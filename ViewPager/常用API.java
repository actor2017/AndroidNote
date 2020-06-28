学习资料:http://www.jianshu.com/p/f8bf9e8b0a39

★★★★★注意:ViewPager的高度必须写成定值,如果写成match_parent非常有可能会出错!!!!!!★★★★★★

vpViewPager.setCurrentItem(0,true);//第二个参数SmoothScroll:是否平滑的滑动切换到该页面?
vpViewpager.setCurrentItem(vp_viewpager.getCurrentItem()+1);//让ViewPager滑动到下一页(居然不做越界判断...)

vp_viewpager.getCurrentItem();//获取现在是第几个item

viewPager.getAdapter().getCount();//这种才是获取viewpager的size
viewPager.getChildCount();//表示当前可见页size,在0--3中变化

vp_viewpager.setOffscreenPageLimit(123); //viewpager的两边预加载数(>0,否则无效)★★★注意:多层嵌套一定要设置,否则有可能不显示内容★★★
viewPager.setPageMargin(35);//设置page间隔(Banner效果)
viewPager.setPageMarginDrawable();//设置drawableId | Drawable, 没试过
/**
 * 设置页面切换动画
 * @param reverseDrawingOrder 控制View在绘制子view时的顺序,true child倒叙,false child顺序
 * @param ViewPager.PageTransformer transformer,一个接口,回调中实际动画编码
 * @param pageLayerType https://www.jianshu.com/p/d4a9326543fa
 *                      默认开启硬件加速:ViewCompat.LAYER_TYPE_HARDWARE, 有时候会有问题
 */
viewPager.setPageTransformer(true, new xxxTransformer(xxx));

//========注意:如果ViewPager有Indicator页签,添加页面改变的监听是写indicator.addOnPageChangeListener========
indicator.addOnPageChangeListener(老毕说的,没有验证过)

//页面改变的监听
vp_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener {
	//触发onOntouchEvent调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		LogUtils.e(TAG, "当前页面划过屏幕百分比[0, 1)：" + positionOffset);
		LogUtils.e(TAG, "当前页面偏移像素[0, 屏幕宽度):" + positionOffsetPixels);
    }

	//选中某个页面调用
    @Override
    public void onPageSelected(int position) {
        setSlidingMenuEnable(position == 0 || position == mPagers.size()-1);//自定义方法
    }

	//页面滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int state) {
	    switch (state) {
            case ViewPager.SCROLL_STATE_IDLE://空闲, 完全停下来
                mHandler.removeMessages(0); //避免有多个消息
                mHandler.sendEmptyMessageDelayed(0,2000);//重写开始轮播
                break;
            case ViewPager.SCROLL_STATE_DRAGGING://牵引,拖拽, 滑动过程中
            case ViewPager.SCROLL_STATE_SETTLING://安置,沉陷,手放开的时候
            default:
                mHandler.removeMessages(0);
            }
    }
});

    //1.下面是viewpager的适配器
    class mAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return COUNT;
        }

        //这个方法的写法非常固定
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //返回ViewPager在某一个位置上的view对象,比较类似于getView
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);//要删掉,点进去会看见抛异常
            //1、创建View对象
            ImageView imageView = new ImageView(GuideActivity.this);
            //imageView.setImageResource(mImgs[position]);      //图片不缩放,会有留白
            imageView.setBackgroundResource(mImgs[position]);
            //2、将iv添加到container
            container.addView(imageView);
            //3、将iv返回
            return imageView;       //注意:这儿不能反悔container★★★★★★★★★★★★
        }


        //或者这种填充方式都可以
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //View view = View.inflate(BigPhotosActivity.this,R.layout.item_bigphoto_pic,null);//可以
            View view = LayoutInflater.from(BigPhotosActivity.this).inflate(R.layout//这种填充方式也可以
                    .item_bigphoto_pic, container,false);

            ImageView ivPic = (ImageView) view.findViewById(R.id.iv_pic);
            //1、创建View对象
            //imageView.setImageResource(mImgs[position]);      //图片不缩放,会有留白
            loadImage(BigPhotosActivity.this, pics.get(position).imgUrl, ivPic, null, null);
            //2、将iv添加到container
            container.addView(view);
            //3、将iv返回
            return view;
        }


        //这个方法的写法非常固定
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);//要删掉,点进去会看见抛异常
            container.removeView((View) object);
        }
		
		@Override
        public CharSequence getPageTitle(int position) {//可重写这个方法,返回标题
        	return super.getPageTitle(position);
    	}
		
		@Override
        public float getPageWidth(int position) {//获取pager宽度?
            return super.getPageWidth(position);
        }
    }


//==========================================================轮播有2种实现方式,如下============
1.添加触摸监听	2.添加页面改变的监听(这个方法不起作用,不知道为什么原因)

//===========================================================1.给ViewPager添加触摸监听==============
    private Handler mHandler;               //图片轮播

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_pager);

        mHandler = new Handler() {				//先在onCreate中初始化Handler
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int currentItem = vp_viewpager.getCurrentItem();
                int next = currentItem + 1;
                if (next == smallImgsList.size()) {
                    next = 0;
                }
                vp_viewpager.setCurrentItem(next);

                sendEmptyMessageDelayed(0, 2000);
            }
        };
    

        //设置触摸监听
        vp_viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:	//当按下的时候//注意,如果viewpager内容有点击事件,那么不走这个down方法.如果要写触摸和点击事件,建议在up事件中自己写时间判断
                        mHandler.removeMessages(0);	//停止轮播
			mHandler.removeCallbacksAndMessages(null);//这样写也行
			Toast.makeText(MainActivity.this, "未来记得添加双击事件", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:		//继续轮播
                    case MotionEvent.ACTION_CANCEL:
                        mHandler.removeMessages(0);	//先移除消息
                        mHandler.sendEmptyMessageDelayed(0, 2000);
                        break;	//返回true表示全权消费掉此事件,会导致viewpager无法响应触摸; 所以此处要返回false
                }
                return false;
            }
        });

	//解析网络数据
	private void parseJson(String response) {
		Gson gson = new Gson();
		HomeAdInfo homeAdInfo = gson.fromJson(response, HomeAdInfo.class);
		home_images = homeAdInfo.home_images;

		viewPager.setAdapter(new MyPagerAdapter());
		indicator.setViewPager(viewPager);

		//						3.首次进入轮播
		mHandler.removeMessages(0);
		mHandler.sendEmptyMessageDelayed(0, 2000);
	}

//==============================================================================================
public boolean dispatchTouchEvent(MotionEvent ev){
	int action = ev.getAction();
	if(action == MotionEvent.ACTION_DOWN){
		//请求父控件,祖宗控件不拦截事件
		所谓请求父控件不饿拦截事件,意思:请求父控件别拦截接下来的move事件
		//对于当前的事件,子控件是无法控制父控件是否拦截
		getParent().requestDisallowInterceptTouchEvent(true);
	}
}
