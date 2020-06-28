package com.example.frame;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		  ImageView rocketImage = (ImageView) findViewById(R.id.iv);
		  //�����и�imageview���ñ���
		  rocketImage.setBackgroundResource(R.drawable.anim);
		  
		  //��ȡimageview�ϵ�����ͼƬ
		  AnimationDrawable rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
		  
		  //��ʼ����֡����
		  rocketAnimation.start();

	}

}
//----------------------------------------------
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" >

    <ImageView
        android:id="@+id/iv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@drawable/anim" />

</LinearLayout>
