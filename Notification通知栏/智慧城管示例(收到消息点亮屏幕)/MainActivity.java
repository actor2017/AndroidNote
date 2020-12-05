
1.LoginActivity:
//停止服务, 防止登录的不是同一个人, 会接收上一个人的消息.
stopService(new Intent(this, PrinceImNotificationService.class));


2.MainActivity:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //经测试, 不要下方几行设置 window 也可以...
        Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED //锁屏状态下显示
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD //解锁
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON //保持屏幕长亮
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON); //打开屏幕

        //<!--后台服务-->
        //<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

        //开启服务
        if (!ServiceUtils.isServiceRunning(PrinceImNotificationService.class)) {
            startForegroundService(new Intent(activity, PrinceImNotificationService.class));
        }
    }