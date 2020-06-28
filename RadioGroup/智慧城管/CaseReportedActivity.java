package com.kuchuanyun.wisdomcitymanagement.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.jaeger.library.StatusBarUtil;
import com.kuchuanyun.wisdomcitymanagement.R;
import com.kuchuanyun.wisdomcitymanagement.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 案件上报
 */
public class CaseReportedActivity extends BaseActivity {

    @BindView(R.id.tb_toolBar)
    Toolbar tbToolBar;
    @BindView(R.id.rg_radioGroup)
    RadioGroup rgRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_reported);
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
        rgRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_radioButton1:
                        ToastUtils.show(CaseReportedActivity.this, "1");
                        break;
                    case R.id.rb_radioButton2:
                        ToastUtils.show(CaseReportedActivity.this, "2");
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
