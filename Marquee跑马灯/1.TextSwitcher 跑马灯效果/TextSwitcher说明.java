TextSwitcher extends ViewSwitcher extends ViewAnimator extends FrameLayout extends ViewGroup
1.只能添加2个TextView
2.不能自动切换,需要自己写Handler实现.
3.默认没有动画

    <TextSwitcher
        android:id="@+id/ts_textSwitcher"
        android:layout_width="match_parent"
        android:layout_height="20dp"
        android:animateFirstView="true"
        android:inAnimation="@android:anim/slide_in_left"
		android:loopViews="true"//循环(没有这个属性)
        android:outAnimation="@android:anim/slide_out_right"><!-- 还有其它动画,也可以自己实现动画 -->

3.添加TextView
ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
TextView textView = new TextView(this);
textView.setText("11111111111111");
TextView textView2 = new TextView(this);
textView2.setText("22222222222222");
textSwitcher.addView(textView, 0, layoutParams);//可不要position & layoutparams
textSwitcher.addView(textView2, 1, layoutParams);

4.也可以这样添加TextView,会同时创建2个TextView,默认match_parent&wrap_content
  然后通过textSwitcher通过textSwitcher.setText()赋值
textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
    @Override
    public View makeView() {
        TextView textView = new TextView(MainActivity.this);
        return textView;
    }
});
textSwitcher.setText("11111111111111");
textSwitcher.setText("22222222222222");

5.方法
textSwitcher.setText(items.get(msg.what));//★★★设置下一个TextView的内容, 并切换到下一内容(如果有动画,会展示动画)★★★
textSwitcher.setCurrentText(items.get(msg.what));//设置现在的text, 不会切换到下一个view
textSwitcher.showNext();//切换到下一个,如果设置了循环和动画,都会展示
		
示例:
    String[] strs=new String[]{
            "疯狂Java讲义",
            "疯狂Android讲义",
            "疯狂ajax讲义",
            "轻量级Java EE企业应用实战"
    };
    private Handler handler = new Handler();
    private int pos = 0;
    private Runnable runnable;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tsTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView textView = new TextView(activity);
                textView.setTextSize(36);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.parseColor("#333333"));
                textView.setMaxLines(1);
                textView.setOnClickListener(new View.OnClickListener() {//点击事件
                    @Override
                    public void onClick(View v) {
                        toast(textView.getText());
                    }
                });
                return textView;
            }
        });
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
                tsTextSwitcher.setText(strs[pos]);
                pos++;
                if (pos == strs.length) pos = 0;
            }
        };
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 2000);
		
		
    @Override
    protected void onDestroy() {
        super.onDestroy();
        println("onDestroy");
        handler.removeCallbacks(runnable);
		handler.removeCallbacksAndMessages(null);
    }