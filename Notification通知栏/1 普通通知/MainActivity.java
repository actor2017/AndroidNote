package com.shijing.googleplay.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import com.shijing.googleplay.R;

public class MainActivity extends AppCompatActivity {

    public static final int TYPE_Normal = 1;
    public static final int TYPE_Progress = 2;
    public static final int TYPE_BigText = 3;
    public static final int TYPE_Inbox = 4;
    public static final int TYPE_BigPicture = 5;
    public static final int TYPE_Hangup = 6;
    public static final int TYPE_Media = 7;
    public static final int TYPE_Customer = 8;
    private NotificationManager notificationManger;
    private Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManger = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        initNotify();

        findViewById(R.id.btn_Notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				String ids = System.currentTimeMillis()+"";
                int id = Integer.parseInt(ids.substring(ids.length()-8));
			    //如果不想多条推送只显示1条消息,那么这儿的id不能一样
                notificationManger.notify(id,notification);//原来的值:TYPE_Normal
				
				notificationManger.cancel(id);//取消通知栏
            }
        });
    }

    /**
     * 初始化Notification
     *
     * build内提供了很多设置，但是在不同的系统版本显示有很多差异，使用时需要注意
     * setTicker 通知到来时低版本上会在系统状态栏显示一小段时间 5.0以上版本好像没有用了
     * setContentInfo和setNumber同时使用 number会被隐藏
     * setSubText显示在通知栏的第三行文本，在低版本上不显示，比如4.0系统
     * setVibrate设置震动 参数是个long[]｛震动时长，间隔时长，震动时长，间隔时长…｝单位毫秒 设置提醒声音 setSound(Uri sound) 一般默认的就好
     * builder.setLights()设置呼吸灯的颜色 并不是所有颜色都被支持 个人感觉没什么用
     * 清除通知栏特定通知 manager.cancel(id) id即为manger.notify()的第一个参数
     */
    private void initNotify(){
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
		/**
		 * 状态栏
		 */
        //Ticker是状态栏显示的提示★★★注意:一定要设置,否则只有声音,不下弹显示★★★
        .setTicker("简单Notification")
		//系统状态栏显示的小图标(Ticker左边 & 下拉后右下角显示的小图标)
		//★★★注意,如果不设置,notification有声音,不显示,如果图片太大,也不显示(长宽最好不要超过100)★★★
        .setSmallIcon(R.drawable.push)
		
		/**
		 * 通知栏
		 */
        //第一行内容  通常作为通知栏标题
        .setContentTitle("标题")
        //第二行内容 通常是通知正文
        .setContentText("通知内容")
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        .setSubText("这里显示的是通知第三行内容！")
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        //.setContentInfo("2")
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
		//通知的时间,一般为系统时间,也可以使用setWhen()设置
		//.setWhen(System.currentTimeMillis())
		//设置为ture，表示它为一个正在进行的通知。简单的说，当为ture时，不可以被侧滑消失。(和"正在运行"好像有区别)
		//.setOngoing(true)
		//在通知的右侧 时间的下面
        .setNumber(2)
        //点击通知栏后是否删除(如果=false,点击后不消失,但是能左滑消失)
        .setAutoCancel(true)
        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.push));//下拉显示的大图标
        Intent intent = new Intent(this,TestActivity.class);
		//设置flag位
        //FLAG_AUTO_CANCEL        该通知能被状态栏的清除按钮给清除掉
        //FLAG_NO_CLEAR           该通知不能被状态栏的清除按钮给清除掉
		//FLAG_UPDATE_CURRENT     第2次更新第1次
        //FLAG_ONGOING_EVENT      通知放置在正在运行
        //FLAG_INSISTENT          是否一直进行，比如音乐一直播放，直到用户响应
		//                      PendingIntent.getService
        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
		
        //点击跳转的intent
        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        notification = builder.build();
        //notificationManger.notify(TYPE_Normal,notification);
    }
}
