new Thread(){
	@Override
	public void run() {//重写run方法
		super.run();
		//子线程做耗时操作
	}
}.start();


//在小括号里面写Runnable
new Thread(new Runnable() {
	@Override
	public void run() {
	}
}).start();


new Thread(runnable).run();//调用thread的run()方法, 会调用到runnable的run()
new Thread().start();//子线程


Thread.currentThread().getId();		//线程id,主线程=1??	子线程id比较大

//==============================================================
    public static void showToast(final String toast, final Context context) {
    	new Thread(new Runnable() {//子线程
			
			@Override
			public void run() {//主线程, 具体见Hander文件夹
				Looper.prepare();
				Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}).start();
    }

