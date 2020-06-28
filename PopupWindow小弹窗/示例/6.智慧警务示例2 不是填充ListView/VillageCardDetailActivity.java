package com.kuchuan.wisdompolice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.kuchuan.wisdompolice.R;
import com.kuchuan.wisdompolice.global.Global;
import com.kuchuan.wisdompolice.gson.CheckResultGson;
import com.kuchuan.wisdompolice.gson.VillageOrBuildingCardDetailGson;
import com.kuchuan.wisdompolice.utils.MyOkHttpUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.kuchuan.wisdompolice.application.MyApplication.aCache;

/**
 * Description: 小区牌/路牌管理
 * Copyright  : Copyright (c) 2017
 * Company    : 酷川科技 www.kuchuanyun.com
 * Author     : 李小松
 * Date       : 2017/10/23 on 16:36.
 */

public class VillageCardDetailActivity extends BaseActivity {

    private PopupWindow popup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_card_detail);
        ButterKnife.bind(this);
        
        popup = getPopupMore(new OnPopupWindowItemClickListener() {
            @Override
            public void onPopupWindowItemClick(int position) {
                switch (position) {
                    case 0:
                        toast("修改门牌类型");
                        break;
                    case 1:
                        toast("更新经纬度");
                        break;
                }
            }
        });
    }



//----------------------------------------------------------------------
    //这个方法写在BaseActivity里面,因为不止一个地方调用
    private PopupWindow popup = null;
    protected PopupWindow getPopupMore(final OnPopupWindowItemClickListener onItemClckListener){
        View view = View.inflate(this, R.layout.popupwindow_more, null);
        view.findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//修改门牌类型
                if (onItemClckListener != null) {
                    onItemClckListener.onPopupWindowItemClick(0);
                }
                startActivity(new Intent(activity,EditDoorTypeActivity.class));
                popup.dismiss();
            }
        });
        view.findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//更新经纬度信息
                if (onItemClckListener != null) {
                    onItemClckListener.onPopupWindowItemClick(1);
                }
                popup.dismiss();
            }
        });
        popup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);//400, 236
        popup.setBackgroundDrawable(new ColorDrawable());
        return popup;
    }

    protected interface OnPopupWindowItemClickListener {
        void onPopupWindowItemClick(int position);
    }
//----------------------------------------------------------------------



    @OnClick({R.id.iv_plus,R.id.ll_more1, R.id.ll_more2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_plus:
                popup.showAsDropDown(ivPlus, -10, 25);
                break;
        }
    }
}
