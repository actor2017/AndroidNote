new Thread(){
	@Override
	public void run() {//��дrun����
		super.run();
		//���߳�����ʱ����
	}
}.start();


//��С��������дRunnable
new Thread(new Runnable() {
	@Override
	public void run() {
	}
}).start();


new Thread(runnable).run();//����thread��run()����, ����õ�runnable��run()
new Thread().start();//���߳�


Thread.currentThread().getId();		//�߳�id,���߳�=1??	���߳�id�Ƚϴ�

//==============================================================
    public static void showToast(final String toast, final Context context) {
    	new Thread(new Runnable() {//���߳�
			
			@Override
			public void run() {//���߳�, �����Hander�ļ���
				Looper.prepare();
				Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}).start();
    }

