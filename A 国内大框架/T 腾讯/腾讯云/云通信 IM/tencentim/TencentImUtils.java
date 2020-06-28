package com.ly.hihifriend.utils.tencentim;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ly.hihifriend.R;
import com.ly.hihifriend.utils.LogUtils;
import com.ly.hihifriend.utils.SPUtils;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMFriendProfileOption;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMGroupAddOpt;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupMemberRoleType;
import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMGroupSystemElem;
import com.tencent.imsdk.TIMGroupSystemElemType;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMImage;
import com.tencent.imsdk.TIMImageElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMOfflinePushListener;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSNSChangeInfo;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMSnapshot;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUploadProgressListener;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.TIMVideo;
import com.tencent.imsdk.TIMVideoElem;
import com.tencent.imsdk.common.IMBaseListener;
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo;
import com.tencent.imsdk.ext.group.TIMGroupManagerExt;
import com.tencent.imsdk.ext.group.TIMGroupMemberResult;
import com.tencent.imsdk.ext.group.TIMGroupPendencyGetParam;
import com.tencent.imsdk.ext.group.TIMGroupPendencyGetType;
import com.tencent.imsdk.ext.group.TIMGroupPendencyItem;
import com.tencent.imsdk.ext.group.TIMGroupPendencyListGetSucc;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMManagerExt;
import com.tencent.imsdk.ext.message.TIMMessageExt;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.friendship.TIMFriendPendencyInfo;
import com.tencent.imsdk.friendship.TIMFriendPendencyItem;
import com.tencent.imsdk.friendship.TIMFriendPendencyRequest;
import com.tencent.imsdk.friendship.TIMFriendPendencyResponse;
import com.tencent.imsdk.friendship.TIMFriendRequest;
import com.tencent.imsdk.friendship.TIMFriendResponse;
import com.tencent.imsdk.friendship.TIMFriendResult;
import com.tencent.imsdk.friendship.TIMFriendshipListener;
import com.tencent.imsdk.log.QLog;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Description: IM封装帮助类,使用依赖:
 * implementation 'com.tencent.imsdk:imsdk:4.3.81'//4.2.28
 *
 * 1.ImSDK 对象简介
 * ImSDK 对象主要分为通讯管理器，会话、消息，群管理，具体的含义参见下表：
 * 对象               介绍          功能
 * TIMManager       管理器类，负责 SDK 基本操作	初始化、登录、注销、创建会话等，可以通过扩展类 TIMManagerExt 使用更多管理器相关高级功能
 * TIMConversation  会话，负责会话相关操作	如发送消息，获取会话消息缓存，获取未读计数等，可以通过扩展类 TIMConversationExt 使用更多会话相关高级功能
 * TIMMessage       消息	包括文本、图片等不同类型消息。可以通过扩展类 TIMMessageExt 使用更多消息相关高级功能
 * TIMGroupManager  群组管理器	负责创建群组、加群、退群等，可以通过扩展类 TIMGroupManagerExt 使用更多群组相关高级功能
 *
 * 2.调用顺序介绍
 * ImSDK 调用 API 需要遵循以下顺序，其余辅助方法需要在登录成功后调用。
 * 步骤       对应函数                        说明
 * 初始化    TIMSdkConfig                      设置 SDK 基本配置，比如 sdkappid、日志等级等
 *          TIMManager : init               初始化 SDK
 *          TIMManager : setUserConfig      设置用户基本配置
 *          TIMManager : addMessageListener 设置消息监听
 *
 * 登录      TIMManager : login               登录
 *
 * 消息收发  TIMManager : getConversation    获取会话
 *          TIMConversation : sendMessage   发送消息
 *
 * 群组管理  TIMGroupManager                群组管理
 *
 * 注销       TIMManager : logout             注销
 *
 * 3.SDK错误码列表: https://cloud.tencent.com/document/product/269/1671
 *
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/4/11 on 14:35
 */
public class TencentImUtils {//腾讯Demo中的 TUIKit

    private static final String TAG = "TencentImUtils";
    public static TencentIMConfigs tencentIMConfigs;//所有配置
    public static Context context;

    public static void init(Context context, int sdkAppID) {//初始化, TencentIMConfigs configs
//        TencentImUtils.configs = configs;
        TencentImUtils.context = context;
        tencentIMConfigs = new TencentIMConfigs();
        tencentIMConfigs.setIMEventListener(new IMEventListener());//注册IM事件回调

        TIMSdkConfig timSdkConfig = tencentIMConfigs.getTIMSdkConfig();
        if (timSdkConfig == null) {
            timSdkConfig = new TIMSdkConfig(sdkAppID)
                    .setLogLevel(TIMLogLevel.DEBUG)//日志级别
                    .enableCrashReport(true);//崩溃上报?
        }
        //初始化通讯管理器
        TIMManager.getInstance().init(context, timSdkConfig);
        //设置离线消息通知
        TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {

            @Override
            public void handleNotification(TIMOfflinePushNotification notification) {
                if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify){
                    //消息被设置为需要提醒
                    notification.doNotify(context, R.drawable.logo);//R.mipmap.ic_launcher
                }
            }
        });
        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {//被其他终端踢下线
                        if (tencentIMConfigs.getIMEventListener() != null) {
                            tencentIMConfigs.getIMEventListener().onForceOffline();
                        }
//                        unInit();
                    }

                    @Override
                    public void onUserSigExpired() {//用户签名过期了，需要刷新 userSig 重新登录 SDK
                        if (tencentIMConfigs.getIMEventListener() != null) {
                            tencentIMConfigs.getIMEventListener().onUserSigExpired();
                        }
//                        unInit();
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {//连接成功
                        if (tencentIMConfigs.getIMEventListener() != null)
                            tencentIMConfigs.getIMEventListener().onConnected();
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {//断开连接
                        if (tencentIMConfigs.getIMEventListener() != null)
                            tencentIMConfigs.getIMEventListener().onDisconnected(code, desc);
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {//WIFI需要验证
                        if (tencentIMConfigs.getIMEventListener() != null)
                            tencentIMConfigs.getIMEventListener().onWifiNeedAuth(name);
                    }
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem elem) {//群Tips事件通知回调
                        if (tencentIMConfigs.getIMEventListener() != null) {
                            tencentIMConfigs.getIMEventListener().onGroupTipsEvent(elem);
                        }
                    }
                })
                //设置上传进度监听
                .setUploadProgressListener(new TIMUploadProgressListener() {
                    @Override
                    public void onMessagesUpdate(TIMMessage timMessage, int i, int i1, int i2) {
                    }
                });
        //设置会话刷新监听器
        userConfig.setRefreshListener(new TIMRefreshListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onRefreshConversation(List<TIMConversation> conversations) {//刷新会话
                if (tencentIMConfigs.getIMEventListener() != null) {
                    tencentIMConfigs.getIMEventListener().onRefreshConversation(conversations);
                }
            }
        });
        userConfig.setFriendshipStorageEnabled(true);
//        userConfig.setTIMFriendProfileOption();//什么??
        userConfig.setFriendshipListener(new TIMFriendshipListener() {
            @Override
            public void onAddFriends(List<String> list) {
                if (tencentIMConfigs.getIMEventListener() != null) {
                    tencentIMConfigs.getIMEventListener().onAddFriends(list);
                }
            }

            @Override
            public void onDelFriends(List<String> list) {

            }

            @Override
            public void onFriendProfileUpdate(List<TIMSNSChangeInfo> list) {

            }

            @Override
            public void onAddFriendReqs(List<TIMFriendPendencyInfo> list) {

            }
        });
        //群助手?
        userConfig.setGroupAssistantListener(new IMBaseListener() {});
        /**
         * 添加一个消息监听器
         * @param listener 消息监听器
         *                 默认情况下所有消息监听器都将按添加顺序被回调一次
         *                 除非用户在 ON_NEW_MESSAGE 回调中返回 true，此时将不再继续回调下一个消息监听器
         *
         * 移除消息监听: {@link TIMManager#removeMessageListener(TIMMessageListener listener)}
         */
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> msgs) {
                if (tencentIMConfigs.getIMEventListener() != null) {
                    tencentIMConfigs.getIMEventListener().onNewMessages(msgs);
                }
                return false;
            }
        });
        //消息扩展用户配置
        TIMUserConfigMsgExt ext = new TIMUserConfigMsgExt(userConfig)
                .enableStorage(true)//是否使用消息存储
                .enableReadReceipt(true)//开启消息已读回执
                .setMessageRevokedListener(UIKitMessageRevokedManager.getInstance());//消息撤回监听

        //缓存用户信息,时间可通过 TIMFriendProfileOption 的 setExpiredSeconds 接口设置，默认缓存时间一天。
        TIMFriendProfileOption timFriendProfileOption = new TIMFriendProfileOption();
        timFriendProfileOption.setExpiredSeconds(60 * 60); // 1小时
        ext.setTIMFriendProfileOption(timFriendProfileOption);
        TIMManager.getInstance().setUserConfig(ext);

        TencentFiles.initPath();

        /**
         * FIXED 加载表情已经挪动到自定义View中, {@link com.actor.chatlayout.utils.FaceManager}
         */
//        FaceManager.loadFaceFiles();
    }

    public static TencentIMConfigs getBaseConfigs() {
        return tencentIMConfigs;
    }

    //是否已初始化??
    public static boolean isInited() {
        return TIMManager.getInstance().isInited();
    }

    /**
     * 可以获取当前用户名，也可以通过这个方法判断是否已经登录
     * @return
     */
    public static String getLoginUser() {
        return TIMManager.getInstance().getLoginUser();
    }

    //是否登录
    public static boolean isLogin() {
        return getLoginUser() != null;
    }

    /**
     * IM登录
     * @param identifier 用户帐号
     * @param userSig 用户帐号签名，由私钥加密获得，具体请参考文档
     * @param callback onError(int code, String desc), onSuccess()
     *                 错误码 code 列表请参见错误码表
     */
    public static void login(String identifier, String userSig, TIMCallBack callback) {
        TIMManager.getInstance().login(identifier, userSig, callback);
    }

    /**
     * IM退出
     * 如用户主动注销或需要进行用户的切换，则需要调用注销操作
     * @param callback onError(int code, String desc), onSuccess()
     *                 错误码 code 列表请参见错误码表
     */
    public static void logout(TIMCallBack callback) {
        TIMManager.getInstance().logout(callback);
    }

    /**
     * 获取单聊会话列表
     */
    public static void getConversationListC2C(TIMValueCallBack<List<SessionInfo>> callBack) {
        if (callBack == null) return;
        List<TIMConversation> conversationList = TIMManagerExt.getInstance().getConversationList();
        if (conversationList != null && conversationList.size() > 0) {
            List<SessionInfo> sessionInfos = new ArrayList<>();
            List<String> identifiers = new ArrayList<>();
            for (TIMConversation timConversation : conversationList) {
                if (timConversation != null && timConversation.getType() == TIMConversationType.C2C) {//C2C
                    SessionInfo sessionInfo = TIMConversation2SessionInfo(timConversation);
                    if (sessionInfo != null) {
                        String peer = sessionInfo.getPeer();
                        if (!TextUtils.isEmpty(peer)) {
                            sessionInfos.add(sessionInfo);
                            identifiers.add(peer);//不判空, 下方会走onError方法
                        }
                    }
                }
            }
            if (identifiers.isEmpty()) {
                callBack.onSuccess(sessionInfos);
            } else {
                // FIXME: 2019/5/13 还需要自己获取 头像 & 昵称......
                getUsersProfiles(identifiers, false, new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int i, String s) {
                        LogUtils.formatError("获取好友资料失败, code=%d, msg=%s", false, i, s);
                        callBack.onSuccess(sessionInfos);
                    }

                    @Override
                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                        if (timUserProfiles != null && timUserProfiles.size() > 0) {
                            for (int i = 0; i < sessionInfos.size(); i++) {
                                SessionInfo sessionInfo = sessionInfos.get(i);
                                if (sessionInfo != null) {
                                    String identifier = sessionInfo.getPeer();
                                    if (identifier != null) {
                                        for (int j = 0; j < timUserProfiles.size(); j++) {
                                            TIMUserProfile timUserProfile = timUserProfiles.get(j);
                                            if (timUserProfile != null) {
                                                if (identifier.equalsIgnoreCase(timUserProfile.getIdentifier())) {
                                                    sessionInfo.setIconUrl(timUserProfile.getFaceUrl());//设置头像
                                                    sessionInfo.setTitle(timUserProfile.getNickName());//昵称
                                                    sessionInfo.timUserProfile = timUserProfile;//整个塞进去...
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            callBack.onSuccess(sessionInfos);
                        } else callBack.onSuccess(sessionInfos);
                    }
                });
            }
        } else callBack.onSuccess(null);
    }

    /**
     * 获取单聊会话
     * @param identifier 对方identifier
     * @param size 你要一次获取多少条
     * @param lastMessage 如果获取前面↑的消息,传列表第一条消息. 如果获取最新↓消息,传null
     * @param callBack
     */
    public static void getConversationC2C(String identifier, int size, MessageInfo lastMessage, IUIKitCallBack<List<MessageInfo>> callBack) {
        TIMConversation conversation = getConversation(TIMConversationType.C2C,//会话类型：单聊
                identifier);                //会话对方用户帐号,对方ID
        TIMConversationExt ext = new TIMConversationExt(conversation);//扩展
        int unreadMeg = (int) ext.getUnreadMessageNum();//未读消息
        TIMMessage timMessage = null;
        if (lastMessage != null) timMessage = lastMessage.getTIMMessage();
        ext.getMessage(unreadMeg > size ? unreadMeg : size, timMessage, new TIMValueCallBack<List<TIMMessage>>() {

            @Override
            public void onError(int code, String desc) {
                callBack.onError(TAG, code, desc);
                logFormat("获取单聊会话失败getConversationC2C: code=%d, desc=%s", code, desc);
            }

            @Override
            public void onSuccess(List<TIMMessage> timMessages) {
                logError("获取单聊会话会话成功!");
                if (unreadMeg > 0) {
                    ext.setReadMessage(null, new TIMCallBack() {
                        @Override
                        public void onError(int code, String desc) {
                            QLog.e(TAG, "setReadMessage failed, code: " + code + "|desc: " + desc);
                        }

                        @Override
                        public void onSuccess() {
                            QLog.d(TAG, "setReadMessage succ");
                        }
                    });
                }
                ArrayList<TIMMessage> messages = new ArrayList<>(timMessages);
                Collections.reverse(messages);
                List<MessageInfo> msgInfos = MessageInfoUtil.TIMMessages2MessageInfos(messages, false);
                if (msgInfos != null) {
                    for (MessageInfo msgInfo : msgInfos) {
                        // FIXME: 2019/5/6 这儿获取的fromUser不是真正的昵称,在这儿转换一下
                        String fromUser = msgInfo.getFromUser();
                        TencentImUtils.getUserProfile(fromUser, false, new TIMValueCallBack<List<TIMUserProfile>>() {
                            @Override
                            public void onError(int i, String s) {
                                logError("接收到群视频消息, 但未获取到用户信息&昵称!");
                            }
                            @Override
                            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                                if (timUserProfiles != null && timUserProfiles.size() > 0) {
                                    msgInfo.setFromUser(timUserProfiles.get(0).getNickName());
                                }
                            }
                        });
                        TIMMessage timMessage = msgInfo.getTIMMessage();
                        if (timMessage != null) {
                            TIMElem element = timMessage.getElement(0);
                            switch (element.getType()) {
                                case Image:
                                    // FIXME: 2019/6/10 视频应该换成
                                    TIMImageElem imageElem = (TIMImageElem) element;
                                    ArrayList<TIMImage> imageList = imageElem.getImageList();
                                    String thumb = null, large = null, Original = null;
                                    if (imageList != null) {
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
                                    }
                                    if (Original != null) {
                                        msgInfo.setDataPath(Original);
                                    } else if (large != null) {
                                        msgInfo.setDataPath(large);
                                    } else msgInfo.setDataPath(thumb);
                                    break;
                                case Video:
                                    // FIXME: 2019/6/10 视频应该换成
                                    TIMVideoElem videoEle = (TIMVideoElem) element;
                                    String snapshotPath = videoEle.getSnapshotPath();//本地快照,不科学
                                    String videoPath = videoEle.getVideoPath();//本地视频路径,不科学
                                    TIMSnapshot snapshotInfo = videoEle.getSnapshotInfo();
                                    if (snapshotInfo != null) {
                                        msgInfo.setImgWithd((int) snapshotInfo.getWidth());
                                        msgInfo.setImgHeight((int) snapshotInfo.getHeight());
                                        String uuid = snapshotInfo.getUuid();
                                        try {
                                            Field urlsField = snapshotInfo.getClass().getDeclaredField("urls");
                                            urlsField.setAccessible(true);
                                            List<String> urls = (List<String>) urlsField.get(snapshotInfo);
                                            if (urls != null && urls.size() > 0) {
                                                msgInfo.setDataPath(urls.get(0));//封面图片网络路径
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    TIMVideo videoInfo = videoEle.getVideoInfo();
                                    if (videoInfo != null) {
                                        msgInfo.durationS = videoInfo.getDuaration();//时长
                                        try {
                                            Field urlsField = videoInfo.getClass().getDeclaredField("urls");
                                            urlsField.setAccessible(true);
                                            if (urlsField != null) {
                                                List<String> urls = (List<String>) urlsField.get(videoInfo);
                                                if (urls != null && urls.size() > 0) {
                                                    msgInfo.videoUrl = urls.get(0);
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                } else callBack.onSuccess(msgInfos);

//                mCurrentProvider.addMessageInfos(msgInfos, true);
                for (int i = 0; i < msgInfos.size(); i++) {
                    MessageInfo info = msgInfos.get(i);
                    if (info.getStatus() == MessageInfo.MSG_STATUS_SENDING) {//消息发送中状态
                        sendText(TIMConversationType.C2C, info, identifier, true, null);
                    }
                }
                callBack.onSuccess(msgInfos);
            }
        });
//        return TIMConversation2SessionInfo(conversation);
    }

    /**
     * 获取进群申请列表
     */
    public static void getGroupPendencyList(IUIKitCallBack<List<GroupApplyInfo>> callBack) {
        TIMGroupPendencyGetParam param = new TIMGroupPendencyGetParam();
        long aLong = SPUtils.getLong(TAG + "pendencyTime", 0);
        param.setTimestamp(aLong);
        TIMGroupManagerExt.getInstance().getGroupPendencyList(param, new TIMValueCallBack<TIMGroupPendencyListGetSucc>() {
            @Override
            public void onError(final int code, final String desc) {
                logError("getGroupPendencyList failed, code: " + code + "|desc: " + desc);
                callBack.onError(TAG, code, desc);
            }

            @Override
            public void onSuccess(final TIMGroupPendencyListGetSucc timGroupPendencyListGetSucc) {
                long timestamp = timGroupPendencyListGetSucc.getMeta().getNextStartTimestamp();
                SPUtils.putLong(TAG + "pendencyTime", timestamp);
                List<TIMGroupPendencyItem> pendencies = timGroupPendencyListGetSucc.getPendencies();
                List<GroupApplyInfo> mCurrentApplies = new ArrayList<>();//进群申请List
                for (int i = 0; i < pendencies.size(); i++) {
                    logError("pendencie=" + new String(pendencies.get(i).getAuth()));
                    GroupApplyInfo info = new GroupApplyInfo(pendencies.get(i));
                    info.setStatus(0);
                    mCurrentApplies.add(info);
                }
//                if (mCurrentApplies.size() > 0)
                    callBack.onSuccess(mCurrentApplies);
            }
        });
    }

    //群聊会话
//    public static SessionInfo getGroupConversation(String groupId) {
//        TIMConversation conversation = TIMManager.getInstance()
//                .getConversation(TIMConversationType.Group,//会话类型：群组
//                groupId);                       //群组 ID
//        return TIMConversation2SessionInfo(conversation);
//    }

    //系统会话
//    public static SessionInfo getSystemConversation() {
//        TIMConversation conversation = TIMManager.getInstance()
//                .getConversation(TIMConversationType.System, null);
//        return TIMConversation2SessionInfo(conversation);
//    }

    /**
     * TIMConversation转换为SessionInfo
     */
    private static SessionInfo TIMConversation2SessionInfo(TIMConversation conversation) {
        if (conversation == null) return null;
        TIMConversationExt ext = new TIMConversationExt(conversation);
        TIMMessage lastMessage = ext.getLastMsg();
        if (lastMessage == null) return null;
        /**
         * TIMConversationType:
         * Invalid(0),
         * C2C(1),
         * Group(2),
         * System(3);
         */
        TIMConversationType type = conversation.getType();
        if (type == TIMConversationType.System) {//系统消息
            if (lastMessage.getElementCount() > 0) {
                TIMElem ele = lastMessage.getElement(0);
                /**
                 * TIMElemType:
                 * Invalid(0),
                 * Text(1),
                 * Image(4),
                 * Sound(5),
                 * Custom(6),
                 * File(7),
                 * GroupTips(9),
                 * Face(10),
                 * Location(11),
                 * GroupSystem(12),
                 * SNSTips(13),
                 * ProfileTips(14),
                 * Video(15),
                 * UGC(16);
                 */
                TIMElemType eleType = ele.getType();
                if (eleType == TIMElemType.GroupSystem) {//这是一条群相关的系统消息(解散,被踢出...)
                    TIMGroupSystemElem groupSysEle = (TIMGroupSystemElem) ele;
                    TIMGroupSystemElemType sysEleSubtype = groupSysEle.getSubtype();
                    //群组解散或者被踢出群组
                    if (sysEleSubtype == TIMGroupSystemElemType.TIM_GROUP_SYSTEM_KICK_OFF_FROM_GROUP_TYPE
                            || sysEleSubtype == TIMGroupSystemElemType.TIM_GROUP_SYSTEM_DELETE_GROUP_TYPE) {
                        //imsdk会自动删除持久化的数据，应用层只需删除会话数据源中的即可
//                        deleteSession(groupSysEle.getGroupId());
                        if (tencentIMConfigs.getIMEventListener() != null) {
                            tencentIMConfigs.getIMEventListener().onGroupOut(groupSysEle.getGroupId());
                        }
                    }
                }
            }
            return null;//系统消息不显示
        }
        SessionInfo info = new SessionInfo();
        boolean isGroup = type == TIMConversationType.Group;
        info.setLastMessageTime(lastMessage.timestamp() * 1000);
        MessageInfo msg = MessageInfoUtil.TIMMessage2MessageInfo(lastMessage, isGroup);
        info.setLastMessage(msg);
        if (isGroup) {
            info.setTitle(conversation.getGroupName());
        } else info.setTitle(conversation.getPeer());
        info.setPeer(conversation.getPeer());//对方用户ID/群聊为群组ID
        info.setGroup(isGroup);
        if (ext.getUnreadMessageNum() > 0) info.setUnRead((int) ext.getUnreadMessageNum());
        return info;
    }

    /**
     * 创建群组
     * IM 云通讯有多种群组类型，其特点以及限制因素可参考 群组系统 介绍，群组使用唯一 ID 标识，通过群组 ID 可以进行不同操作，其中群组相关操作都由
     * TIMGroupManager 实现，需要用户登录成功后操作。
     * @param identifier 创建者usrId, 如果!=null自动入群, 否则没有入群?
     * @param groupType 群类型
     * @param groupName 群名称
     * @param groupFaceUrl 群头像
     * @param introduction 群简介
     * @param notification 群公告
     */
    public static void createGroup(String identifier, GroupType groupType, String groupName, String groupFaceUrl,
                                   String introduction, String notification, TIMValueCallBack<String> callBack) {
        TIMGroupManager.CreateGroupParam param = new TIMGroupManager.CreateGroupParam(groupType.type, groupName);
        param.setFaceUrl(groupFaceUrl);//群名称
        param.setIntroduction(introduction);//指定群简介
        param.setNotification(notification);//指定群公告

        //添加群成员 identifier
        List<TIMGroupMemberInfo> infos = new ArrayList<TIMGroupMemberInfo>();
        TIMGroupMemberInfo member = new TIMGroupMemberInfo(identifier);//群成员 identifier
        if (identifier != null) member.setRole(TIMGroupMemberRoleType.ROLE_TYPE_OWNER);//设置权限
        /**
         * Owner(400L),群主?
         * Admin(300L),管理员?
         * Normal(200L),
         * NotMember(0L);
         */
        member.setRole(TIMGroupMemberRoleType.ROLE_TYPE_NORMAL);
        infos.add(member);
        param.setMembers(infos);
        TIMGroupManager.getInstance().createGroup(param, callBack);//创建群组
    }

    /**
     * 群类型                   说明
     * 私有群(Private)     适用于较为私密的聊天场景，群组资料不公开，只能通过邀请的方式加入，类似于微信群。
     * 公开群(Public)      适用于公开群组，具有较为严格的管理机制、准入机制，类似于 QQ 群。
     * 聊天室(ChatRoom)    群成员可以随意进出。
     * 直播聊天室(AVChatRoom)与聊天室相似，但群成员人数无上限；支持以游客身份（不登录）接收消息。
     * 在线成员广播大群(BChatRoom)适用于需要向全体在线用户推送消息的场景。
     */
    public enum GroupType {
        Private("Private"),
        Public("Public"),
        ChatRoom("ChatRoom"),
        AVChatRoom("AVChatRoom"),
        BChatRoom("BChatRoom");
        private String type;
        GroupType(String type) {
            this.type = type;
        }
    }

    /**
     * 申请加入群, 不是邀请
     * @param groupId 群id,不能为空
     * @param reason 入群原因
     * @param callBack 不能为空
     */
    public static void applyJoinGroup(String  groupId, String reason, @NonNull IUIKitCallBack<TIMGroupAddOpt> callBack) {
        getGroupPublicInfo(groupId, new IUIKitCallBack<TIMGroupDetailInfo>() {
            @Override
            public void onSuccess(TIMGroupDetailInfo data) {
                TIMGroupManager.getInstance().applyJoinGroup(groupId, reason, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        logFormat("申请加入群失败: code=%d, desc=%s", code, desc);
                        callBack.onError(TAG, code, desc);
                    }

                    @Override
                    public void onSuccess() {
                        callBack.onSuccess(data.getAddOption());
//                        if (data == TIMGroupAddOpt.TIM_GROUP_ADD_AUTH) {
//                            UIUtils.toastLongMessage("申请入群成功，请等待管理员审批");
//                        } else {
//                            UIUtils.toastLongMessage("加入群聊成功");
//                        }
                    }
                });
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                callBack.onError(module, errCode, errMsg);
            }
        });
    }

    /**
     * 邀请入群, 不是申请入群
     * @param groupId 群id
     * @param addMembers 群成员列表
     * @param callBack
     */
    public static void inviteGroupMember(String groupId, List<String> addMembers, IUIKitCallBack callBack) {
        TIMGroupManagerExt.getInstance().inviteGroupMember(groupId, addMembers,
                new TIMValueCallBack<List<TIMGroupMemberResult>>() {
            @Override
            public void onError(int code, String desc) {
                logError("addGroupMembers failed, code: " + code + "|desc: " + desc);
                callBack.onError(TAG, code, desc);
            }

            @Override
            public void onSuccess(List<TIMGroupMemberResult> timGroupMemberResults) {
                List<String> adds = new ArrayList<>();
                if (timGroupMemberResults.size() > 0) {
                    for (int i = 0; i < timGroupMemberResults.size(); i++) {
                        TIMGroupMemberResult res = timGroupMemberResults.get(i);
                        if (res.getResult() == 3) {
                            callBack.onSuccess("邀请成功，等待对方接受");
                            return;
                        }
                        if (res.getResult() > 0)
                            adds.add(res.getUser());
                    }
                }
//                if (adds.size() > 0) {
//                    getGroupMembers(groupId, new IUIKitCallBack() {
//                        @Override
//                        public void onSuccess(Object data) {
//                            List<GroupMemberInfo> mCurrentGroupMembers = (List<GroupMemberInfo>) data;
                              //加载从0开始的群成员信息
//                            loadGroupMembersDetailRemote(0);
                              //给群设置现在的群成员
//                            mCurrentChatInfo.setMemberDetails(mCurrentGroupMembers);
//                            callBack.onSuccess(adds);
//                        }
//
//                        @Override
//                        public void onError(String module, int errCode, String errMsg) {
//                            callBack.onError(TAG, errCode, errMsg);
//                        }
//                    });
//
//                }
            }
        });
    }

    /**
     * 退群
     * 私有群：全员可退出群组。
     * 公开群、聊天室、直播大群：群主不能退出。
     * @param groupId
     * @param callBack
     */
    public static void quitGroup(@NonNull String groupId, @NonNull TIMCallBack callBack) {
        TIMGroupManager.getInstance().quitGroup(groupId, callBack);
    }

    /**
     * 删除群组成员
     * 私有群：只有创建者可删除群组成员。
     * 公开群、聊天室：只有管理员和群主可以踢人。
     * 对于直播大群：不能踢人。
     * @param groupId
     * @param callBack
     */
    public static void deleteGroup(@NonNull String groupId, @NonNull TIMCallBack callBack) {
        TIMGroupManager.getInstance().deleteGroup(groupId, callBack);
    }

    /**
     * 获取群成员
     * @param groupId
     * @param callBack
     */
    public static void getGroupMembers(String groupId, IUIKitCallBack<List<GroupMemberInfo>> callBack) {
//        GroupBaseManagerExt.getInstance().getGroupMembers();
        TIMGroupManagerExt.getInstance().getGroupMembers(groupId, new TIMValueCallBack<List<TIMGroupMemberInfo>>() {
            @Override
            public void onError(int code, String desc) {
                logError("getGroupMembers failed, code: " + code + "|desc: " + desc);
                callBack.onError(TAG, code, desc);
            }

            @Override
            public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
                List<GroupMemberInfo> members = new ArrayList<>();
                for (int i = 0; i < timGroupMemberInfos.size(); i++) {
                    members.add(TIMGroupMember2GroupMember(timGroupMemberInfos.get(i)));
                }
                callBack.onSuccess(members);
            }
        });
    }

    /**
     * 获取群消息, 包括系统消息
     * @param groupId
     * @param size
     * @param lastMessage
     * @param callBack
     */
    public static void getGroupMessageList(String groupId, int size, MessageInfo lastMessage, IUIKitCallBack<List<MessageInfo>> callBack) {
        TIMConversationExt conversationExt = getConversationExt(TIMConversationType.Group, groupId);
        int unread = (int) conversationExt.getUnreadMessageNum();
        conversationExt.getMessage(unread > size ? unread : size,
                lastMessage == null ? null : lastMessage.getTIMMessage(),
                new TIMValueCallBack<List<TIMMessage>>() {
                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(TAG, code, desc);
                        logFormat("获取群消息失败: code=%d, desc=%s", code, desc);
                    }

                    @Override
                    public void onSuccess(List<TIMMessage> timMessages) {
                        ArrayList<TIMMessage> messages = new ArrayList<>(timMessages);
                        Collections.reverse(messages);
                        List<MessageInfo> messageInfos = MessageInfoUtil.TIMMessages2MessageInfos(messages, true);
                        callBack.onSuccess(messageInfos);
                    }
                });
    }

    /**
     * 获取群聊天消息, 不包括系统消息
     * fixme 好像有bug
     */
    public static void getGroupTextMessageList(String groupId, int size, MessageInfo lastMessage, IUIKitCallBack<List<MessageInfo>> callBack) {
        getGroupMessageList(groupId, size, lastMessage, new IUIKitCallBack<List<MessageInfo>>() {
            @Override
            public void onSuccess(List<MessageInfo> data) {
                if (callBack != null) {
                    if (data != null && data.size() > 0) {
                        List<MessageInfo> result = new ArrayList<>(data.size());
                        for (MessageInfo datum : data) {
                            //如果是群文字消息,才返回去             为何是Invalid?
                            if (datum != null && datum.getMsgType() == TIMElemType.Invalid.value()) {//Text
                                // FIXME: 2019/5/6 这儿获取的fromUser不是真正的昵称,在这儿转换一下
                                TencentImUtils.getUserProfile(datum.getFromUser(), false, new TIMValueCallBack<List<TIMUserProfile>>() {
                                    @Override
                                    public void onError(int i, String s) {
                                        logError("接收到群消息, 但未获取到用户信息&昵称!");
                                    }
                                    @Override
                                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                                        if (timUserProfiles != null && timUserProfiles.size() > 0) {
                                            datum.setFromUser(timUserProfiles.get(0).getNickName());
                                        }
                                    }
                                });
                                result.add(datum);
                            }
                        }
                        callBack.onSuccess(result);
                    } else callBack.onSuccess(data);
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                logFormat("获取群系统消息失败 getGroupChatMessageList, module=%s, errCode=%d, errMsg=%s", module, errCode, errMsg);
                if (callBack != null) callBack.onError(module, errCode, errMsg);
            }
        });
    }

    /**
     * 获取群系统消息
     * @param groupId
     * @param size
     * @param lastMessage
     * @param callBack
     */
    public static void getGroupSystemMessageList(String groupId, int size, MessageInfo lastMessage, IUIKitCallBack<List<MessageInfo>> callBack) {
        getGroupMessageList(groupId, size, lastMessage, new IUIKitCallBack<List<MessageInfo>>() {
            @Override
            public void onSuccess(List<MessageInfo> data) {
                if (callBack != null) {
                    if (data != null && data.size() > 0) {
                        List<MessageInfo> result = new ArrayList<>(data.size());
                        for (int i = 0; i < data.size(); i++) {
                            MessageInfo messageInfo = data.get(i);
                            //如果是群系统消息,才返回去
                            if (messageInfo.getMsgType() == TIMElemType.GroupSystem.value()) result.add(messageInfo);
                        }
                        callBack.onSuccess(result);
                    } else callBack.onSuccess(data);
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                logFormat("获取群系统消息失败 getGroupSystemMessageList, module=%s, errCode=%d, errMsg=%s", module, errCode, errMsg);
                if (callBack != null) callBack.onError(module, errCode, errMsg);
            }
        });
    }

    /**
     * 获取系统消息
     * @param systemId
     * @param size
     * @param lastMessage
     */
    public static void getSystemMessageList(String systemId, int size,MessageInfo lastMessage, IUIKitCallBack<List<MessageInfo>> callBack) {
        TIMConversationExt conversationExt = getConversationExt(TIMConversationType.System, systemId);
        int unread = (int) conversationExt.getUnreadMessageNum();
        conversationExt.getMessage(unread > size ? unread : size,
                lastMessage == null ? null : lastMessage.getTIMMessage(),
                new TIMValueCallBack<List<TIMMessage>>() {
                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(TAG, code, desc);
                        logFormat("获取系统消息失败: code=%d, desc=%s", code, desc);
                    }

                    @Override
                    public void onSuccess(List<TIMMessage> timMessages) {
                        ArrayList<TIMMessage> messages = new ArrayList<>(timMessages);
                        Collections.reverse(messages);
                        List<MessageInfo> messageInfos = MessageInfoUtil.TIMMessages2MessageInfos(messages, false);
                        callBack.onSuccess(messageInfos);
                    }
                });
    }

    @Deprecated
    public static void getGroupMembers1(String groupId, IUIKitCallBack callBack) {
        getGroupPublicInfo(groupId, new IUIKitCallBack<TIMGroupDetailInfo>() {
            @Override
            public void onSuccess(TIMGroupDetailInfo data) {
                GroupChatInfo chatInfo = TIMGroupDetailInfo2GroupChatInfo(data);
                UIKitRequest topRequest = new UIKitRequest();
                topRequest.setAction(UIKitRequestDispatcher.SESSION_ACTION_GET_TOP);
                topRequest.setRequest(groupId);
                topRequest.setModel(UIKitRequestDispatcher.MODEL_SESSION);

                Object res = UIKitRequestDispatcher.getInstance().dispatchRequest(topRequest);
                if (res == null) return;
                boolean isTop = (boolean) res;
                chatInfo.setTopChat(isTop);
//                if (chatInfo != null) {
//                    getGroupMembers(groupId, new IUIKitCallBack<List<GroupMemberInfo>>() {
//                        @Override
//                        public void onSuccess(List<GroupMemberInfo> data) {
////                            setChatInfo(chatInfo);
////                            mCurrentGroupMembers = data;
//                            loadGroupMembersDetailRemote(0);
//                            mCurrentChatInfo.setMemberDetails(mCurrentGroupMembers);
//                            callBack.onSuccess(chatInfo);
//                        }
//
//                        @Override
//                        public void onError(String module, int errCode, String errMsg) {
//                        }
//                    });
//                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
            }
        });
    }
    public static GroupChatInfo TIMGroupDetailInfo2GroupChatInfo(TIMGroupDetailInfo detailInfo) {
        if (detailInfo == null)
            return null;
        GroupChatInfo chatInfo = new GroupChatInfo();
        chatInfo.setChatName(detailInfo.getGroupName());
        chatInfo.setGroupName(detailInfo.getGroupName());
        chatInfo.setPeer(detailInfo.getGroupId());
        chatInfo.setNotice(detailInfo.getGroupNotification());
        chatInfo.setMemberCount((int) detailInfo.getMemberNum());
        chatInfo.setGroupType(detailInfo.getGroupType());
        chatInfo.setOwner(detailInfo.getGroupOwner());
        chatInfo.setJoinType((int) detailInfo.getAddOption().getValue());
        return chatInfo;
    }

    public static GroupMemberInfo TIMGroupMember2GroupMember(TIMGroupMemberInfo info) {
        GroupMemberInfo member = new GroupMemberInfo();
        member.setAccount(info.getUser());
        member.setTinyId(info.getTinyId());
        member.setJoinTime(info.getJoinTime());
        member.setMemberType(info.getRole());//.ordinal()
        return member;
    }

    /**
     * 获取群公共信息
     * @param groupId
     * @param callBack
     */
    public static void getGroupPublicInfo(String groupId, IUIKitCallBack<TIMGroupDetailInfo> callBack) {
        List<String> groupList = new ArrayList<>();
        groupList.add(groupId);
        TIMGroupManagerExt.getInstance().getGroupPublicInfo(groupList, new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
            @Override
            public void onError(int code, String desc) {
                logFormat("获取群公共信息failed: code=%d, desc=%s", code, desc);
                callBack.onError(TAG, code, desc);
            }

            @Override
            public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                if (timGroupDetailInfos.size() > 0) {
                    TIMGroupDetailInfo info = timGroupDetailInfos.get(0);
                    logError("获取群公共信息success:" + info.toString());
                    callBack.onSuccess(info);
                }
            }
        });
    }

    /**
     * 发送文字, 注意处理重复发送问题
     * https://cloud.tencent.com/document/product/269/9232#.E6.96.87.E6.9C.AC.E6.B6.88.E6.81.AF.E5.8F.91.E9.80.81
     * @param type
     * @param message
     * @param userId 对方/群/系统 im的Id, 房间号
     * @param reSend 是否是重复发送
     * @param callBack
     */
    public static synchronized void sendText(TIMConversationType type, MessageInfo message, String userId, boolean reSend, IUIKitCallBack<MessageInfo> callBack) {
//        if (mCurrentChatInfo == null || mCurrentConversation == null) return;
        message.setSelf(true);
        message.setRead(true);
        message.setPeer(getLoginUser());
        //消息先展示，通过状态来确认发送是否成功
        if (message.getMsgType() < MessageInfo.MSG_TYPE_TIPS) {//提示类信息
//            if (mCurrentProvider == null) return;
            message.setStatus(MessageInfo.MSG_STATUS_SENDING);//消息发送中状态
//            if (!reSend) {
//                mCurrentProvider.addMessageInfo(message);
//            }
//            mCurrentProvider.updateMessageInfo(message);

//            if (callBack != null) callBack.onSuccess(mCurrentProvider);
        }
        getConversation(type, userId)
                .sendMessage(message.getTIMMessage(), new TIMValueCallBack<TIMMessage>() {
                    @Override
                    public void onError(int code, String desc) {
                        logError("sendC2CMessage fail:" + code + "=" + desc);
//                        if (mCurrentProvider == null) return;
                        message.setStatus(MessageInfo.MSG_STATUS_SEND_FAIL);
//                        mCurrentProvider.updateMessageInfo(message);
                        if (callBack != null)
                            callBack.onError(TAG, code, desc);
                    }

                    @Override
                    public void onSuccess(TIMMessage timMessage) {
                        logError("发送'C2C/群/系统'消息成功!");
//                        if (mCurrentProvider == null) return;
                        message.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
                        message.setMsgId(timMessage.getMsgId());
//                        mCurrentProvider.updateMessageInfo(message);
                        if (callBack != null) callBack.onSuccess(message);
                    }
                });
    }

    //发送文字消息
    public static void sendText(TIMConversation conversation, String text, TIMValueCallBack callBack) {
        if (TextUtils.isEmpty(text)) return;
        //构造一条消息并添加一个文本内容
        TIMMessage msg = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(text);
        msg.addElement(elem);
        //发送消息
        conversation.sendMessage(msg, callBack);
    }

    /**
     * 发送C2C语音消息
     * @param peer
     * @param audioPath 语音路径
     * @param duration 时间, 单位ms
     * @param reSend 是否是重新发送
     */
    public static void sendC2CVoice(String peer, String audioPath, int duration, boolean reSend, IUIKitCallBack<MessageInfo> callBack) {
        MessageInfo message = MessageInfoUtil.buildAudioMessage(audioPath, duration);
        message.setPeer(peer);
        message.setSelf(true);
        message.setRead(true);
//        message.setGroup(true);
        if (message.getMsgType() < MessageInfo.MSG_TYPE_TIPS) {
            message.setStatus(MessageInfo.MSG_STATUS_SENDING);
//            if (!reSend) mCurrentProvider.addMessageInfo(message);
//            mCurrentProvider.updateMessageInfo(message);//更新状态为发送中
//            if (callBack != null) callBack.onSuccess(mCurrentProvider);
    }
        getConversation(TIMConversationType.C2C, peer).sendMessage(message.getTIMMessage(), new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int code, String desc) {
                logFormat("发送C2C语音消息 fail,sendC2CVoice: code=%d, desc=%s", code, desc);
                message.setStatus(MessageInfo.MSG_STATUS_SEND_FAIL);
                if (callBack != null) callBack.onError(TAG, code, desc);
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                logError("发送C2C语音消息 成功");
                message.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
                message.setMsgId(timMessage.getMsgId());
                if (callBack != null) callBack.onSuccess(message);
            }
        });
    }

    /**
     * 发送C2C图片
     */
    public static void sendC2CImage(String peer, String imgPath, boolean reSend, IUIKitCallBack<MessageInfo> callBack) {
        Uri uri = Uri.fromFile(new File(imgPath));
        MessageInfo message = MessageInfoUtil.buildImageMessage(uri, false, true);
        message.setSelf(true);
        message.setRead(true);
        message.setPeer(peer);
        //消息先展示，通过状态来确认发送是否成功
        if (message.getMsgType() < MessageInfo.MSG_TYPE_TIPS) {
            message.setStatus(MessageInfo.MSG_STATUS_SENDING);
//            if (!reSend) mCurrentProvider.addMessageInfo(message);
//            mCurrentProvider.updateMessageInfo(message);
//            if (callBack != null) callBack.onSuccess(mCurrentProvider);
        }
        getConversation(TIMConversationType.C2C, peer).sendMessage(message.getTIMMessage(), new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(final int code, final String desc) {
                logFormat("发送C2C图片失败 sendC2CMessage fail: code=%d, desc=%s", code, desc);
                message.setStatus(MessageInfo.MSG_STATUS_SEND_FAIL);
//                mCurrentProvider.updateMessageInfo(message);
                if (callBack != null) callBack.onError(TAG, code, desc);
            }

            @Override
            public void onSuccess(final TIMMessage timMessage) {
                message.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
                message.setMsgId(timMessage.getMsgId());
//                mCurrentProvider.updateMessageInfo(message);
                if (callBack != null) callBack.onSuccess(message);
            }
        });
    }

    /**
     * 发送C2C视频
     */
    public static synchronized void sendC2CVideo(String peer, String imgPath, String videoPath, int width, int height, long duration, boolean reSend, IUIKitCallBack<MessageInfo> callBack) {
        MessageInfo message = MessageInfoUtil.buildVideoMessage(imgPath, videoPath, width, height, duration);
        message.setSelf(true);
        message.setRead(true);
        message.setPeer(peer);
        message.durationS = duration / 1000;
        message.videoUrl = videoPath;
        //消息先展示，通过状态来确认发送是否成功
        if (message.getMsgType() < MessageInfo.MSG_TYPE_TIPS) {
            message.setStatus(MessageInfo.MSG_STATUS_SENDING);
//            if (!reSend) mCurrentProvider.addMessageInfo(message);
//            mCurrentProvider.updateMessageInfo(message);
//            if (callBack != null) callBack.onSuccess(mCurrentProvider);
        }
        getConversation(TIMConversationType.C2C, peer).sendMessage(message.getTIMMessage(), new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int code, String desc) {
                logFormat("发送C2C视频失败 sendC2CVideo fail: code=%d, desc=%s", code, desc);
                message.setStatus(MessageInfo.MSG_STATUS_SEND_FAIL);
//                mCurrentProvider.updateMessageInfo(message);
                if (callBack != null) callBack.onError(TAG, code, desc);

            }

            @Override
            public void onSuccess(final TIMMessage timMessage) {
                message.setStatus(MessageInfo.MSG_STATUS_SEND_SUCCESS);
                message.setMsgId(timMessage.getMsgId());
                message.setTIMMessage(timMessage);
//                try {
//                    TIMVideoElem element = (TIMVideoElem) timMessage.getElement(0);
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//                mCurrentProvider.updateMessageInfo(message);
                if (callBack != null) callBack.onSuccess(message);
            }
        });
    }

    /**
     * 设置消息已读
     * https://cloud.tencent.com/document/product/269/9226#.E5.B7.B2.E8.AF.BB.E4.B8.8A.E6.8A.A53
     * @param type
     * @param userId
     * @param messageInfo 这条消息以及之前的消息会被设置为已读
     * @param callBack
     */
    public static void setReadMessage(TIMConversationType type, String userId, MessageInfo messageInfo, @Nullable TIMCallBack callBack) {
        TIMConversationExt conversationExt = getConversationExt(type, userId);
        if (callBack == null) callBack = new TIMCallBack() {
            @Override
            public void onError(int code, String msg) {
                logFormat("设置消息已读 失败: code=%d, msg=%s", code, msg);
            }

            @Override
            public void onSuccess() {
                logError("设置消息已读 成功!");
            }
        };
        //callBack不能传null, 否则不走方法
        conversationExt.setReadMessage(messageInfo.getTIMMessage(), callBack);
    }

    /**
     * 删除消息
     * @param position
     * @param messageInfo
     */
    public static boolean deleteMessage(int position, MessageInfo messageInfo) {
        TIMMessageExt ext = new TIMMessageExt(messageInfo.getTIMMessage());
        return ext.remove();
    }

    /**
     * 撤回消息,回滚消息
     * @param type
     * @param userId
     * @param messageInfo
     * @param callBack
     */
    public void rollBackMessage(TIMConversationType type, String userId, MessageInfo messageInfo, TIMCallBack callBack) {
        getConversationExt(type, userId).revokeMessage(messageInfo.getTIMMessage(), callBack);
        //测回错误示例:   if (code == 6223) {ToastUtils.show("消息发送已超过2分钟");
        //              else ToastUtils.show("撤销失败:" + code + "=" + desc);
    }

    /**
     * 获取C2C, 群聊, System 等会话
     * @param type 类型,枚举
     * @param userId 对方/群id
     * @return
     */
    private static TIMConversation getConversation(TIMConversationType type, String userId) {
        return TIMManager.getInstance().getConversation(type, userId);
    }

    private static TIMConversationExt conversationExt;
    private static TIMConversationExt getConversationExt(TIMConversationType type, String userId) {
//        if (conversationExt == null)
        conversationExt = new TIMConversationExt(getConversation(type, userId));
        return conversationExt;
    }

    /**
     * 修改自己的资料信息: https://cloud.tencent.com/document/product/269/33926
     * @param profileMap 需要修改的字段放在hashMap中, key值取TIMUserProfile中定义的常量, 示例:
     * {@link com.tencent.imsdk.TIMUserProfile#TIM_PROFILE_TYPE_KEY_NICK}
     * TIM_PROFILE_TYPE_KEY_NICK            String  昵称
     * TIM_PROFILE_TYPE_KEY_FACEURL         String  头像
     * TIM_PROFILE_TYPE_KEY_ALLOWTYPE       int     好友申请
     * TIM_PROFILE_TYPE_KEY_GENDER          int     性别,{@link com.tencent.imsdk.TIMFriendGenderType#GENDER_MALE}
     * TIM_PROFILE_TYPE_KEY_BIRTHDAY        int     生日,注意是int
     * TIM_PROFILE_TYPE_KEY_LOCATION        String  位置
     * TIM_PROFILE_TYPE_KEY_LANGUAGE        int     语言
     * TIM_PROFILE_TYPE_KEY_LEVEL           int     等级
     * TIM_PROFILE_TYPE_KEY_ROLE            int     角色
     * TIM_PROFILE_TYPE_KEY_SELFSIGNATURE   String  签名
     * TIM_PROFILE_TYPE_KEY_CUSTOM_PREFIX   String, int 自定义字段前缀
     *
     * @param callBack 回调
     */
    public static void modifySelfProfile(HashMap<String, Object> profileMap, @NonNull TIMCallBack callBack) {
//        profileMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_CUSTOM_PREFIX + "Blood", 1);//自定义字段
        TIMFriendshipManager.getInstance().modifySelfProfile(profileMap, callBack);
//        TIMFriendshipManager.getInstance().modifyFriend(identifier, profileMap, callBack);//修改指定人
    }

    /**
     * 获取自己的资料
     */
    public static void getSelfProfile(TIMValueCallBack<TIMUserProfile> callBack) {
        TIMFriendshipManager.getInstance().getSelfProfile(callBack);
    }

    public static void getUserProfile(String identifier, boolean forceUpdate, @NonNull TIMValueCallBack<List<TIMUserProfile>> callBack) {
        if (identifier == null || callBack == null) return;
        List<String> list = new ArrayList<>(1);
        list.add(identifier);
        TIMFriendshipManager.getInstance().getUsersProfile(list, forceUpdate, callBack);
    }

    /**
     * 获取指定好友资料（不包括：备注，好友分组）
     * @param identifiers
     * @param forceUpdate true:强制从后台拉取数据,并把返回的数据缓存下来 false:先在本地查找,如果本地没有数据则再向后台请求数据
     * @param callBack TIMUserProfile中包含:
     *                 getIdentifier()  获取用户的 identifier
     *                 getNickName()    获取用户的昵称
     *                 getFaceUrl()     获取用户头像 URL
     *                 getSelfSignature()获取用户个人签名
     *                 getAllowType()   获取用户加好友的选项
     *                 getRemark()      获取用户备注
     *                 getFriendGroups()获取被加入的好友分组列表, 没有这个方法,垃圾
     *                 getCustomInfo()  获取用户自定义信息
     *                 getCustomInfoUint()获取用户自定义信息
     *                 getGender()      获取用户性别类型
     *                 getBirthday()    获取用户生日信息
     *                 getLanguage()    获取语言
     *                 getLocation()    获取位置信息
     *                 getLevel()
     *                 getRole()
     */
    public static void getUsersProfiles(List<String> identifiers, boolean forceUpdate, @NonNull TIMValueCallBack<List<TIMUserProfile>> callBack) {
        if (identifiers == null || callBack == null) return;
        TIMFriendshipManager.getInstance().getUsersProfile(identifiers, forceUpdate, callBack);
    }

    /**
     * 获取未决列表:
     * 其它用户通过addFriend方法添加自己为好友，此时会在后台增加一条未决记录。
     * 当自己向其它用户请求好友时，后台也会记录一条未决信息。
     * @param size 本次想请求多少条数据
     * @param type 如果直接填BOTH,这儿返回的数据的type也是BOTH,FIXME 有bug...
     */
    public static void getPendencyList(int size, TIMGroupPendencyGetType type, TIMValueCallBack<List<TIMFriendPendencyItem>> callBack) {
        if (callBack == null) return;
        TIMFriendPendencyRequest request = new TIMFriendPendencyRequest();
        //未决列表序列号。建议客户端保存 seq 和未决列表，请求时填入 server 返回的 seq。如果 seq 是 server 最新的，则不返回数据
//        request.setSeq();
        /**
         * 翻页时间戳，只用来翻页，server 返回0时表示没有更多数据，第一次请求填0
         * 特别注意的是，如果 server 返回的 seq 跟填入的 seq 不同，翻页过程中，需要使用客户端原始 seq 请求，直到数据请求完毕，才能更新本地 seq
         */
//        request.setTimestamp();
        request.setNumPerPage(size);
        //TIMGroupPendencyGetType.BOTH_SELFAPPLY_AND_INVITED.getValue()
        request.setTimPendencyGetType((int) type.getValue());//我请求添加 & 别人请求添加我
        TIMFriendshipManager.getInstance().getPendencyList(request, new TIMValueCallBack<TIMFriendPendencyResponse>() {

            @Override
            public void onError(int i, String s) {
                logFormat("获取添加好友请求列表失败: code=%d, msg=%s", i, s);
                callBack.onError(i, s);
            }

            @Override
            public void onSuccess(TIMFriendPendencyResponse timFriendPendencyResponse) {
                long seq = timFriendPendencyResponse.getSeq();//获取本次请求的未决列表序列号
                long timestamp = timFriendPendencyResponse.getTimestamp();//获取本次请求的翻页时间戳
                long unreadCnt = timFriendPendencyResponse.getUnreadCnt();//获取未决请求未读数量
                List<TIMFriendPendencyItem> items = timFriendPendencyResponse.getItems();//获取未决信息列表

                callBack.onSuccess(items);

//                TIMFriendPendencyItem item = items.get(0);
//                String identifier = item.getIdentifier();//获取用户 ID
//                long addTime = item.getAddTime();//获取增加时间
//                String addSource = item.getAddSource();//获取来源
//                String addWording = item.getAddWording();//获取好友附言
//                String nickname = item.getNickname();//获取好友昵称
//                int type = item.getType();//获取未决请求类型，见 TIMPendencyGetType 常量定义
            }
        });
    }

    /**
     * 添加好友
     */
    public static void addFriend(String imIdentifier, TIMValueCallBack<TIMFriendResult> callBack) {
        if (imIdentifier != null) {
            TIMFriendRequest request = new TIMFriendRequest(imIdentifier);
//            request.setRemark("用户备注（备注最大96字节）");
//            request.setAddWording("请求说明（最大120字节）");
            //AddSource_Type_Unknow, AddSource_Type_Java
//            request.setAddSource("添加来源,不能超过8个字节，并且需要添加“AddSource_Type_”前缀");
//            request.setFriendGroup("分组名");
            TIMFriendshipManager.getInstance().addFriend(request,callBack);
        }
    }

    /**
     * 同意添加好友
     */
    public static void addFriendAgree(String imIdentifier, TIMValueCallBack<TIMFriendResult> callBack) {
        if (imIdentifier != null) {
            TIMFriendResponse response = new TIMFriendResponse();
            /**
             * TIM_FRIEND_RESPONSE_AGREE 同意加好友（建立单向好友）
             * TIM_FRIEND_RESPONSE_AGREE_AND_ADD 同意加好友并加对方为好友（建立双向好友）
             * TIM_FRIEND_RESPONSE_REJECT 拒绝对方好友请求
             */
            response.setResponseType(TIMFriendResponse.TIM_FRIEND_RESPONSE_AGREE_AND_ADD);
            response.setIdentifier(imIdentifier);
//            response.setRemark("备注好友（可选，如果要加对方为好友）。备注最大96字节");
            TIMFriendshipManager.getInstance().doResponse(response, callBack);
        }
    }

    private static void logError(String msg) {
        LogUtils.Error(msg, false);
    }

    private static void logFormat(String format, Object... args) {
        LogUtils.formatError(format, false, args);
    }

    //dp转px
    public static int dp2px(int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
