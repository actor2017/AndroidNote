package com.itheima.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//==========================================
		// 3. 创建Fragment01对象
		Fragment f1 = new FragmentWc();
		// 1. 获取fragment管理器
		FragmentManager fm = getFragmentManager();
		// 2. 获取事务
		FragmentTransaction ft = fm.beginTransaction();
		// 4. 设置布局
		ft.replace(R.id.fl, f1);
		// 5. 提交
		ft.commit();
	}

	/**
	 * 微信的页面
	 */
	public  void wc(View v){
		// 1. 获取fragment管理器
		FragmentManager fm = getFragmentManager();
		// 2. 获取事务
		FragmentTransaction ft = fm.beginTransaction();
		// 3. 创建Fragment01对象
		FragmentWc f1 = new FragmentWc();
		// 4. 设置布局
		ft.replace(R.id.fl, f1);
		// 5. 提交
		ft.commit();
	}
	/**
	 * 通讯录
	 */
	public void contacts(View v){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		FragmentCntacts f2 = new FragmentCntacts();
		ft.replace(R.id.fl, f2);
		ft.commit();
	}
	
	/**
	 * 下面2个页面自写
	 */
	public void found(View v){
		//1.创建fragment对象
		Fragment fragment_found = new FragmentFound();
		//2.获取fragment管理器
		FragmentManager fm = getFragmentManager();
		//3.获取事物
		FragmentTransaction ft = fm.beginTransaction();
		//4.设置布局
		ft.replace(R.id.fl, fragment_found);
		//5.提价
		ft.commit();
	}
	
	public void me(View v){
		//1.创建fragment对象
		Fragment fme = new FragmentMe();
		//2.获取fragment管理器
		FragmentManager fm = getFragmentManager();
		//3.获取事物
		FragmentTransaction ft = fm.beginTransaction();
		//4.设置布局
		ft.replace(R.id.fl, fme);
		//5.提交
		ft.commit();
	}
}
