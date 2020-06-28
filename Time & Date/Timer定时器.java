java.util.Timer	:定时器Timer,可以单次执行,重复执行(可做轮播图,TimeTask设置flag暂停/播放)

timer.schedule(TimerTask task, long delay);			//延时delay毫秒后执行1次
timer.schedule(TimerTask task, long delay, long period);	//延时delay毫秒后周期period时间执行任务
timer.schedule(TimerTask task, Date time);			//在固定时间time执行
timer.schedule(TimerTask task, Date firstTime, long period);	//在固定时间以周期period时间执行任务
timer.scheduleAtFixedRate(TimerTask task, long delay, long period);//延时delay毫秒后周期period时间执行任务
timer.scheduleAtFixedRate(TimerTask task, Date firstTime, long period);//在固定时间firstTime以周期period时间执行任务


//1.按照固定频率执行某个任务; 参1:任务对象; 参2:第一次执行任务是的延时; 参3:任务执行间隔时间
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
        timer.scheduleAtFixedRate(new TimerTask() {//TimerTask.cancel();//如果不要任务了,调用cancel();,可重复调用不报错
            @Override
            public void run() {//子线程
                System.out.println("银行每晚12点计息了...");
				cancel();//TimerTask 的方法, 调用了之后, 有时候任务还会继续执行...?????????
            }
        }, date, 24*60*60*1000);
        timer.schedule(timerTask, 600, 600);
}

@Override
protected void onStop() {
	super.onStop();
	if (timerTask != null) timerTask.cancel();//调用了之后, 有时候任务还会继续执行...?????????
	if (timer != null) timer.cancel();//取消后再调用timer.schedule()会报错
	timerTask = null;
	timer = null;
}


//2.单次执行任务:图片显示.隐藏------------
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