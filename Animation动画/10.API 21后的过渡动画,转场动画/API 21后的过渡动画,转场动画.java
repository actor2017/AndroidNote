2.2��API 21 ֮��Activity���ɶ���ʹ��
��API 21֮��google���Ƴ���һ�ֱ�֮ǰЧ�������޵Ĺ��ɶ����� ͨ��ActivityOptions + Transition��ʵ��Activity���ɶ�����
�ڽ�ʹ��ActivityOptions + Transition��ʵ��Activity���ɶ���֮ǰ�����˿���ActivityOptions���漸����������ɶ��˼��

/**
 * ��overridePendingTransition����,������תʱ��Ľ��붯�����˳�����
 */
public static ActivityOptions makeCustomAnimation(Context context, int enterResId, int exitResId);

/**
 * ͨ���Ŵ�һ��View���ɵ��µ�Activity
 * ��һ���򵥵����������source=view,startX=view.getWidth(),startY=view.getHeight(),startWidth=0,startHeight=0
 * �����µ�Activity��view�����Ĵ��޵��������Ŵ�Ĺ���
 */
public static ActivityOptions makeScaleUpAnimation(View source, int startX, int startY, int width, int height);

/**
 * ͨ���Ŵ�һ��Bitmap���ɵ��µ�Activity
 */
public static ActivityOptions makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY);

/**
 * ��������������������Activity�е�ĳЩviewЭͬȥ��ɹ��ɶ���Ч�����������������ܸ��õĿ���Ч��
 * ������,�� android:launchMode="singleTask",������singleTaskҳ��ʱ,Ԫ�ع����νӲ�����
 * ������,�� android:launchMode="singleInstance" ������,Ӧ����2������ջ֮��Activity���ܹ���Ԫ��?��ٶ���ô���?
 */
public static ActivityOptions makeSceneTransitionAnimation(Activity activity, View sharedElement, String sharedElementName);

/**
 * ����������ͬ���ǶԶ��Viewͬʱ������
 */
public static ActivityOptions makeSceneTransitionAnimation(Activity activity, android.util.Pair<View, String>... sharedElements);


2.2.2��������ɶ���,ϵͳ�������ṩ������Transition���ɶ�������������ֱ��ʹ�á�
    ϵͳĬ�϶���		����
��ը,�ֽ�(explode)	�ӳ�������������Ƴ���ͼ 
����(slide)			�ӳ�����Ե������Ƴ���ͼ 
���뵭��(fade)		ͨ������͸�����ڳ�����������Ƴ���ͼ

���������ַ�ʽ��������ɶ�������Դ�ļ��ķ�ʽ������ķ�ʽ���������Դ�ļ�������Ӧ����
�����Ӧ				xml��Ӧ				����
addTarget(int targetId)	android:targetId	ָ��Ŀ��View����Ŀ��View���붯��
excludeTarget()			android:excludeId	�Ƿ����ָ��View(�Ƿ���붯��)
excludeChildren()		��					�Ƿ����ָ��ViewGroup����View(�Ƿ���붯��)


2.2.3�����ù��ɶ���
Transition���ɶ��������ÿ�����style�ļ���ͳһ����Ҳ�����ڴ���������(���������õ����ȼ���style�����ļ����ȼ���)��
����ָ��							style����ָ��					����
getWindow().setEnterTransition()	android:windowEnterTransition	A����B��B�е�View���볡����transition(��������λ��B)
getWindow().setExitTransition()		android:windowExitTransition	A����B��A�е�View�˳�������transition(��������λ��A)
getWindow().setReturnTransition()	android:windowReturnTransition	B����A��B�е�View�˳�������transition(��������λ��B)
getWindow().setReenterTransition()	android:windowReenterTransition	B����A��A�е�View���½��볡����transition(��������λ��A)


<!-- ��ʹ��5.0��Activity���ɶ�������Ҫ��������������ڴ�����������:getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); -->
<item name="android:windowContentTransitions">true</item>
<!--���������Ҫ��5.0�ϲź���,����ת�����̶������������Ȼ,ָ��������˳��Ķ��������ص� -->
<item name="android:windowAllowEnterTransitionOverlap">true</item>
<item name="android:windowAllowReturnTransitionOverlap">true</item>

<!-- ָ��������˳�transitions,ע��ֻ��makeSceneTransitionAnimation -->
<!--A����B��B�е�View���볡����transition(��������λ��B)-->
<item name="android:windowEnterTransition">@transition/fade</item>
<!--A����B��A�е�View�˳�������transition(��������λ��A)-->
<item name="android:windowExitTransition">@transition/fade</item>
<!--B����A��A�е�View���½��볡����transition(��������λ��A)-->
<item name="android:windowReenterTransition">@transition/fade</item>
<!--B����A��B�е�View�˳�������transition(��������λ��B)-->
<item name="android:windowReturnTransition">@transition/fade</item>












