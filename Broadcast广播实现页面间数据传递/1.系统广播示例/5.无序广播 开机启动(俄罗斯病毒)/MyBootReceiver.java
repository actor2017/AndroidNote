package org.androidpn.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kuchuan.wisdompolice.activity.MainActivity;

/**
 * 开机启动广播
 */
public class MyBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {

            System.out.println("哥哥，手机开机楼！");
            //开启MainActivity(显示意图)
            Intent intent2 = new Intent(context, MainActivity.class);
             //下面一句,把MainActivity放到任务栈中(否则会报错:刚启动,没有activity)
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);
        }
    }
}
