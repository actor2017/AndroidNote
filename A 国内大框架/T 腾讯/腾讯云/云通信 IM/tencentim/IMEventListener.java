package com.ly.hihifriend.utils.tencentim;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSONObject;
import com.ly.hihifriend.application.MyApplication;
import com.ly.hihifriend.info.MessageEvent;
import com.ly.hihifriend.utils.LogUtils;
import com.ly.hihifriend.utils.ToastUtils;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMGroupSystemElem;
import com.tencent.imsdk.TIMGroupSystemElemType;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMImage;
import com.tencent.imsdk.TIMImageElem;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMSNSSystemElem;
import com.tencent.imsdk.TIMSnapshot;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.TIMVideo;
import com.tencent.imsdk.TIMVideoElem;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * IM事件监听
 */

public class IMEventListener {

    /**
     * 被踢下线时回调,您的账号已在其它终端登录
     */
    public void onForceOffline(){
        logError("被踢下线时回调,您的IM账号已在其它终端登录onForceOffline");
        ToastUtils.show("您的IM账号已在其它终端登录");
        Context context = MyApplication.instance;
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    /**
     * 用户票据过期
     */
    public void onUserSigExpired(){
        logError("用户票据过期onUserSigExpired");
    }

    /**
     * 连接建立
     */
    public void onConnected(){
        logError("连接建立onConnected");
    }

    /**
     * 连接断开
     * @param code 错误码
     * @param desc 错误描述
     */
    public void onDisconnected(int code, String desc){
        logFormat("连接断开onDisconnected: code=%d, desc=%s", code, desc);
    }

    /**
     * WIFI需要验证
     *
     * @param name wifi名称
     */
    public void onWifiNeedAuth(String name){
        logFormat("WIFI需要验证onWifiNeedAuth: name=%s", name);
    }

    /**
     * 单聊 & 群聊 & 系统消息, 收到1条消息时会回调多次
     * 部分会话刷新, 接收到消息等（包括多终端已读上报同步）
     * @param conversations 需要刷新的会话列表
     */
    public void onRefreshConversation(List<TIMConversation> conversations){
        logFormat("部分会话刷新（包括多终端已读上报同步）onRefreshConversation, 会话数量size=%d", conversations != null ? conversations.size() : 0);
        if (conversations != null && conversations.size() > 0) {
            EventBus.getDefault().postSticky(new MessageEvent(GlobalTencent.ON_REFRESH_CONVERSATION, conversations));
        }
    }

    /**
     * 添加好友
     * @param list
     */
    public void onAddFriends(List<String> list) {
        int size = list == null ? 0 : list.size();
        logError("添加好友onAddFriends: list=" + size);
        if (list != null) {
            for (int i = 0; i < size; i++) {
                logError(list.get(i));
            }
        }
    }

    /**
     * 收到新消息回调,包括 C2C & 群聊 & 系统消息
     * @param msgs 收到的新消息
     */
    public void onNewMessages(List<TIMMessage> msgs){
        logFormat("onNewMessages: 收到新消息回调, 消息数量size=%d", msgs == null ? 0 : msgs.size());
        if (msgs != null && msgs.size() > 0) {
            // FIXME: 2019/4/29 被注释了, 还有一些type
//            List<MessageInfo> messageInfos = MessageInfoUtil.TIMMessages2MessageInfos(msgs, true);
//            EventBus.getDefault().post(new MessageEvent(GlobalTencent.ON_NEW_MESSAGE, messageInfos));

            for (TIMMessage message : msgs) {
                for (int i = 0; i < message.getElementCount(); i++) {
                    TIMElem element = message.getElement(i);
                    logFormat("element.getType()=%d", element.getType().value());
                    TIMConversation conversation = message.getConversation();
                    TIMConversationType type = conversation.getType();//消息类型,Invalid, C2C, Group, System
                    MessageInfo messageInfo = MessageInfoUtil.TIMMessage2MessageInfo(message, type == TIMConversationType.Group);
                    switch (element.getType()) {
                        case Text:
                            TIMTextElem textElem = (TIMTextElem) element;
                            //message.getConversation().getPeer(), textElem.getText()
                            onTextInfo(type, messageInfo);
                            switch (type) {
                                case C2C:
                                    logError("收到C2C消息:" + textElem.getText());
                                    break;
                                case Group:
                                    logError("收到群聊消息:" + textElem.getText());
                                    break;
                            }
                            break;
                        case Sound://语音
//                            TIMSoundElem soundElem = (TIMSoundElem) element;
                            onSoundInfo(type, messageInfo);
//                            switch (type) {
//                                case C2C:
//                                    break;
//                            }
                            break;
                        case Image://图片
                            TIMImageElem imageElem = (TIMImageElem) element;
                            ArrayList<TIMImage> imageList = imageElem.getImageList();
                            //https://cloud.tencent.com/document/product/269/9232#.E6.8E.A5.E6.94.B6.E5.9B.BE.E7.89.87.E6.B6.88.E6.81.AF
                            if (imageList != null && imageList.size() > 0) {
                                String thumb = null, large = null, Original = null;
                                for (TIMImage timImage : imageList) {
                                    if (timImage != null) {
                                        switch (timImage.getType()) {//Original(原图)、Large(大图)、Thumb(缩略图)
                                            case Thumb:
                                                thumb = timImage.getUrl();
                                                break;
                                            case Large:
                                                large = timImage.getUrl();
                                                break;
                                            case Original:
                                                Original = timImage.getUrl();
                                                break;
                                        }
                                    }
                                }
                                if (Original != null) {
                                    messageInfo.setDataPath(Original);
                                } else if (large != null) {
                                    messageInfo.setDataPath(large);
                                } else messageInfo.setDataPath(thumb);
                            }
                            onImageInfo(type, messageInfo);
                            break;
                        case Video://视频
                            // FIXME: 2019/6/10 视频应该换成
                            TIMVideoElem videoEle = (TIMVideoElem) element;
                            String snapshotPath = videoEle.getSnapshotPath();//本地快照,不科学
                            String videoPath = videoEle.getVideoPath();//本地视频路径,不科学
                            TIMSnapshot snapshotInfo = videoEle.getSnapshotInfo();//
                            if (snapshotInfo != null) {
                                messageInfo.setImgWithd((int) snapshotInfo.getWidth());//宽
                                messageInfo.setImgHeight((int) snapshotInfo.getHeight());//高
                                String uuid = snapshotInfo.getUuid();
                                try {
                                    Field urlsField = snapshotInfo.getClass().getDeclaredField("urls");
                                    urlsField.setAccessible(true);
                                    List<String> urls = (List<String>) urlsField.get(snapshotInfo);
                                    if (urls != null && urls.size() > 0) {
                                        messageInfo.setDataPath(urls.get(0));//封面图片网络路径
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            TIMVideo videoInfo = videoEle.getVideoInfo();
                            if (videoInfo != null) {
                                messageInfo.durationS = videoInfo.getDuaration();//时长
                                try {
                                    Field urlsField = videoInfo.getClass().getDeclaredField("urls");
                                    urlsField.setAccessible(true);
                                    if (urlsField != null) {
                                        List<String> urls = (List<String>) urlsField.get(videoInfo);
                                        if (urls != null && urls.size() > 0) {
                                            messageInfo.videoUrl = urls.get(0);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            onVideoInfo(type, messageInfo);
                            break;
                        case GroupTips://群提示(有昵称,头像等 debug见)
                            TIMGroupTipsElem groupTipsElem = (TIMGroupTipsElem) element;
                            String groupName = groupTipsElem.getGroupName();
                            logError("群提示:" + groupName);
                            break;
                        case GroupSystem://群系统消息
                            TIMGroupSystemElem groupSystemElem = (TIMGroupSystemElem) element;
                            logError("群系统消息:" + JSONObject.toJSONString(groupSystemElem));
                            logError("groupSystemElem.getSubtype()顺序:" + groupSystemElem.getSubtype().ordinal());//顺序
                            TIMGroupSystemElemType systemElemType = groupSystemElem.getSubtype();
                            switch (systemElemType) {
                                case TIM_GROUP_SYSTEM_DELETE_GROUP_TYPE://删群
                                    logFormat("onNewMessage subType = %s", systemElemType);
                                    onGroupOut(((TIMGroupSystemElem) element).getGroupId());
                                    return;
                                case TIM_GROUP_SYSTEM_CUSTOM_INFO://群系统自定义消息
                                    TIMGroupSystemElem element1 = (TIMGroupSystemElem) element;
                                    byte[] userData = element1.getUserData();
                                    if (userData == null || userData.length == 0){
                                        logFormat("userData == null");
                                        return;
                                    }
                                    String data = new String(userData);
                                    logFormat("onNewMessage subType = %s content = %s", systemElemType, data);
                                    onGroupSystemCustomInfo(element1, element1.getGroupId(), data);
                                    return;
                            }
                            break;
                        case Custom://自定义消息
                            byte[] userData = ((TIMCustomElem) element).getData();
                            if (userData == null || userData.length == 0){
                                logError("自定义消息userData == null");
                                return;
                            }
                            String data = new String(userData);
                            logFormat("自定义消息 subType = Custom, content = %s", data);
                            onCustomInfo(data);
                            break;
                        case SNSTips://别人添加我为好友的请求
                            TIMSNSSystemElem snsSystemElem = (TIMSNSSystemElem) element;
                            break;
                    }
                }
            }
        }
    }

    /**
     * 群Tips事件通知回调
     *
     * @param elem 群tips消息
     */
    public void onGroupTipsEvent(TIMGroupTipsElem elem){
        logFormat("群Tips事件通知回调onGroupTipsEvent: GroupId=%s, TipsType=%d", elem.getGroupId(), elem.getTipsType().ordinal());
    }

    /**
     * 群组解散或者被踢出群组
     */
    public void onGroupOut(String groupId) {
        logError("群组解散或者被踢出群组, groupId=" + groupId);
    }

    public void onGroupSystemCustomInfo(TIMGroupSystemElem element, String groupId, String data) {
        logError("群系统自定义消息:" + data);
        EventBus.getDefault().post(new MessageEvent<>(GlobalTencent.TIM_GROUP_SYSTEM_CUSTOM_INFO, groupId, data));
    }

    /**
     * 收到文字消息,包括C2C 群聊 等
     * @param type
     * @param messageInfo
     */
    public synchronized void onTextInfo(TIMConversationType type, MessageInfo messageInfo) {
        logError(messageInfo.toString());
        switch (type) {
            case C2C:
                /**
                 * 判断是否是聊天对象, 如果是就:myAdapter.addData(messageInfo);
                 * recyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
                 */
                EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_TEXT_C2C, messageInfo));
                break;
            case Group:
                // FIXME: 2019/5/6 这儿获取的fromUser不是真正的昵称,在这儿转换一下
                String fromUser = messageInfo.getFromUser();
                TencentImUtils.getUserProfile(fromUser, false, new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int i, String s) {
                        logError("接收到群消息, 但未获取到用户信息&昵称!");
                        EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_TEXT_GROUP, messageInfo));
                    }
                    @Override
                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                        if (timUserProfiles != null && timUserProfiles.size() > 0) {
                            messageInfo.setFromUser(timUserProfiles.get(0).getNickName());
                            EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_TEXT_GROUP, messageInfo));
                        }
                    }
                });
                break;
        }
    }

    /**
     * 收到语音消息, 包括C2C 群聊 等
     * @param type
     * @param messageInfo
     */
    public synchronized void onSoundInfo(TIMConversationType type, MessageInfo messageInfo) {
        logError(messageInfo.toString());
        switch (type) {
            case C2C:
                /**
                 * 判断是否是聊天对象, 如果是就:myAdapter.addData(messageInfo);
                 * recyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
                 */
                EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_AUDIO_C2C, messageInfo));
                break;
            case Group:
                EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_AUDIO_GROUP, messageInfo));
                break;
        }
    }

    /**
     * 收到图片消息,包括C2C 群聊 等
     * @param type
     * @param messageInfo
     */
    public synchronized void onImageInfo(TIMConversationType type, MessageInfo messageInfo) {
        logError(messageInfo.toString());
        switch (type) {
            case C2C:
                /**
                 * 判断是否是聊天对象, 如果是就:myAdapter.addData(messageInfo);
                 * recyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
                 */
                EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_IMAGE_C2C, messageInfo));
                break;
            case Group:
                // FIXME: 2019/5/6 这儿获取的fromUser不是真正的昵称,在这儿转换一下
                String fromUser = messageInfo.getFromUser();
                TencentImUtils.getUserProfile(fromUser, false, new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int i, String s) {
                        logError("接收到群图片消息, 但未获取到用户信息&昵称!");
                        EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_IMAGE_GROUP, messageInfo));
                    }
                    @Override
                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                        if (timUserProfiles != null && timUserProfiles.size() > 0) {
                            messageInfo.setFromUser(timUserProfiles.get(0).getNickName());
                            EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_IMAGE_GROUP, messageInfo));
                        }
                    }
                });
                break;
        }
    }

    /**
     * 收到视频消息,包括C2C 群聊 等
     * @param type
     * @param messageInfo
     */
    public synchronized void onVideoInfo(TIMConversationType type, MessageInfo messageInfo) {
        logError(messageInfo.toString());
        switch (type) {
            case C2C:
                /**
                 * 判断是否是聊天对象, 如果是就:myAdapter.addData(messageInfo);
                 * recyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
                 */
                EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_VIDEO_C2C, messageInfo));
                break;
            case Group:
                // FIXME: 2019/5/6 这儿获取的fromUser不是真正的昵称,在这儿转换一下
                String fromUser = messageInfo.getFromUser();
                TencentImUtils.getUserProfile(fromUser, false, new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int i, String s) {
                        logError("接收到群视频消息, 但未获取到用户信息&昵称!");
                        EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_VIDEO_GROUP, messageInfo));
                    }
                    @Override
                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                        if (timUserProfiles != null && timUserProfiles.size() > 0) {
                            messageInfo.setFromUser(timUserProfiles.get(0).getNickName());
                            EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_VIDEO_GROUP, messageInfo));
                        }
                    }
                });
                break;
        }
    }

    public void onCustomInfo(String data) {
        logError("自定义消息:" + data);
        EventBus.getDefault().post(new MessageEvent(GlobalTencent.TIM_CUSTOM, data));
    }

    private void logError(String msg) {
        LogUtils.Error(msg, false);
    }

    private void logFormat(String msg, Object... args) {
        LogUtils.formatError(msg, false, args);
    }
}
