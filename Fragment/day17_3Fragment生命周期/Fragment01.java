package com.itheima.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment 是3.0以后的API Level 11
 * 
 */
public class Fragment01 extends Fragment {
	
	private static final String TAG = "Fragment01";
	
	/**
	 * fragment第一次依附activity的时候调用
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.e(TAG, "onAttach");
	}

	/**
	 * 初始化fragment
	 *  onAttach(Activity) 之后
	 *  onCreateView(LayoutInflater, ViewGroup, Bundle)之前 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "onCreate");
	}

	/**
	 * 相当于listview的getView方法
	 * 给fragment加载布局的
	 * onCreate(Bundle) 之后
	 *  onActivityCreated(Bundle)之前
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_wc, null);
		Log.e(TAG, "onCreateView");
		return view;
	}
	
	/**
	 * activity创建后调用
	 *  onCreateView 之后
	 *  onStart()之前
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e(TAG, "onActivityCreated");
	}
	/**
	 * fragment可视后调用
	 */
	@Override
	public void onStart() {
		super.onStart();
		Log.e(TAG, "onStart");
	}
	/**
	 * fragment获取焦点后调用
	 */
	@Override
	public void onResume() {
		super.onResume();
		Log.e(TAG, "onResume");
	}
	/**
	 * 失去焦点前调用
	 */
	@Override
	public void onPause() {
		super.onPause();
		Log.e(TAG, "onPause");
	}
	/**
	 * 不可视前调用
	 */
	@Override
	public void onStop() {
		super.onStop();
		Log.e(TAG, "onStop");
	}
	/**
	 * fragment不在依附activity了
	 * 移除布局
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.e(TAG, "onDestroyView");
	}
	/**
	 * fragment销毁前调用
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
	/**
	 * framgent不在依附activity了
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		Log.e(TAG, "onDetach");
	}


}
