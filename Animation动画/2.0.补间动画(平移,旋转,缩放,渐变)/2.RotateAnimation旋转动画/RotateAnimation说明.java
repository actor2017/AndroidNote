旋转动画
1.构造方法
RotateAnimation(Context context, AttributeSet attrs);
RotateAnimation(float fromDegrees, float toDegrees);//默认view的左上角圆点旋转
RotateAnimation(float fromDegrees, float toDegrees, float pivotX, float pivotY);//pivotX旋转点的X值,距离左侧的偏移量.不是0-1
//pivotXType:相对谁旋转		pivotXValue:x轴比例,0-1
RotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue);

2.普通方法
void initialize(int width, int height, int parentWidth, int parentHeight);

3.示例写法
RotateAnimation anim = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1);
anim.setDuration(500);
anim.setFillAfter(true);				//动画结束后保持原位
anim.setStartOffset(200);				//延时多长时间之后再启动动画
anim.setRepeatMode(Animation.REVERSE);	//播放完成后,从结束开始向前重新运行
anim.setRepeatCount(Animation.INFINITE);//无限播放
view.startAnimation(anim);				//注意:不要写成view.setAnimation(),否则不会播放