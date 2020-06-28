package org.androidpn.client;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.kuchuan.wisdompolice.R;
import com.kuchuan.wisdompolice.activity.MainActivity;
import com.kuchuan.wisdompolice.application.MyApplication;

/**
 * 监听电池电量发生改变
 */
public class BatteryService extends Service {
    public BatteryService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter batteryfilter = new IntentFilter();
        batteryfilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, batteryfilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.icon_smile);
        builder.setContentTitle("智慧警务");
        builder.setContentText("正在运行中");
        //builder.setContentInfo("Content Info");
        builder.setWhen(System.currentTimeMillis());
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        //在onStartCommand里面调用 startForeground
        startForeground(9527, notification);//id 唯一的通知标识
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }

    // 接收电池信息更新的广播
    private BroadcastReceiver batteryReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
			int level = intent.getIntExtra("level", -1);//0~100
            LogUtils.e(TAG, "系统电量：" + level);
            if (!ServiceStateUtils.isServiceRunning(MyApplication.applicationContext,NotificationService.class)) {
                Intent intent2 = NotificationService.getIntent();
                //本例中必须加下面这句,否则崩溃
                intent2.setPackage(context.getPackageName());
                context.startService(intent2);
            }
            ;
        }
    };
}
