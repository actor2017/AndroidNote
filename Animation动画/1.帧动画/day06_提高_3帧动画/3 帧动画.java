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
		  //代码中给imageview设置背景
		  rocketImage.setBackgroundResource(R.drawable.anim);
		  
		  //获取imageview上的所有图片
		  AnimationDrawable rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
		  
		  //开始播放帧动画
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
