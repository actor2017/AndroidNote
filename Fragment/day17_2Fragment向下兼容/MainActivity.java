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
		
		// 3. ����Fragment01����
		Fragment01 f1 = new Fragment01();
		/**
		 * 1. ��ȡfragment������
		 * ע��:��v4��,���д��getSupportFragmentManager()����,��Ҫд��getFragmentManager()
		 */
		// 
		FragmentManager fm =getSupportFragmentManager();
		// 2. ��ȡ����
		FragmentTransaction ft = fm.beginTransaction();
		// 4. ���ò���
		ft.replace(R.id.fl, f1);
		// 5. �ύ
		ft.commit();
	}
	
	/**
	 * @param ��΢�ŵ�TextView����ֵ
	 */
	public void send(View v){
		String text = etContent.getText().toString().trim();
		f1.setText(text);
	}

	/**
	 * ����f2���ݹ���������
	 */
	public void setText(String text){
		tv.setText(text);
	}
	
	/**
	 * ΢�ŵ�ҳ��
	 */
	public  void wc(View v){
		// 1. ��ȡfragment������
		FragmentManager fm = getSupportFragmentManager();
		// 2. ��ȡ����
		FragmentTransaction ft = fm.beginTransaction();
		// 3. ����Fragment01����
		
		f1 = new Fragment01();
		// 4. ���ò���
		ft.replace(R.id.fl, f1);
		// 5. �ύ
		ft.commit();
	}
	/**
	 * ͨѶ¼
	 */
	public void contacts(View v){
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment02 f2 = new Fragment02();
		ft.replace(R.id.fl, f2);
		ft.commit();
	}
	
	/**
	 * ����
	 */
	public void found(View v){
		Fragment03 f3 = new Fragment03();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.fl, f3);
		ft.commit();
	}
	
}
