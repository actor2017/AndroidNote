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
	 * ������֧���ķ��� ֧�����ĺ����߼�
	 */
	public void aliPay(String name, String pwd, float money, long time) {
		System.out.println("���н��ܲ���");
		System.out.println("��֤�˺ź������Ƿ�ƥ��");
		System.out.println("��ѯ�˻����");

		System.out.println("֧���ɹ���" + money);
	}
}
