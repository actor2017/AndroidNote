package com.kuchuan.wisdompolice.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.kuchuan.wisdompolice.R;
import com.kuchuan.wisdompolice.fragment.FragmentFactory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 采集(流动人口,酷云车辆信息,图片,人像,典当物品信息,房屋租赁信息)
 */
public class CollectionActivity extends BaseActivity {

    private PopupWindow popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
    }

    //右上角的按钮
    @OnClick(R.id.iv_plus)
    public void onClick() {
        View view = View.inflate(this, R.layout.popupwindow_content, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CollectionActivity.this,ThemeActivity.class));
                popup.dismiss();
            }
        });
        popup = new PopupWindow(view, 350, 120, true);
        popup.setBackgroundDrawable(new ColorDrawable());
        popup.showAsDropDown(iv_Plus, 0, 15);
    }
}
