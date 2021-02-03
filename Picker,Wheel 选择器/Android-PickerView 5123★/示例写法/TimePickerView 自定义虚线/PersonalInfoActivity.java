package com.ly.hihifriend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.jaeger.library.StatusBarUtil;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.ly.hihifriend.R;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: 登录-->注册-->填写个人信息
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/3/18 on 11:56
 */
public class PersonalInfoActivity extends BaseActivity {

    @BindView(R.id.iv_head)//头像
    ImageView ivHead;
    @BindView(R.id.et_nickname)//昵称
    EditText  etNickname;
    @BindView(R.id.tv_province)//省
    TextView  tvProvince;
    @BindView(R.id.tv_city)//市
    TextView  tvCity;
    @BindView(R.id.tv_district)//区
    TextView  tvDistrict;
    @BindView(R.id.iv_boy)
    ImageView ivBoy;
    @BindView(R.id.tv_boy)
    TextView  tvBoy;
    @BindView(R.id.iv_line_boy)
    ImageView ivLineBoy;
    @BindView(R.id.iv_girl)
    ImageView ivGirl;
    @BindView(R.id.tv_girl)
    TextView  tvGirl;
    @BindView(R.id.iv_line_girl)
    ImageView ivLineGirl;
    @BindView(R.id.tv_birth_day)//选择出生日期
    TextView  tvBirthDay;

    private boolean        isBoy;//是否是boy
    private TimePickerView timePickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 30);
        ButterKnife.bind(this);

        timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                String format = sdf.format(date);
                tvBirthDay.setText(format);
            }
        }).setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
            @Override
            public void customLayout(View v) {
                //如果虚线画不出来, 开启硬件加速
                v.findViewById(R.id.view1).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                v.findViewById(R.id.view2).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                View year = v.findViewById(R.id.year);
                View month = v.findViewById(R.id.month);
                View day = v.findViewById(R.id.day);
                resetItem(year);//通过反射, 设置显示5个item
                resetItem(month);
                resetItem(day);
                v.findViewById(R.id.tv_finish).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timePickerView.returnData();
                        timePickerView.dismiss();
                    }
                });
                v.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timePickerView.dismiss();
                    }
                });
            }
        }).setDividerColor(getResources().getColor(R.color.trans))
                .setLineSpacingMultiplier(2.5f)//设置间距倍数,但是只能在1.0-4.0f之间
                .build();
    }

    //v4.1.8 版本这样设置,   v4.1.9 版本设置方法:
    //WheelView year = (WheelView) timePickerView.findViewById(R.id.year);
    //year.setItemsVisibleCount(5);
    private void resetItem(View view) {
        Class<? extends View> aClass = view.getClass();
        try {
            Field field = aClass.getDeclaredField("itemsVisible");
            field.setAccessible(true);
            field.setInt(view, 7);//通过反射, 设置显示7-2 = 5个item
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tv_birth_day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_birth_day://dialog选择生日
                timePickerView.show();
                break;
        }
    }
}
