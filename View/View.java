��utils�ļ�����ViewHelper.java��װ,

view.isPressed();			//view��ǰ�Ƿ�'����'״̬

ViewPropertyAnimator animate = view.animate();
//animate.alpha(1).setDuration(500L).start();//���䶯��,alpha[0,1]
.setDuration(long duration);//����ʱ��, Ĭ��300ms
animate.setListener();
animate.setUpdateListener();//�����null,�Ƴ�����
.start();					//��ʼ


    View�з���                 ����       ��Ӧ�� ViewPropertyAnimator �еķ���
view.setAlpha(float alpha);	//͸����		alpha(),alphaBy();

 view.post(new Runnable() {//https://github.com/Moosphan/Android-Daily-Interview/issues/126
            @Override
            public void run() {
                 Logger.e("view �ĸ߶�Ϊ��" + view.getHeight());
            }
        });
view.postDelayed(Runnable action, long delayMillis);//��ʱִ��run()
view.performClick();//���ֶ�����view�ĵ���¼�

view.setPivotX(float pivotX);//������ת����x����
view.setPivotY(float pivotY);//������ת����Y����
view.setRotation(90);		//��ת			rotation(),rotationBy();
view.setRotationX(90);		//��ת			rotationX(),rotationXBy();
view.setRotationY(90);		//��ת			rotationY(),rotationYBy();

view.setScaleX(0.5f);		//��������		scaleX(),scaleXBy();
view.setScaleY(0.5f);

view.setTranslationX();		//ƽ��(����indicator��������Ч��)			translationX(),translationXBy();
view.setTranslationY();
view.setTranslationZ();
view.setX();				//����x�����ֵ	x(),xBy();
view.setY();
view.setZ();

view.setOnTouchListener(new OnTouchListener() {//�����¼�

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		v.onTouchEvent(event);//����Ӧ����&����¼�,������ACTION_UP���ж�����ǵ���¼�v.performClick();
//        if (event.getAction() == MotionEvent.ACTION_UP) v.performClick();
		return true;//���return false, ֻ��һ�λص�
	}
});


Bitmap bitmap = view.getDrawingCache();//��ȡ��ǰview�����ͼ

//Activity/View����,һ����Activity��onResume()�����?
@Override
protected void onAttachedToWindow() {
	super.onAttachedToWindow();
}

//Activity/View����,һ����Activity��onDestroy()�����?
@SuppressWarnings("deprecation")
@Override
protected void onDetachedFromWindow() {
	super.onDetachedFromWindow();
}


//��Ҫע����ǣ�ͨ���˷�������ȡScrollView���ͱ��뱣֤��view�ĸ��ؼ��㼶�б����Ҫ��һ��ScrollView
private ScrollView getScrollView(View view) {
    //��ȡ���ؼ�
    ViewParent parent = view.getParent();
    if(parent instanceof  ScrollView) {
        return (ScrollView) parent;
    } else {
        return getScrollView((View) parent);
    }
}

//View����,��MaterialRatingBar���sample
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

//����3����Ӱ, View����
android:fadingEdgeLength="10dp"//��Ӱ�߶�, Ĭ��12px
android:requiresFadingEdge="vertical"//��Ӱ����
android:fadingEdge="vertical"//��Ӱ����(��api14��ʼ, ��ʱ)

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
��������ҪfadingEdgeЧ����ֱ����дgetTopFadingEdgeStrength()������0�Ϳ�����

