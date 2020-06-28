package cn.itcast.mobilesafe12.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.annotation.DrawableRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import cn.itcast.mobilesafe12.R;

/**
 * Created by Kevin.
 * 归属地显示的自定义吐司,相当于工具类
 * 使用方法:
 *  1.private AddressToast mToast = new AddressToast(this);穿件本类对象,传this
 *  2.mToast.show(address);调用自定义显示的方法,把归属地传进来,用来显示
 *    mToast.show(address, AddressToast.STYLE.ORANGE);//默认提供的一种背景颜色
 *    mToast.show(address, R.drawable.shape_toast_custom);//自定义shape背景
 *  3.mToast.hide();        调用自定义隐藏的方法
 *
 * 为了保证窗口布局能够响应触摸监听:
 *
 * 1. layoutParams.flags: 去掉FLAG_NOT_TOUCHABLE标记
 * 2. layoutParams.type: 修改显示类型(级别): WindowManager.LayoutParams.TYPE_PHONE, 电话类型
 * 3. 加权限: android.permission.SYSTEM_ALERT_WINDOW
 */

public class AddressToast {

    private WindowManager windowManager;
    private TextView textView;
    private WindowManager.LayoutParams layoutParams;
    private int startX;
    private int startY;

    public AddressToast(Context ctx) {

        //窗口管理器
        //窗口: 安卓系统最高级别的布局, 所有界面都基于窗口显示, 在窗口中显示activity, dialog, 状态栏
        windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);

        //初始化布局参数
        //初始化布局的宽高, 位置等信息
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//宽高包裹内容
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽高包裹内容
        layoutParams.format = PixelFormat.TRANSLUCENT;//显示方式, 默认就行
        //layoutParams.windowAnimations = com.android.internal.R.style.Animation_Toast;//布局动画
        //layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;//窗口类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;//窗口类型, 显示级别
        //layoutParams.setTitle("Toast");//窗口标题,不要标题,否则上面多一块
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //去掉不能触摸的特性,否则无法移动
        //| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;//窗口标记, 制定某些特性

        //int x = layoutParams.x;//当前窗口的x相对坐标
        //int y = layoutParams.y;//当前窗口的y相对坐标

        //初始化布局对象
//        textView = new TextView(ctx);
//        textView.setTextColor(Color.RED);
//        textView.setTextSize(30);

        //初始化自定义布局
        textView = (TextView) View.inflate(ctx, R.layout.toast_address, null);

        //设置触摸监听,实现可移动
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // System.out.println("onTouch:" + event.getAction());//实时打印0,1,2:按下,抬起,移动
//                1. 按下时, 记录起点坐标
//                2. 移动时, 记录移动后的坐标k
//                3. 计算坐标偏移量
//                4. 根据坐标偏移量, 更新控件位置
//                5. 重新初始化起点坐标
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //1. 按下时, 记录起点坐标
//                        int x = (int) event.getX();//获取的是相对于当前控件的坐标, 以控件左上角为0,0点
//                        int y = (int) event.getY();
//                        System.out.println("x:" + x + ";y:" + y);

                        //获取的是相对于屏幕的坐标, 以屏幕左上角为0,0点
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        //System.out.println("rawX:" + rawX + ";rawY:" + rawY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 2. 移动时, 记录移动后的坐标
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();

                        // 3. 计算坐标偏移量
                        int dx = moveX - startX;
                        int dy = moveY - startY;

                        // 4. 根据坐标偏移量, 更新控件位置
                        layoutParams.x += dx;
                        layoutParams.y += dy;//当前控件位置也要进行相应偏移
                        //将偏移后的效果作用到窗口布局上
                        windowManager.updateViewLayout(textView, layoutParams);//更新控件

                        // 5. 重新初始化起点坐标
                        startX = moveX;
                        startY = moveY;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;//消费此事件, 事件不再向下传递
            }
        });
    }

    public enum STYLE {
        TRANSPARENT_GRAY(R.drawable.shape_toast_normal),//半透明,透明灰
        ORANGE(R.drawable.shape_toast_orange),//活力橙
        BLUR(R.drawable.shape_toast_blue),//卫士蓝
        LITE_GRAY(R.drawable.shape_toast_gray),//金属灰
        LITE_GREEN(R.drawable.shape_toast_green);//苹果绿

        private int resId;
        STYLE(@DrawableRes int resId) {
            this.resId = resId;
        }
    }

    /**
     * 默认显示半透明,透明灰背景
     */
    public void show(String text) {
        show(text, STYLE.TRANSPARENT_GRAY);
    }

    /**
     * 可在提供的几种背景中选择样式
     */
    public void show(String text, STYLE style) {
        show(text, style.resId);
    }

    /**
     * 自定义@DrawableRes背景
     */
    public void show(String text, @DrawableRes int drawableRes) {
        textView.setBackgroundResource(drawableRes);//更新TextView背景
        textView.setText(text);
        windowManager.addView(textView, layoutParams);//给窗口添加布局对象
    }

    //隐藏
    public void hide() {
        //一旦启动服务, 会马上进入CALL_STATE_IDLE(电话空闲)状态, 执行隐藏窗口布局的方法,
        // 但此时还没有给窗口添加布局, 从而系统报错: View not attached to window manager
        //解决方法: ctrl+alt+t : 自动try catch
        try {
            windowManager.removeView(textView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
