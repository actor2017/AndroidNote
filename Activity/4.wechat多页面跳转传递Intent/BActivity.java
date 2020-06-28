package com.example.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		Button btTOC  = (Button) this.findViewById(R.id.bt_to_C);
		btTOC.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent(BActivity.this, CActivity.class);
				startActivity(intent);
			}
		});
		int num = getIntent().getIntExtra("num", 0);
		TextView tvHint = (TextView) findViewById(R.id.tv_hint);
		tvHint.setText(num+" ");
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("B Activity ±ªèÕ”√¡À");
		setIntent(intent);
		int num = getIntent().getIntExtra("num", 0);
		TextView tvHint = (TextView) findViewById(R.id.tv_hint);
		tvHint.setText(num+" ");
		super.onNewIntent(intent);
	}
}
