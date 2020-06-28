
    private int nowYear;
    private int nowMonth;
    private int nowDay;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pawn_register);
        ButterKnife.bind(this);

        //����������ѡ��
        Calendar calendar = Calendar.getInstance();//java.util.Calendar
        nowYear = calendar.get(Calendar.YEAR);
        nowMonth = calendar.get(Calendar.MONTH);//������ʼ����Month,So,���ﲻҪ+1
        nowDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    //����ѡ�����Ĵ���
    @Override
    protected Dialog onCreateDialog(final int id) {
        return new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,//������Style,��������������
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