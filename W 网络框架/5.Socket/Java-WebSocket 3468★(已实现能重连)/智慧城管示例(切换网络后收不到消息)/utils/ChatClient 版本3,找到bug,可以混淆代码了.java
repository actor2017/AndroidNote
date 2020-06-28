package com.kuchuanyun.wisdomcitymanagement.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.kuchuanyun.wisdomcitymanagement.R;
import com.kuchuanyun.wisdomcitymanagement.activity.AdministrativePenaltyAlreadyShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.AdministrativePenaltyNoShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.AlreadyTrialDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.CaseClosedCheckAlreadyShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.CaseClosedCheckNoShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.LaiFengLiveActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.MakeCaseReportedAlreadyShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.MakeCaseReportedNoShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.MyTaskAlreadyShenHeActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.MyTaskNoShenHeActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.PendingTrialDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.QiangZhiCheckAlreadyShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.activity.QiangZhiCheckNoShenHeDetailActivity;
import com.kuchuanyun.wisdomcitymanagement.application.MyApplication;
import com.kuchuanyun.wisdomcitymanagement.bean.VideoCloseBean;
import com.kuchuanyun.wisdomcitymanagement.global.Global;

import org.greenrobot.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import static com.kuchuanyun.wisdomcitymanagement.application.MyApplication.aCache;
import static com.kuchuanyun.wisdomcitymanagement.application.MyApplication.isDebugMode;


/**
 * java websocket 客户端
 *
 * @author yxl
 */
public class ChatClient extends WebSocketClient {

    private static ChatClient client = null;
    private static final String ipAndPort = Global.WEB_SOCKET_IP;//ip和端口,示例:111.85.15.163:9013
    private static String userno = null;//登录账号
    private static final String USERNO_IN_CHATCLIENT = "USERNO_IN_CHATCLIENT";

    private ChatClient(URI serverURI) {
        super(serverURI);
    }

    private ChatClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    //获取实例对象
    public static ChatClient getInstance(){
        if (client == null) {
            client = new ChatClient(initUri());
        }
        return client;
    }

    private static URI initUri() {
        try {
            return new URI("ws://" + ipAndPort);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("WebSocket初始化失败,URI=null");
            return null;
        }
    }

    /**
     * 设置Ip,Port,登录账号
     * @param userNo 登录账号
     * @return
     */
    public void setUserNo(String userNo) {
        this.userno = userNo;
        aCache.put(USERNO_IN_CHATCLIENT,userNo);
    }

    //获取登录账号
    private String getUserNo() {
        if (TextUtils.isEmpty(userno)) {
            userno = aCache.getAsString(USERNO_IN_CHATCLIENT);
        }
        return userno;
    }

    //客户端连接服务器
    public void startConnection() {
        println("WebSocket开始连接,No:" + getUserNo());
        if (!TextUtils.isEmpty(getUserNo())) {
            try {
                getInstance().connect();
            } catch (Exception e) {
                println("WebSocketClient objects are not reuseable,WebSocketClient对象不可重用");
                client = null;
                getInstance().connect();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        checkOnline();
        println("WebSocket:连接成功");
        send("[join]" + getUserNo() + "-app");
    }

    private void checkOnline(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //15秒后重新连接
                        Thread.sleep(15000);
                        if(client == null || client.getConnection() == null || client.getConnection().isClosed()){
                            println(getUserNo() + "WebSocket:正在断线重连...");
                            startConnection();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        println("WebSocket:断线重连失败");
                    }
                }
            }
        }).start();
    }

    //发送消息
    public void sendLng(String lng, String lat) {
        if(client == null || client.getConnection() == null || client.getConnection().isClosed()){
            startConnection();
            println("WebSocket,sendLng的时候closed------------");
        } else {
            String msg = "{\"usercode\":\"" + getUserNo() + "-app\",\"lng\":\"" + lng + "\",\"lat\":\""
                    + lat + "\"}";
            getInstance().send(msg);
            println("WebSocket,发送消息sendLng:" + msg);
        }
    }

    @Override
    public void onMessage(String message) {
//        if (aCache.getAsString(Global.EXCEPTION) == null) {
//            aCache.put(Global.EXCEPTION, message);
//        } else {
//            aCache.put(Global.EXCEPTION, aCache.getAsString(Global.EXCEPTION) + "\n\n\n" + message);
//        }

        println("WebSocket 收到消息:" + message);
        if (!TextUtils.isEmpty(message)) {//智慧城管
            JSONObject msgObj = JSONObject.parseObject(message);
            if (msgObj.containsKey("type")) {
                switch (msgObj.getString("type")) {
                    case "video":
                        String admin = msgObj.getString("admin");
                        String url = Global.RTMP_LINK + msgObj.getString("roomid");
                        println(admin + "正在向你发起视频" + url);
                        //TO-DO
                        setNotification(MyApplication.instance,1, admin, url,0,null,0);
                        break;
                    case "videoclose"://视频关闭通知
                        EventBus.getDefault().post(new VideoCloseBean(true));//领导关闭视频
                        break;
                    case "notice":
                        String msg = msgObj.getString("msg");
                        int step = msgObj.getIntValue(Global.STEP);
                        String caseSbid = msgObj.getString("caseSbid");
                        int status = msgObj.getIntValue(Global.STATUS);//1 未审核，2已审核
                        setNotification(MyApplication.instance,2,msg,null,step,caseSbid,status);
                        break;
                }
            }
        }
    }

    @Override
    public void onFragment(Framedata fragment) {
        println("WebSocket:received fragment: " + new String(fragment.getPayloadData().array()));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        println("WebSocket:连接 closed by " + (remote ? "remote peer" : "us"));
    }

    @Override
    public void onError(Exception ex) {
        println("WebSocket,连接 onError");
        ex.printStackTrace();
    }

    private Notification notification;
    private NotificationManager notificationManager;

    private void setNotification(Context context,int type, String title, String url,int step,String caseSbid,int status) {//1 未审核，2已审核
//		if (notification == null) {
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent = new Intent();
        switch (type) {
            case 1:
                builder.setTicker(title + "正在向你发起视频");//Ticker是状态栏显示的提示
                builder.setContentTitle(title + "正在向你发起视频");//第一行内容  通常作为通知栏标题
                builder.setContentText("请尽快接听!");//第二行内容 通常是通知正文
                intent.setClass(context,LaiFengLiveActivity.class);
                intent.putExtra(Global.URL, url);
                break;
            case 2:
                builder.setTicker(title);
                builder.setContentTitle(title);
                builder.setContentText("请点击查看!");
                if (step <= 10) {
                    intent.setClass(context,status == 1?MyTaskNoShenHeActivity.class:MyTaskAlreadyShenHeActivity.class);
                }else if (step > 10 && step <= 35) {
                    intent.setClass(context,status == 1?PendingTrialDetailActivity.class:AlreadyTrialDetailActivity.class);
                } else if (step > 35 && step <= 75) {
                    intent.setClass(context,status == 1?MakeCaseReportedNoShenHeDetailActivity.class:MakeCaseReportedAlreadyShenHeDetailActivity.class);
                } else if (step > 75 && step <= 120) {
                    intent.setClass(context,status == 1?AdministrativePenaltyNoShenHeDetailActivity.class:AdministrativePenaltyAlreadyShenHeDetailActivity.class);
                } else if (step > 120 && step <= 150) {
                    intent.setClass(context,status == 1?QiangZhiCheckNoShenHeDetailActivity.class:QiangZhiCheckAlreadyShenHeDetailActivity.class);
                } else if (step > 150) {
                    intent.setClass(context,status == 1?CaseClosedCheckNoShenHeDetailActivity.class:CaseClosedCheckAlreadyShenHeDetailActivity.class);
                }
                intent.putExtra(Global.ID, caseSbid);
                break;
        }
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
//			builder.setSubText("这里显示的是通知第三行内容！");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        //builder.setContentInfo("2");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        //通知的时间,一般为系统时间,也可以使用setWhen()设置
        //builder.setWhen(System.currentTimeMillis());
        //设置为ture，表示它为一个正在进行的通知。简单的说，当为ture时，不可以被侧滑消失。(和"正在运行"好像有区别)
        //builder.setOngoing(true);
        //在通知的右侧 时间的下面
//			builder.setNumber(2);
        //点击通知栏后是否删除(如果=false,点击后不消失,但是能左滑消失)
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标(Ticker左边 & 下拉后右下角显示的小图标)
        //★★★注意,如果不设置,notification有声音,不显示★★★
        builder.setSmallIcon(R.drawable.city_management);
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.city_management));
        //设置flag位
        //FLAG_AUTO_CANCEL        该通知能被状态栏的清除按钮给清除掉
        //FLAG_NO_CLEAR           该通知能被状态栏的清除按钮给清除掉
        //FLAG_ONGOING_EVENT      通知放置在正在运行
        //FLAG_INSISTENT          是否一直进行，比如音乐一直播放，知道用户响应
        PendingIntent pIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        //点击跳转的intent
        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notification = builder.build();
        //manger.notify(TYPE_Normal,notification);
//		}
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context
                    .NOTIFICATION_SERVICE);
        }
        notificationManager.notify(new Random().nextInt(Integer.MAX_VALUE), notification);
    }

//	public static void main( String[] args ){
//		ChatClient.startServer("1009");//在登录成功后调用
//		ChatClient.sendLng("106.973056","26.463056");//百度有左边后开始发送坐标.
//	}

    private void println(String msg){
        if (isDebugMode) System.out.println(msg);
    }
}

