
    //����ѡ�����Ĵ���
    @Override
    protected Dialog onCreateDialog(final int id) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,//1.���������StyleΪ��
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
        datePickerDialog.getDatePicker().setSpinnersShown(true);//2.Spinner��ʾ
        datePickerDialog.getDatePicker().setCalendarViewShown(true);//3.Calendar��ʾ
        return datePickerDialog;
    }