
package com.kuchuanyun.wisdomcitymanagement.mobileimsdk;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.PowerManager;
import android.text.TextUtils;

import com.actor.myandroidframework.utils.LogUtils;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.NotificationUtils;
import com.blankj.utilcode.util.VibrateUtils;
import com.kuchuanyun.wisdomcitymanagement.R;
import com.kuchuanyun.wisdomcitymanagement.activity.AdministrativePenaltyAlreadyShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.AdministrativePenaltyNoShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.CaseClosedCheckAlreadyShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.CaseClosedCheckNoShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.CaseReportedAlreadyCheck;
import com.kuchuanyun.wisdomcitymanagement.activity.CaseReportedNoCheck;
import com.kuchuanyun.wisdomcitymanagement.activity.LaiFengLiveActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.MakeCaseReportedAlreadyShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.MakeCaseReportedNoShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.MyTaskAlreadyShenHeActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.MyTaskNoShenHeActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.QiangZhiCheckAlreadyShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.QiangZhiCheckNoShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.TaskActivity;
import com.kuchuanyun.wisdomcitymanagement.application.MyApplication;
import com.kuchuanyun.wisdomcitymanagement.bean.VideoCloseBean;
import com.kuchuanyun.wisdomcitymanagement.global.Global;

import net.openmob.mobileimsdk.android.event.ChatTransDataEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * 与IM服务器的数据交互事件在此ChatTransDataEvent子类中实现即可。
 *
 * @author 卧龙 20171018
 * @version.1.1
 */
public class ChatTransDataEventImpl implements ChatTransDataEvent {

    @Override
    public void onTransBuffer(String fingerPrintOfProtocal, String userid, String dataContent, int type) {
        logFormat("收到指纹协议 = %s, 来自用户 %s 的消息, type= %d, msg = %s", fingerPrintOfProtocal, userid, type, dataContent);

        if (!TextUtils.isEmpty(dataContent)) {//智慧城管
            JSONObject msgObj = JSONObject.parseObject(dataContent);
//            String id = "";
//            String text = "";
            switch (type) {
                case -1://普通纯文本消息
                    break;
                case 10://新的聊天信息
                case 301://新任务消息
                case 302://任务审核信息
                    //{"bridge":false,"type":4,"dataContent":"f3ea3341-7256-4181-86f4-7fce0249db4f",
                    // "from":"10010312345-app","to":"0","QoS":false,"typeu":-1}
                    String title = msgObj.getString("title");
                    String id = msgObj.getString(Global.ID);
                    setNotification(MyApplication.instance, type, title, id, 0, null, 0);
                    break;
                case 101://发起视频
                    String admin = msgObj.getString("admin");
                    String url = Global.RTMP_LINK + msgObj.getString("roomid");
                    logError(admin + "正在向你发起视频" + url);
                    setNotification(MyApplication.instance, type, admin, url, 0, null, 0);
                    break;
                case 102://视频关闭通知
                    logError("视频关闭通知");
                    EventBus.getDefault().post(new VideoCloseBean(true));//领导关闭视频
                    break;
                case 201://案件
                    String msg = msgObj.getString("msg");
                    int step = msgObj.getIntValue(Global.STEP);
                    String caseSbid = msgObj.getString("caseSbid");
                    int status = msgObj.getIntValue(Global.STATUS);//1 未审核，2已审核
                    setNotification(MyApplication.instance, type, msg, null, step, caseSbid, status);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 一期原有的方法
     *
     * @param type 消息类型
     * @param title 标题
     * @param url url
     * @param step step
     * @param caseSbid 案件id
     * @param status   1 未审核，2已审核
     */
    private void setNotification(Context context, int type, String title, String url, int step, String caseSbid, int status) {
        NotificationUtils.notify(caseSbid == null ? "null" : caseSbid, type, builder -> {
            //Uri.fromFile(new File("/system/media/audio/ringtones/Orion.ogg")
//            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));//默认铃声
            Intent intent = new Intent();
            boolean needNotify = true;
            switch (type) {
                case 10://新的聊天信息
                case 301://新的任务
                case 302://任务审核信息
                    builder.setTicker(title)
                            .setContentTitle(title)
                            .setContentText("请点击查看!");
                    intent.setClass(context, TaskActivity.class)
                            .putExtra(Global.ID, url)
                            .putExtra("to", type == 10 ? "1" : "2");
                    break;
                case 101://发起视频
                    builder.setTicker("领导正在向你发起视频")//Ticker是状态栏显示的提示
                            .setContentTitle(title + "正在向你发起视频")//第一行内容  通常作为通知栏标题
                            .setContentText("请尽快接听!");//第二行内容 通常是通知正文
                    intent.setClass(context, LaiFengLiveActivity.class)
                            .putExtra(Global.URL, url);
                    break;
                case 201://案件
                    builder.setTicker(title)
                            .setContentTitle(title)
                            .setContentText("请点击查看!");
                    if (step <= 10) {
                        intent.setClass(context, status == 1 ? MyTaskNoShenHeActivity.class : MyTaskAlreadyShenHeActivity.class);
                    } else if (step <= 35) {
                        intent.setClass(context, status == 1 ? CaseReportedNoCheck.class : CaseReportedAlreadyCheck.class);
                    } else if (step <= 75) {
                        intent.setClass(context, status == 1 ? MakeCaseReportedNoShenHeDetailActivity.class : MakeCaseReportedAlreadyShenHeDetailActivity.class);
                    } else if (step <= 120) {
                        intent.setClass(context, status == 1 ? AdministrativePenaltyNoShenHeDetailActivity.class : AdministrativePenaltyAlreadyShenHeDetailActivity.class);
                    } else if (step <= 150) {
                        intent.setClass(context, status == 1 ? QiangZhiCheckNoShenHeDetailActivity.class : QiangZhiCheckAlreadyShenHeDetailActivity.class);
                    } else {
                        intent.setClass(context, status == 1 ? CaseClosedCheckNoShenHeDetailActivity.class : CaseClosedCheckAlreadyShenHeDetailActivity.class);
                    }
                    intent.putExtra(Global.ID, caseSbid);
                    break;
                default:
                    needNotify = false;
                    break;
            }
            if (needNotify) {
                VibrateUtils.vibrate(200);
                PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                if (!powerManager.isScreenOn()) {

                    /**
                     * 点亮屏幕
                     * 参2: tag
                     */
                    PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "myapp:mywakelocktag");//bright
//                        //点亮屏幕15秒钟
                    wl.acquire(10000 * 15);
                    //释放资源
                    wl.release();
                }

                builder.setAutoCancel(true)//能左滑删除
                        .setSmallIcon(R.drawable.city_management)//小图标
                        //下拉显示的大图标
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.city_management));
                //设置flag位
                //FLAG_AUTO_CANCEL        该通知能被状态栏的清除按钮给清除掉
                //FLAG_NO_CLEAR           该通知能被状态栏的清除按钮给清除掉
                //FLAG_ONGOING_EVENT      通知放置在正在运行
                //FLAG_INSISTENT          是否一直进行，比如音乐一直播放，知道用户响应
                PendingIntent pIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //点击跳转的intent
                builder.setContentIntent(pIntent);
            }
        });
    }

    @Override
    public void onErrorResponse(int errorCode, String errorMsg) {
        logFormat("收到服务端错误消息，errorCode = %d, errorMsg = %s", errorCode, errorMsg);
    }

    private void logError(String msg) {
        LogUtils.error(msg, false);
    }

    private void logFormat(String msg, Object... args) {
        LogUtils.formatError(msg, false, args);
    }
}
