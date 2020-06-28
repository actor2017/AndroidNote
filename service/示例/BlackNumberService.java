package cn.itcast.mobilesafe12.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

import cn.itcast.mobilesafe12.db.dao.BlackNumberDao;

/**
 * 黑名单拦截服务
 */
public class BlackNumberService extends Service {

    private InnerSmsReceiver mReceiver;
    private BlackNumberDao mDao;
    private TelephonyManager mTM;
    private InnerPhoneListener mListener;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mDao = BlackNumberDao.getInstance(this);

        //拦截短信
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(Integer.MAX_VALUE);

        mReceiver = new InnerSmsReceiver();
        registerReceiver(mReceiver, filter);

        //拦截电话
        //原理: 监听来电->判断号码是否是黑名单->通过代码挂断电话
        //监听来电
        mTM = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        mListener = new InnerPhoneListener();//电话监听器
        mTM.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);//监听电话状态
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mReceiver = null;

        //停止来电监听
        mTM.listen(mListener, PhoneStateListener.LISTEN_NONE);
        mListener = null;
    }

    class InnerPhoneListener extends PhoneStateListener {

        //电话状态发生变化的回调
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    //响铃
                    if (mDao.find(incomingNumber)) {
                        //0, 2
                        if (mDao.findMode(incomingNumber) != 1) {
                            //挂断电话
                            endCall();

                            //删除通话记录
                            //系统写通话记录是耗时操作, 如果通话记录没有写入之前就立即删除通话记录, 可能导致最新的记录无法清除
                            //deleteCalllog(incomingNumber);
                            //监听系统通话记录数据库的变化, 一旦数据变化, 再进行删除操作
                            //content://call_logs/call/id/xxx/xxx

                            //注册内容观察者
                            getContentResolver().registerContentObserver(CallLog.Calls
                                    .CONTENT_URI, true, new CalllogObserver(new Handler(),
                                    incomingNumber));
                        }
                    }
                    break;
                default:
                    break;
            }

        }
    }

    //内容观察者
    class CalllogObserver extends ContentObserver {

        private String incomingNumber;

        public CalllogObserver(Handler handler, String incomingNumber) {
            super(handler);
            this.incomingNumber = incomingNumber;
        }

        //一旦内容变化, 就走到此方法中
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            System.out.println("通话记录数据库变化了");

            deleteCalllog(incomingNumber);

            //注销内容观察者
            getContentResolver().unregisterContentObserver(this);
        }
    }

    //删除通话记录
    //<uses-permission android:name="android.permission.WRITE_CALL_LOG"/>
    //<uses-permission android:name="android.permission.READ_CALL_LOG"/>
    private void deleteCalllog(String incomingNumber) {
        getContentResolver().delete(CallLog.Calls.CONTENT_URI, "number=?", new
                String[]{incomingNumber});
    }

    //挂断电话
    //<uses-permission android:name="android.permission.CALL_PHONE"/>
    //注意: 拷贝相关aidl文件
    private void endCall() {
        System.out.println("挂断电话");
        try {
            //反射获得系统服务的getService方法对象
            Method method = Class.forName("android.os.ServiceManager")
                    .getMethod("getService", String.class);

            //执行这个方法得到一个IBinder对象
            IBinder binder = (IBinder) method.invoke(null, new Object[]{TELEPHONY_SERVICE});

            //转换为具体的服务类(ITelephony)接口对象  aidl
            ITelephony telephony = ITelephony.Stub.asInterface(binder);

            //结束通话
            telephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //拦截短信的广播
    //当优先级相同时, 动态注册的广播优先拦截
    class InnerSmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Object[] objs = (Object[]) intent.getExtras().get("pdus");

            for (Object obj : objs) {
                SmsMessage message = SmsMessage.createFromPdu((byte[]) obj);
                String originatingAddress = message.getOriginatingAddress();
                String messageBody = message.getMessageBody();
                System.out.println("短信地址:" + originatingAddress + ";短信内容:" + messageBody);

                if (mDao.find(originatingAddress)) {
                    //是黑名单 , 电话0, 短信1, 全部2-->
                    if (mDao.findMode(originatingAddress) > 0) {
                        abortBroadcast();//拦截短信
                    }
                }
            }
        }
    }
}
