
    private int nowYear;
    private int nowMonth;
    private int nowDay;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pawn_register);
        ButterKnife.bind(this);

        //日历的日期选择
        Calendar calendar = Calendar.getInstance();//java.util.Calendar
        nowYear = calendar.get(Calendar.YEAR);
        nowMonth = calendar.get(Calendar.MONTH);//用来初始化的Month,So,这里不要+1
        nowDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    //日期选择器的创建
    @Override
    protected Dialog onCreateDialog(final int id) {
        return new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,//★★添加Style,这儿报错别管它★★
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String yue = month < 9 ? "" + 0 + (month + 1) : "" + (month + 1);//month
                String day = dayOfMonth < 10 ? "" + 0 + dayOfMonth : "" + dayOfMonth;
                switch (id) {
                    case 0:
                        tvYsrq.setText(year + "/" + yue + "/" + day);
                        break;
                }
            }
        }, nowYear, nowMonth, nowDay);
    }

	@OnClick({R.id.iv_back, R.id.tv_ysrq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_ysrq:
                showDialog(0);
                break;