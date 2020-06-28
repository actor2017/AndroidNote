���䶯��������:
ֻ�ı�view�Ļ������̣����ı�View����ʵ����(�а�ť�ĵط����ǿ��Ե��)

android.view.animation.Animation;//�������4�����Զ����ĸ���
1.����
public static final int INFINITE = -1;				//���Ŵ���,<0��������,Ĭ��=0
public static final int RESTART = 1;				//����������ɺ�,��ͷ��ʼ��������
public static final int REVERSE = 2;				//����������ɺ�,�ӽ�����ʼ��ǰ��������
public static final int START_ON_FIRST_FRAME = -1;
public static final int ABSOLUTE = 0;
public static final int RELATIVE_TO_SELF = 1;		//����Լ�
public static final int RELATIVE_TO_PARENT = 2;		//��Ը�View
public static final int ZORDER_NORMAL = 0;
public static final int ZORDER_TOP = 1;
public static final int ZORDER_BOTTOM = -1;

2.��ͨ����
void reset();
void cancel();
boolean isInitialized();
void initialize(int width, int height, int parentWidth, int parentHeight);
void setInterpolator(Interpolator i);//��ֵ��
void setInterpolator(Context context, @AnimRes @InterpolatorRes int resID);
void setStartOffset(long startOffset);				//��ʱ�೤ʱ��֮������������
void setDuration(long durationMillis);				//��������ʱ��
void restrictDuration(long durationMillis);
void scaleCurrentDuration(float scale);
void setStartTime(long startTimeMillis);
void start();
void startNow();
void setRepeatMode(int repeatMode);					//����������ɺ�,RESTART��ͷ��ʼ��������,REVERSE�ӽ�����ʼ��ǰ��������
void setRepeatCount(int repeatCount);				//�������Ŵ���,Ĭ��0,INFINITE����(<0Ҳ������)
boolean isFillEnabled();
void setFillEnabled(boolean fillEnabled);
void setFillBefore(boolean fillBefore);
void setFillAfter(boolean fillAfter);				//���������󱣳�ԭλ
void setZAdjustment(int zAdjustment);				//��ʾ��animated������������ʱ��z���ϵ�λ�ã�Ĭ��Ϊnormal��normal�������ݵ�ǰ��z��˳��top����ʱ�������ʾbottom����ʱ����ײ���ʾ
void setBackgroundColor(@ColorInt int bg);
void setDetachWallpaper(boolean detachWallpaper);	//�Ƿ��ڱ�ֽ������??
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

3.ʹ��
view.startAnimation(anim);	//��ʼ����
view.clearAnimation();		//1.����Visiable��Ч��ǰ�ȵ���,������Ч.2.�ᴥ��AnimationListener�е�onAnimationEnd����
