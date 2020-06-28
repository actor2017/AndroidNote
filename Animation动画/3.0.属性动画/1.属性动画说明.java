1.属性动画在3.0系统出现,改变View真实的属性,如果要在2.x版本也要支持属性动画,则需要加入兼容包:
nineoldandroids-2.4.0.jar

2.继承关系
ObjectAnimator extends ValueAnimator extends Animator;

3.区别:
ValueAnimator:要获取中间值需设置监听,只是产生一系列的中间值,一般不用这个
ObjectAnimator:在产生中间值的时候,它会不断的调用target.setXxx(中间值)

4.android.animation.Animator说明:
//静态变量
public static final long DURATION_INFINITE = -1;//动画时间无限

//普通方法
void start();
void cancel();
void end();
void pause();
void resume();
boolean isPaused();
long getTotalDuration();
TimeInterpolator getInterpolator();
boolean isStarted();
void addListener(AnimatorListener listener);	//开始,停止,取消,重新开始
void removeListener(AnimatorListener listener);
ArrayList<AnimatorListener> getListeners();
void addPauseListener(AnimatorPauseListener listener);
void removePauseListener(AnimatorPauseListener listener);
void removeAllListeners();
Animator clone();
void setupStartValues();
void setupEndValues();
void setTarget(@Nullable Object target);

5.ObjectAnimator 和 ValueAnimator 分别看相应java文件