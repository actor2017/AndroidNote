<com.contrarywind.view.WheelView
    android:id="@+id/min"
    android:layout_width="fill_parent"
    android:layout_height="wrap_content"
    android:layout_weight="1.1"
    //下方自定义属性, 不知道有什么卵用, 设置了没看见效果...感觉应该是单独使用的时候才起作用吧.
    app:wheelview_dividerColor="@color/red"
    app:wheelview_dividerWidth="1dp"
    app:wheelview_gravity="left"             //显示内容位置: center, left, right
    app:wheelview_lineSpacingMultiplier="2.5"//条目间距倍数, float, [1.0, 4.0]
    app:wheelview_textColorCenter="@color/blue_00b1ff"
    app:wheelview_textColorOut="@color/red"
    app:wheelview_textSize="20sp" />


private TimePickerView    timePickerView;

        Calendar startData = Calendar.getInstance();
        startData.set(1919, 1, 9);
        Calendar endData = Calendar.getInstance();
        Calendar showData = Calendar.getInstance();//显示哪一天
        showData.set(2000, 5, 6);
        timePickerView = new TimePickerBuilder(this, (date, v) -> {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            birthday = sdf.format(date);
            long day = TimeUtils.getTimeSpanByNow(date, TimeConstants.DAY);
            tvAge.setText(String.valueOf(Math.abs(day / 365)));
        }).setLayoutRes(R.layout.pickerview_custom_time, v -> {
            //如果虚线画不出来, 开启硬件加速
            v.findViewById(R.id.view1).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            v.findViewById(R.id.view2).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            View year = v.findViewById(R.id.year);
            View month = v.findViewById(R.id.month);
            View day = v.findViewById(R.id.day);
            resetItem(year);//通过反射, 设置显示5个item
            resetItem(month);
            resetItem(day);
            v.findViewById(R.id.tv_finish).setOnClickListener(v1 -> {
                timePickerView.returnData();
                timePickerView.dismiss();
            });
            v.findViewById(R.id.tv_cancel).setOnClickListener(v12 -> timePickerView.dismiss());
        }).setDividerColor(getResources().getColor(R.color.trans))
                .setDate(showData)//默认显示哪一天(设置在setRangDate时间范围之内, 否则判断的时候如果超出范围, 会显示startData)
                .setRangDate(startData, endData)//设置时间显示范围
				.setType(new boolean[]{true, true, true, true, true, false})//年月日时分秒
                .setLineSpacingMultiplier(2.5f)//设置间距倍数,但是只能在1.0-4.0f之间
				.setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {//时间选中监听
                        Calendar selectTime = Calendar.getInstance();//选中的时间
                        selectTime.setTime(date);
                        Calendar nowTime = Calendar.getInstance();//现在时间
                        if (nowTime.compareTo(selectTime) > 0) {//如果选择的时间在现在时间之前
                            timePickerView.setDate(nowTime);
                        }
                    }
                })
                .build();

timePickerView.show();


    //v4.1.8 版本这样设置,   v4.1.9 版本设置方法:
    //WheelView year = (WheelView) timePickerView.findViewById(R.id.year);
    //year.setItemsVisibleCount(5);
    private void resetItem(View view) {
        Class<? extends View> aClass = view.getClass();
        try {
            Field field = aClass.getDeclaredField("itemsVisible");
            field.setAccessible(true);
            field.setInt(view, 7);//通过反射, 设置显示7-2 = 5个item
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
