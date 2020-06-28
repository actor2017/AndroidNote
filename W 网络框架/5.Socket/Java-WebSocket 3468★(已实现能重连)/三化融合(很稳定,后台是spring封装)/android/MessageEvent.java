package com.ly.changyi.model;

/**
 * EventBus消息体
 * Created by Administrator on 2018/4/11.
 */

public class MessageEvent {

    public int code;
    public String msg;
    public Object object;

    public MessageEvent(int code, String msg, Object object) {
        this.code = code;
        this.msg = msg;
        this.object = object;
    }
}
