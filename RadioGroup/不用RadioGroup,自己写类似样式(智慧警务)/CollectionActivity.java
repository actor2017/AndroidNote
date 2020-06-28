package com.kuchuan.wisdompolice.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.kuchuan.wisdompolice.R;
import com.kuchuan.wisdompolice.fragment.FragmentFactory;
import com.kuchuan.wisdompolice.global.Global;

import org.androidpn.client.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kuchuan.wisdompolice.application.MyApplication.aCache;

/**
 * 采集(流动人口,酷云车辆信息,图片,人像,典当物品信息,房屋租赁信息)
 * 本类启动模式是singletop
 */
public class CollectionActivity extends BaseActivity {

    @BindView(R.id.iv_plus)
    ImageView iv_Plus;
    @BindView(R.id.fl_framelayout)
    FrameLayout fl_Framelayout;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.iv_warn)
    ImageView ivWarn;
    @BindView(R.id.tv_warn)
    TextView tvWarn;
    @BindView(R.id.iv_task)
    ImageView ivTask;
    @BindView(R.id.tv_task)
    TextView tvTask;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    private ArrayList<Fragment> fragments;
    private int position = -1;//记录现在点击的是哪一个
    private PopupWindow popup;
    private int[] pressedIcons = new int[]{R.drawable.s0, R.drawable.s1, R.drawable.s2, R
            .drawable.s3};
    private int[] normalIcons = new int[]{R.drawable.d0, R.drawable.d1, R.drawable.d2, R.drawable
            .d3};
    private String[] colors = new String[]{"#29A1F7", "#000000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this, getResources().getColor(R.color.blue_titlebar));
        fragments = FragmentFactory.getFragments();
        //初始化
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (savedInstanceState != null) {
            int fragmentNum = fragments.size();//记录个数
            fragments.clear();//说明fragment有保存的值
            Fragment fragment;
            for (int i = 0; i < fragmentNum; i++) {
                fragment = getSupportFragmentManager().findFragmentByTag("tag" + i);
                fragmentTransaction.add(R.id.fl_framelayout, fragment, "tag" + i);
                fragments.add(fragment);
            }
        } else {
            //初始化
            for (int i = 0; i < fragments.size(); i++) {
                fragmentTransaction.add(R.id.fl_framelayout, fragments.get(i), "tag" + i);//加上标记
            }
        }
        fragmentTransaction.commit();
        switchToPosition(0);//默认切换到第0条
//        System.out.println(aCache.getAsString(Global.NOTIFICATION_THEMEID));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String themeId = intent.getStringExtra(Constants.SERVER_ID);//主题id
        if (themeId != null) {
            //LOGINTYPE=1&LOGINCD=zy&ID=1&msgtype=12
//            if (aCache.getAsString(Global.NOTIFICATION_THEMEID) == null) {// || !themeId.equals
            // (aCache.getAsString(Global.NOTIFICATION_THEMEID))//删除了检查重复的逻辑
            String loginType = intent.getStringExtra(Constants.LOGINTYPE);
            String logincd = intent.getStringExtra(Constants.LOGINCD);
            String msgType = intent.getStringExtra(Constants.MSGTYPE);
            String msg = intent.getStringExtra(Constants.MSG);

            // T ODO: 2017/8/29 1111111111111111111111111111111111111111111111
//                System.out.println("themeId:"+themeId);
//                System.out.println("loginType:" + loginType);
//                System.out.println("logincd:" + logincd);
//                System.out.println("msgType:" + msgType);
//                System.out.println("msg:" + msg);

            aCache.put(Global.NOTIFICATION_THEMEID, themeId);
            if (loginType != null) {
                aCache.put(Global.NOTIFICATION_LOGINTYPE, loginType);
            }
            if (logincd != null) {
                aCache.put(Global.NOTIFICATION_LOGINCD, logincd);
            }
            if (msgType != null) {
                aCache.put(Global.NOTIFICATION_MSGTYPE, msgType);
            }
            if (msg != null) {
                aCache.put(Global.NOTIFICATION_MSG, msg);
            }
            switch (msg) {//1:协查.2:问询.3:消息.4:报警
                case "1":
                case "2":
                case "3":
                    switchToPosition(2);//任务
                    break;
                case "4":
                    switchToPosition(1);//报警
                default:
                    break;
            }

//                CallPoliceFragment callPoliceFragment = (CallPoliceFragment) fragments.get(1);
//                callPoliceFragment.requestCallPoliceList(themeId);
        }
//        }
    }

    /**
     * 切换到某一个fragment,为了避免Fragment的onCreateView重复执行的方法
     * 1、将每一个Fragment的view对象变成成员变量  在onCreateView中进行非空的判断
     * 2、不使用replace  而是用add 和hide来操作Fragment
     */
    private void switchToPosition(int i) {
        if (i != position) {
            position = i;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            for (int j = 0; j < fragments.size(); j++) {
                if (j == i) {
                    fragmentTransaction.show(fragments.get(j));
                } else {
                    fragmentTransaction.hide(fragments.get(j));
                }
            }
            ivCollection.setImageResource(i == 0 ? pressedIcons[0] : normalIcons[0]);
            ivWarn.setImageResource(i == 1 ? pressedIcons[1] : normalIcons[1]);
            ivTask.setImageResource(i == 2 ? pressedIcons[2] : normalIcons[2]);
            ivMine.setImageResource(i == 3 ? pressedIcons[3] : normalIcons[3]);
            tvCollection.setTextColor(i == 0 ? Color.parseColor(colors[0]) : Color.parseColor
                    (colors[1]));
            tvWarn.setTextColor(i == 1 ? Color.parseColor(colors[0]) : Color.parseColor(colors[1]));
            tvTask.setTextColor(i == 2 ? Color.parseColor(colors[0]) : Color.parseColor(colors[1]));
            tvMine.setTextColor(i == 3 ? Color.parseColor(colors[0]) : Color.parseColor(colors[1]));
            fragmentTransaction.commit();
        }
    }

    @OnClick({R.id.iv_plus, R.id.ll_0, R.id.ll_1, R.id.ll_2, R.id.ll_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_plus://右上角的按钮
                View view1 = View.inflate(this, R.layout.popupwindow_content, null);
                view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CollectionActivity.this, ThemeActivity.class));
                        popup.dismiss();
                    }
                });
                popup = new PopupWindow(view1, 350, 120, true);
                popup.setBackgroundDrawable(new ColorDrawable());
                popup.showAsDropDown(iv_Plus, 0, 15);
                break;
            case R.id.ll_0:
                switchToPosition(0);
                break;
            case R.id.ll_1:
                switchToPosition(1);
                break;
            case R.id.ll_2:
                switchToPosition(2);
                break;
            case R.id.ll_3:
                switchToPosition(3);
                break;
        }
    }
}
