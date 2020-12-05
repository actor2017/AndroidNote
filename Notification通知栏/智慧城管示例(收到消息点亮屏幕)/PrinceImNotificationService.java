package com.kuchuanyun.wisdomcitymanagement.mobileimsdk;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

import com.actor.myandroidframework.service.BaseService;
import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.SPUtils;
import com.blankj.utilcode.util.NotificationUtils;
import com.blankj.utilcode.util.Utils;
import com.kuchuanyun.wisdomcitymanagement.R;
import com.kuchuanyun.wisdomcitymanagement.global.Global;

import net.openmob.mobileimsdk.android.core.LocalUDPDataSender;

/**
 * Created by Administrator on 2017/10/17.
 */
public class PrinceImNotificationService extends BaseService {
    private PowerManager pm;
    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate() {
        logError("开始创建Service---");
        String logincd = SPUtils.getString(Global.LOGIN_NUM);

        //
        // 确保MobileIMSDK被初始化哦（整个APP生生命周期中只需调用一次哦）
        if (!TextUtils.isEmpty(logincd)) {
            logError("开始登陆...");
            doLoginImpl();
        }
    }

    @Override
    protected void fitForegroundService() {
//        super.fitForegroundService();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationUtils.notify(1, new Utils.Consumer<NotificationCompat.Builder>() {
                @Override
                public void accept(NotificationCompat.Builder builder) {
                    RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.item_notification);
                    Intent intent1 = new Intent(PrinceImNotificationService.this, PrinceImNotificationService.class);
                    intent1.putExtra("command","CommandPlay");
                    PendingIntent pIntent1 =  PendingIntent.getService(PrinceImNotificationService.this,
                            5/*requestCode*/, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setOnClickPendingIntent(R.id.ll, pIntent1);
                    builder
                            .setSmallIcon(R.drawable.city_management)
                            .setCustomContentView(remoteViews)
                            .setVisibility(View.GONE);

                    //高版本要调用这句, 否则过几秒后会退出
                    startForeground(1, builder.build());
                }
            });
        } else {
            // TODO: 2020/5/19 适配低版本
        }
    }

    /**
     * 真正的登陆信息发送实现方法。
     */
    private void doLoginImpl() {
        // 异步提交登陆id和token
        new LocalUDPDataSender.SendLoginDataAsync(this,
                SPUtils.getString(Global.LOGIN_NUM) + "-app", SPUtils.getString(Global.LOGIN_NUM) + "-app") {
            /**
             * 登陆信息发送完成后将调用本方法（注意：此处仅是登陆信息发送完成
             * ，真正的登陆结果要在异步回调中处理哦）。
             *
             * @param code 数据发送返回码，0 表示数据成功发出，否则是错误码
             */
            @Override
            protected void fireAfterSendLogin(int code) {
                if (code == 0) {
                    logError("登陆信息提交成功:" + code);
                } else {
                    logError("登陆信息提交失败:" + code);
                }
            }
        }.execute();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        logError("onStart()...");
        super.onStart(intent, startId);
        //创建PowerManager对象
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        //保持cpu一直运行，不管屏幕是否黑屏
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPUKeepRunning");
        wakeLock.acquire();
    }

    @Override
    public void onDestroy() {
        logError("onDestroy()...");
        stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        logError("onBind()...");
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        logError("onRebind()...");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        logError("onUnbind()...");
        return true;
    }

    private void stop() {
        new AsyncTask<Object, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Object... params) {
                int code = -1;
                try {
                    code = LocalUDPDataSender.getInstance(PrinceImNotificationService.this)
                            .sendLoginout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logError("登出返回的CODE" + code);

                //## BUG FIX: 20170713 START by JackJiang
                // 退出登陆时记得一定要调用此行，不然不退出APP的情况下再登陆时会报 code=203错误哦！
                IMClientManager.getInstance(PrinceImNotificationService.this).resetInitFlag();
                //## BUG FIX: 20170713 END by JackJiang

                return code;
            }

            @Override
            protected void onPostExecute(Integer code) {
                if (code == 0)
                    logError("注销登陆请求已完成！");
                else
                    logError("注销登陆请求发送失败。错误码是：" + code + "！");
            }
        }.execute();
    }

    private void logError(String msg) {
        LogUtils.error(msg, false);
    }
}
