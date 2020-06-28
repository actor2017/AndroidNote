package com.kuchuan.wisdompolicehy.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuchuan.wisdompolicehy.R;
import com.kuchuan.wisdompolicehy.fragment.FragmentFactory;
import com.kuchuan.wisdompolicehy.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_framelayout)
    FrameLayout flFramelayout;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.iv_inform)
    ImageView ivInform;
    @BindView(R.id.tv_inform)
    TextView tvInform;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    private int flagCount = 0;
    private long flagClicked = 0;
    private long flagClicked2 = 0;
    private int[] pressedIcons;
    private int[] normalIcons;
    private ArrayList<Fragment> fragments;
    private int position = 1;//记录现在点击的是哪一个

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pressedIcons = new int[]{R.drawable.s0, R.drawable.s1, R.drawable.s3};
        normalIcons = new int[]{R.drawable.d0, R.drawable.d1, R.drawable.d3};
        fragments = FragmentFactory.getFragments();
        //初始化
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            fragmentTransaction.add(R.id.fl_framelayout, fragments.get(i));
        }
        fragmentTransaction.commit();
        switchToPosition(0);//默认切换到第0条
    }

    //选择第几个
    private void switchToPosition(int i) {
        if (i != position) {
            ivCollection.setImageResource(i == 0 ? pressedIcons[0] : normalIcons[0]);
            ivInform.setImageResource(i == 1 ? pressedIcons[1] : normalIcons[1]);
            ivMine.setImageResource(i == 2 ? pressedIcons[2] : normalIcons[2]);
            tvCollection.setTextColor(i == 0 ? Color.parseColor("#3F51B5") : Color.parseColor
                    ("#000000"));
            tvInform.setTextColor(i == 1 ? Color.parseColor("#3F51B5") : Color.parseColor
                    ("#000000"));
            tvMine.setTextColor(i == 2 ? Color.parseColor("#3F51B5") : Color.parseColor("#000000"));

            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            for (int j = 0; j < fragments.size(); j++) {
                if (j == i) {
                    fragmentTransaction.show(fragments.get(j));
                } else {
                    fragmentTransaction.hide(fragments.get(j));
                }
            }
            fragmentTransaction.commit();
        }
    }

    @OnClick({R.id.tv_add, R.id.ll_1, R.id.ll_2, R.id.ll_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                //指定对话框的样式
                final Dialog dialog = new Dialog(this, R.style.MyDialog);
                //加载对话框的布局文件
                View contentView = getLayoutInflater().inflate(R.layout.dialog_view, null);
                TextView tvLease = (TextView) contentView.findViewById(R.id.tv_lease);//汽车租赁等
                contentView.findViewById(R.id.ll_one).setOnClickListener(new View.OnClickListener
                        () {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.show(MainActivity.this, "click 1");
                        dialog.dismiss();
                    }
                });
                contentView.findViewById(R.id.ll_two).setOnClickListener(new View.OnClickListener
                        () {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.show(MainActivity.this, "click 2");
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(contentView);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                //http://blog.csdn.net/fancylovejava/article/details/21617553位置修改详细说明
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                //dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                dialogWindow.setGravity(Gravity.TOP);//对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
                //lp.x = 100; // 新位置X坐标
                lp.y = 200; // 新位置Y坐标
                dialogWindow.setAttributes(lp);
                dialog.show();
                break;
            case R.id.ll_1:
                switchToPosition(0);
                position = 0;
                break;
            case R.id.ll_2:
                switchToPosition(1);
                position = 1;
                break;
            case R.id.ll_3:
                switchToPosition(2);
                position = 2;
                break;
        }
    }

    /**
     * 重写返回键
     * private long flagClicked;
     */
    @Override
    public void onBackPressed() {
		if(System.currentTimeMillis() - flagClicked > 1000) {
			flagClicked = System.currentTimeMillis();
			ToastUtils.show(this, "再点击一下退出");
		} else {
			super.onBackPressed();
		}
    }
}
