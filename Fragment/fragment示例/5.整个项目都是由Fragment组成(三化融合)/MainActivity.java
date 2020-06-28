package com.ly.changyi.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.ly.changyi.R;
import com.ly.changyi.adapter.NoMoveFragmentPagerAdapter;
import com.ly.changyi.constant.Global;
import com.ly.changyi.constant.StaticClass;
import com.ly.changyi.fragment.main.CommunityFragment;
import com.ly.changyi.fragment.main.EarnPlayFragment;
import com.ly.changyi.fragment.main.HomeFragment;
import com.ly.changyi.fragment.main.MineFragment;
import com.ly.changyi.model.MessageEvent;
import com.ly.changyi.responseInfo.DictInfo;
import com.ly.changyi.responseInfo.UpDownMsgInfo;
import com.ly.changyi.service.SocketService;
import com.ly.changyi.utils.L;
import com.ly.changyi.utils.MyOkhttpUtils.MyOkHttpUtils;
import com.ly.changyi.utils.MyOkhttpUtils.OnBaseListener;
import com.ly.changyi.utils.SPUtils;
import com.ly.image.ImageConfig;
import com.ly.image.ImageSelector;
import com.ly.image.activity.ImageSelectorActivity;
import com.ly.image.utils.GlideImageLoader;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp)
    ViewPager    vp;
    @BindView(R.id.main_tab_layout)
    TabLayout    tabLayout;

    private int[] menuImgs = new int[]{R.drawable.tab_menu_0, R.drawable.tab_menu_1, R.drawable.tab_menu_2, R.drawable.tab_menu_3};
    private String[]   pageTitles = new String[]{"奇愣事", "拉马儿", "玩赚嗨", "我的"};
    private Fragment[] fragments = new Fragment[]{new HomeFragment(), new CommunityFragment(),
            new EarnPlayFragment(), new MineFragment()};


    //上一次添加进来的fragment索引值
    private int            preIndex     = -1;
    //event bus 消息体
    private MessageEvent   msgEvent;
    //单选/多选 图片选择配置
    private ImageConfig    configMuti, configSingle;
    //图片选择回调路径数组
    private ArrayList<String>   listPath = new ArrayList<>();
    //socket链接服务
    private Intent              intent;
    private MediaRecorderConfig mediaRecorderConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        vp.setAdapter(new NoMoveFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(vp);
        for (int i = 0; i < menuImgs.length; i++) {
            View view = LayoutInflater.from(activity).inflate(R.layout.menu_tab_layout, null, false);
            ImageView imageView = view.findViewById(R.id.img_menu_pic);
            TextView textView = view.findViewById(R.id.tv_menu_name);
            imageView.setImageResource(menuImgs[i]);
            textView.setText(pageTitles[i]);
            tabLayout.getTabAt(i).setCustomView(view);
        }

        ImageConfig.Builder build = new ImageConfig.Builder(
                new GlideImageLoader())
                .steepToolBarColor(getResources().getColor(R.color.agree_green))
                .titleBgColor(getResources().getColor(R.color.agree_green))
                .titleSubmitTextColor(getResources().getColor(R.color.colorWhite))
                .titleTextColor(getResources().getColor(R.color.colorWhite))
                .mutiSelectMaxSize(Global.IMAGE_SIZE)//最多选择6张照片
                .pathList(listPath)
                .showCamera()
                .requestCode(Global.REQUEST_IMAGE_CODE);//300

        intent = new Intent(this, SocketService.class);
        startService(intent);

//        getUserInfo();

        //获取上传下达消息，查看是否有未读消息
        getMessage();
        getDefaultInfo();
    }

    //供Fragment调用
    public void switchToPosition(int position){
        tabLayout.getTabAt(position).select();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Global.REQUEST_IMAGE_CODE:
                if (data != null) {
                    List<String> listFile = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                    if (listFile.size() > 0) {
                        msgEvent = new MessageEvent(Global.REQUEST_IMAGE_CODE, listFile.get(listFile.size() - 1), listFile);
                        EventBus.getDefault().post(msgEvent);
                    }
                }
                break;
        }
    }

    //添加到list里面的fragment
    private List<Fragment> listFragment = new ArrayList<>();
//    @Subscribe(threadMode = ThreadMode.MAIN)父类有注释
    @Override
    public void onNormalMessage(MessageEvent event) {
        super.onNormalMessage(event);
        switch (event.code) {
            case Global.ADD_FRAGMENT://添加fragment
                L.e(event.object.getClass().getName());
                if (listPath != null)//新加入fragment的时候情况当前选择的图片列表
                    listPath.clear();
                if (event.msg.equals("index")) {//登录的时候调用
                    listFragment.add((Fragment) event.object);
                    changeFragment(listFragment.size() - 1, null);
                    getHandler().postDelayed(() -> {
                        Fragment fragment = listFragment.remove(0);
                        //Fragment fragment = listFragment.remove(listFragment.size() - 1);
                        changeFragment(listFragment.size() - 1, fragment);
                    }, 2000);
                } else {//跳转页面调用
                    listFragment.add((Fragment) event.object);
                    changeFragment(listFragment.size() - 1, null);
                }
                break;
            case Global.REMOVE_FRAGMENT://移除fragment
                Fragment fragment = listFragment.remove(listFragment.size() - 1);
                changeFragment(listFragment.size() - 1, fragment);
                if (event.object != null && !TextUtils.isEmpty(event.object.getClass().getName())) {
                    listFragment.add((Fragment) event.object);
                    changeFragment(listFragment.size() - 1, null);
                }
                break;
            case Global.CHOSE_SINGLE_IMAGE://选择单张图片
                checkPermission(() ->
                                ImageSelector.open(MainActivity.this, configSingle), R.string
                                .ask_again,
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                );
                break;
            case Global.CHOSE_MUTI_IMAGE://选择多张图片
                checkPermission(() ->
                                ImageSelector.open(MainActivity.this, configMuti), R.string
                                .ask_again,
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                );
                break;
            case Global.DELETE_IMAGE://删除当前选择的图片
                if (listPath.size() > 0) {
                    listPath.remove(Integer.parseInt(event.object.toString()));
                }
                break;
            case Global.RESET_IMAGE://重置当前图片选择
                if (listPath != null)
                    listPath.clear();
                break;
//            case Global.SOCKET_RETRY:
//                stopService(intent);
//                getHandler().postDelayed(() -> startService(intent), 500);
//                break;
            case Global.HIDE_SOFT_INPUT://关闭软键盘
                hideKeyboard();
                break;
            case Global.CLEAR_CHOSE_IMAGE://清空当前选择图片
                listPath.clear();
                break;
            case Global.MEDIA_RECORDER://视频录制
                MediaRecorderActivity.goSmallVideoRecorder(activity,
                        SendSmallVideoActivity.class.getName(), mediaRecorderConfig);
                break;
            case Global.LOGIN_OUT://登出
                showToast("您的帐号在其它地方登录，请重新登录！", Toast.LENGTH_LONG);
                //退出环信
                EMClient.getInstance().logout(true);
                SPUtils.putString(Global.TOKEN, null);
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }.start();
                break;
            case Global.GET_SELECT_INFO://获取下拉选择信息
                getDefaultInfo();
                break;
            case Global.GET_NEW_MESSAGE://获取上传下达判断是否有未读消息
                getMessage();
                //测试没有新消息
//                StaticClass.isNewMessage=false;
//                EventBus.getDefault().post(new MessageEvent(Global.NEW_MESSAGE,"",""));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }

    /**
     * 选择切换fragment
     *
     * @param position
     * @param removeFragment 需要移除的Fragment
     */
    private void changeFragment(int position, Fragment removeFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (position < 0) {
            // TODO: 2019/1/27 测试在什么情况下会传负数
            throw new IndexOutOfBoundsException();
//            System.exit(0);
        } else {
            if (!listFragment.get(position).isAdded()) {//Fragment还未添加到Activity
                if (preIndex == -1) {
                    try {
                        transaction.add(R.id.main_activity, listFragment.get(position)).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    transaction.hide(listFragment.get(preIndex)).add(R.id.main_activity,
                            listFragment.get(position)).commit();
                }
            } else {
                if (removeFragment == null) {
                    transaction.hide(listFragment.get(preIndex)).show(listFragment.get(position))
                            .commit();
                } else {
                    transaction.remove(removeFragment).show(listFragment.get(position)).commit();
                }
            }
            preIndex = position;
            listFragment.get(position).setUserVisibleHint(true);//界面是否可见
        }
    }


    /**
     * 获取基础信息
     * 收入、学历、干部、政治面貌、工作地点... 等 下拉选择信息
     */
    private void getDefaultInfo() {
        MyOkHttpUtils.get(Global.GET_FIND_ALL, null, new OnBaseListener<DictInfo>() {
            @Override
            public void onOk(String response, int id, DictInfo dictInfo) {
                if (dictInfo != null && dictInfo.data != null) {
                    StaticClass.income = dictInfo.data.income;
                    StaticClass.education = dictInfo.data.education;
                    StaticClass.cadreType = dictInfo.data.cadreType;
                    StaticClass.nation = dictInfo.data.nation;
                    StaticClass.politic = dictInfo.data.politic;
                    StaticClass.worktype = dictInfo.data.worktype;
                    StaticClass.health = dictInfo.data.health;
                    StaticClass.duty = dictInfo.data.duty;
                    StaticClass.workplace = dictInfo.data.workplace;
                    StaticClass.hobby = dictInfo.data.hobby;
                }
            }
        });
    }

    /**
     * 获取上传下达，看是不否有未读消息
     */
    private void getMessage() {
        params.clear();
        params.put("page", "1");
        params.put("size", "30");
        MyOkHttpUtils.post(Global.SEARCH_UP_DOWN_LIST_JS, params, new OnBaseListener<UpDownMsgInfo>() {
            @Override
            public void onOk(String response, int id, UpDownMsgInfo upDownMsgInfo) {
                if (upDownMsgInfo != null && upDownMsgInfo.data != null) {
                    List<UpDownMsgInfo.DataBean.RowsBean> list = upDownMsgInfo.data.rows;
                    if (list != null) {
                        boolean ss = false;
                        for (int i = 0; i < list.size() && !ss; i++) {
                            ss = list.get(i).status == 0;
                        }
                        StaticClass.isNewMessage = ss;
                    } else {
                        StaticClass.isNewMessage = false;
                    }
                    //测试有新消息
//                    StaticClass.isNewMessage=true;
                    EventBus.getDefault().post(new MessageEvent(Global.NEW_MESSAGE, "", ""));
                }
            }
        });
    }
}
