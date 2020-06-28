package com.ly.changyi.mobileimsdk;

import android.content.Context;

import com.ly.changyi.application.MyApplication;

import net.openmob.mobileimsdk.android.ClientCoreSDK;
import net.openmob.mobileimsdk.android.conf.ConfigEntity;
import net.openmob.mobileimsdk.android.core.LocalUDPDataSender;
import net.openmob.mobileimsdk.android.event.ChatBaseEvent;
import net.openmob.mobileimsdk.android.event.MessageQoSEvent;
import net.openmob.mobileimsdk.server.protocal.Protocal;

import java.util.ArrayList;

public class IMClientManager {

    private static IMClientManager instance;
    private        Context         context;

    private boolean init = false;//MobileIMSDK是否已被初始化

    //
    private ChatBaseEventImpl      baseEventListener;
    //
    private ChatTransDataEventImpl transDataListener;
    //
    private MessageQoSEventImpl    messageQoSListener;

    public static IMClientManager getInstance() {
        if (instance == null) instance = new IMClientManager(MyApplication.instance);
        return instance;
    }

    private IMClientManager(Context context) {
        this.context = context;
    }

    public void initMobileIMSDK(String ip, int port) {
        println("开始初始化IMClientManager");
        println("当前SDK状态为:" + String.valueOf(init));
        if (!init) {
            println("开始初始化IMSDK...");
            // 设置AppKey
            ConfigEntity.appKey = "5418023dfd98c579b6001741";

            // 设置服务器ip和服务器端口
            ConfigEntity.serverIP = ip;//111.85.15.162(龙里)
            ConfigEntity.serverUDPPort = port;//7901

            // MobileIMSDK核心IM框架的敏感度模式设置,让客户端更省电,默认MODE_3S
            //特别说明：为了保证算法的一致性，以上设置需所有平台客户端和服务端都保持一致，否则将发生不可预测问题。
            //ConfigEntity.setSenseMode(SenseMode.MODE_10S);

            // 开启/关闭DEBUG信息输出
            ClientCoreSDK.DEBUG = MyApplication.instance.isDebugMode;

            // 【特别注意】请确保首先进行核心库的初始化（这是不同于iOS和Java端的地方)
            ClientCoreSDK.getInstance().init(context);

            // 设置事件回调
            baseEventListener = new ChatBaseEventImpl();
            transDataListener = new ChatTransDataEventImpl(context);
            messageQoSListener = new MessageQoSEventImpl();
            ClientCoreSDK.getInstance().setChatBaseEvent(baseEventListener);
            ClientCoreSDK.getInstance().setChatTransDataEvent(transDataListener);
            ClientCoreSDK.getInstance().setMessageQoSEvent(messageQoSListener);

            init = true;
            println("IMClientManager初始化完成");
        }
    }

    public void release() {
        ClientCoreSDK.getInstance().release();
        resetInitFlag();
    }

    /**
     * 发送消息
     *
     * @param message
     * @param msgType           发送消息的类型,自定义.111:发送的坐标
     * @param onMsgSendListener
     */
    public void sendMessage(String message, int msgType, final OnMsgSendListener onMsgSendListener) {

        //没有初始化的时候的值(初始化的时候服务器没有运行)
        println(String.valueOf(ClientCoreSDK.getInstance().isConnectedToServer()));//true
        println(String.valueOf(ClientCoreSDK.getInstance().isInitialed()));//true
        println(String.valueOf(ClientCoreSDK.getInstance().isLocalDeviceNetworkOk()));//true
        println(String.valueOf(ClientCoreSDK.getInstance().isLoginHasInit()));//false
        //断网值:false true false true

        if (ClientCoreSDK.getInstance().isLoginHasInit()) {
            //第三个参数是服务器id
            new LocalUDPDataSender.SendCommonDataAsync(context, message, "0", msgType) {

                @Override
                protected void onPostExecute(Integer integer) {
                    println("发送消息返回码:" + integer);
                    if (onMsgSendListener != null) {
                        if (integer == null) {
                            onMsgSendListener.onMegSend(-1);
                        } else {
                            onMsgSendListener.onMegSend(integer.intValue());
                        }
                    }
                }
            }.execute();
        }
    }

    //消息发送完成的回调,0:发送成功.否则发送失败
    public interface OnMsgSendListener {
        void onMegSend(int errCode);
    }

    /**
     * 重置init标识。
     * <p>
     * 重要说明：不退出APP的情况下，重新登陆时记得调用一下本方法，不然再
     * 次调用 {@link #initMobileIMSDK(String, int)} 时也不会重新初始化MobileIMSDK
     * 详见 {@link #initMobileIMSDK()}代码）而报 code=203错误！
     */
    public void resetInitFlag() {
        init = false;
    }

    public ChatBaseEventImpl getBaseEventListener() {
        return baseEventListener;
    }

    public ChatTransDataEventImpl getTransDataListener() {
        return transDataListener;
    }

    public MessageQoSEventImpl getMessageQoSListener() {
        return messageQoSListener;
    }

    /**
     * 与IM服务器的连接事件在此ChatBaseEvent子类中实现即可。
     */
    private class ChatBaseEventImpl implements ChatBaseEvent {

        @Override
        public void onLoginMessage(int dwErrorCode) {
            if (dwErrorCode == 0) {
                //登陆/连接 MobileIMSDK服务器成功后的事情在此实现即可
                println("IM服务器登录/连接成功！" + ClientCoreSDK.getInstance().getCurrentLoginUserId());
            } else {
                println("IM服务器登录/连接失败，错误代码：" + dwErrorCode);
            }
        }

        @Override
        public void onLinkCloseMessage(int dwErrorCode) {
            println("与IM服务器的网络连接出错关闭了，error：" + dwErrorCode);
        }
    }

    /**
     * 消息送达相关事件（由QoS机制通知上来的）在此MessageQoSEvent子类中实现即可。
     */
    private class MessageQoSEventImpl implements MessageQoSEvent {

        @Override
        public void messagesLost(ArrayList<Protocal> lostMessages) {
            println("收到系统的未实时送达事件通知，当前共有 " + lostMessages.size() +
                    " 个包QoS保证机制结束，判定为【无法实时送达】！");
        }

        @Override
        public void messagesBeReceived(String theFingerPrint) {
            if (theFingerPrint != null) {
                println("收到对方已收到消息事件的通知，消息指纹码:" + theFingerPrint);
            }
        }
    }

    private void println(String msg) {
        if (MyApplication.instance.isDebugMode) {
            System.out.println(getClass().getSimpleName() + ":" + msg);
        }
    }
}
