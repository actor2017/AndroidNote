本例来源:来电显示的自定义Toast
    //展示自定义吐司(查看Toast,MakeText,show的源码)
    /**
     * 声明2个全局变量:
     * private WindowManager mWM;
     * private TextView mView;
     */
    private void AddressToast(String address) {
        //窗口管理器
        //窗口: 安卓系统最高级别的布局, 所有界面都基于窗口显示, 在窗口中显示activity, dialog, 状态栏
        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

        //初始化布局参数(这段从Toast的第366行抄的)
        //初始化布局的宽高, 位置等信息
        final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//宽高包裹内容
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽高包裹内容
        mParams.format = PixelFormat.TRANSLUCENT;//显示方式, 默认就行
        //布局动画(这儿是指Toast的淡入淡出动画,可要可不要)
        //mParams.windowAnimations = com.android.internal.R.style.Animation_Toast;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;//窗口类型(Toast的类型)
        mParams.setTitle("Toast");//窗口标题(可要可不要)
        //窗口标记, 制定某些特性
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON//保持屏幕之上
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE //没有焦点
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;//不能触摸

        //初始化布局对象(不是抄的,殷凯写的)
        mView = new TextView(this);
        mView.setText(address);
        mView.setTextColor(Color.RED);
        mView.setTextSize(30);

        //给窗口添加布局对象(抄的,Toast的425行)
        mWM.addView(mView, mParams);
    }
}
