    @BindView(drawer)
    SlidingDrawer mDrawer;                   //抽屉

public class ProcessManagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_manager);
        ButterKnife.bind(this);

        //初始化箭头动画
        initArrowAnim();

        //监听抽屉打开的事件
        mDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                //抽屉被打开
                ivArrow1.setImageResource(R.drawable.drawer_arrow_down);
                ivArrow2.setImageResource(R.drawable.drawer_arrow_down);

                //停止动画
                ivArrow1.clearAnimation();
                ivArrow2.clearAnimation();
            }
        });
		//监听抽屉关闭的事件
        mDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                //抽屉关闭
                ivArrow1.setImageResource(R.drawable.drawer_arrow_up);
                ivArrow2.setImageResource(R.drawable.drawer_arrow_up);

                //开始动画
                initArrowAnim();
            }
        });
    }

    //初始化箭头动画
    private void initArrowAnim() {
        //渐变动画
        AlphaAnimation arrow1 = new AlphaAnimation(0.2f, 1);
        //持续时间
        arrow1.setDuration(500);
        //重复次数,infinite:无限
        arrow1.setRepeatCount(Animation.INFINITE);
        //重复模式:往返
        arrow1.setRepeatMode(Animation.REVERSE);
        //开启箭头1的动画
        ivArrow1.startAnimation(arrow1);
        
        //渐变动画
        AlphaAnimation arrow2 = new AlphaAnimation(1, 0.2f);
        //持续时间
        arrow2.setDuration(500);
        //持续次数
        arrow2.setRepeatCount(Animation.INFINITE);
        //持续模式
        arrow2.setRepeatMode(Animation.REVERSE);
        //开启箭头2的动画
        ivArrow2.startAnimation(arrow2);
    }
