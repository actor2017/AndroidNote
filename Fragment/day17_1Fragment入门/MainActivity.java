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
		// 3. ����Fragment01����
		Fragment f1 = new FragmentWc();
		// 1. ��ȡfragment������
		FragmentManager fm = getFragmentManager();
		// 2. ��ȡ����
		FragmentTransaction ft = fm.beginTransaction();
		// 4. ���ò���
		ft.replace(R.id.fl, f1);
		// 5. �ύ
		ft.commit();
	}

	/**
	 * ΢�ŵ�ҳ��
	 */
	public  void wc(View v){
		// 1. ��ȡfragment������
		FragmentManager fm = getFragmentManager();
		// 2. ��ȡ����
		FragmentTransaction ft = fm.beginTransaction();
		// 3. ����Fragment01����
		FragmentWc f1 = new FragmentWc();
		// 4. ���ò���
		ft.replace(R.id.fl, f1);
		// 5. �ύ
		ft.commit();
	}
	/**
	 * ͨѶ¼
	 */
	public void contacts(View v){
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		FragmentCntacts f2 = new FragmentCntacts();
		ft.replace(R.id.fl, f2);
		ft.commit();
	}
	
	/**
	 * ����2��ҳ����д
	 */
	public void found(View v){
		//1.����fragment����
		Fragment fragment_found = new FragmentFound();
		//2.��ȡfragment������
		FragmentManager fm = getFragmentManager();
		//3.��ȡ����
		FragmentTransaction ft = fm.beginTransaction();
		//4.���ò���
		ft.replace(R.id.fl, fragment_found);
		//5.���
		ft.commit();
	}
	
	public void me(View v){
		//1.����fragment����
		Fragment fme = new FragmentMe();
		//2.��ȡfragment������
		FragmentManager fm = getFragmentManager();
		//3.��ȡ����
		FragmentTransaction ft = fm.beginTransaction();
		//4.���ò���
		ft.replace(R.id.fl, fme);
		//5.�ύ
		ft.commit();
	}
}
