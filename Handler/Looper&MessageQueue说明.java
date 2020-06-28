1.0.Looper			//轮询器
//静态方法,直接调用
void prepare();	//绑定消息对象,创建消息队列
void prepareMainLooper();
getMainLooper();//android.content.ContextWrapper extends Context,Activity中方法
Looper getMainLooper();	//获取主线程的looper
Looper myLooper();		//获取现在线程的looper
void loop();			//启动消息循环,轮询消息队列
MessageQueue myQueue();

1.1.普通方法
boolean isCurrentThread();
void setMessageLogging(@Nullable Printer printer);
void quit();
void quitSafely();
Thread getThread();
MessageQueue getQueue();
void dump(@NonNull Printer pw, @NonNull String prefix);

1.2.Looper示例
public static void showToast(final String toast, final Context context) {
	new Thread(new Runnable() {//子线程
		
		@Override
		public void run() {
			Looper.prepare();
			Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
			Looper.loop();
		}
	}).start();
}


2.MessageQueue消息队列 ==========================================================


