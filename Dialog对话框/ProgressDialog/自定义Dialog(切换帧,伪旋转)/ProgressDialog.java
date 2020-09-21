package com.cmcc.yyn.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.actor.myandroidframework.dialog.BaseDialog;
import com.cmcc.yyn.R;

/**
 * description: 加载对话框(切换帧,伪旋转)
 *
 * @author : 李大发
 * date       : 2020/8/28 on 18:00
 * @version 1.0
 */
public class ProgressDialog extends BaseDialog {

    TextView     tvTips;
    CharSequence tips;

    public ProgressDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * @param tips 提示
     */
    public ProgressDialog(@NonNull Context context, CharSequence tips) {
        super(context);
        this.tips = tips;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_progress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTips = findViewById(R.id.progress_dialog_message);
        if (TextUtils.isEmpty(tips)) {
            tvTips.setVisibility(View.GONE);
        } else {
            tvTips.setVisibility(View.VISIBLE);
            tvTips.setText(tips);
        }
    }

    /**
     * @param tips 提示
     */
    public void setTips(CharSequence tips) {
        this.tips = tips;
    }
}
