### 9 activity���������ڣ��ص㣩
* ������()-->��()-->����()-->����()-->����-->����()-->�̱���

* ��	     activity��onCreate()-->onStart()-->onResume()
* �ر�,���ؼ�activity��onPause()-->onStop()-->onDestroy()
* ��С��,Home��	     ��onPause()-->onStop()
* ���,Home����������onRestart()-->onStart()-->onResume()

���෽��:
public boolean onTouchEvent(MotionEvent event);//�����¼�

ע�⣺��Activity�ں�̨ʱ�����ϵͳ�ڴ治���ã���̨��Activity���п��ܻᱻǿ�����ٻ����ڴ�ģ�����
��ôһ���ᴥ��onSaveInstanceState(Bundle outState) ����,���ǿ��԰��뱣������ݱ����� outState������,
��Activity�����´���ִ��onCreate(Bundle saveInstanceState) �Ӳ���saveInstanceState�ٶ�ȡ���������

ActivityA �� ActivityB
A��onPause()	B��onCreate()	B��onStart()	B��onResume()	A��onStop()

* ���ǣ����ActivityB����ʽ�� ͸�� or Dialog��ʽ��Bû����ȫ�ڵ�A��ActivityB���������ڸ��ղ�һ����
����ActivityA��û��ִ��onStop()

��ActivityB ���� ActivityA��������ģ�����ڴ���ActivityB ���水��back�������� ActivityA��
�����������£�
B��onPause() 
A��onRestart() 
A��onStart() 
A��onResume() 
B��onStop() 
B��onDestory()

//-------------------------------------------------------------------
### 10 ���ĵ��鿴activity���������ڵķ���

1. ������������	    1.���Ӻ�	2.��ȡ�����  3.ʧȥ����  4.������  5.���ٺ����
	onCreate()-->onStart()-->onResume()-->onPause()-->onStop()-->onDestroy()

2. ������������			6.���������
		     onStart()-->onResume()-->onPause()-->onStop()

3. ǰ̨��������
				 onResume()-->onPause()

4.���  7.���¿�ʼ
	onRestart()-->onStart()-->onResume()
//-------------------------------------------------------------------
### 11 �������л�activity����������
* ������activity�����´���һ���µ�activity

#����ջ��Activity������ģʽ
//-------------------------------------------------------------------
### 12 ����ջ�ĸ���	
* ���񣺴򿪵�activity����
* ջ	  ���Ƚ����FILO
* ���ã�����򿪵�activity����ģ�����ά���û�������
//-------------------------------------------------------------------
### 13 Activity������ģʽ		//launchMode����ģʽ
* ���ã�Ӱ��activity������ջ�е�˳��

* standard����׼Ĭ��
	* ����Ŀ��activity,ϵͳ����һ��activity�Ķ���,ʹ��λ��Ӧ������ջ��ջ��,������ؼ�,�Ƴ�����ջջ����activity

	* Ӧ�ó�����Ĭ�ϵģ����Բ�д

* singleTop:��һ����ģʽ
	* ����Ŀ��activity,ϵͳ��ȥ����ջ��ջ��������û�����activity,�����,�͸���ջ����activity;���û��,����ջ������һ���µ�activityʵ������
	
	* ������Ӧ�ã����������ǩ

* singleTask����һ����ģʽ
	* �����ǰ��������Activity�Ѿ�����,������ջ���κ�λ��,���Ὣ��λ�������Activityȫ���Ƴ�,Ȼ���ø�Activity.
	�����,���Ƴ����activity���������activityʵ������,ʹ��λ������ջ��ջ��;���û������ջ���������activity��ʵ������
	* ����Ӧ�ã�ϵͳ�����

* singleInstance����һʵ��ģʽ
	* һ������ջ��ֻ��һ��Activity������֤����������,�������ֻ���ֻ��һ��ʵ��
	* ����Ŀ��activity,ϵͳ��Ϊ���activity��������һ������ջ,�����ֻ���ֻ��һ��������ʵ�����󡣵�����ؼ�,�����һ������ջ,Ȼ�����������һ������ջ��
	
	* ������Ӧ�ã��ֻ��������

onNewIntent
��Activity����Standardģʽ,���ұ����õ�ʱ��,�ᴥ��onNewIntent(Intent intent)�������,һ��������ȡ�µ�Intent���ݵ�����.

����һ����MainAcitivy����ΪSingleTask,���˱�֤MainActivity��Ψһ������������singleTask��������һЩ���������Զ�����ջ���������õ�Acitivity.

Intent Flags
��סһ�㣺Activity��������������ջ���棬�����Ҫ�ӹ㲥������BordercastReceiver���߷���Serviceȥ����һ��Activity������Ϊ��ǰActivity����һ���µ�����ջ����������ʾ
public class MyReceiver extends BroadcastReceiver{
   public void onReceive(Context context, Intent intent) {
      Intent intent=new Intent(context,DemoActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//�����µ�����ջ
      context.startActivity(intent);
 }
//============================================================================
package com.itheima.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	
	/**
	 * activity����������õĵ�һ������
	 * �ʺ���һЩ��ʼ��������
	 * ��ʼ���ؼ�������activity�Ĳ����ļ�
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.e(TAG, "onCreate");
	}

	/**
	 * activity���Ӻ����
	 */
	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "onStart");
	}
	/**
	 * activity���������
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e(TAG, "onRestart");
	}
	
	//�����¼�
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
	
	/**
	 * activity��ȡ��������
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "onResume");
	}
	/**
	 * ʧȥ����ǰ����
	 */
	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, "onPause");
	}
	/**
	 * activity������ǰ����
	 */
	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "onStop");
	}
	/**
	 * activity����ǰ����
	 * �ʺ�����β�Ĺ���
	 * ��������
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy");
	}
}
