package com.itheima.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private EditText	etContent;
	private Fragment01	f1;
	private TextView	tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//====================================================
		etContent = (EditText) findViewById(R.id.et_content);
		tv = (TextView) findViewById(R.id.tv);
		
		// 3. 创建Fragment01对象
		Fragment01 f1 = new Fragment01();
		/**
		 * 1. 获取fragment管理器
		 * 注意:导v4包,这儿写成getSupportFragmentManager()兼容,不要写成getFragmentManager()
		 */
		// 
		FragmentManager fm =getSupportFragmentManager();
		// 2. 获取事务
		FragmentTransaction ft = fm.beginTransaction();
		// 4. 设置布局
		ft.replace(R.id.fl, f1);
		// 5. 提交
		ft.commit();
	}
	
	/**
	 * @param 给微信的TextView设置值
	 */
	public void send(View v){
		String text = etContent.getText().toString().trim();
		f1.setText(text);
	}

	/**
	 * 接受f2传递过来的数据
	 */
	public void setText(String text){
		tv.setText(text);
	}
	
	/**
	 * 微信的页面
	 */
	public  void wc(View v){
		// 1. 获取fragment管理器
		FragmentManager fm = getSupportFragmentManager();
		// 2. 获取事务
		FragmentTransaction ft = fm.beginTransaction();
		// 3. 创建Fragment01对象
		
		f1 = new Fragment01();
		// 4. 设置布局
		ft.replace(R.id.fl, f1);
		// 5. 提交
		ft.commit();
	}
	/**
	 * 通讯录
	 */
	public void contacts(View v){
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment02 f2 = new Fragment02();
		ft.replace(R.id.fl, f2);
		ft.commit();
	}
	
	/**
	 * 发现
	 */
	public void found(View v){
		Fragment03 f3 = new Fragment03();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.fl, f3);
		ft.commit();
	}
	
}
