https://github.com/JakeWharton/NineOldAndroids

compile 'com.nineoldandroids:library:2.4.0'

<dependency>
  <groupId>com.nineoldandroids</groupId>
  <artifactId>library</artifactId>
  <version>2.4.0</version>
</dependency>

ȱ����ǣ���������̫���ˣ��Լ�ȥ���Ļ�Ч��̫����

NineOldAndroids��һ�����¼��ݵĶ����⣬��Ҫ��ʹ����API 11��ϵͳҲ�ܹ�ʹ��View�����Զ�����
�����������÷���ٷ��Ķ�һ����ֻ�ǰ�����һ����
ʹ�øÿ⣬��Ϳ�����API �汾�ܵ͵������Ҳ�ܹ�ʹ�ø������Զ����������Ӧ�ø����ж��С�ƽ����
����ԭ����
����һ����˵������ʹ��NineOldAndroids�����Զ���ʱ�Ĵ������������������:
����ValueAnimator colorAnim = ObjectAnimator.ofFloat(myView, "scaleX", 0.3f);
    colorAnim.setDuration(1000);
	colorAnim.start();
������������ὫmyView ��View�������ͣ��Ŀ����1����֮�����ŵ�ԭʼ��ȵ�30%��

    ��������������˵��һ��NineOldAndroids�Ļ���ԭ��
���������ǹٷ���֧�֣�����nideoldandroids�ĵͰ汾֧�֣�����ʹ�õļ���ԭ����һ���ġ�NineOldAndroids�Ļ���ԭ������ڹ������Զ���ʱ�����û���ϵͳ�汾��ѡ��ͬ��ʵ�ַ�ʽ�����Ҷ��ڵͰ汾��APIʹ���Լ���ʵ���������Զ�����
����û���ϵͳAPI���ڵ���11����Android 3.0�������ϣ���ô�ͻ��ڶ�����duration�ڼ���������ͨ�����������ø����Ե�set�������޸�����ֵ��
��������� scaleX���ԣ��ö���������ڲ����� scaleX ��set��������ʽ����Ϊset + ����ĸ��д������ + ����������setS caleX (float scale)��������һ��ʱ�����������޸�myView������ֵ�ʹﵽ�˶�����Ч����

1.ObjectAnimator(ϵͳҲ�������)

2.ValueAnimator(ϵͳҲ�������)

3.ViewHelper(ϵͳû�������)
                  /**���淽��������:void*/
//                ViewHelper.setAlpha(View view, float alpha);//����͸����[0, 1]

                /**����2������ֻ�����õ�,��Ҫ���rotation,scale�Ȳ���Ч��(�໬�˵� ������QQʵ�ֶ���Ч��)*/
//                ViewHelper.setPivotX(View view, float pivotX);//view��pivotX��X��Ϊ������ת/����
//                ViewHelper.setPivotY(View view, float pivotY);//view��pivotY��Y��Ϊ������ת/����

//                ViewHelper.setRotation(View view, float rotation);//��view����,��Z����תrotation��
//                ViewHelper.setRotationX(View view, float rotationX);//��view�߶�/2��X��Ϊ����,��תrotationX��
//                ViewHelper.setRotationY(View view, float rotationY);//��view���/2��Y��Ϊ����,��תrotationY��
//                ViewHelper.setScaleX(View view, float scaleX);//��view���/2��Y��Ϊ����,����scaleX��
//                ViewHelper.setScaleY(View view, float scaleY);//��view�߶�/2��X��Ϊ����,����scaleY��
//                ViewHelper.setScrollX(View view, int scrollX);//view��X�������scrollX����(��������)
//                ViewHelper.setScrollY(View view, int scrollY);//view��Y�������scrollY����
//                ViewHelper.setTranslationX(View view, float translationX);//view��X��ƽ��xx����(view.setTranslationX(value);)
//                ViewHelper.setTranslationY(View view, float translationY);//view��Y��ƽ��xx����
//                ViewHelper.setX(View view, float x);//����view�I��X������
//                ViewHelper.setY(View view, float y);//����view�I��Y������

4.ViewPropertyAnimator(ϵͳҲ�������)
                /**û��ע�͵Ķ�����:ViewPropertyAnimator*/
//                ViewPropertyAnimator.animate(view).alpha(float value);//����͸����[0, 1]
//                ViewPropertyAnimator.animate(view).alphaBy(float value);//������͸������ƫ��value
//                ViewPropertyAnimator.animate(view).cancel();//void ȡ������
//                ViewPropertyAnimator.animate(view).getDuration();//long ����ʱ��
//                ViewPropertyAnimator.animate(view).getStartDelay();//long ��ȡ����Ĭ��������ʱ
//                ViewPropertyAnimator.animate(view).rotation(float value);//��view����,��Z����ת
//                ViewPropertyAnimator.animate(view).rotationBy(float value);//��view����,��Z����תƫ����value
//                ViewPropertyAnimator.animate(view).rotationX(float value);//��view�߶�/2��X��Ϊ����,��תvalue��
//                ViewPropertyAnimator.animate(view).rotationXBy(float value);//��תƫ����value
//                ViewPropertyAnimator.animate(view).rotationY(float value);//��view���/2��Y��Ϊ����,��תvalue��
//                ViewPropertyAnimator.animate(view).rotationYBy(float value);//��תƫ����value
//                ViewPropertyAnimator.animate(view).scaleX(float value);//��view���/2��Y��Ϊ����,����value��
//                ViewPropertyAnimator.animate(view).scaleXBy(float value);//����ƫ����value
//                ViewPropertyAnimator.animate(view).scaleY(float value);//��view�߶�/2��X��Ϊ����,����value��
//                ViewPropertyAnimator.animate(view).scaleYBy(float value);//����ƫ����value
//                ViewPropertyAnimator.animate(view).setDuration(long duration);//���ö���ʱ��
//                ViewPropertyAnimator.animate(view).setInterpolator(Interpolator interpolator);//���ò�ֵ��
//                ViewPropertyAnimator.animate(view).setListener(Animator.AnimatorListener listener);//���ü���
//                ViewPropertyAnimator.animate(view).setStartDelay(long startDelay);//������ʱ
//                ViewPropertyAnimator.animate(view).start();//void
//                ViewPropertyAnimator.animate(view).translationX(float value);//view��X��ƽ��value����
//                ViewPropertyAnimator.animate(view).translationXBy(float value);//ƽ��ƫ����value
//                ViewPropertyAnimator.animate(view).translationY(float value);//view��Y��ƽ��value����
//                ViewPropertyAnimator.animate(view).translationYBy(float value);//ƽ��ƫ����value
//                ViewPropertyAnimator.animate(view).x(float value);//����view�I��X������
//                ViewPropertyAnimator.animate(view).xBy(float value);//ƫ����
//                ViewPropertyAnimator.animate(view).y(float value);//����view�I��Y������
//                ViewPropertyAnimator.animate(view).yBy(float value);//ƫ����

5.TimeAnimator(ϵͳҲ�������,�о�ûʲô��)
                /**TimeAnimator extends ValueAnimator*/
//                TimeAnimator.clearAllAnimations();//void
//                TimeAnimator.getCurrentAnimationsCount();//int
//                TimeAnimator.getFrameDelay();//long
//                TimeAnimator.ofArgb(int... values);/**ofArgb��Androidԭ��,nineoldandroidsû��*/
//                TimeAnimator.ofInt(int... values);
//                TimeAnimator.ofFloat(float... values);
//                TimeAnimator.ofObject(TypeEvaluator evaluator, Object... values);
//                TimeAnimator.ofPropertyValuesHolder(PropertyValuesHolder... values);
//                TimeAnimator.setFrameDelay(long frameDelay);//void

//                TimeAnimator timeAnimator = new TimeAnimator();
//                timeAnimator.setTimeListener(TimeAnimator.TimeListener listener);
//                timeAnimator.start();
//                ...
