package com.kuchuanyun.wisdomcitymanagement.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.kuchuanyun.wisdomcitymanagement.R;
import com.kuchuanyun.wisdomcitymanagement.widget.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 待审详情页
 */
public class PendingTrialDetailActivity extends BaseActivity {

    @BindView(R.id.tb_toolBar)
    Toolbar tbToolBar;
    @BindView(R.id.tv_caseNum)
    TextView tvCaseNum;
    @BindView(R.id.tv_caseName)
    TextView tvCaseName;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_personName)
    TextView tvPersonName;
    @BindView(R.id.tv_duty)
    TextView tvDuty;
    @BindView(R.id.tv_caseIntroduce)
    TextView tvCaseIntroduce;
    @BindView(R.id.tv_undertaker)
    TextView tvUndertaker;
    @BindView(R.id.tv_lawNum)
    TextView tvLawNum;
    @BindView(R.id.tv_opinion)
    TextView tvOpinion;
    @BindView(R.id.vp_pics)
    ViewPager vpPics;
    @BindView(R.id.cpi_circlepageindicator)
    CirclePageIndicator cpiCirclepageindicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_trial_detail);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this, getResources().getColor(R.color.blue_titlebar));
        setSupportActionBar(tbToolBar);
        //设置返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        vpPics.setAdapter(new MyVPAdapter());
        cpiCirclepageindicator.setViewPager(vpPics);
    }

    private class MyVPAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //1、创建View对象
            ImageView imageView = new ImageView(PendingTrialDetailActivity.this);
            //imageView.setImageResource(mImgs[position]);      //图片不缩放,会有留白
            imageView.setBackgroundResource(R.drawable.testpic);
            //2、将iv添加到container
            container.addView(imageView);
            //3、将iv返回
            return imageView;       //注意:这儿不能反悔container★★★★★★★★★★★★
        }
    }
}
