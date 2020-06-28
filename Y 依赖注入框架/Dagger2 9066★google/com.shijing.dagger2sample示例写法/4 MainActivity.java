package com.shijing.dagger2sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    //这儿必须写inject的注解
    @Inject
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.tv);

        //示例写法:
        MainActivityCommpont commpont = DaggerMainActivityCommpont
                .builder()
                .mainActivityModule(new MainActivityModule())
                .build();
        commpont.inject(this);

        //示例用法:
        System.out.println(user.toString());//User{name='张三', age=23}
        tv.setText(user.toString());
    }
}
