DatePicker支持直接输入日期值（长按就可以，仅限于在spinner模式下）

DatePicker、TimePicker、NumberPicker等控件在由于默认是可编辑的，所以会经常跳出键盘。要屏蔽这些编辑模式只需要如下代码：
picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);//ViewGroup方法, 或者↓
android:descendantFocusability="blocksDescendants"
见View和ViewGroup属性.xml


    <DatePicker
        android:id="@+id/datePicker"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:firstDayOfWeek="1"//星期几排在第1列,默认=0(星期天),取值0-7
        android:calendarViewShown="false"//在spinner模式时,是否显示右侧的calendar,默认true
        android:datePickerMode="calendar|spinner"//显示哪种形式(默认calendar)

	//android:startYear="2016"//设置日期选择器允许选择的第一年
        //android:endYear="2017"//设置日期选择器允许选择的最后一年
        //android:minDate="string"//设置可选最小日期，以mm/dd/yyyy格式设置
        //android:maxDate="string"//设置可选最大日期，以mm/dd/yyyy格式设置
        //android:headerTextColor="@color/red"//datePickerMode=calendar时,顶部字体颜色
        //android:headerBackground="@color/red"//datePickerMode=calendar时,顶部字体背景颜色/图片
        //android:headerMonthTextAppearance="@style/..."//datePickerMode=calendar时,顶部字体
        //android:headerDayOfMonthTextAppearance="@style/..."//表头天的文本外观
        //android:headerYearTextAppearance="@style/..."//表头年的文本外观
        //android:dayOfWeekBackground="color"//日期的背景色
        //android:dayOfWeekTextAppearance="@style/..."//日期的文本大小&颜色
        //android:yearListSelectorColor="@color/..."//列表中年的选择圆圈的颜色
        //android:yearListItemTextAppearance="@style/..."//列表中年的文本外观
        //android:calendarTextColor="@color/..."//日历列表中的文本颜色, 没效果?
        //android:spinnersShown="true">//在spinner模式时,是否显示左侧的spinner,默认true
    </DatePicker>


int year = dpDatePicker.getYear();
int month = dpDatePicker.getMonth();//0-11
int dayOfMonth = dpDatePicker.getDayOfMonth();


//初始化并监听(可不设置监听,直接用上面的方法获取)
        dpDatePicker.init(year, month, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                println(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        });

//设置监听(为什么我的需要api26??,并且强制运行提示没有这个方法异常...)
        dpDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                println(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        });