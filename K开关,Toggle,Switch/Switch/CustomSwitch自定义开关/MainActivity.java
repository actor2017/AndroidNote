package com.aswitch.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aswitch.R;
import com.aswitch.utils.ToastUtils;
import com.aswitch.view.CustomSwitch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomSwitch mSwitch = (CustomSwitch) findViewById(R.id.my_switch);
        mSwitch.setOnCheckChangeListener(new CustomSwitch.OnCheckChangeListener() {
            @Override
            public void onCheckChanged(View view, boolean isOpen) {
                ToastUtils.show(MainActivity.this, view + "开关状态: " + isOpen);
            }
        });
    }
}
