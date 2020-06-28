package cn.mobilesafe12.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import cn.mobilesafe12.db.dao.AddressDao;
import cn.mobilesafe12.view.AddressToast;

/**
 * 归属地显示服务
 * <p>
 * 静态注册广播: 一旦注册, 无法注销; 即使不启动app, 也可以收到系统的广播(开机, 电量变化, 网络状态变化, 锁屏开屏)
 * 动态注册广播: 可以注册, 可以注销
 *
 * 添加权限
 * <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>
 */
public class AddressService extends Service {

    private TelephonyManager mTM;
    private PhoneListener mListener;
    private PhoneReceiver mReceiver;
//    private WindowManager mWM;
//    private TextView view;

    //创建归属地显示的自定义Toast
    private AddressToast mToast;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //监听来电
        mTM = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        mListener = new PhoneListener();//电话监听器
        mTM.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);//监听电话状态

        //监听去电
        //广播 <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>
        mReceiver = new PhoneReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(mReceiver, filter);

        //初始化自定义吐司
        mToast = new AddressToast(this);
    }

    class PhoneListener extends PhoneStateListener {

        //电话状态发生变化的回调
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    //响铃
                    String address = AddressDao.getAddress(getApplicationContext(), incomingNumber);
                    //ToastUtils.show(getApplicationContext(), address);
                    //showToast(address);
                    mToast.show(address);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //摘机, 接听
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    //空闲
                    //移除窗口布局
//                    if (mWM != null && view != null) {
//                        mWM.removeView(view);
//                    }
                    System.out.println("挂断电话, 隐藏吐司");

                    mToast.hide();
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止来电监听
        mTM.listen(mListener, PhoneStateListener.LISTEN_NONE);
        mListener = null;

        //注销去电监听广播
        unregisterReceiver(mReceiver);
        mReceiver = null;
    }

    //去电广播
    class PhoneReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String number = getResultData();//获取当前去电的电话号码

            String address = AddressDao.getAddress(context, number);
            // ToastUtils.show(context, address);
            //showToast(address);
            mToast.show(address);
        }
    }

    //展示自定义吐司
//    private void showToast(String text) {
//        //窗口管理器
//        //窗口: 安卓系统最高级别的布局, 所有界面都基于窗口显示, 在窗口中显示activity, dialog, 状态栏
//        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);
//
//        //初始化布局参数
//        //初始化布局的宽高, 位置等信息
//        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;//宽高包裹内容
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽高包裹内容
//        params.format = PixelFormat.TRANSLUCENT;//显示方式, 默认就行
//        //params.windowAnimations = com.android.internal.R.style.Animation_Toast;//布局动画
//        params.type = WindowManager.LayoutParams.TYPE_TOAST;//窗口类型
//        //params.setTitle("Toast");//窗口标题
//        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;//窗口标记, 制定某些特性
//
//        //初始化布局对象
//        view = new TextView(this);
//        view.setText(text);
//        view.setTextColor(Color.RED);
//        view.setTextSize(30);
//
//        //给窗口添加布局对象
//        mWM.addView(view, params);
//    }
}
