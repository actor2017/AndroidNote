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
		//在URI上注册一个内容观察者，只要这个uri上的数据库发生变化，所有注册了的内容观察者就会收到通知
		/**
		 * uri	：要观察的uri
		 * notifyForDescendents：true 以content://tian.wang.gai.di.hu/bao.ta.zhen.he.yao/开头
		 * 						false 精确匹配到某张表,大概这个意思.content://tian.wang.gai.di.hu/bao.ta.zhen.he.yao/index
		 * observer:观察者
		 */
		Uri uri = Uri.parse("content://tian.wang.gai.di.hu/bao.ta.zhen.he.yao");
		getContentResolver().registerContentObserver(
				uri, 
				true,
				new MyObserver(new Handler()));
		
	}

	/**
	 * 内容观察者
	 */
	private class MyObserver extends ContentObserver {
		private MyObserver(Handler handler) {
			super(handler);
		}
		//当数据库的表变化后回到
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			System.out.println("哈哈，被我逮到了。。。");
		}
	}
}
