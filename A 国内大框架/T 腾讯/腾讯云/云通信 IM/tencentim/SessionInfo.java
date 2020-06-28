package com.ly.hihifriend.utils.tencentim;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;

import java.io.Serializable;
import java.util.List;

public class SessionInfo implements Serializable, Comparable<SessionInfo> {


    /**
     * 消息未读数
     */
    private int    unRead;
    /**
     * 会话ID，目前与peer保持一致
     */
    private String sessionId;
    /**
     * 会话标识，C2C为对方用户ID，群聊为群组ID
     */
    private String peer;
    /**
     * 会话头像url
     */
    private String iconUrl;
    /**
     * 会话标题
     */
    private String title;

    /**
     * 会话头像
     */
    private Bitmap      icon;
    /**
     * 是否为群会话
     */
    private boolean     isGroup;
    /**
     * 是否为置顶会话
     */
    private boolean     top;
    /**
     * 最后一条消息时间
     */
    private long        lastMessageTime;
    /**
     * 最后一条消息，MessageInfo对象
     */
    private MessageInfo lastMessage;

    /**
     * 每一个人的信息
     * 我后来添加的, 获取会话后再调用获取每一个人的信息 {@link TencentImUtils#getUsersProfiles(List, boolean, TIMValueCallBack)}
     */
    public TIMUserProfile timUserProfile;

    public SessionInfo() {

    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPeer() {
        return peer;
    }

    public void setPeer(String peer) {
        this.peer = peer;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUnRead() {
        return unRead;
    }

    public void setUnRead(int unRead) {
        this.unRead = unRead;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public long getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public MessageInfo getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageInfo lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int compareTo(@NonNull SessionInfo other) {
        return this.lastMessageTime > other.lastMessageTime ? -1 : 1;
    }

    @Override
    public String toString() {
        return "SessionInfo{" +
                "unRead=" + unRead +
                ", sessionId='" + sessionId + '\'' +
                ", peer='" + peer + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", title='" + title + '\'' +
                ", icon=" + icon +
                ", isGroup=" + isGroup +
                ", top=" + top +
                ", lastMessageTime=" + lastMessageTime +
                ", lastMessage=" + lastMessage +
                '}';
    }
}
