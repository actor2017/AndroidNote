DatePicker֧��ֱ����������ֵ�������Ϳ��ԣ���������spinnerģʽ�£�

DatePicker��TimePicker��NumberPicker�ȿؼ�������Ĭ���ǿɱ༭�ģ����Իᾭ���������̡�Ҫ������Щ�༭ģʽֻ��Ҫ���´��룺
picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);//ViewGroup����, ���ߡ�
android:descendantFocusability="blocksDescendants"
��View��ViewGroup����.xml


    <DatePicker
        android:id="@+id/datePicker"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:firstDayOfWeek="1"//���ڼ����ڵ�1��,Ĭ��=0(������),ȡֵ0-7
        android:calendarViewShown="false"//��spinnerģʽʱ,�Ƿ���ʾ�Ҳ��calendar,Ĭ��true
        android:datePickerMode="calendar|spinner"//��ʾ������ʽ(Ĭ��calendar)

	//android:startYear="2016"//��������ѡ��������ѡ��ĵ�һ��
        //android:endYear="2017"//��������ѡ��������ѡ������һ��
        //android:minDate="string"//���ÿ�ѡ��С���ڣ���mm/dd/yyyy��ʽ����
        //android:maxDate="string"//���ÿ�ѡ������ڣ���mm/dd/yyyy��ʽ����
        //android:headerTextColor="@color/red"//datePickerMode=calendarʱ,����������ɫ
        //android:headerBackground="@color/red"//datePickerMode=calendarʱ,�������屳����ɫ/ͼƬ
        //android:headerMonthTextAppearance="@style/..."//datePickerMode=calendarʱ,��������
        //android:headerDayOfMonthTextAppearance="@style/..."//��ͷ����ı����
        //android:headerYearTextAppearance="@style/..."//��ͷ����ı����
        //android:dayOfWeekBackground="color"//���ڵı���ɫ
        //android:dayOfWeekTextAppearance="@style/..."//���ڵ��ı���С&��ɫ
        //android:yearListSelectorColor="@color/..."//�б������ѡ��ԲȦ����ɫ
        //android:yearListItemTextAppearance="@style/..."//�б�������ı����
        //android:calendarTextColor="@color/..."//�����б��е��ı���ɫ, ûЧ��?
        //android:spinnersShown="true">//��spinnerģʽʱ,�Ƿ���ʾ����spinner,Ĭ��true
    </DatePicker>


int year = dpDatePicker.getYear();
int month = dpDatePicker.getMonth();//0-11
int dayOfMonth = dpDatePicker.getDayOfMonth();


//��ʼ��������(�ɲ����ü���,ֱ��������ķ�����ȡ)
        dpDatePicker.init(year, month, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                println(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        });

//���ü���(Ϊʲô�ҵ���Ҫapi26??,����ǿ��������ʾû����������쳣...)
        dpDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                println(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        });