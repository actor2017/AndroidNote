package com.heima.mobilesafe_work.activity;

import android.content.Intent;
import android.os.Bundle;

import com.heima.mobilesafe_work.R;

public class Setup3Activity extends BaseGestureDetector {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

    }

    @Override
    public void goToPre() {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_pre_enter,R.anim.anim_pre_exit);
    }

    @Override
    public void goToNext() {
        startActivity(new Intent(Setup3Activity.this,Setup4Activity.class));
        finish();
		//����������2��Activityҳ���л��Ķ���,��������ʶ����,�����Լӽ��������û�����
        overridePendingTransition(R.anim.anim_next_enter,R.anim.anim_next_exit);
    }
}
