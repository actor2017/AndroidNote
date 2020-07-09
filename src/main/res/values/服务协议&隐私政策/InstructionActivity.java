package com.ly.bridgeemergency.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ly.bridgeemergency.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1."隐私政策"说明页面, 先在登录页显示Dialog提示, 再跳转过来
 *
 * 2."关于"页面的"隐私政策"也要跳转过来
 * <TextView
 *     android:id="@+id/tv_privaty_policy"
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     android:gravity="center"
 *     android:padding="10dp"
 *     android:text="隐私政策"
 *     android:textColor="#507fcf" />
 */
public class InstructionActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        ButterKnife.bind(this);

        tvTitle.setText("使用许可协议");
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
