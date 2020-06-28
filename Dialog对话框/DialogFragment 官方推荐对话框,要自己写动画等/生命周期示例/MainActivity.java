package com.kuchuanyun.cpptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements LoginInputListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentEx dialogFragment = new DialogFragmentEx();
                dialogFragment.show(getSupportFragmentManager(), "EditNameDialog");//第2参数tag
            }
        });
    }

	//实现接口,获取Fragment回调数据
    @Override
    public void onLoginInputComplete(String userName, String password) {
        System.out.println(userName + ":" + password);
    }
}
