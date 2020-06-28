package com.kuchuanyun.cpptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private int[] arr = new int[]{1, 2, 3, 4, 5};
    private Button btn;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
		
        //Java调C
        btn.setText(JavaCallC.stringFromJNI());
        btn.append("\n1+1=" + JavaCallC.add(1, 1));
        JavaCallC.add10(arr);//地址传递...
        btn.append("\n1-5数组每项+10=" + Arrays.toString(arr));

        //C调Java
        CCallJava.callVoid();
        CCallJava.staticMethodCalledVoid();//C调用Java的静态方法
    }
}
