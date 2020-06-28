getWidth();//获取控件宽度
getHeight();//获取控件高度
getPaddingLeft();//获取控件的PaddingLeft
getPaddingRight();//获取控件的PaddingRight
getPaddingTop();//获取控件的PaddingTop
getPaddingBottom();//获取控件的PaddingBottom
getScrollX();//获取当前滑动结束后x离初始化时x=0位置的距离(左滑负右滑正)(示例 ViewGroup 中)
view.setTag(Object o);
view.setTag(int id, Object o);
Object tag = view.getTag();//获取view绑定的对象
Object tag = view.getTag(R.id.xxx);//获取view绑定的id对象
getParent().requestDisallowInterceptTouchEvent(true);//请求父类不拦截触摸事件

inflate(context, R.layout.tab_view, this);//view方法

getChildCount();//ViewGroup中获取控件个数
public void addView(View child, int index);//ViewGroup 方法
protected int getChildDrawingOrder(int childCount, int i);//覆写, 控制子view绘制顺序, ViewGroup 方法

public final void measure(int widthMeasureSpec, int heightMeasureSpec);//测量,view.measure(0, 0)传2个0代表将测量的工作交给系统来完成,开发者不参与其他的限制意见
view.getMeasuredHeight();//获取测量后的高度
view.setPadding(0, -headerHeight, 0, 0);//隐藏头布局,设置Padding隐藏

if (Looper.myLooper() == Looper.getMainLooper()) {
    invalidate();//重绘控件, 让onDraw重新执行一次
} else postInvalidate();//子线程中调用重绘

public void scrollBy(int x, int y);//传x,y偏移量
public void scrollTo(int x, int y);//传x,y坐标值,但滑动时没有动画.android.widget.Scroller有滑动动画


1.调用多次测量控件的宽高,可以修改当前控件的宽和高的信息.ViewGroup中还在有多个控件时,必须重写这个方法,否则不显示
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	//setMeasuredDimension(100, 100);//重写宽高信息, 指定为100x100(整个控件最大宽高)
	//根据图片大小来确定控件大小
    setMeasuredDimension(mSlideBg.getWidth(), mSlideBg.getHeight());
	//ViewGroup中,遍历所有子控件,测量每个控件
    for (int i = 0; i < getChildCount(); i++) {
        //每个子控件的所有孩子都可以通过此方法进行测量
        getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
    }
	//不是纯粹的宽高信息, 而是同时带有宽高模式:1073742304-->1000000000000000000000111100000
        System.out.println("widthMeasureSpec:" + widthMeasureSpec);//1073742304
        System.out.println("heightMeasureSpec:" + heightMeasureSpec);//1073742511

        //宽高模式:
        //MeasureSpec.EXACTLY; 确定模式, 类似布局文件中把宽高写死, match_parent
        //MeasureSpec.AT_MOST: 至多模式, 类似wrap_content
        //MeasureSpec.UNSPECIFIED; 未指定模式, 宽高没有确定, ListView, ScrollView
        int width = MeasureSpec.getSize(widthMeasureSpec);//宽度
        int mode = MeasureSpec.getMode(widthMeasureSpec);//模式
		//重新生成measureSpec(RatioLayout中修改确定了高度之后)
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);

        System.out.println("width:" + width);//480
        System.out.println("mode:" + mode);//1073741824-->1000000000000000000000000000000
}


2.设置当前控件的位置(extends ViewGroup的时候会必须重写本方法)
/**
 * View的左上角 & 右下角, 可间接获得View宽高
 * left: View的左上角x坐标
 * top: View的左上角y坐标
 * right: View的右下角x坐标
 * bottom: View的右下角y坐标
 */
protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
	//遍历所有子控件,设置位置, 一字排开
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());//l,t,r,b
        }
}


3.用户绘制控件的内容
protected void onDraw(Canvas canvas) {
    //在左上角绘制一个200x200的矩形
    //canvas.drawRect(0, 0, 200, 200, paint);
	canvas.drawBitmap(bitmapButton, mSlideLeft, 0, null);//Bitmap bitmap, float left, float top, @Nullable Paint paint
}

4.宽高发生变化
protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	super.onSizeChanged(w, h, oldw, oldh);
}

5.事件分发的方法:dispatchTouchEvent-->onInterceptTouchEvent-->onTouchEvent
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    return super.dispatchTouchEvent(ev);
}

//-----------------------------------------onInterceptTouchEvent-----------------------------------------------
@Override
public boolean onInterceptTouchEvent(MotionEvent ev);//ViewGroup方法,是否中断事件传递

//---------------------------------------------onTouchEvent---------------------------------------------------
//View,Activity..可重写触摸事件,三种返回值: true, false, super
//设置视图的 WindowManager 布局参数的 flags为FLAG_WATCH_OUTSIDE_TOUCH，这样点击事件发生在这个视图之外时，该视图就可以接收到一个 ACTION_OUTSIDE 事件。待测试
@Override
public boolean onTouchEvent(MotionEvent event) {
    event.getX();//1. 记录按下的x坐标点
	return super.onTouchEvent(event);//使用super可以让onTouch和onClick都响应到事件,也可写在view.setOnTouchListener中
}

/**
 * Activity的onResume的时候被调用,也就是act对应的window被添加的时候,且每个view只会被调用一次,
 * 父view的调用在前,不论view的visibility状态都会被调用,适合做些view特定的初始化操作
 */
@Override
protected void onAttachedToWindow() {
	super.onAttachedToWindow();
}

/**
 * Activity的onDestroy的时候被调用的,也就是act对应的window被删除的时候,且每个view只会被调用一次,
 * 父view的调用在后,也不论view的visibility状态都会被调用,适合做最后的清理操作
 */
@Override
protected void onDetachedFromWindow() {
	super.onDetachedFromWindow();
}

//------------------------------------------------computeScroll-----------------------------------------------------
private Scroller mScroller = new Scroller(getContext());//滑动器, 可以实现平滑滑动效果
//参1:起始x位置; 参2:起始y位置; 参3:x偏移; 参4:y偏移; 参5:滑动时间,会回调computeScroll();方法
mScroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx));//int startX, int startY, int dx, int dy, int duration
invalidate();//为了保证正常运行, 需要此处刷新
@Override
public void computeScroll() {
    if (mScroller.computeScrollOffset()) {//判断滑动是否结束
        int currX = mScroller.getCurrX();//获取当前滑动器应该滑动的位置
        System.out.println("currX:" + currX);
        scrollTo(currX, 0);//滑动到该位置
        invalidate();//为了保证正常运行, 需要此处刷新
    }
}

//-----------------------------------------------------------------------------------------------------
