package cn.itcast.mobilesafe12.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by Kevin.
 * <p>
 * Handler, Looper, MessageQueen
 * 在其它线程调用:ToastUtils.show(this?,"wxyz");就行
 */
public class ToastUtils {

    //使用主线程looper初始化handler,保证handler发送的消息运行在主线程
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void show(final Context ctx, final String text) {
        //判断当前是主线程还是子线程
        if (Looper.myLooper() == Looper.getMainLooper()) {
            //当前looper是否等于主线程looper, 如果是, 说明当前是在主线程
            Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
            System.out.println("主线程吐司....");
        } else {
            //子线程
            //handler.sendEmptyMessage(0);//handler发送一个消息给队列
            //handler发送一个任务给队列
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //当Looper轮询到此任务时, 会在主线程运行此方法
                    Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
                }
            });
            System.out.println("子线程吐司....");
        }
    }

}
