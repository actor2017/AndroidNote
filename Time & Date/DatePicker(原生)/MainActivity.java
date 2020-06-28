public class MainActivity extends Activity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText mEditText;
    // ����5����¼��ǰʱ��ı���
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
        // ��ȡ��ǰ��ʱ��

       // Calendar ca = Calendar.getInstance();
        Calendar ca = Calendar.getInstance();

        year = ca.get(Calendar.YEAR);
        month = ca.get(Calendar.MONTH);
        day = ca.get(Calendar.DAY_OF_MONTH);
        hour = ca.get(Calendar.HOUR_OF_DAY);
        minute = ca.get(Calendar.MINUTE);
        Date d = new Date();
       d.setTime(System.currentTimeMillis());


        // ��ʼ��DatePicker�ؼ�
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MainActivity.this.year = year;
                MainActivity.this.month = monthOfYear;
                MainActivity.this.day = dayOfMonth;
                // ��ʾ��ǰʱ��
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

    // ��ʾ��ǰʱ��
    private void showDate(int year,int month,int day,int hour,int minute){
        mEditText.setText("����Ϊ:"+ year+"��"+(month+1)+"��"+day+"��"+hour+"ʱ"+minute+"��");
    }
}