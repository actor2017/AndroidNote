https://github.com/prolificinteractive/material-calendarview
A Material design back port of Android's CalendarView

Usage
1.Add compile 'com.prolificinteractive:material-calendarview:1.4.3' to your dependencies.
  如果报错:class file for org.threeten.bp.LocalDate not found, 需要导入:
  implementation 'com.jakewharton.threetenabp:threetenabp:1.2.0'

2.Add MaterialCalendarView into your layouts or view hierarchy.
3.Set a OnDateSelectedListener or call MaterialCalendarView.getSelectedDates() when you need it.


API:
Javadoc Available Here
http://prolificinteractive.github.io/material-calendarview/


<com.prolificinteractive.materialcalendarview.MaterialCalendarView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/calendarView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:mcv_showOtherDates="all"
    app:mcv_selectionColor="#00F"
    />

private CalendarDay currentDate;//自定义的日期对象

//        calendarView.getSelectedDate();//获取被选中的日期
        //选择模式，允许一次选择多个日期。
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {//监听选中日期的变化
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                logError("监听选中日期的变化setOnDateChangedListener:====" + date.getYear() + "===" + date.getMonth());
                //由于点击之后会默认选中/取消选中,所以再重新选回去...
                calendarView.setDateSelected(date, !selected);
            }
        });
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {//月份改变监听(周模式下按周改变?)
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                logError("月份改变监听onMonthChanged:====" + date.getYear() + "===" + date.getMonth());
                getSighRecord(date.getYear(), date.getMonth());
            }
        });

calendarView.clearSelection();//清空选中
calendarView.setDateSelected(currentDate, true);
calendarView.setDateSelected(CalendarDay.from(dataBean.year, dataBean.month, dataBean.day), isSign);
calendarView.setSelectedDate(CalendarDay.today());//会清空选中, 然后选中指定日期
