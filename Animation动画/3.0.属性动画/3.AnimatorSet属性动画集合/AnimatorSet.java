AnimatorSet extends Animator implements AnimationHandler.AnimationFrameCallback

1.构造方法
public AnimatorSet();

2.普通方法
public void playTogether(Animator... items);//几个属性动画一起播放
public void playTogether(Collection<Animator> items)
public void playSequentially(Animator... items)
public void playSequentially(List<Animator> items)
public ArrayList<Animator> getChildAnimations()
public void setTarget(Object target)
public void setInterpolator(TimeInterpolator interpolator)
public TimeInterpolator getInterpolator()
public Builder play(Animator anim)		//返回 Builder
public void cancel()
public void end()
public boolean isRunning()
public boolean isStarted()
public long getStartDelay()
public void setStartDelay(long startDelay)
public long getDuration()
public AnimatorSet setDuration(long duration)
public void setupStartValues()
public void setupEndValues()
public void pause()
public void resume()
public void start()
public void setCurrentPlayTime(long playTime)
public long getCurrentPlayTime()
public AnimatorSet clone()
public void reverse()
public long getTotalDuration()



AnimatorSet.Builder:
public Builder with(Animator anim)	//将现有动画和传入的"动画"同时执行
public Builder before(Animator anim)//将现有动画插入到传入的动画"之前"执行
public Builder after(Animator anim)	//将现有动画插入到传入的动画"之后"执行
public Builder after(long delay)	//将现有动画"延迟"指定毫秒后执行












