package com.kuchuan.wisdompolicehy.activity;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kuchuan.wisdompolicehy.R;
import com.kuchuan.wisdompolicehy.utils.ToastUtils;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.kuchuan.wisdompolicehy.application.MyApplication.aCache;

/**
 * 网吧信息登记
 */
public class InternetBarRegisterActivity extends BaseActivity {

    @BindView(R.id.tv_kksj)//开卡时间
    TextView tvKksj;
    @BindView(R.id.tv_jssj)//结束时间
    TextView tvJssj;
    private TimePickerDialog timePickerDialog;

    private int nowYear;//★★★★★★★★★★★★★★★★★★★★
    private int nowMonth;//★★★★★★★★★★★★★★★★★★★★
    private int nowDay;//★★★★★★★★★★★★★★★★★★★★
    private int nowHour;//★★★★★★★★★★★★★★★★★★★★
    private int nowMinute;//★★★★★★★★★★★★★★★★★★★★

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_bar_register);
        ButterKnife.bind(this);

        Calendar calendar = Calendar.getInstance();//★★★★★★★★★★★★★★★★★★★★
        nowYear = calendar.get(Calendar.YEAR);//★★★★★★★★★★★★★★★★★★★★
        nowMonth = calendar.get(Calendar.MONTH) + 1;//★★★★★★★★★★★★★★★★★★★★
        nowDay = calendar.get(Calendar.DAY_OF_MONTH);//★★★★★★★★★★★★★★★★★★★★
        nowHour = calendar.get(Calendar.HOUR_OF_DAY);//★★★★★★★★★★★★★★★★★★★★
        nowMinute = calendar.get(Calendar.MINUTE);//★★★★★★★★★★★★★★★★★★★★

        timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog,
                new TimePickerDialog.OnTimeSetListener() {//★★★★★★★★★★★★★★★★★★★★
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                switch (clickTag) {
                    case 1:
                        tvKksj.setText(getYMD(hourOfDay,minute));
                        break;
                    case 2:
                        tvJssj.setText(getYMD(hourOfDay,minute));
                        break;
                    default:
                        break;
                }
            }
        }, nowHour, nowMinute, true);//(context,themeResId可以完全不写,时,分,是否是24h的View)
//        timePickerDialog.setTitle("dialog'Title");//★★★★★★★★★★★★★★★★★★★★
//        timePickerDialog.show();//★★★★★★★★★★★★★★★★★★★★
    }

	//获取年月日
    private String getYMD(int hourOfDay, int minute) {
        String yue = nowMonth < 9 ? "" + 0 + (nowMonth + 1) : "" + (nowMonth + 1);//month:0-11
        String day = nowDay < 10 ? "" + 0 + nowDay : "" + nowDay;
        String hour = hourOfDay < 10 ? "" + 0 + hourOfDay : "" + hourOfDay;
        String min = minute < 10 ? "" + 0 + minute : "" + minute;
        return nowYear + "-" + yue + "-" + day + " " + hour + ":" + min + ":" + getRandomSecond();
    }

	//获取随机秒数
    private int getRandomSecond(){
        return new Random().nextInt(60);
    }

    @OnClick({R.id.ll_kksj, R.id.ll_jssj})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_kksj://开卡时间
                clickTag = 1;
                timePickerDialog.setTitle("开卡时间");//★★★★★★★★★★★★★★★★★★★★
                timePickerDialog.show();//★★★★★★★★★★★★★★★★★★★★
                break;
            case R.id.ll_jssj://结束时间
                clickTag = 2;
                timePickerDialog.setTitle("结束时间");//★★★★★★★★★★★★★★★★★★★★
                timePickerDialog.show();//★★★★★★★★★★★★★★★★★★★★
                break;
        }
    }
}
