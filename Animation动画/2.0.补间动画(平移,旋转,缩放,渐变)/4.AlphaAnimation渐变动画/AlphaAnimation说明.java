渐变动画
1.构造方法
AlphaAnimation(Context context, AttributeSet attrs);
AlphaAnimation(float fromAlpha, float toAlpha);

2.普通方法
boolean willChangeTransformationMatrix();
boolean willChangeBounds();

3.示例写法:
AlphaAnimation anim = new AlphaAnimation(0, 1);
anim.setDuration(500);
anim.setFillAfter(true);				//动画结束后保持原位
anim.setStartOffset(200);				//延时多长时间之后再启动动画
anim.setRepeatMode(Animation.REVERSE);	//播放完成后,从结束开始向前重新运行
anim.setRepeatCount(Animation.INFINITE);//无限播放
btn.startAnimation(anim);				//注意:不要写成view.setAnimation(),否则不会播放