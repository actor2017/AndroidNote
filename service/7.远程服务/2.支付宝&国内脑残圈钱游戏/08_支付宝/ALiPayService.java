package com.itheima.alipay;

import com.itheima.alipay.IALiPayService.Stub;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ALiPayService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return new MaYun();
	}

	private class MaYun extends Stub {
		@Override
		public void pay(String name, String pwd, float money, long time) {
			aliPay(name, pwd, money, time);
		}
	}

	/**
	 * 服务里支付的方法 支付宝的核心逻辑
	 */
	public void aliPay(String name, String pwd, float money, long time) {
		System.out.println("进行解密操作");
		System.out.println("验证账号和密码是否匹配");
		System.out.println("查询账户余额");

		System.out.println("支付成功：" + money);
	}
}
