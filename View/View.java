见utils文件夹里ViewHelper.java封装,

view.isPressed();			//view当前是否'按下'状态

ViewPropertyAnimator animate = view.animate();
//animate.alpha(1).setDuration(500L).start();//渐变动画,alpha[0,1]
.setDuration(long duration);//动画时长, 默认300ms
animate.setListener();
animate.setUpdateListener();//如果传null,移除监听
.start();					//开始


    View中方法                 功能       对应的 ViewPropertyAnimator 中的方法
view.setAlpha(float alpha);	//透明度		alpha(),alphaBy();

 view.post(new Runnable() {//https://github.com/Moosphan/Android-Daily-Interview/issues/126
            @Override
            public void run() {
                 Logger.e("view 的高度为：" + view.getHeight());
            }
        });
view.postDelayed(Runnable action, long delayMillis);//延时执行run()
view.performClick();//可手动触发view的点击事件

view.setPivotX(float pivotX);//设置旋转中心x坐标
view.setPivotY(float pivotY);//设置旋转中心Y坐标
view.setRotation(90);		//旋转			rotation(),rotationBy();
view.setRotationX(90);		//旋转			rotationX(),rotationXBy();
view.setRotationY(90);		//旋转			rotationY(),rotationYBy();

view.setScaleX(0.5f);		//横向缩放		scaleX(),scaleXBy();
view.setScaleY(0.5f);

view.setTranslationX();		//平移(可做indicator滑动动画效果)			translationX(),translationXBy();
view.setTranslationY();
view.setTranslationZ();
view.setX();				//设置x轴绝对值	x(),xBy();
view.setY();
view.setZ();

view.setOnTouchListener(new OnTouchListener() {//触摸事件

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		v.onTouchEvent(event);//可响应触摸&点击事件,不用在ACTION_UP中判断如果是点击事件v.performClick();
//        if (event.getAction() == MotionEvent.ACTION_UP) v.performClick();
		return true;//如果return false, 只有一次回调
	}
});


Bitmap bitmap = view.getDrawingCache();//获取当前view缓存截图

//Activity/View方法,一般在Activity的onResume()后调用?
@Override
protected void onAttachedToWindow() {
	super.onAttachedToWindow();
}

//Activity/View方法,一般在Activity的onDestroy()后调用?
@SuppressWarnings("deprecation")
@Override
protected void onDetachedFromWindow() {
	super.onDetachedFromWindow();
}


//需要注意的是，通过此方法来获取ScrollView，就必须保证，view的父控件层级中必须得要有一个ScrollView
private ScrollView getScrollView(View view) {
    //获取父控件
    ViewParent parent = view.getParent();
    if(parent instanceof  ScrollView) {
        return (ScrollView) parent;
    } else {
        return getScrollView((View) parent);
    }
}

//View方法,见MaterialRatingBar框架sample
rbRatingbar.setAnimation(new Animation() {
    
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(rbRatingbar.getNumStars() * 4 * getResources().getInteger(android.R.integer.config_longAnimTime/**500*/));
        setInterpolator(new LinearInterpolator());
        setRepeatCount(Animation.INFINITE);
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int progress = Math.round(interpolatedTime * rbRatingbar.getMax());
        rbRatingbar.setProgress(progress);
    }
    @Override
    public boolean willChangeTransformationMatrix() {
        return false;
    }
    @Override
    public boolean willChangeBounds() {
        return false;
    }
});

//以下3个阴影, View属性
android:fadingEdgeLength="10dp"//阴影高度, 默认12px
android:requiresFadingEdge="vertical"//阴影方向
android:fadingEdge="vertical"//阴影方向(从api14开始, 过时)

mWebView.getHorizontalFadingEdgeLength();
mWebView.getVerticalFadingEdgeLength();
mWebView.isHorizontalFadingEdgeEnabled();
mWebView.isScrollbarFadingEnabled();
mWebView.isVerticalFadingEdgeEnabled();
mWebView.setFadingEdgeLength(100);
mWebView.setHorizontalFadingEdgeEnabled(true);
mWebView.setVerticalFadingEdgeEnabled(true);

https://www.jianshu.com/p/78bf0f2e44d1
http://www.voidcn.com/article/p-thoeaxja-bpp.html
顶部不需要fadingEdge效果，直接重写getTopFadingEdgeStrength()，返回0就可以了

