
mSwitchCompat = (SwitchCompat) findViewById(R.id.sc_switch_compat);
        mSwitchCompat.setChecked(false);
        mSwitchCompat.setOnCheckedChangeListener(this);
        mSwitchCompat2 = (SwitchCompat) findViewById(R.id.sc_switch_compat2);
        mSwitchCompat2.setChecked(false);
        mSwitchCompat2.setOnCheckedChangeListener(this);
        mSwitch2 = (Switch) findViewById(R.id.s_switch);
        mSwitch2.setChecked(false);
        mSwitch2.setOnCheckedChangeListener(this);

    @Override
    public void onCheckedChanged(CompoundButton button, boolean checked) {
        if (checked) {        
//         做我们要实现的一些操作
            mDayNightHelper.setMode(DayNight.NIGHT);
            setTheme(R.style.NightTheme);
            switchMode();
            mText.setText("Night");
            mSwitchCompat.setChecked(true);
            mSwitchCompat2.setChecked(true);
            mSwitch2.setChecked(true);
        } else {

            mDayNightHelper.setMode(DayNight.DAY);
            setTheme(R.style.DayTheme);
            switchMode();
            mText.setText("Day");
            mSwitchCompat.setChecked(false);
            mSwitchCompat2.setChecked(false);
            mSwitch2.setChecked(false);
        }
    }