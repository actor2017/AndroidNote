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
		// 3. ����Fragment01����
		Fragment01 f1 = new Fragment01();
		// 1. ��ȡfragment������
		FragmentManager fm = getFragmentManager();
		// 2. ��ȡ����
		FragmentTransaction ft = fm.beginTransaction();
		// 4. ���ò���
		ft.replace(R.id.fl, f1);
		// 5. �ύ
		ft.commit();
	}
}
