package com.itheima.boot;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * ÆÁ±Î·µ»Ø¼ü
	 */
	@Override
	public void onBackPressed() {
//		super.onBackPressed();
	}
}
