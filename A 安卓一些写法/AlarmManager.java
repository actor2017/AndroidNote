ע��:
AlarmManager������ʱ��,����,
���Ǻ������ظ�ִ�д���.

//��Ŀ����������
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent restartIntent = PendingIntent.getActivity(this, 0, intent, 0);//PendingIntent.FLAG_...
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);//1000:1���Ӻ�����Ӧ��
        System.exit(-1);//�˳�
		

7.AlarmManager��������service���÷�ʽԭ����ͨ����ʱ��������������service����������service��ɱ����Ҳ����������ͬʱҲ���Լ��������л����������ȹ㲥������service��
�ο�ʵ�ַ�ʽ���£�

Intent intent =new Intent(mContext, MyService.class);
PendingIntent sender=PendingIntent.getService(mContext, 0, intent, 0);
AlarmManager alarm=(AlarmManager)getSystemService(ALARM_SERVICE);
alarm.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),5*1000,sender);

�÷�ʽ�������Ա�֤��������������£��Լ��������Ƴ���ʷ�����С�ס������ֻ����⣩��service����ɱ��������360������ܼ���Ȼ����ɱ�������⻹�в����������߼������鷳��



����һ�������ӣ���һ��������ʾ�������ͣ��ڶ���������ʾ����ִ��ʱ�䣬������������ʾ������Ӧ������
set(int type, long triggerAtMillis, PendingIntent operation)


�����ظ����ӣ���һ��������ʾ�������ͣ��ڶ���������ʾ�����״�ִ��ʱ�䣬������������ʾ��������ִ�еļ��ʱ�䣬���ĸ�������ʾ������Ӧ������
setRepeating(int type, long triggerAtMillis,long intervalMillis, PendingIntent operation)


�����ظ����ӣ���ڶ����������ƣ���������ʱ�䲻��ȷ��
setInexactRepeating(int type, long triggerAtMillis,long intervalMillis, PendingIntent operation)


����1�ͷ���2��SDK_INT 19��ǰ�Ǿ�ȷ������,19�Ժ�Ϊ�˽���ʡ�磨����ϵͳ���Ѻ͵��ʹ�ã���ʹ��Alarm.set()��Alarm.setRepeating()�Ѿ����ܱ�֤��ȷ��,��������Google���ṩ��������ȷ��Alarm����setWindow()��setExact(),����19�Ժ���Ҫ��ȷ�����Ӿ���Ҫ������������,����ԭ�������˵
setExact(int type, long triggerAtMillis, PendingIntent operation)
setWindow(int type, long windowStartMillis, long windowLengthMillis,PendingIntent operation)


ȡ��Intent��ͬ������,�����Ǹ���Intent��filterEquals(Intent other)�������ж��Ƿ���ͬ
cancel(PendingIntent operation)


�������;���ǰ��setxxx()������һ������int type.
AlarmManager.ELAPSED_REALTIME��ʹ�����ʱ�䣬����ͨ��SystemClock.elapsedRealtime() ��ȡ���ӿ��������ڵĺ������������ֻ���˯��ʱ�䣩���豸����ʱ�����ỽ���豸��
AlarmManager.ELAPSED_REALTIME_WAKEUP����ELAPSED_REALTIME��������һ����ֻ�ǻ����豸����ʱ�����豸��
AlarmManager.RTC��ʹ�þ���ʱ�䣬����ͨ�� System.currentTimeMillis()��ȡ���豸����ʱ�����ỽ���豸��
AlarmManager.RTC_WAKEUP: ��RTC��������һ����ֻ�ǻ����豸����ʱ�����豸�� 





ʾ��:
AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

Intent intent = new Intent(this, AlarmService.class);
intent.setAction(AlarmService.ACTION_ALARM);
PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
if(Build.VERSION.SDK_INT < 19){
    am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
}else{
    am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
}