<!--
在styles.xml中找到的默认值:
<style name="Widget.CalendarView">
	<item name="showWeekNumber">true</item>
	<item name="minDate">01/01/1900</item>
	<item name="maxDate">12/31/2100</item>
	<item name="shownWeekCount">6</item>
	<item name="selectedWeekBackgroundColor">#330099FF</item>
	<item name="focusedMonthDateColor">#FFFFFFFF</item>
	<item name="unfocusedMonthDateColor">#66FFFFFF</item>
	<item name="weekNumberColor">#33FFFFFF</item>
	<item name="weekSeparatorLineColor">#19FFFFFF</item>
	<item name="selectedDateVerticalBar">@drawable/day_picker_week_view_dayline_holo</item>
	<item name="weekDayTextAppearance">@style/TextAppearance.Small.CalendarViewWeekDayView</item>
	<item name="dateTextAppearance">?attr/textAppearanceSmall</item>
	<item name="calendarViewMode">holo</item>
</style>
-->

<CalendarView
    android:id="@+id/calendar_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"

    android:firstDayOfWeek="1"  				//设置每周第一天(1-7 => 周日-周六)
    android:focusedMonthDateColor="@color/red"  //设置获取焦点的月份的日期文字的颜色(实测无用)
    android:dateTextAppearance="@android:style/TextAppearance.Small" //设置该日历视图的日期文字的样式, @style/custom_calendar_style
    android:maxDate="12/31/2100"        		//设置日历组件支持的最大日期
    android:minDate="01/01/1900"        		//设置日历组件支持的最小日期
    android:weekDayTextAppearance="@style/custom_calendar_style"//设置星期几的文字样式
    android:background="@color/white"			//设置背景色
	
    android:selectedDateVerticalBar="@drawable/"	//设置绘制在选中日期两边额竖线对应额Drawable(实测无用)
    android:selectedWeekBackgroundColor="@color/red"//设置被选中周的背景色(实测无用)
    android:showWeekNumber="true"					//设置是否显示第几周(已经过时，实测无用)
    android:shownWeekCount ="3"     				//设置总共显示几个星期(已经过时，实测无用)
    android:unfocusedMonthDateColor="@color/red"	//设置没有焦点的月份的日期文字的颜色(已经过时，实测无用)
    android:weekNumberColor ="@color/red"			//设置显示周编号的颜色(已经过时，实测无用)
    android:weekSeparatorLineColor="@color/red" 	//设置周分割线的颜色(已经过时，实测无用)
    android:calendarViewMode="material" 			//holo/material, 私有属性, 设置了编译通不过...
	/>

<style name="custom_calendar_style">
    <item name="android:textStyle">bold</item>
    <item name="android:textSize">20sp</item>
    <item name="android:textColor">@color/red</item>
</style>



@BindView(R.id.calendar_view)
CalendarView calendarView;


//日历选中监听
 calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
	@Override
	public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
		//显示用户选择的日期
		Toast.makeText(CalendarviewActivity.this,year + "年" + month + "月" + dayOfMonth + "日",Toast.LENGTH_SHORT).show();
	}
});

//参1:从1.1, 1970 00:00:00 至今.   参2:切换到日期是否显示动画.  参3:是否将当前日期居中，即使它已经可见。???
calendarView.setDate(System.currentTimeMillis(), true, true);
