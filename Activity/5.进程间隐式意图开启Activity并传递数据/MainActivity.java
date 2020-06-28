package com.itheima.androidl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
//      Intent intent = new Intent();
//      intent.setAction("com.google.sample.fade");//Intent.ACTION_DIAL拨号
//		Intent intent = new Intent("com.google.sample.fade");
//      intent.setData(Uri.parse("callPhone://buzhidao"));	
//      intent.setType("text/xxx");//和上面方法冲突
//      intent.setDataAndType();//data和type共存...

		//							action, uri
		Intent intent = new Intent("com.google.sample.fade", Uri.parse("callPhone://buzhidao"));
        startActivity(intent);
    }
}
