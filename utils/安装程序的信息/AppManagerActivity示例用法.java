package cn.mobilesafe12.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.mobilesafe12.R;
import cn.mobilesafe12.bean.AppInfo;
import cn.mobilesafe12.engine.AppInfoProvider;
import cn.mobilesafe12.utils.ToastUtils;
import cn.mobilesafe12.view.ProgressView;

import static android.R.id.list;

/**
 * 软件管理
 * <p>
 * 复杂ListView展示-->同时展示多种类型布局
 * <p>
 * 1. 调整大集合, 添加两个标题栏对象, 重新整理集合对象顺序
 * 2. 重写getViewTypeCount返回布局类型个数
 * 3. 重写getItemViewType返回当前条目类型
 * 4. 在getView方法中根据不同类型加载不同的布局文件
 */
public class AppManagerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.pv_rom)
    ProgressView pvRom;
    @BindView(R.id.pv_sdcard)
    ProgressView pvSdcard;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.tv_show_title)
    TextView tvTitle;//常驻的浮窗

    //ListView条目的布局类型
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_TITLE = 1;

    private ArrayList<AppInfo> mList;//所有已安装app集合
    private AppInfoAdapter mAdapter;
    private ArrayList<AppInfo> userList;
    private ArrayList<AppInfo> systemList;
    private PopupWindow mPopup;
    private AppInfo mCurrentInfo;//当前被点击的对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        ButterKnife.bind(this);

        pvRom.setTitle("内部存储:");
        pvSdcard.setTitle("外部存储:");

        initSpaceInfo();

        //initData();

        lvList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                //firstVisibleItem: 当前ListView显示的第一个条目的位置
                if (userList != null && systemList != null) {
                    if (firstVisibleItem > userList.size()) {
                        //系统应用
                        tvTitle.setText("系统应用(" + systemList.size() + ")");
                    } else {
                        //用户应用
                        tvTitle.setText("用户应用(" + userList.size() + ")");
                    }
                }
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view: 当前被点击的条目的布局对象
                mCurrentInfo = mList.get(position);
                if (!mCurrentInfo.isTitle) {
                    showPopup(view);
                }
            }
        });
    }

    //显示小弹窗
    private void showPopup(View itemView) {
        if (mPopup == null) {
            View view = View.inflate(this, R.layout.popup_appinfo, null);

            view.findViewById(R.id.tv_uninstall).setOnClickListener(this);
            view.findViewById(R.id.tv_open).setOnClickListener(this);
            view.findViewById(R.id.tv_share).setOnClickListener(this);
            view.findViewById(R.id.tv_info).setOnClickListener(this);

            //宽高包裹内容
            mPopup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT, true);

            mPopup.setBackgroundDrawable(new ColorDrawable());

            //设置动画样式
            mPopup.setAnimationStyle(R.style.PopupAnim);
        }

        mPopup.showAsDropDown(itemView, 70, -itemView.getHeight());
    }

    private void initData() {
        llLoading.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                mList = AppInfoProvider.getInstalledApps
                        (getApplicationContext());

                System.out.println("已安装app:" + list);

                //用户应用
                userList = new ArrayList<AppInfo>();
                //系统应用
                systemList = new ArrayList<AppInfo>();

                for (AppInfo appInfo : mList) {
                    if (appInfo.isUserApp) {
                        userList.add(appInfo);
                    } else {
                        systemList.add(appInfo);
                    }
                }

                //重新给大集合添加数据
                mList.clear();//清空当前集合

                //1. 先添加用户应用标题栏
                AppInfo title1 = new AppInfo();
                title1.isTitle = true;
                title1.title = "用户应用";
                mList.add(title1);

                //2. 再添加用户应用集合
                mList.addAll(userList);

                //3. 再添加系统应用标题栏
                AppInfo title2 = new AppInfo();
                title2.isTitle = true;
                title2.title = "系统应用";
                mList.add(title2);

                //4. 最后添加系统应用集合
                mList.addAll(systemList);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //给ListView设置数据
                        mAdapter = new AppInfoAdapter();
                        lvList.setAdapter(mAdapter);

                        llLoading.setVisibility(View.GONE);
                    }
                });
            }
        }.start();
    }

    //一旦activity启动, 或者从后台又展示出来, 都重新初始化一遍数据
    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    //初始化空间信息
    private void initSpaceInfo() {
        //内部存储文件夹: /data
        File dataDirectory = Environment.getDataDirectory();
        long totalSpace = dataDirectory.getTotalSpace();//总空间
        long freeSpace = dataDirectory.getFreeSpace();//剩余空间
        long usedSpace = totalSpace - freeSpace;//已用空间

        //Formatter.formatFileSize: 可以将字节转为带单位的字符串
        pvRom.setLeftText(Formatter.formatFileSize(this, usedSpace) + "已用");
        pvRom.setRightText(Formatter.formatFileSize(this, freeSpace) + "可用");

        int progress = (int) (usedSpace * 100 / totalSpace);
        pvRom.setProgress(progress);

        //外部存储文件夹
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        long sdcardTotalSpace = externalStorageDirectory.getTotalSpace();//总空间
        long sdcardFreeSpace = externalStorageDirectory.getFreeSpace();//剩余空间
        long sdcardUsedSpace = sdcardTotalSpace - sdcardFreeSpace;//已用空间

        pvSdcard.setLeftText(Formatter.formatFileSize(this, sdcardUsedSpace) + "已用");
        pvSdcard.setRightText(Formatter.formatFileSize(this, sdcardFreeSpace) + "可用");

        int sdcardProgress = (int) (sdcardUsedSpace * 100 / sdcardTotalSpace);
        pvSdcard.setProgress(sdcardProgress);
    }

    @Override
    public void onClick(View v) {
        //弹窗消失
        mPopup.dismiss();

        switch (v.getId()) {
            case R.id.tv_uninstall:
                //卸载 发送隐式意图, 跳到系统卸载页面进行卸载
                if (mCurrentInfo.isUserApp) {
                    if (!mCurrentInfo.packageName.equals(getPackageName())) {
                        Uri packageURI = Uri.parse("package:" + mCurrentInfo.packageName);
                        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                        startActivity(uninstallIntent);
                    } else {
                        ToastUtils.show(this, "不能卸载当前应用!");
                    }
                } else {
                    ToastUtils.show(this, "不能卸载系统应用!");
                }

                break;
            case R.id.tv_open:
                //启动
                PackageManager pm = getPackageManager();

                //获取某个app的入口页面的intent
                Intent launchIntentForPackage = pm.getLaunchIntentForPackage(mCurrentInfo
                        .packageName);

                if (launchIntentForPackage != null) {
                    startActivity(launchIntentForPackage);
                } else {
                    ToastUtils.show(this, "无法启动该应用!");
                }

                break;
            case R.id.tv_share:
                //分享, 使用当前系统安装的可分享的应用程序来分享
                //原理: 发送隐式意图, 会展示系统弹窗, 用户选择要分享的应用后, 始终&仅此一次, 跳到该应用进行分享
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "发现了一个非常好的应用, 赶紧下载吧! 下载地址: https://play.google" +
                        ".com/store/apps/details?id=" + mCurrentInfo.packageName);
                intent.setType("text/plain");//分享类型, 纯文本类型
                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(intent);
                break;
            case R.id.tv_info:
                //信息: 隐式意图, 跳到系统应用信息页面
                //搜索技巧: 先百度->谷歌->换英文搜索
                //google.com
                //github.com
                //http://stackoverflow.com/
                Intent infoIntent = new Intent();
                infoIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mCurrentInfo.packageName, null);
                infoIntent.setData(uri);
                startActivity(infoIntent);
                break;

            default:
                break;
        }
    }
}
