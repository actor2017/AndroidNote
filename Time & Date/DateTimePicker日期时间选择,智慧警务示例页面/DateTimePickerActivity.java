package com.kuchuanyun.wisdompolicehy4sd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kuchuanyun.wisdompolicehy4sd.R;

import java.util.Calendar;

/**
 * 日期时间选择器,可以不显示DatePicker | TimePicker
 * intent = new Intent(this, DateTimePickerActivity.class);
 * intent.putExtra(DatePicker.class.getName(), false);//不显示DatePicker
 * intent.putExtra(TimePicker.class.getName(), false);//不显示TimePicker
 * startActivityForResult(intent, 0);
 *
 * 获取返回数据:data.getStringExtra(Intent.EXTRA_RETURN_RESULT);//返回时间日期yyyy-MM-dd HH:mm:ss
 */
public class DateTimePickerActivity extends BaseActivity {

    private TextView tv1;
    private DatePicker dpDatePicker;
    private TextView tv2;
    private TimePicker tpTimePicker;
    private boolean datePickerIsShow;//DatePicker是否显示
    private boolean timePickerIsShow;//TimePicker是否显示
    private String result = "";//返回字符串

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_picker);

        initSupportToolBar(true);
        setTitle("日期时间选择");

        tv1 = (TextView) findViewById(R.id.tv_1);
        dpDatePicker = (DatePicker) findViewById(R.id.dp_datePicker);
        tv2 = (TextView) findViewById(R.id.tv_2);
        tpTimePicker = (TimePicker) findViewById(R.id.tp_timePicker);

        Intent intent = getIntent();
        datePickerIsShow = intent.getBooleanExtra(DatePicker.class.getName(), true);
        timePickerIsShow = intent.getBooleanExtra(TimePicker.class.getName(), true);
        if (!datePickerIsShow) {
            tv1.setVisibility(View.GONE);
            dpDatePicker.setVisibility(View.GONE);
        }
        if (!timePickerIsShow) {
            tv2.setVisibility(View.GONE);
            tpTimePicker.setVisibility(View.GONE);
        } else {
            tpTimePicker.setIs24HourView(true);
        }


        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datePickerIsShow) {
                    int year = dpDatePicker.getYear();//年
                    int month = dpDatePicker.getMonth() + 1;//月
                    int dayOfMonth = dpDatePicker.getDayOfMonth();//日
                    result = year + "-" + head0(month) + "-" + head0(dayOfMonth);
                }
                if (timePickerIsShow) {
                    if (datePickerIsShow) result += " ";
                    int hour = tpTimePicker.getCurrentHour();//时
                    int minute = tpTimePicker.getCurrentMinute();//分
                    int second = Calendar.getInstance().get(Calendar.SECOND);//秒
                    result += head0(hour) + ":" + head0(minute) + ":" + head0(second);
                }
                setResult(RESULT_OK, new Intent().putExtra(Intent.EXTRA_RETURN_RESULT, result));
                finish();
            }
        });
    }

    //前面是否跟0
    private String head0(int date) {
        return date > 9 ? String.valueOf(date) : "0" + date;
    }
}
