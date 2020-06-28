补间动画分四种:
只改变view的绘制流程，不改变View的真实属性(有按钮的地方还是可以点击)

android.view.animation.Animation;//这个类是4中属性动画的父类
1.常量
public static final int INFINITE = -1;				//播放次数,<0都是无穷,默认=0
public static final int RESTART = 1;				//动画播放完成后,从头开始重新运行
public static final int REVERSE = 2;				//动画播放完成后,从结束开始向前重新运行
public static final int START_ON_FIRST_FRAME = -1;
public static final int ABSOLUTE = 0;
public static final int RELATIVE_TO_SELF = 1;		//相对自己
public static final int RELATIVE_TO_PARENT = 2;		//相对父View
public static final int ZORDER_NORMAL = 0;
public static final int ZORDER_TOP = 1;
public static final int ZORDER_BOTTOM = -1;

2.普通方法
void reset();
void cancel();
boolean isInitialized();
void initialize(int width, int height, int parentWidth, int parentHeight);
void setInterpolator(Interpolator i);//插值器
void setInterpolator(Context context, @AnimRes @InterpolatorRes int resID);
void setStartOffset(long startOffset);				//延时多长时间之后再启动动画
void setDuration(long durationMillis);				//动画播放时长
void restrictDuration(long durationMillis);
void scaleCurrentDuration(float scale);
void setStartTime(long startTimeMillis);
void start();
void startNow();
void setRepeatMode(int repeatMode);					//动画播放完成后,RESTART从头开始重新运行,REVERSE从结束开始向前重新运行
void setRepeatCount(int repeatCount);				//动画播放次数,默认0,INFINITE无穷(<0也是无穷)
boolean isFillEnabled();
void setFillEnabled(boolean fillEnabled);
void setFillBefore(boolean fillBefore);
void setFillAfter(boolean fillAfter);				//动画结束后保持原位
void setZAdjustment(int zAdjustment);				//表示被animated的内容在运行时在z轴上的位置，默认为normal。normal保持内容当前的z轴顺序top运行时在最顶层显示bottom运行时在最底层显示
void setBackgroundColor(@ColorInt int bg);
void setDetachWallpaper(boolean detachWallpaper);	//是否在壁纸上运行??
Interpolator getInterpolator();
long getStartTime();
long getDuration();
long getStartOffset();
int getRepeatMode();
int getRepeatCount();
boolean getFillBefore();
boolean getFillAfter();
int getZAdjustment();
int getBackgroundColor();
boolean getDetachWallpaper();
boolean willChangeTransformationMatrix();
boolean willChangeBounds();
void setAnimationListener(AnimationListener listener);
void ensureInterpolator();
long computeDurationHint();
boolean getTransformation(long currentTime, Transformation outTransformation);
boolean getTransformation(long currentTime, Transformation outTransformation, float scale);
boolean hasStarted();
boolean hasEnded();

3.使用
view.startAnimation(anim);	//开始动画
view.clearAnimation();		//1.设置Visiable等效果前先调用,否则无效.2.会触发AnimationListener中的onAnimationEnd方法
