1.ValueAnimator extends Animator,值动画.要获取中间值需设置监听,只是产生一系列的中间值

2.构造方法
public ValueAnimator();

3.静态变量
public static final int RESTART = 1;//动画播放完后,从头播放
public static final int REVERSE = 2;//动画播放完后,从尾往头播放回去
public static final int INFINITE = -1;//播放次数,无限

4.静态方法
ValueAnimator ofInt(int... values);//0,300.在0和300之间在设置时间段内产生一系列中间值
ValueAnimator ofArgb(int... values);
ValueAnimator ofFloat(float... values);
ValueAnimator ofPropertyValuesHolder(PropertyValuesHolder... values);
ValueAnimator ofObject(TypeEvaluator evaluator, Object... values);
long getFrameDelay();
void setFrameDelay(long frameDelay);

5.一般方法
void setIntValues(int... values);
void setFloatValues(float... values);
void setObjectValues(Object... values);
void setValues(PropertyValuesHolder... values);
PropertyValuesHolder[] getValues();
ValueAnimator setDuration(long duration);
long getDuration();
long getTotalDuration();
void setCurrentPlayTime(long playTime);
void setCurrentFraction(float fraction);
long getCurrentPlayTime();
long getStartDelay();
void setStartDelay(long startDelay);
Object getAnimatedValue();					//获取已经产生的中间值,强转成int,float...
Object getAnimatedValue(String propertyName);
void setRepeatCount(int value);				//播放次数,示例:ValueAnimator.INFINITE 无限
int getRepeatCount();
void setRepeatMode(@RepeatMode int value);	//重复播放模式,示例:ValueAnimator.REVERSE
int getRepeatMode();
void addUpdateListener(AnimatorUpdateListener listener);//设置产生中间值的监听
void removeAllUpdateListeners();
void removeUpdateListener(AnimatorUpdateListener listener);
void setInterpolator(TimeInterpolator value);//插值器
TimeInterpolator getInterpolator();
void setEvaluator(TypeEvaluator value);		//估值算法,根据插值器算出的值,返回相应的值
void reverse();
float getAnimatedFraction();

6.Override
public static final long DURATION_INFINITE = -1;//无限播放
void start(boolean playBackwards);
void start();
public void cancel()
void end();
void resume();
void pause();
public boolean isPaused()
public long getTotalDuration()
boolean isRunning();
boolean isStarted();
ValueAnimator clone();
public void addListener(AnimatorListener listener)
public void removeListener(AnimatorListener listener)
public ArrayList<AnimatorListener> getListeners()
public void addPauseListener(AnimatorPauseListener listener)
public void removePauseListener(AnimatorPauseListener listener)
public void removeAllListeners()


6.示例写法
