package com.example.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity{

	private EditText etHint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		Button btTOB  = (Button) this.findViewById(R.id.bt_to_B);
		btTOB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent(MainActivity.this,BActivity.class);
				intent.putExtra("num", 1001);
				System.out.println("Mainí“ÃæµÄ "+intent.hashCode());
				startActivity(intent);
			}
		});
		
		
		if(savedInstanceState!=null){
			String hint = savedInstanceState.getString("hint");
			
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		//±£´æ”µ“þ
		outState.putString("hint", "hint");
		super.onSaveInstanceState(outState);
	}
	
	
}
