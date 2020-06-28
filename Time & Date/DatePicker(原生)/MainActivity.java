public class MainActivity extends Activity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText mEditText;
    // 定义5个记录当前时间的变量
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private  void initView(){
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        mEditText = (EditText)findViewById(R.id.show);
        // 获取当前的时间

       // Calendar ca = Calendar.getInstance();
        Calendar ca = Calendar.getInstance();

        year = ca.get(Calendar.YEAR);
        month = ca.get(Calendar.MONTH);
        day = ca.get(Calendar.DAY_OF_MONTH);
        hour = ca.get(Calendar.HOUR_OF_DAY);
        minute = ca.get(Calendar.MINUTE);
        Date d = new Date();
       d.setTime(System.currentTimeMillis());


        // 初始化DatePicker控件
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MainActivity.this.year = year;
                MainActivity.this.month = monthOfYear;
                MainActivity.this.day = dayOfMonth;
                // 显示当前时间
                showDate(year,month,day,hour,minute);
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                MainActivity.this.hour = hourOfDay;
                MainActivity.this.minute = minute;
                showDate(year,month,day,hour,minute);
            }
        });
    }

    // 显示当前时间
    private void showDate(int year,int month,int day,int hour,int minute){
        mEditText.setText("日期为:"+ year+"年"+(month+1)+"月"+day+"日"+hour+"时"+minute+"分");
    }
}