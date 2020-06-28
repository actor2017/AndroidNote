package cn.mobilesafe12.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.mobilesafe12.R;
import cn.mobilesafe12.bean.ProcessInfo;
import cn.mobilesafe12.engine.ProcessInfoProvider;
import cn.mobilesafe12.global.GlobalConstants;
import cn.mobilesafe12.service.AutoKillService;
import cn.mobilesafe12.utils.PrefUtils;
import cn.mobilesafe12.utils.ServiceStatusUtils;
import cn.mobilesafe12.utils.ToastUtils;
import cn.mobilesafe12.view.ProgressView;
import cn.mobilesafe12.view.SettingItemView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * 进程管理
 * <p>
 * 项目要使用第三方的代码
 * 1. 在libs中拷贝jar包, 无法看源码, 也不能使用资源文件xml,图片
 * 2. 在gradle文件的dependencies中配置项目包名, 无法看源码, 但能够同时访问资源文件和图片
 * 3. 将第三方代码先以module形式导入进来, 然后关联在一起, 可以看源码, 也能够同时访问资源文件和图片
 * <p>
 * 将ListView改造成粘性ListView:
 * <p>
 * 1. 导入粘性ListView的module, 然后和当前项目关联
 * 2. 修改布局文件, 将ListVIew改为StickyListHeadersListView
 * 3. 原来的adapter实现StickyListHeadersAdapter接口
 * 4. 重写adapter的两个方法getHeaderView(返回标题栏布局), getHeaderId(返回标题栏id)
 * <p>
 * ListView条目多选效果实现流程:
 * 1. 给ProcessInfo添加一个boolean属性isChecked,标记当前对象的选中状态
 * 2. 在getView中,根据对象的isChecked来更新Checkbox的选中状态
 * 3. 禁用原生Checkbox的点击事件, 从而可以让整个条目响应点击
 * 4. 监听条目点击事件, 修改对象和Checkbox的选中状态
 * <p>
 * 跳过手机卫士:
 * 1. getView中不显示手机卫士的勾选框
 * 2. 条目点击时效果
 * 3.全选&反选跳过
 */
public class ProcessManagerActivity extends AppCompatActivity {

    @BindView(R.id.pv_process_num)
    ProgressView pvProcessNum;
    @BindView(R.id.pv_memory)
    ProgressView pvMemory;
    @BindView(R.id.lv_list)
    StickyListHeadersListView lvList;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.iv_arrow1)
    ImageView ivArrow1;
    @BindView(R.id.iv_arrow2)
    ImageView ivArrow2;
    @BindView(R.id.drawer)
    SlidingDrawer mDrawer;
    @BindView(R.id.siv_show_system)
    SettingItemView sivShowSystem;
    @BindView(R.id.siv_auto_kill)
    SettingItemView sivAutoKill;

    private ArrayList<ProcessInfo> mList;//大集合
    private ArrayList<ProcessInfo> userList;//用户集合
    private ArrayList<ProcessInfo> systemList;//系统集合

    private ProcessAdapter mAdapter;

    private int runningProcessNum;
    private long availMemory;
    private int totalProcessNum;
    private long totalMemory;
    private boolean isShowSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_manager);
        ButterKnife.bind(this);

        pvProcessNum.setTitle("进程数:");
        pvMemory.setTitle("内    存:");

        //运行进程数
        runningProcessNum = ProcessInfoProvider.getRunningProcessNum(this);
        //总进程数
        totalProcessNum = ProcessInfoProvider.getTotalProcessNum(this);
        //可用内存
        availMemory = ProcessInfoProvider.getAvailMemory(this);
        //总内存
        totalMemory = ProcessInfoProvider.getTotalMemory(this);

        initInfo();
        initData();

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProcessInfo info = mList.get(position);//当前被点击的对象

                //跳过手机卫士
                if (info.packageName.equals(getPackageName())) {
                    return;
                }

                info.isChecked = !info.isChecked;//更新对象

                //更新checkbox
                CheckBox cbCheck = (CheckBox) view.findViewById(R.id.cb_check);
                cbCheck.setChecked(info.isChecked);
            }
        });

        initArrowAnim();

        //监听抽屉打开和关闭的事件
        mDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                //抽屉被打开
                ivArrow1.setImageResource(R.drawable.drawer_arrow_down);
                ivArrow2.setImageResource(R.drawable.drawer_arrow_down);

                //停止动画
                ivArrow1.clearAnimation();
                ivArrow2.clearAnimation();
            }
        });

        mDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                //抽屉关闭
                ivArrow1.setImageResource(R.drawable.drawer_arrow_up);
                ivArrow2.setImageResource(R.drawable.drawer_arrow_up);

                initArrowAnim();
            }
        });

        //获取当前是否显示系统进程的状态
        isShowSystem = PrefUtils.getBoolean(this, GlobalConstants.PREF_SHOW_SYSTEM, true);
        //更新开关
        sivShowSystem.setToggleOn(isShowSystem);
    }

    //初始化箭头动画
    private void initArrowAnim() {
        //渐变动画
        AlphaAnimation anim1 = new AlphaAnimation(0.2f, 1);
        anim1.setDuration(500);
        anim1.setRepeatCount(Animation.INFINITE);
        anim1.setRepeatMode(Animation.REVERSE);

        ivArrow1.startAnimation(anim1);

        //渐变动画
        AlphaAnimation anim2 = new AlphaAnimation(1, 0.2f);
        anim2.setDuration(500);
        anim2.setRepeatCount(Animation.INFINITE);
        anim2.setRepeatMode(Animation.REVERSE);

        ivArrow2.startAnimation(anim2);
    }

    private void initData() {
        llLoading.setVisibility(View.VISIBLE);

        new Thread() {
            @Override
            public void run() {
                //获取正在运行的进程集合
                mList = ProcessInfoProvider.getRunningProcess
                        (getApplicationContext());

                userList = new ArrayList<ProcessInfo>();
                systemList = new ArrayList<ProcessInfo>();

                for (ProcessInfo processInfo : mList) {
                    if (processInfo.isUserProcess) {
                        userList.add(processInfo);
                    } else {
                        systemList.add(processInfo);
                    }
                }

                mList.clear();
                mList.addAll(userList);
                mList.addAll(systemList);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new ProcessAdapter();
                        lvList.setAdapter(mAdapter);

                        llLoading.setVisibility(View.GONE);
                    }
                });

            }
        }.start();
    }

    //初始化进程和内存信息
    private void initInfo() {
        //初始化进程信息
        //正在运行进程数
        pvProcessNum.setLeftText("正在运行:" + runningProcessNum + "个");
        pvProcessNum.setRightText("可有进程:" + totalProcessNum + "个");

        int percent = runningProcessNum * 100 / totalProcessNum;
        pvProcessNum.setProgress(percent);

        //初始化内存信息
        long usedMemory = totalMemory - availMemory;//已用内存

        pvMemory.setLeftText("已用内存:" + Formatter.formatFileSize(this, usedMemory));
        pvMemory.setRightText("剩余内存:" + Formatter.formatFileSize(this, availMemory));
        int memPrecent = (int) (usedMemory * 100 / totalMemory);
        pvMemory.setProgress(memPrecent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //回显锁屏自动清理开关
        boolean serviceRunning = ServiceStatusUtils.isServiceRunning(this, AutoKillService.class);
        sivAutoKill.setToggleOn(serviceRunning);
    }

    @OnClick({R.id.btn_clean, R.id.btn_select_all, R.id.btn_reverse_select, R.id.siv_show_system,
            R.id.siv_auto_kill})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clean:
                //一键清理 <uses-permission android:name="android.permission
                // .KILL_BACKGROUND_PROCESSES"/>
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

                //并发修改异常: 遍历集合的时候修改集合元素数量
                ArrayList<ProcessInfo> killList = new ArrayList<>();//待删除的对象集合
                for (ProcessInfo processInfo : mList) {
                    if (processInfo.isChecked) {
                        //杀死后台进程
                        am.killBackgroundProcesses(processInfo.packageName);
                        //mList.remove(processInfo);
                        killList.add(processInfo);//
                    }
                }

                mList.removeAll(killList);//移除所有待删除集合

                //注意: 一定也要更新一下两个子集合, 否则用户进程和系统进程的数量显示有问题
//                for (ProcessInfo info : killList) {
//                    if(info.isUserProcess) {
//                        userList.remove(info);
//                    }else {
//                        systemList.remove(info);
//                    }
//                }
                //1,2,3,4,5,6,,,,,,4,5,6,7,8,9
                //1,2,3
                userList.removeAll(killList);
                systemList.removeAll(killList);

                mAdapter.notifyDataSetChanged();


                int savedMemory = 0;//总共节省的内存
                for (ProcessInfo info : killList) {
                    savedMemory += info.memory;
                }

                //给用户友好提示
//                ToastUtils.show(this, "帮您杀死了" + killList.size() + "个进程, 共节省" + Formatter
//                        .formatFileSize(this, savedMemory) + "内存");
                String toast = String.format("帮您杀死了%d个进程,共节省%s内存", killList.size(), Formatter
                        .formatFileSize(this, savedMemory));
                ToastUtils.show(this, toast);

                //更新进程数和内存
                runningProcessNum -= killList.size();
                availMemory += savedMemory;

                initInfo();

                break;
            case R.id.btn_select_all:
                //全选
                //1. 遍历集合, 将所有的对象的isChecked置为true
                //2. 刷新LIstView
                for (ProcessInfo processInfo : mList) {
                    //跳过手机卫士
                    if (processInfo.packageName.equals(getPackageName())) {
                        continue;
                    }

                    processInfo.isChecked = true;
                }

                mAdapter.notifyDataSetChanged();

                break;
            case R.id.btn_reverse_select:
                //反选
                for (ProcessInfo processInfo : mList) {
                    //跳过手机卫士
                    if (processInfo.packageName.equals(getPackageName())) {
                        continue;
                    }

                    processInfo.isChecked = !processInfo.isChecked;
                }

                mAdapter.notifyDataSetChanged();
                break;
            case R.id.siv_show_system:
                //显示和隐藏系统进程
                isShowSystem = !isShowSystem;

                PrefUtils.putBoolean(this, GlobalConstants.PREF_SHOW_SYSTEM, isShowSystem);

                sivShowSystem.setToggleOn(isShowSystem);

                //刷新ListView, 在getCount中修改条目个数, 并使之生效
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.siv_auto_kill:
                //锁屏自动清理
                if (ServiceStatusUtils.isServiceRunning(this, AutoKillService.class)) {
                    stopService(new Intent(this, AutoKillService.class));
                    sivAutoKill.setToggleOn(false);
                } else {
                    startService(new Intent(this, AutoKillService.class));
                    sivAutoKill.setToggleOn(true);
                }
                break;
        }
    }

    class ProcessAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        @Override
        public int getCount() {
            if (isShowSystem) {
                //显示系统进程
                return mList.size();
            } else {
                return userList.size();//只返回用户集合的大小
            }
        }

        @Override
        public ProcessInfo getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(ProcessManagerActivity.this, R.layout
                        .item_processinfo, null);

                holder = new ViewHolder(convertView);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ProcessInfo info = getItem(position);
            holder.tvName.setText(info.name);
            holder.ivIcon.setImageDrawable(info.icon);
            holder.tvMemory.setText(Formatter.formatFileSize(getApplicationContext(), info.memory));

            //手机卫士不显示勾选框
            if (info.packageName.equals(getPackageName())) {
                holder.cbCheck.setVisibility(View.GONE);
            } else {
                holder.cbCheck.setVisibility(View.VISIBLE);
            }

            //更新勾选框
            holder.cbCheck.setChecked(info.isChecked);

            return convertView;
        }

        //返回头布局, 标题栏布局
        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            TitleHolder holder;
            if (convertView == null) {
                convertView = View.inflate(ProcessManagerActivity.this, R.layout.item_title, null);
                holder = new TitleHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (TitleHolder) convertView.getTag();
            }

            ProcessInfo info = getItem(position);

            if (info.isUserProcess) {
                //用户进程
                holder.tvTitle.setText("用户进程(" + userList.size() + ")");
            } else {
                //系统进程
                holder.tvTitle.setText("系统进程(" + systemList.size() + ")");
            }

            return convertView;
        }

        //返回标题栏布局的id
        //用户进程0, 系统进程1
        @Override
        public long getHeaderId(int position) {
            ProcessInfo info = getItem(position);
            return info.isUserProcess ? 0 : 1;
        }
    }
}
