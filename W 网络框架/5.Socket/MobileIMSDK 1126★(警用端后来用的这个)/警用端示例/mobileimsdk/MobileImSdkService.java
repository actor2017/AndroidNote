package com.ly.changyi.mobileimsdk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ly.changyi.application.MyApplication;
import com.ly.changyi.constant.Global;
import com.ly.changyi.utils.SPUtils;

import net.openmob.mobileimsdk.android.core.LocalUDPDataSender;

public class MobileImSdkService extends Service {

    @Override
    public void onCreate() {
        // 确保MobileIMSDK被初始化（整个APP生生命周期中只需调用一次）
        println("开始登陆...");
//        String user = Global.APP_KEY + "_" + SPUtils.getString(Global.LOGINCD);//ZHJW_101212419
        int userId = SPUtils.getInt(Global.USER_ID, -1);
        String token = SPUtils.getString(Global.TOKEN);
        // 异步提交登陆id和token
        new LocalUDPDataSender.SendLoginDataAsync(this, String.valueOf(userId), token) {//user, user
            /**
             * 登陆信息发送完成后将调用本方法（注意：此处仅是登陆信息发送完成
             * ，真正的登陆结果要在异步回调中处理哦）。
             *
             * @param code 数据发送返回码，0 表示数据成功发出，否则是错误码
             */
            @Override
            protected void fireAfterSendLogin(int code) {
                if (code == 0) {
                    println("登陆信息提交成功:" + code);
                } else println("登陆信息提交失败:" + code);
            }
        }.execute();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stop();
    }

    private void stop() {
        new Thread(() -> {
            int code = -1;
            try {
                code = LocalUDPDataSender.getInstance(MobileImSdkService.this).sendLoginout();
            } catch (Exception e) {
                e.printStackTrace();
            }
            println("退出返回码:" + code);

            //## BUG FIX: 20170713 START by JackJiang
            // 退出登陆时记得一定要调用此行，不然不退出APP的情况下再登陆时会报 code=203错误
            IMClientManager.getInstance().resetInitFlag();
            //## BUG FIX: 20170713 END by JackJiang

            if (code == 0) {
                println("注销登陆请求已完成！");
            } else println("注销登陆请求发送失败。错误码是：" + code + "！");
        }).start();
//        new AsyncTask<Object, Integer, Integer>() {
//            @Override
//            protected Integer doInBackground(Object... params) {
//                int code = -1;
//                try {
//                    code = LocalUDPDataSender.getInstance(MobileImSdkService.this).sendLoginout();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                println("退出返回码:" + code);
//
//                //## BUG FIX: 20170713 START by JackJiang
//                // 退出登陆时记得一定要调用此行，不然不退出APP的情况下再登陆时会报 code=203错误
//                IMClientManager.getInstance().resetInitFlag();
//                //## BUG FIX: 20170713 END by JackJiang
//
//                return code;
//            }
//
//            @Override
//            protected void onPostExecute(Integer code) {
//                if (code == 0) {
//                    println("注销登陆请求已完成！");
//                } else println("注销登陆请求发送失败。错误码是：" + code + "！");
//            }
//        }.execute();
    }

    private void println(String msg) {
        if (MyApplication.instance.isDebugMode) {
            System.out.println(getClass().getSimpleName() + ":" + msg);
        }
    }
}
