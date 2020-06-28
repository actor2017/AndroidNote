package com.heima.mobilesafe_work.activity;
import android.view.animation.AnimationUtils;

    //这个按钮是butterKnife生成的点击事件
    findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhoneNum.getText().toString())) {
					ToastUtils.show(this,"请输入号码");
					//执行EditText左右晃动和手机振动
					Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
					etPhoneNum.startAnimation(animation);
				}
            }
        });
    }
}
