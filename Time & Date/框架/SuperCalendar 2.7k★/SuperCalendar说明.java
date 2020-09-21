https://github.com/MagicMashRoom/SuperCalendar
https://www.jianshu.com/p/8e3fc46e5a80

android 自定义日历控件 支持左右无限滑动 周月切换 标记日期显示 自定义显示效果跳转到指定日期

implementation 'com.github.MagicMashRoom:SuperCalendar:1.6'

//只有1个View能写在xml中
<com.ldf.calendar.view.MonthPager
	android:id="@+id/calendar_view"
	android:layout_width="match_parent"
	android:layout_height="300dp"
	android:background="#fff">
</com.ldf.calendar.view.MonthPager>


private CalendarViewAdapter calendarAdapter;
private CalendarDate currentDate;

currentDate = new CalendarDate();
tvYear.setText(currentDate.getYear() + "年");
tvMonth.setText(currentDate.getMonth() + "");
CustomDayView customDayView = new CustomDayView(context, R.layout.custom_day);//自定义天的layout
calendarAdapter = new CalendarViewAdapter(
        context,
        onSelectDateListener,
        CalendarAttr.WeekArrayType.Monday,
        customDayView);
calendarAdapter.setOnCalendarTypeChangedListener(new CalendarViewAdapter.OnCalendarTypeChanged() {
    @Override
    public void onCalendarTypeChanged(CalendarAttr.CalendarType type) {
        rvToDoList.scrollToPosition(0);
    }
});

HashMap<String, String> markData = new HashMap<>();
markData.put("2017-8-9", "1");
markData.put("2017-7-9", "0");
markData.put("2017-6-9", "1");
markData.put("2017-6-10", "0");
calendarAdapter.setMarkData(markData);



//TextView点击, 切换周月
scrollSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK) {//日历显示 WEEK 模式, 只有1行
                    Utils.scrollTo(content, rvToDoList, monthPager.getViewHeight(), 200);
                    calendarAdapter.switchToMonth();
                } else {
                    Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);//日历显示 MONTH 模式, 显示全
                    calendarAdapter.switchToWeek(monthPager.getRowIndex());//切换周显示模式, 显示当前选中天的那一行
                }
            }
        });

//自定义当前选中天的背景
ThemeDayView themeDayView = new ThemeDayView(context, R.layout.custom_day_focus);
calendarAdapter.setCustomDayRenderer(themeDayView);
calendarAdapter.notifyDataSetChanged();
calendarAdapter.notifyDataChanged(new CalendarDate());


monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);//切换下一月
monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);//切换上一月

//偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
monthPager.selectOtherMonth(offset);


