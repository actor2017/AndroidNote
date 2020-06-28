平移动画
1.构造方法
TranslateAnimation(Context context, AttributeSet attrs);
TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta);//fromXDelta平移点的X值,距离左侧的偏移量.不是0-1
//fromXType:相对谁平移		fromXValue:x轴比例,0-1
TranslateAnimation(int fromXType, float fromXValue, int toXType, float toXValue, int fromYType, float fromYValue, int toYType, float toYValue);

2.普通方法
void initialize(int width, int height, int parentWidth, int parentHeight);

3.示例写法:
TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1,
		Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
anim.setDuration(500);
anim.setInterpolator(new AccelerateInterpolator());//插值器
anim.setFillAfter(true);				//动画结束后保持原位
anim.setStartOffset(200);				//延时多长时间之后再启动动画
anim.setRepeatMode(Animation.REVERSE);	//播放完成后,从结束开始向前重新运行
anim.setRepeatCount(Animation.INFINITE);//无限播放
btn.startAnimation(anim);				//注意:不要写成view.setAnimation(),否则不会播放