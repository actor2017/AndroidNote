package com.ly.changyi.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ly.changyi.constant.Global;
import com.ly.changyi.model.MessageEvent;
import com.ly.changyi.utils.SPUtils;
import com.ly.changyi.utils.ThreadUtils;
import com.ly.changyi.view.pickers.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * socket链接服务
 * Created by Administrator on 2018/7/20.
 */
public class SocketService extends Service {

    private static final String          TAG = "SocketService";
    private              URI             uri;
    private              WebSocketClient mWebSocketClient;
    private boolean isOnDestroy = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadUtils.RunOnSubThread(() -> initSocket());
    }

    //初始化socket链接
    public void initSocket() {
        //ws://192.168.1.67:30006/websocket?token=
        String address = Global.HOST_SOCKET + "/websocket?token=" + SPUtils.getString(Global.TOKEN);
        try {
            uri = new URI(address);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (mWebSocketClient == null) {
            mWebSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    if (serverHandshake != null) {
                        //status=101 message=
                        LogUtils.error(TAG, "onOpen: HttpStatus=" + serverHandshake.getHttpStatus()
                                + ",HttpStatusMessage=" + serverHandshake.getHttpStatusMessage());
                    } else LogUtils.error(TAG, "onOpen: serverHandshake=null");
                }

                @Override
                public void onMessage(String s) {
                    /**
                     * 连接成功后返回:OK>>>区域编号：1>>>用户编号：8186
                     * 所以解析json最好tryc
                     */
                    LogUtils.error(TAG, "onMessage:" + s);
                    if (!TextUtils.isEmpty(s)) {
                        MessageEvent msg = new MessageEvent(Global.SOCKET_REFRESH, s, null);
                        EventBus.getDefault().post(msg);
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    /**
                     * i=1011
                     * s=failed to connect to /192.168.1.67 (port 30006): connect failed:
                     * ENETUNREACH (Network is unreachable)
                     * b=true
                     */
                    LogUtils.error(TAG, "onClose: i=" + i + ",b=" + b + ",s=" + s);
                    if (!isOnDestroy) reConnect();//要判断, 否则第2次登录后,第1次登录的会一直重连
                }

                @Override
                public void onError(Exception e) {
                    /**
                     * 比如断网:
                     * java.net.ConnectException: failed to connect to /192.168.1.67 (port 30006)
                     * : connect failed: ENETUNREACH (Network is unreachable)
                     */
                    LogUtils.error(TAG, "onError: " + e.toString());
                }
            };
        }
        try {
//            mWebSocketClient.connect();//不阻塞
            mWebSocketClient.connectBlocking();//blocks阻塞
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新连接, 需要另外开一个线程重连, 否则报错:
     * java.lang.IllegalStateException: You cannot initialize a reconnect out of the websocket
     * thread. Use reconnect in another thread to insure a successful cleanup.
     */
    private void reConnect() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                boolean connect = mWebSocketClient.reconnectBlocking();//重新连接, 阻塞
//                boolean isOpen = mWebSocketClient.isOpen();//true(和上面状态一致)
//                LogUtils.error("mWebSocketClient.reconnectBlocking():" + b);
//                LogUtils.error("mWebSocketClient.isOpen():" + String.valueOf(isOpen));
//                if (isOpen) mWebSocketClient.send("2");//随意给服务器发个东西
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.error(TAG, "onDestroy");
        isOnDestroy = true;
        mWebSocketClient.close();
    }
}
