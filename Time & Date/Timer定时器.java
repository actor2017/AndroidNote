java.util.Timer	:��ʱ��Timer,���Ե���ִ��,�ظ�ִ��(�����ֲ�ͼ,TimeTask����flag��ͣ/����)

timer.schedule(TimerTask task, long delay);			//��ʱdelay�����ִ��1��
timer.schedule(TimerTask task, long delay, long period);	//��ʱdelay���������periodʱ��ִ������
timer.schedule(TimerTask task, Date time);			//�ڹ̶�ʱ��timeִ��
timer.schedule(TimerTask task, Date firstTime, long period);	//�ڹ̶�ʱ��������periodʱ��ִ������
timer.scheduleAtFixedRate(TimerTask task, long delay, long period);//��ʱdelay���������periodʱ��ִ������
timer.scheduleAtFixedRate(TimerTask task, Date firstTime, long period);//�ڹ̶�ʱ��firstTime������periodʱ��ִ������


//1.���չ̶�Ƶ��ִ��ĳ������; ��1:�������; ��2:��һ��ִ�������ǵ���ʱ; ��3:����ִ�м��ʱ��
    private Timer     timer;
    private TimerTask timerTask;

@Override
protected void onStart() {
	super.onStart();
	timer = new Timer();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	String today = "2016-08-18 00:00:00";
	Date date = null;
	try {
		date = format.parse(today);
	} catch (ParseException e) {
		e.printStackTrace();
	}
        timer.scheduleAtFixedRate(new TimerTask() {//TimerTask.cancel();//�����Ҫ������,����cancel();,���ظ����ò�����
            @Override
            public void run() {//���߳�
                System.out.println("����ÿ��12���Ϣ��...");
				cancel();//TimerTask �ķ���, ������֮��, ��ʱ�����񻹻����ִ��...?????????
            }
        }, date, 24*60*60*1000);
        timer.schedule(timerTask, 600, 600);
}

@Override
protected void onStop() {
	super.onStop();
	if (timerTask != null) timerTask.cancel();//������֮��, ��ʱ�����񻹻����ִ��...?????????
	if (timer != null) timer.cancel();//ȡ�����ٵ���timer.schedule()�ᱨ��
	timerTask = null;
	timer = null;
}


//2.����ִ������:ͼƬ��ʾ.����------------
        ivSuccess.setVisibility(View.VISIBLE);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {//schedule(TimerTask task, long delay)
                @Override
                public void run() {
                        ThreadUtils.RunOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                        ivSuccess.setVisibility(View.GONE);
                                        timer.cancel();
                                }
                        });
                }
        },1000);