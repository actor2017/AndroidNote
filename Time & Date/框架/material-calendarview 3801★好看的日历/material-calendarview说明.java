https://github.com/prolificinteractive/material-calendarview
A Material design back port of Android's CalendarView

Usage
1.Add compile 'com.prolificinteractive:material-calendarview:1.4.3' to your dependencies.
  �������:class file for org.threeten.bp.LocalDate not found, ��Ҫ����:
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

private CalendarDay currentDate;//�Զ�������ڶ���

//        calendarView.getSelectedDate();//��ȡ��ѡ�е�����
        //ѡ��ģʽ������һ��ѡ�������ڡ�
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {//����ѡ�����ڵı仯
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                logError("����ѡ�����ڵı仯setOnDateChangedListener:====" + date.getYear() + "===" + date.getMonth());
                //���ڵ��֮���Ĭ��ѡ��/ȡ��ѡ��,����������ѡ��ȥ...
                calendarView.setDateSelected(date, !selected);
            }
        });
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {//�·ݸı����(��ģʽ�°��ܸı�?)
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                logError("�·ݸı����onMonthChanged:====" + date.getYear() + "===" + date.getMonth());
                getSighRecord(date.getYear(), date.getMonth());
            }
        });

calendarView.clearSelection();//���ѡ��
calendarView.setDateSelected(currentDate, true);
calendarView.setDateSelected(CalendarDay.from(dataBean.year, dataBean.month, dataBean.day), isSign);
calendarView.setSelectedDate(CalendarDay.today());//�����ѡ��, Ȼ��ѡ��ָ������
