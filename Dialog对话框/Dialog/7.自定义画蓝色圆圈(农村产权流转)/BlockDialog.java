package com.yys.land.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.actor.myandroidframework.dialog.BaseDialog;
import com.blankj.utilcode.util.SizeUtils;
import com.yys.land.R;

/**
 * description: 加载中...
 * company    : 重庆元山元科技有限公司 http://www.ysytech.net/
 * @author    : 李大发
 * date       : 2020/10/5 on 20:02
 */
public class BlockDialog extends BaseDialog {

    private static final int DEFAULT_WIDTH  = 160;//dp
    private static final int DEFAULT_HEIGHT = 120;//dp

    public BlockDialog(Context context) {
        this(context, false);//点击外部不能取消
    }

    public BlockDialog(Context context, boolean outCancle) {
        super(context);
        setCanceledOnTouchOutside(outCancle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_block_dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = SizeUtils.dp2px(DEFAULT_WIDTH);
        params.height = SizeUtils.dp2px(DEFAULT_HEIGHT);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }
}
