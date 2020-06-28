package com.ly.changyi.mobileimsdk;

import android.content.Context;

import com.ly.changyi.application.MyApplication;

import net.openmob.mobileimsdk.android.event.ChatTransDataEvent;

/**
 * Description: 与IM服务器的数据交互事件在此ChatTransDataEvent子类中实现即可。
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/2/20 on 17:56
 */
public class ChatTransDataEventImpl implements ChatTransDataEvent {

    private static final String  TAG         = "ChatTransDataEventImpl";
    private              Context context;
    private              boolean isDebugMode = MyApplication.instance.isDebugMode;

    public class ImMsgType {
        public static final int COMMON        = -1;//普通纯文本消息
        public static final int NEW_TASK      = 101;//新任务消息
        public static final int ALARM         = 102;//报警或者爆料
        public static final int APPLY         = 103;//申请
        public static final int REPORT        = 104;//报备
        public static final int NEW_TASK_CHAT = 105;//新的任务,聊天
    }

    public ChatTransDataEventImpl(Context context) {
        this.context = context;
    }

    @Override
    public void onTransBuffer(String fingerPrintOfProtocal, String userid, String dataContent,
                              int type) {
        println("[type=" + type + "]收到来自用户" + userid + "的消息:" + dataContent + ",指纹协议:" +
                fingerPrintOfProtocal);
    }


    @Override
    public void onErrorResponse(int errorCode, String errorMsg) {
        println("收到服务端错误消息，errorCode=" + errorCode + ", errorMsg=" + errorMsg);
    }

    private void println(String msg) {
        if (isDebugMode) {
            System.out.println(TAG + ":" + msg);
        }
    }
}
