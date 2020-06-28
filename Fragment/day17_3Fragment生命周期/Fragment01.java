package com.itheima.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment ��3.0�Ժ��API Level 11
 * 
 */
public class Fragment01 extends Fragment {
	
	private static final String TAG = "Fragment01";
	
	/**
	 * fragment��һ������activity��ʱ�����
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.e(TAG, "onAttach");
	}

	/**
	 * ��ʼ��fragment
	 *  onAttach(Activity) ֮��
	 *  onCreateView(LayoutInflater, ViewGroup, Bundle)֮ǰ 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "onCreate");
	}

	/**
	 * �൱��listview��getView����
	 * ��fragment���ز��ֵ�
	 * onCreate(Bundle) ֮��
	 *  onActivityCreated(Bundle)֮ǰ
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_wc, null);
		Log.e(TAG, "onCreateView");
		return view;
	}
	
	/**
	 * activity���������
	 *  onCreateView ֮��
	 *  onStart()֮ǰ
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.e(TAG, "onActivityCreated");
	}
	/**
	 * fragment���Ӻ����
	 */
	@Override
	public void onStart() {
		super.onStart();
		Log.e(TAG, "onStart");
	}
	/**
	 * fragment��ȡ��������
	 */
	@Override
	public void onResume() {
		super.onResume();
		Log.e(TAG, "onResume");
	}
	/**
	 * ʧȥ����ǰ����
	 */
	@Override
	public void onPause() {
		super.onPause();
		Log.e(TAG, "onPause");
	}
	/**
	 * ������ǰ����
	 */
	@Override
	public void onStop() {
		super.onStop();
		Log.e(TAG, "onStop");
	}
	/**
	 * fragment��������activity��
	 * �Ƴ�����
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.e(TAG, "onDestroyView");
	}
	/**
	 * fragment����ǰ����
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
	/**
	 * framgent��������activity��
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		Log.e(TAG, "onDetach");
	}


}
