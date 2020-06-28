注意:
AlarmManager能做定时器,闹钟,
但是好像不能重复执行代码.

//项目崩溃后重启
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent restartIntent = PendingIntent.getActivity(this, 0, intent, 0);//PendingIntent.FLAG_...
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);//1000:1秒钟后重启应用
        System.exit(-1);//退出
		

7.AlarmManager不断启动service。该方式原理是通过定时警报来不断启动service，这样就算service被杀死，也能再启动。同时也可以监听网络切换、开锁屏等广播来启动service。
参考实现方式如下：

Intent intent =new Intent(mContext, MyService.class);
PendingIntent sender=PendingIntent.getService(mContext, 0, intent, 0);
AlarmManager alarm=(AlarmManager)getSystemService(ALARM_SERVICE);
alarm.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),5*1000,sender);

该方式基本可以保证在正常运行情况下，以及任务栏移除历史任务后（小米、魅族手机除外），service不被杀死。但是360等软件管家依然可以杀死。另外还有不断启动的逻辑处理麻烦。



设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作。
set(int type, long triggerAtMillis, PendingIntent operation)


设置重复闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟首次执行时间，第三个参数表示闹钟两次执行的间隔时间，第四个参数表示闹钟响应动作。
setRepeating(int type, long triggerAtMillis,long intervalMillis, PendingIntent operation)


设置重复闹钟，与第二个方法相似，不过闹钟时间不精确。
setInexactRepeating(int type, long triggerAtMillis,long intervalMillis, PendingIntent operation)


方法1和方法2在SDK_INT 19以前是精确的闹钟,19以后为了节能省电（减少系统唤醒和电池使用）。使用Alarm.set()和Alarm.setRepeating()已经不能保证精确性,不过还好Google又提供了两个精确的Alarm方法setWindow()和setExact(),所以19以后需要精确的闹钟就需要上面两个方法,具体原因后面再说
setExact(int type, long triggerAtMillis, PendingIntent operation)
setWindow(int type, long windowStartMillis, long windowLengthMillis,PendingIntent operation)


取消Intent相同的闹钟,这里是根据Intent中filterEquals(Intent other)方法来判断是否相同
cancel(PendingIntent operation)


闹钟类型就是前面setxxx()方法第一个参数int type.
AlarmManager.ELAPSED_REALTIME：使用相对时间，可以通过SystemClock.elapsedRealtime() 获取（从开机到现在的毫秒数，包括手机的睡眠时间），设备休眠时并不会唤醒设备。
AlarmManager.ELAPSED_REALTIME_WAKEUP：与ELAPSED_REALTIME基本功能一样，只是会在设备休眠时唤醒设备。
AlarmManager.RTC：使用绝对时间，可以通过 System.currentTimeMillis()获取，设备休眠时并不会唤醒设备。
AlarmManager.RTC_WAKEUP: 与RTC基本功能一样，只是会在设备休眠时唤醒设备。 





示例:
AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

Intent intent = new Intent(this, AlarmService.class);
intent.setAction(AlarmService.ACTION_ALARM);
PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
if(Build.VERSION.SDK_INT < 19){
    am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
}else{
    am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
}