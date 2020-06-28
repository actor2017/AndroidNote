package com.baidu.bce.livecamera.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.support.v4.net.ConnectivityManagerCompat;
 

public class NetworkUtils {
	
	//获取本机ip
	public static String getIpMime() {
		try {
			InetAddress host=InetAddress.getLocalHost();//获得本机的InetAddress对象 ，回送IP地址
			return host.getHostAddress();			//获得IP（以文本表现形式）
		} catch (IOException e) {
			e.printStackTrace();
			return "127.0.0.1";
		}
	}
 
    /**
     * 检查当前WIFI是否连接，两层意思——是否连接，连接是不是WIFI
     * 
     * @param context
     * @return true表示当前网络处于连接状态，且是WIFI，否则返回false
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()
                && ConnectivityManager.TYPE_WIFI == info.getType()) {
            return true;
        }
        return false;
    }
 
    /**
     * 检查当前移动数据是否连接，两层意思——是否连接，连接是不是移动数据
     * 
     * @param context
     * @return true表示当前网络处于连接状态，且是移动数据，否则返回false
     */
    public static boolean isMobileConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()
                && ConnectivityManager.TYPE_MOBILE == info.getType()) {
            return true;
        }
        return false;
    }
 
    /**
     * 检查当前是否连接
     * 
     * @param context
     * @return true表示当前网络处于连接状态，否则返回false
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }
 
    /**
     * 对大数据传输时，需要调用该方法做出判断，如果流量敏感，应该提示用户
     * 
     * @param context
     * @return true表示流量敏感，false表示不敏感
     */
    public static boolean isActiveNetworkMetered(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return ConnectivityManagerCompat.isActiveNetworkMetered(cm);
    }
 
    public static Intent registerReceiver(Context context,
            ConnectivityChangeReceiver receiver) {
        return context.registerReceiver(receiver,
                ConnectivityChangeReceiver.FILTER);
    }
 
    public static void unregisterReceiver(Context context,
            ConnectivityChangeReceiver receiver) {
        context.unregisterReceiver(receiver);
    }
 
    public abstract static class ConnectivityChangeReceiver extends
            BroadcastReceiver {
        public static final IntentFilter FILTER = new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION);
 
        @Override
        public final void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = cm
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo gprsInfo = cm
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
 
            // 判断是否是Connected事件
            boolean wifiConnected = false;
            boolean gprsConnected = false;
            if (wifiInfo != null && wifiInfo.isConnected()) {
                wifiConnected = true;
            }
            if (gprsInfo != null && gprsInfo.isConnected()) {
                gprsConnected = true;
            }
            if (wifiConnected || gprsConnected) {
                onConnected();
                return;
            }
 
            // 判断是否是Disconnected事件，注意：处于中间状态的事件不上报给应用！上报会影响体验
            boolean wifiDisconnected = false;
            boolean gprsDisconnected = false;
            if (wifiInfo == null || wifiInfo != null
                    && wifiInfo.getState() == State.DISCONNECTED) {
                wifiDisconnected = true;
            }
            if (gprsInfo == null || gprsInfo != null
                    && gprsInfo.getState() == State.DISCONNECTED) {
                gprsDisconnected = true;
            }
            if (wifiDisconnected && gprsDisconnected) {
                onDisconnected();
                return;
            }
        }
 
        protected abstract void onDisconnected();
 
        protected abstract void onConnected();
    }
}
