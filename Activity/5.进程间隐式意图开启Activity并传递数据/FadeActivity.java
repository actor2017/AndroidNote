package com.itheima.androidl.taransitions;

import android.os.Bundle;

import com.itheima.androidl.R;

public class FadeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fade);

        Intent intent = getIntent();
        System.out.println(intent.getDataString());             //callPhone://buzhidao
        Uri data = intent.getData();
        System.out.println("Scheme:" + data.getScheme());       //callPhone
        System.out.println("Authority:" + data.getAuthority()); //buzhidao
        System.out.println("Host:" + data.getHost());           //buzhidao
        System.out.println("Port:" + data.getPort());           //-1
        System.out.println("Path:" + data.getPath());           //
        System.out.println("Query:" + data.getQuery());         //null
		//... ...
    }
}
