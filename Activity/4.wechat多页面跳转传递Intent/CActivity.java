package com.example.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main3);
		Button btTOD  = (Button) this.findViewById(R.id.bt_to_D);
		btTOD.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent(CActivity.this,DActivity.class);				
				startActivity(intent);
			}
		});
	}
	@Override
	protected void onDestroy() {
		Intent intent  = new Intent(CActivity.this,BActivity.class);
		intent.putExtra("num", 1003);
		startActivity(intent);
		super.onDestroy();
	}
}
