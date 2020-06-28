package com.itheima.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment 是3.0以后的API  11
 * 
 */
public class Fragment01 extends Fragment {
	
	private TextView	tv;

	/**
	 * 相当于listview的getView方法
	 * 给fragment加载布局的
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_wc, null);
		tv = (TextView) view.findViewById(R.id.tv);
		
		return view;
	}

	/**
	 * 给textview设置文字
	 */
	public void setText(String text){
		tv.setText(text);
	}
}
