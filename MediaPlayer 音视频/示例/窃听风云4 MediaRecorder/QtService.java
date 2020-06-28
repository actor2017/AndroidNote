package com.itheima.qt;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class QtService extends Service {
	private TelephonyManager		tm;
	private MyPhoneStateListener	mPhoneStateListener;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 1. ��ȡ�ֻ��绰������
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		// 2. ���ü���
		mPhoneStateListener = new MyPhoneStateListener();
		tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	/**
	 * 3. �ڲ���ʵ�ּ���
	 */
	private class MyPhoneStateListener extends PhoneStateListener {
		private MediaRecorder	recorder;

		// �ֻ��ĺ���״̬�仯�ص�
		//state:״̬(����,ͨ��,����)
		// incomingNumber:�����ֻ�����
		@Override
		//��д�绰״̬�ı�ķ���,������дһЩ��������(�ź�ǿ��,����ģʽ....)
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			System.out.println("������룺" + incomingNumber);
			switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:// ����
					System.out.println("����");
					if (recorder != null) {
						//8. ֹͣ¼��
						recorder.stop();
						 //9.����
						 recorder.reset();   // You can reuse the object by going back to setAudioSource() step
						 //10. �ͷ���Դ
						 recorder.release(); // Now the object cannot be reused
						 recorder = null;
					}
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:// ͨ��
					System.out.println("ͨ��");
					//��ʼ¼��
					//1. ����¼����
					recorder = new MediaRecorder();
					//2. ������ƵԴ
					 recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					 //3. ������Ƶ�ĸ�ʽ(������ʽҪ�շ�?)
					 recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					 //4. ���ñ�����Ƶ
					 recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					 //5. ������Ƶ�洢��λ��
					 recorder.setOutputFile("/mnt/sdcard/luyin.3gp");
					 try {
						 //6.׼��¼��
						recorder.prepare();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					 //7. ��ʼ¼��
					 recorder.start();   // Recording is now started
					
					break;
				case TelephonyManager.CALL_STATE_RINGING:// ����
					System.out.println("������");
					break;

				default:
					break;
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 4. ��������ǰע������
		tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
	}
}
