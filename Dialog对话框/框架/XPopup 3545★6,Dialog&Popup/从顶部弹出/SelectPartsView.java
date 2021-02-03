package com.ysytech.zhongjiao.widget;

import android.content.Context;

import androidx.annotation.NonNull;

import com.actor.myandroidframework.utils.TextUtils2;
import com.actor.myandroidframework.widget.ItemTextInputLayout;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.ysytech.zhongjiao.R;

/**
 * description: 从顶部弹出, 选择零部件
 * company    : 重庆元山元科技有限公司 http://www.ysytech.net/
 *
 * @author : 李大发
 * date       : 2021/1/14 on 09
 * @version 1.0
 */
public class SelectPartsView extends PartShadowPopupView {

    private ItemTextInputLayout itilXtmc;
    private ItemTextInputLayout itilSbmc;
    private ItemTextInputLayout itilLbjmc;

    private OnYesClickListener listener;

    public SelectPartsView(@NonNull Context context, OnYesClickListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.view_select_parts;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        itilXtmc = findViewById(R.id.itil_xtmc);
        itilSbmc = findViewById(R.id.itil_sbmc);
        itilLbjmc = findViewById(R.id.itil_lbjmc);
        //重  置
        findViewById(R.id.tv_reset).setOnClickListener(v -> {
            itilXtmc.setText("");
            itilSbmc.setText("");
            itilLbjmc.setText("");
        });
        //确 定
        findViewById(R.id.tv_yes).setOnClickListener(v -> {
            if (listener != null) {
                listener.onYesClicked(getText(itilXtmc), getText(itilSbmc), getText(itilLbjmc));
            }
            dismiss();
        });
    }

    public interface OnYesClickListener {
        void onYesClicked(String sysName, String deviceName, String partsName);
    }

    private String getText(ItemTextInputLayout itil) {
        return TextUtils2.getText(itil);
    }
}
