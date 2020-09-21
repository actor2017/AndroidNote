https://github.com/MagicMashRoom/SuperCalendar
https://www.jianshu.com/p/8e3fc46e5a80

android �Զ��������ؼ� ֧���������޻��� �����л� ���������ʾ �Զ�����ʾЧ����ת��ָ������

implementation 'com.github.MagicMashRoom:SuperCalendar:1.6'

//ֻ��1��View��д��xml��
<com.ldf.calendar.view.MonthPager
	android:id="@+id/calendar_view"
	android:layout_width="match_parent"
	android:layout_height="300dp"
	android:background="#fff">
</com.ldf.calendar.view.MonthPager>


private CalendarViewAdapter calendarAdapter;
private CalendarDate currentDate;

currentDate = new CalendarDate();
tvYear.setText(currentDate.getYear() + "��");
tvMonth.setText(currentDate.getMonth() + "");
CustomDayView customDayView = new CustomDayView(context, R.layout.custom_day);//�Զ������layout
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



//TextView���, �л�����
scrollSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK) {//������ʾ WEEK ģʽ, ֻ��1��
                    Utils.scrollTo(content, rvToDoList, monthPager.getViewHeight(), 200);
                    calendarAdapter.switchToMonth();
                } else {
                    Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);//������ʾ MONTH ģʽ, ��ʾȫ
                    calendarAdapter.switchToWeek(monthPager.getRowIndex());//�л�����ʾģʽ, ��ʾ��ǰѡ�������һ��
                }
            }
        });

//�Զ��嵱ǰѡ����ı���
ThemeDayView themeDayView = new ThemeDayView(context, R.layout.custom_day_focus);
calendarAdapter.setCustomDayRenderer(themeDayView);
calendarAdapter.notifyDataSetChanged();
calendarAdapter.notifyDataChanged(new CalendarDate());


monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);//�л���һ��
monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);//�л���һ��

//ƫ���� -1��ʾˢ�³���һ�������� �� 1��ʾˢ�³���һ��������
monthPager.selectOtherMonth(offset);


