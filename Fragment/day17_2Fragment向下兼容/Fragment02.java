package com.itheima.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Fragment 是3.0以后的API  11
 * 通讯录页面
 */
public class Fragment02 extends Fragment {
	
	/**
	 * 相当于listview的getView方法
	 * 给fragment加载布局的
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contacts, null);
		final EditText etContent = (EditText) view.findViewById(R.id.et_content);
		Button btn = (Button) view.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = etContent.getText().toString().trim();
				//获取fragment锁依附的activity
				MainActivity activity = (MainActivity) getActivity();
				activity.setText(text);
			}
		});
		return view;
	}

}
