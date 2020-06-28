package com.itheima.bank;

import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;

public class MainActivity extends Activity {

    private SQLiteDatabase	db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		BankDbOpenHelper helper = new BankDbOpenHelper(getContext());
		db = helper.getWritableDatabase();
		
		Cursor cursor = db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
		long id = db.insert("account", null, values);
		int id = db.delete("account", selection, selectionArgs);
		int id = db.update("account", values, selection, selectionArgs);
	}
}
