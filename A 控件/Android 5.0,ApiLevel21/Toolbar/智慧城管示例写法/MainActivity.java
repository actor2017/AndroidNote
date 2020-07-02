package com.kuchuanyun.wisdomcitymanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuchuanyun.wisdomcitymanagement.R;
import com.kuchuanyun.wisdomcitymanagement.global.Global;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kuchuanyun.wisdomcitymanagement.application.MyApplication.instance;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tb_toolBar)
    Toolbar tbToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initSupportToolBar(tbToolBar,false);//调用BaseActivity的方法初始化ToolBar

        tbToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - flagClicked <= 1000) {
                    finish();
                    System.exit(0);//这样才能完全退出!!
                } else {
                    flagClicked = System.currentTimeMillis();
                    toast("再点击一下退出");
                }
            }
        });
    }
	
	   /**
     * 初始化v7包的ToolBar
     * @param canFinish 点击返回键的时候,是否退出本页面
     */
    public void initSupportToolBar(Toolbar tbToolBar, boolean canFinish){
        setSupportActionBar(tbToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回箭头
        getSupportActionBar().setDisplayShowTitleEnabled(false);//不显示title
        if (canFinish) {
            tbToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
