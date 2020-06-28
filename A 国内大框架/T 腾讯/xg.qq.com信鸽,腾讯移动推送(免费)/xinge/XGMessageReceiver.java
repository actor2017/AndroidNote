package com.ly.bridgeemergency.xinge;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.ly.bridgeemergency.activity.ChatRoomVideoActivity;
import com.ly.bridgeemergency.info.OnNotificationShowedResultInfo;
import com.ly.bridgeemergency.utils.Global;
import com.ly.bridgeemergency.utils.LogUtils;
import com.ly.bridgeemergency.utils.ToastUtils;
import com.tencent.android.tpush.XGLocalMessage;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class XGMessageReceiver extends XGPushBaseReceiver {
    private             Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEWUPDATE_LISTVIEW");

    // 通知被展示触发的回调，可以在此保存APP收到的通知
    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult notifiShowedResult) {
        logFormat("onNotifactionShowedResult您有1条新消息: context=%s, notifiShowedResult=%s",
                String.valueOf(context), String.valueOf(notifiShowedResult));
        if (context == null || notifiShowedResult == null) return;
        XGNotification notific = new XGNotification();
        notific.setMsg_id(notifiShowedResult.getMsgId());
        notific.setTitle(notifiShowedResult.getTitle());
        notific.setContent(notifiShowedResult.getContent());

        //{"room":1}
        String customContent = notifiShowedResult.getCustomContent();//消息自定义key-value
        logError("customContent:" + customContent);

        // notificationActionType==1为Activity，2为url，3为intent
        notific.setNotificationActionType(notifiShowedResult.getNotificationActionType());
        //Activity,url,intent都可以通过getActivity()获得
        notific.setActivity(notifiShowedResult.getActivity());
        notific.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(Calendar.getInstance().getTime()));
        NotificationService.getInstance(context).save(notific);
        context.sendBroadcast(intent);

        //显示到通知栏
        XGLocalMessage xgLocalMessage = new XGLocalMessage();
        xgLocalMessage.setMsgId(notifiShowedResult.getMsgId());
        xgLocalMessage.setTitle(notifiShowedResult.getTitle());
        xgLocalMessage.setContent(notifiShowedResult.getContent());
//        xgLocalMessage.setCustomContent();
        xgLocalMessage.setAction_type(1);
        xgLocalMessage.setActivity(notifiShowedResult.getActivity());
        XGPushManager.addLocalNotification(context, xgLocalMessage);

        OnNotificationShowedResultInfo info =
                com.alibaba.fastjson.JSONObject.parseObject(customContent,
                        OnNotificationShowedResultInfo.class);
        if (info != null) {
            Intent intent = new Intent(context, ChatRoomVideoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent.putExtra(Global.id, info.EmergencyEventId)//事件id
                    .putExtra(Global.roomid, info.room)
                    .putExtra(Global.username, (String) null)//info.userName
                    .putExtra(Global.userId, info.userId)
                    .putExtra(Global.userSig, info.userSig));
        }
    }

    //反注册的回调
    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        logFormat("onUnregisterResult反注册的回调: context=%s, errorCode=%d",
                String.valueOf(context), errorCode);
        if (context == null) return;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            logFormat("反注册成功");
        } else logFormat("反注册失败");
    }

    //设置tag的回调
    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        logFormat("onSetTagResult设置tag的回调: context=%s, errorCode%d, tagName=%s", context,
                errorCode, tagName);
        if (context == null) return;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            logError("设置成功");
        } else logError("设置失败");
    }

    //删除tag的回调
    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        logFormat("onDeleteTagResult删除tag的回调: context=%s, errorCode%d, tagName=%s", context,
                errorCode, tagName);
        if (context == null) return;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            logError("删除成功");
        } else logError("删除失败");
    }

    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击。此处不能做点击消息跳转，详细方法请参照官网的Android常见问题文档
    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult pushClickedResult) {
        logFormat("onNotifactionClickedResult通知被点击回调: context=%s, pushClickedResult=%s",
                context, pushClickedResult);
        if (context == null || pushClickedResult == null) return;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        if (pushClickedResult.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            logError("通知在通知栏被点击啦 。。。。");
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
        } else if (pushClickedResult.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            logError("通知被清除啦 。。。。");
            // APP自己处理通知被清除后的相关动作
        }
//        toast("广播接收到通知被点击:" + pushClickedResult.toString());
        // 获取自定义key-value
        String customContent = pushClickedResult.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    logFormat("获取自定义key-value, get custom value:%s", value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理的过程。。。
    }

    //注册的回调
    @Override
    public void onRegisterResult(Context context, int errorCode, XGPushRegisterResult pushRegisterResult) {
        logFormat("onRegisterResult注册的回调: context=%s, errorCode=%d, pushRegisterResult=%s",
                context, errorCode, pushRegisterResult);
        if (context == null || pushRegisterResult == null) return;
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            logError("注册成功");
            // 在这里拿token
            String token = pushRegisterResult.getToken();
        } else logError("注册失败");
    }

    // 消息透传的回调
    @Override
    public void onTextMessage(Context context, XGPushTextMessage pushTextMessage) {
        logFormat("onTextMessage消息透传的回调: context=%s, pushTextMessage=%s", context, pushTextMessage);
        if (pushTextMessage == null) return;
        // 获取自定义key-value
        String customContent = pushTextMessage.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    logFormat("获取自定义key-value, get custom value:%s", value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理消息的过程...
    }

    private void toast(String text) {
        ToastUtils.show(text);
    }

    private void logError(String msg) {
        LogUtils.Error(msg, true);
    }

    private void logFormat(String format, Object... args) {
        LogUtils.formatError(format, true, args);
    }
}
