package com.ithei.fbi;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.database.ContentObserver;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//��URI��ע��һ�����ݹ۲��ߣ�ֻҪ���uri�ϵ����ݿⷢ���仯������ע���˵����ݹ۲��߾ͻ��յ�֪ͨ
		/**
		 * uri	��Ҫ�۲��uri
		 * notifyForDescendents��true ��content://tian.wang.gai.di.hu/bao.ta.zhen.he.yao/��ͷ
		 * 						false ��ȷƥ�䵽ĳ�ű�,��������˼.content://tian.wang.gai.di.hu/bao.ta.zhen.he.yao/index
		 * observer:�۲���
		 */
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/bao.ta.zhen.he.yao");
		getContentResolver().registerContentObserver(
				uri, 
				true,
				new MyObserver(new Handler()));
		
	}

	/**
	 * ���ݹ۲���
	 */
	private class MyObserver extends ContentObserver {
		private MyObserver(Handler handler) {
			super(handler);
		}
		//�����ݿ�ı�仯��ص�
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			System.out.println("���������Ҵ����ˡ�����");
		}
	}
}
