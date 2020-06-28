package com.itheima.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        if(!TextUtils.isEmpty(msg)) Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //显示通知栏
    public void click1(View view){
        Notification.Builder builder = new Notification.Builder(this)
                //状态栏
                .setTicker("正在播放：小苹果")
                .setSmallIcon(R.drawable.notification_music_playing)

                //通知栏
                .setContentTitle("小苹果")
                .setContentText("独孤求败献给在做的各位")
                .setWhen(System.currentTimeMillis());

        Notification notification = builder.build();

        //显示通知栏
        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,notification);
    }

    //移除通知栏
    public void click2(View v){
         nm.cancel(0);
    }

    //自定义通知栏
    public void click3(View v){
        Notification.Builder builder = new Notification.Builder(this)
                //状态栏
                .setTicker("正在播放：小苹果")
                .setSmallIcon(R.drawable.notification_music_playing)

                //通知栏
                .setContent(getRemoteView())
                .setContentIntent(getPendingIntent());

        Notification notification = builder.build();

        //显示通知栏
        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,notification);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("msg","点击通知栏");
        PendingIntent pi = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }

    /**
     * 获取自定义通知栏的布局
     */
    private RemoteViews getRemoteView() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_audio);
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_pre,getPrePendingIntent());
        remoteViews.setOnClickPendingIntent(R.id.iv_notify_next,getNextPendingIntent());
        return remoteViews;
    }

    private PendingIntent getPrePendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("msg","上一曲");
        PendingIntent pi = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }

    private PendingIntent getNextPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("msg","下一曲");
        PendingIntent pi = PendingIntent.getActivity(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }
}
