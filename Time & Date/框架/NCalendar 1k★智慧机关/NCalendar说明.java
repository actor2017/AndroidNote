https://github.com/yannecer/NCalendar
一款安卓日历，仿miui，钉钉，华为的日历，万年历、365、周日历，月日历，月视图、周视图滑动切换，
农历，节气，Andriod Calendar , MIUI Calendar,小米日历

implementation 'com.necer.ncalendar:ncalendar:4.3.8'//android
implementation 'com.necer.ncalendar:ncalendar:5.0.0'//androidx

日历Api: https://github.com/yannecer/NCalendar/wiki/%E6%97%A5%E5%8E%86Api
自定义属性: https://github.com/yannecer/NCalendar/wiki/%E8%87%AA%E5%AE%9A%E4%B9%89%E5%B1%9E%E6%80%A7

//miui9 和 钉钉日历, Miui10Calendar(米10) EmuiCalendar(华为) 用法类似
<com.necer.calendar.Miui9Calendar
	android:id="@+id/miui9Calendar"
	android:layout_width="match_parent"
	android:layout_height="match_parent">
	
	<androidx.recyclerview.widget.RecyclerView
		android:id="@+id/recyclerView"
		android:layout_width="match_parent"
		android:layout_height="match_parent" />
</com.necer.calendar.Miui9Calendar>



//日 一 二 三 四 五 六
<com.necer.view.WeekBar
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="10dp" />

//单个周日历(日历中的一行,包含今天)
   <com.necer.calendar.WeekCalendar
        android:layout_width="match_parent"
        android:layout_height="50dp" />

//单个月日历, 6行
<com.necer.calendar.MonthCalendar
    android:id="@+id/month_calendar"
    android:layout_width="match_parent"
    android:layout_height="210dp"
	app:isShowLunar="false"				//是否显示农历, 默认false
    app:hollowCircleColor="#2B7CFF"		//当选中的不是今天时, 选中天"圆圈"的颜色, 默认灰色
    app:pointLocation="down"			//标记日期位置 （up、down）??
    app:selectCircleColor="#2B7CFF"		//选中天"圆形背景"颜色, 默认绿色
	app:selectCircleRadius="16dp"		//选中半径
	app:pointDistance="3dp"				//标记点到文字中心的距离??
	app:solarTextSize="14sp"			//公历字体大小
	app:todaySolarTextColor="@color/color_main"//公历今字体颜色
	/>
<com.necer.calendar.MonthCalendar
    
    
    
     />

monthCalendar.toLastPager();	//上个月
monthCalendar.toNextPager();	//下个月












