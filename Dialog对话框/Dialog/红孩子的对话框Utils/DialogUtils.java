package cn.itheima.redboy.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.itheima.redboy.R;

/**
 * Description: 红孩子的对话框工具
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * 用法:
 * 1.声明一个全局变量:private Dialog dialog;
 * 2.下面示例写法:
 * dialog = DialogUtils.createDialog(BigPhotosActivity.this, "登录提示","您还未登陆", new View.OnClickListener() {
 *     @Override
 *     public void onClick(View v) {
 *         boolean positive = (boolean) v.getTag();//判断是确定/取消按钮
 *         if (positive) {
 *             startActivity(new Intent(BigPhotosActivity.this,LogInActivity.class));
 *         }
 *         dialog.dismiss();
 *     }
 * });
 * dialog.show();
 */

public class DialogUtils {
    public static Dialog createDialog(Activity activity, String title, String content,
                                      View.OnClickListener onClickListener) {
        //指定对话框的样式
        Dialog dialog = new Dialog(activity, R.style.MyDialog);
        //加载对话框的布局文件
        View contentView = activity.getLayoutInflater().inflate(
                R.layout.dialog_view, null);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
        TextView tvContent = (TextView) contentView.findViewById(R.id.tvContent);
        tvTitle.setText(title);
        tvContent.setText(content);


        Button btnLeft = (Button) contentView.findViewById(R.id.btnLeft);
        Button btnRight = (Button) contentView.findViewById(R.id.btnRight);
        btnLeft.setTag(true);//用于区分左右两个按钮的点击
        btnRight.setTag(false);
        btnLeft.setOnClickListener(onClickListener);
        btnRight.setOnClickListener(onClickListener);

        //设置对话框的View对象
        dialog.setContentView(contentView);
        //设置对话框是否可取消
        dialog.setCancelable(true);
        //设置对话框的大小
        dialog.getWindow().setLayout(
              //activity.getResources().getDisplayMetrics().heightPixels:屏幕宽度
                activity.getResources().getDisplayMetrics().widthPixels * 3 / 4,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }
}
