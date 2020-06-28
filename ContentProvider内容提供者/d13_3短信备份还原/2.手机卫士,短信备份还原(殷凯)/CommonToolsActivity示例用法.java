package com.heima.mobilesafe_work.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.heima.mobilesafe_work.R;
import com.heima.mobilesafe_work.utils.SmsUtils;
import com.heima.mobilesafe_work.utils.ToastUtils;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 常用工具页面
 */
public class CommonToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_toos);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.rl_queryAddress, R.id.rl_queryPhoneNum, R.id.siv_sms_bacnup, R.id
            .siv_sms_restore, R.id.siv_applock, R.id.siv_locktoggle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_queryAddress:
                //跳到查询地址界面
                startActivity(new Intent(CommonToolsActivity.this, AddressActivity.class));
                break;
            case R.id.rl_queryPhoneNum:
                //跳到常用号码查询界面
                startActivity(new Intent(this, CommonNumberActivity.class));
                break;
            case R.id.siv_sms_bacnup:
                //短信备份
                backupSms();
                break;
            case R.id.siv_sms_restore:
                //短信还原
                restoreSms();
                break;
            case R.id.siv_applock:
                //跳到程序锁管理界面
                break;
            case R.id.siv_locktoggle:
                //开启/关闭程序锁
                break;
        }
    }

    //备份短信
    private void backupSms() {
        //1.创建进度条对话框
        final ProgressDialog progressDialog = new ProgressDialog(this);
        //2.设置进度条对话款 的标题
        progressDialog.setTitle("短信备份中,请稍候...");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);//水平进度条
        progressDialog.setCanceledOnTouchOutside(false);        //按对话框外面不能取消对话框
        progressDialog.show();
        //data/data/com.heima.mobilesafe_work/files/smsBackUp.backup
        System.out.println(SmsUtils.defaultbackupPath(this).toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SmsUtils.SmsBackup(CommonToolsActivity.this, SmsUtils.defaultbackupPath
                                    (CommonToolsActivity.this), new SmsUtils.OnSmsCallback() {

                                int smscount = 0;
                                @Override
                                public void getSmsCount(int smscount) {
                                    this.smscount = smscount;
                                    progressDialog.setMax(smscount);
                                }

                                @Override
                                public void getSmsProgress(int smsprogress) {
                                    progressDialog.setProgress(smsprogress);
                                    if (smsprogress == smscount) {
                                        progressDialog.dismiss();
                                        ToastUtils.showDdfault(CommonToolsActivity.this, "备份完成!");
                                    }
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtils.showDdfault(CommonToolsActivity.this,"备份失败!");
                }
            }
        }).start();
    }

    //还原短信
    private void restoreSms() {
        //1.创建进度条对话框
        final ProgressDialog progressDialog = new ProgressDialog(this);
        //2.设置进度条对话款 的标题
        progressDialog.setTitle("短信还原中,请稍候...");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);//水平进度条
        progressDialog.setCanceledOnTouchOutside(false);        //按对话框外面不能取消对话框
        progressDialog.show();
        //data/data/com.heima.mobilesafe_work/files/smsBackUp.backup
        System.out.println(SmsUtils.defaultbackupPath(this).toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SmsUtils.SmsRestore(CommonToolsActivity.this, SmsUtils.defaultbackupPath
                            (CommonToolsActivity.this), new SmsUtils.OnSmsCallback() {

                        int smscount = 0;
                        @Override
                        public void getSmsCount(int smscount) {
                            this.smscount = smscount;
                            progressDialog.setMax(smscount);
                        }

                        @Override
                        public void getSmsProgress(int smsprogress) {
                            progressDialog.setProgress(smsprogress);
                            if (smsprogress == smscount) {
                                progressDialog.dismiss();
                                ToastUtils.showDdfault(CommonToolsActivity.this, "还原完成!");
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtils.showDdfault(CommonToolsActivity.this,"还原失败!");
                }
            }
        }).start();
    }
}
