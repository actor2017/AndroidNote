package com.itheima.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 3. 创建Fragment01对象
		Fragment01 f1 = new Fragment01();
		// 1. 获取fragment管理器
		FragmentManager fm = getFragmentManager();
		// 2. 获取事务
		FragmentTransaction ft = fm.beginTransaction();
		// 4. 设置布局
		ft.replace(R.id.fl, f1);
		// 5. 提交
		ft.commit();
	}
}
