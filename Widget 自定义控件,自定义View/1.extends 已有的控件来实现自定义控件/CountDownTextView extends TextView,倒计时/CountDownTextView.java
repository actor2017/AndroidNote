package cn.redboy.widget;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author   : 张林
 * Date     : 2017/3/15.15:19
 */

public class CountDownTextView extends TextView {
	public CountDownTextView(Context context) {
		super(context);
	}

	public CountDownTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private String empireTime;

	public void setBeginPosition(String empireTime) {
		this.empireTime = empireTime;
		updateTime();
		mHandler.removeMessages(0);
		mHandler.sendEmptyMessageDelayed(0,1000);

	}

	private void updateTime() {
		try{
			SimpleDateFormat sdf  =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date empireDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(empireTime);
			long remainingTimeMillis = empireDate.getTime() - System.currentTimeMillis();
			//此处纯属取巧,应该换算成时分秒
			SimpleDateFormat sdf1  =   new SimpleDateFormat("dd天 HH时:mm分:ss秒");
			String remainingTime = sdf1.format(remainingTimeMillis);
			setText(remainingTime);
			System.out.println("remainingTime = " + remainingTime);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			updateTime();
			mHandler.removeMessages(0);
			mHandler.sendEmptyMessageDelayed(0,1000);

		}
	};
}
