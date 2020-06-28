1����xml��������android:priority
 <!--���÷�������ȼ�ΪMAX_VALUE-->
 <service android:name=".MyService"
          android:priority="2147483647"
          >
 </service>

2����onStartCommand����������Ϊǰ̨����
 @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("��Notification");
        builder.setContentTitle("����");
        builder.setContentText("֪ͨ����");
        builder.setSubText("������ʾ����֪ͨ���������ݣ�");
        builder.setNumber(2);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        startForeground(1, notification);   //1.notification��id, notification
        return Service.START_STICKY;        //2.����flag����ֵΪSTART_STICKY
    }

3.�����ػ�����,��һ�����񱻸ɵ���,����������һ������
	
4����onDestroy����������service,����֤
@Override
public void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, MyService.class));
}


5����AlarmManager.setRepeating(...)����ѭ���������ӹ㲥,���յ�ʱ�����service��onstart����
Intent intent = new Intent(MainActivity.this,MyAlarmReciver.class);
        PendingIntent sender = PendingIntent.getBroadcast( MainActivity.this, 0, intent, 0);

        // We want the alarm to go off 10 seconds from now.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 1);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        //�ظ�����
        /**
         *  @param type
         * @param triggerAtMillis t ���ӵĵ�һ��ִ��ʱ�䣬�Ժ���Ϊ��λ
         * go off, using the appropriate clock (depending on the alarm type).
         * @param intervalMillis ��ʾ��������ִ�еļ��ʱ�䣬Ҳ���Ժ���Ϊ��λ
         * of the alarm.
         * @param operation �������ӵ�ִ�ж��������緢��һ���㲥��������ʾ�ȵ�
         */
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 2 * 1000, sender);


6��Ŀǰ�г���ĺܶ���������Ϣ����SDK����APP,����Jpush



�ܽ�
�ⴿ�������Ե�ʱ�����һ�����Թ٣������������Service�����������ˣ�ֻ��˵������˽��̵����ȼ���
����Ϊֹ��û�з����ܹ�ͨ�����淽���ﵽ��å����(ͨ������home��������������)�ķ�����
Ŀǰ���з�������ָͨ��Android���ڴ���ջ��ƺ���ͨ�ĵ������ڴ�������ֶκ���Ȼ�������еķ�����
��Щ�ֻ����̰���Щ֪����app�������Լ��İ������У���֤�˽��̲���������û����飨��΢�š�QQ��İİ����С�׵İ������У���
����Ӱ��������Ƴ��������վ����Ǻ���ͨappһ����ܲ��˱�ɱ�����ˡ�


