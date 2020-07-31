package com.ly.bridgeemergency.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.actor.myandroidframework.dialog.BaseDialog;
import com.actor.myandroidframework.utils.SPUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.ly.bridgeemergency.R;
import com.ly.bridgeemergency.activity.InstructionActivity;

/**
 * description: 隐私政策
 *
 * //如果未显示过/未同意 隐私政策
 * if (!SPUtils.getBoolean(InstructionActivity.HAS_SHOW_PRIVACY_POLICY_DIALOG, false)) {
 *     new PrivacyPolicyDialog(this).show();//隐私政策
 * }
 *
 * @author : 李大发
 * date       : 2020/7/9 on 11:05
 * @version 1.0
 */
public class PrivacyPolicyDialog extends BaseDialog implements View.OnClickListener {

    private static final String appName = "桥梁应急";
    private static final String str1 = "感谢您信任并使用" + appName + "! 我们依据最新法律法规及监管要求, 更新了";
    private static final String link1 = "《" + appName + "隐私政策》";
    private static final String str2 = ", 请您务必仔细阅读并透彻理解相关内容, 在确认充分理解并同意后使用" +
            "代表您已阅读并同意" + appName + "隐私政策, 如果您不同意, 将可能影响" + appName + "的产品和服务。\n" +

            "我们将按法律法规要求, 采取相应安保措施, 尽力保护您的个人信息安全可控。";

    public PrivacyPolicyDialog(@NonNull Context context) {
        super(context);
        setCancelAble(false);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_privacy_policy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpannableStringBuilder ssb = new SpannableStringBuilder(str1, 0, str1.length()).append(link1);
        //添加点击
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ActivityUtils.startActivity(InstructionActivity.class);
            }
        }, str1.length(), ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置蓝色
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#507fcf"));
        ssb.setSpan(span, str1.length(), ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(str2);

        TextView tvContent = findViewById(R.id.tv_content);
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        tvContent.setText(ssb);
        findViewById(R.id.tv_dis_agree).setOnClickListener(this);
        findViewById(R.id.tv_agree).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dis_agree://不同意
                dismiss();
                Activity topActivity = ActivityUtils.getTopActivity();
                if (topActivity != null) {
                    topActivity.onBackPressed();
                }
                break;
            case R.id.tv_agree://同意
                SPUtils.putBoolean(InstructionActivity.HAS_SHOW_PRIVACY_POLICY_DIALOG, true);
                dismiss();
                break;
            default:
                break;
        }
    }
}
