1.���Զ�����3.0ϵͳ����,�ı�View��ʵ������,���Ҫ��2.x�汾ҲҪ֧�����Զ���,����Ҫ������ݰ�:
nineoldandroids-2.4.0.jar

2.�̳й�ϵ
ObjectAnimator extends ValueAnimator extends Animator;

3.����:
ValueAnimator:Ҫ��ȡ�м�ֵ�����ü���,ֻ�ǲ���һϵ�е��м�ֵ,һ�㲻�����
ObjectAnimator:�ڲ����м�ֵ��ʱ��,���᲻�ϵĵ���target.setXxx(�м�ֵ)

4.android.animation.Animator˵��:
//��̬����
public static final long DURATION_INFINITE = -1;//����ʱ������

//��ͨ����
void start();
void cancel();
void end();
void pause();
void resume();
boolean isPaused();
long getTotalDuration();
TimeInterpolator getInterpolator();
boolean isStarted();
void addListener(AnimatorListener listener);	//��ʼ,ֹͣ,ȡ��,���¿�ʼ
void removeListener(AnimatorListener listener);
ArrayList<AnimatorListener> getListeners();
void addPauseListener(AnimatorPauseListener listener);
void removePauseListener(AnimatorPauseListener listener);
void removeAllListeners();
Animator clone();
void setupStartValues();
void setupEndValues();
void setTarget(@Nullable Object target);

5.ObjectAnimator �� ValueAnimator �ֱ���Ӧjava�ļ�