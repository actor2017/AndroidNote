package com.ysytech.zhongjiao.widget;

import android.content.Context;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.ysytech.zhongjiao.R;

import java.util.Calendar;

/**
 * description: 从顶部弹出, 选择日期
 * company    : 重庆元山元科技有限公司 http://www.ysytech.net/
 *
 * @author : 李大发
 * date       : 2021/1/14 on 09
 * @version 1.0
 */
public class SelectDateView extends PartShadowPopupView {

    private CalendarView          calendarView;
    private OnDataSelectedListener listener;

    public SelectDateView(@NonNull Context context, OnDataSelectedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.view_select_date;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        calendarView = findViewById(R.id.calendar_view);
        //日历选中监听
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            if (listener != null) {
                listener.onDataSelected(false, year, month, dayOfMonth);
            }
            dismiss();
        });
        //本周
        findViewById(R.id.tv_week).setOnClickListener(v -> {
            //参1:从1.1, 1970 00:00:00 至今.   参2:切换到日期是否显示动画.  参3:是否将当前日期居中，即使它已经可见。???
            calendarView.setDate(System.currentTimeMillis(), true, true);
            if (listener != null) {
                Calendar calendar = Calendar.getInstance();
                listener.onDataSelected(true, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
            dismiss();
        });
        //今天
        findViewById(R.id.tv_today).setOnClickListener(v -> {
            calendarView.setDate(System.currentTimeMillis(), true, true);
            if (listener != null) {
                Calendar calendar = Calendar.getInstance();
                listener.onDataSelected(false, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
            dismiss();
        });
    }

    public interface OnDataSelectedListener {
        /**
         * @param isThisWeekClicked 是否点击了"本周"
         * @param year 年
         * @param month 月
         * @param dayOfMonth 日
         */
        void onDataSelected(boolean isThisWeekClicked, int year, int month, int dayOfMonth);
    }
}
