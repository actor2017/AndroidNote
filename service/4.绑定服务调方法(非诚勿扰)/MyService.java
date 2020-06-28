package com.itheima.call_method;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
private static final String TAG = "MyService";

	//���񱻰󶨳ɹ������
	@Override
	public IBinder onBind(Intent intent) {
		return new ChengMiShu();
	}

	/**
	 * ������ķ���
	 */
	public void methodInService(){
		Toast.makeText(getApplicationContext(), "�����ˣ������Ķ���", 0).show();
	}
	
	/**
	 * ��������ڲ���
	 * �ڲ���Ա
	 * С��
	 */
	public class ChengMiShu extends Binder implements IMyService {
		@Override
		public void qianShouMM(int money){
			if (money > 1000) {
				methodInService();
			}else {
				Toast.makeText(MyService.this, "ûǮ������ϱ������ȥд��������", 0).show();	
			}
		}
		@Override
		public void ���齫(){
			Toast.makeText(MyService.this, "Ҫ�����ˣ�����", 0).show();
		}
		
		//private,ֻ����boss
		private void ϴɣ��(){
			Toast.makeText(MyService.this, "ϴɣ�ð���Ү������", 0).show();
		}
	}
	
}
