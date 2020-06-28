package com.heima.youku.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.heima.youku.R;
import com.heima.youku.utils.YouKuRotateAnimUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rl_level3)
    RelativeLayout rlLevel3;
    @BindView(R.id.rl_level2)
    RelativeLayout rlLevel2;
    @BindView(R.id.rl_level1)
    RelativeLayout rlLevel1;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.iv_home)
    ImageView ivHome;

    private boolean isLeve1Show = true;
    private boolean isLevel2Show = true;
    private boolean isLevel3Show = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_menu, R.id.iv_home})
    public void onClick(View view) {
        switch (view.getId()) {
            //点击了菜单键
            case R.id.iv_menu:
                if (isLevel3Show) {
                    YouKuRotateAnimUtils.hide(rlLevel3);
                    isLevel3Show = false;
                } else {
                    YouKuRotateAnimUtils.show(rlLevel3);
                    isLevel3Show = true;
                }


                break;
            case R.id.iv_home:
                //如果isLeve2Show,rl_level2隐藏动画,rl_level3延时200ms隐藏动画
                if (isLevel2Show) {
                    YouKuRotateAnimUtils.hide(rlLevel2);
                    ivMenu.setClickable(false); //如果不设置,点击原控件位置后,还是能打开,是个bug
                    if (isLevel3Show) {
                        YouKuRotateAnimUtils.hide(rlLevel3, 200);
                        isLevel3Show = false;
                    }
                    isLevel2Show = false;
                } else {
                    YouKuRotateAnimUtils.show(rlLevel2);
                    ivMenu.setClickable(true);  //设置menu能点击
                    isLevel2Show = true;
                }

                break;
        }
    }

    /**
     * 重写菜单键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果点击了菜单键
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            System.out.println("菜单键被点击了!");
            if (isLeve1Show) {
                ivHome.setClickable(false);     //ivhome不能点击
                ivMenu.setClickable(false);     //ivmenu不能点击
                YouKuRotateAnimUtils.hide(rlLevel1);
                isLeve1Show = false;
                if (isLevel2Show) {
                    YouKuRotateAnimUtils.hide(rlLevel2, 200);
                    isLevel2Show = false;
                    if (isLevel3Show) {
                        YouKuRotateAnimUtils.hide(rlLevel3, 400);
                        isLevel3Show = false;
                    }
                }
            } else {
                YouKuRotateAnimUtils.show(rlLevel1);
                isLeve1Show = true;
                ivHome.setClickable(true);      //ivhome设置能点击
            }
            return true;//表示消费掉了菜单按键, 不需要执行系统其他默认操作了
        }
        //注意:下面这句必须写,否则返回键失灵,因为有可能是其它按键,其余如音量键好像没事
        return super.onKeyDown(keyCode, event);
    }
}
