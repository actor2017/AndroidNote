https://github.com/JakeWharton/NineOldAndroids

compile 'com.nineoldandroids:library:2.4.0'

<dependency>
  <groupId>com.nineoldandroids</groupId>
  <artifactId>library</artifactId>
  <version>2.4.0</version>
</dependency>

缺点就是：动画例子太少了，自己去调的话效率太慢了

NineOldAndroids是一个向下兼容的动画库，主要是使低于API 11的系统也能够使用View的属性动画。
它的类名、用法与官方的都一样，只是包名不一样。
使用该库，你就可以在API 版本很低的情况下也能够使用各种属性动画，让你的应用更加有动感、平滑。
基本原理简介
　　一般来说，我们使用NineOldAndroids的属性动画时的代码大致是如下这样的:
　　ValueAnimator colorAnim = ObjectAnimator.ofFloat(myView, "scaleX", 0.3f);
    colorAnim.setDuration(1000);
	colorAnim.start();
　　这个动画会将myView （View的子类型）的宽度在1秒钟之内缩放到原始宽度的30%。

    下面我们先来简单说明一下NineOldAndroids的基本原理。
　　不管是官方的支持，还是nideoldandroids的低版本支持，它们使用的技术原理都是一样的。NineOldAndroids的基本原理就是在构建属性动画时根据用户的系统版本来选择不同的实现方式，并且对于低版本的API使用自己的实现来做属性动画。
如果用户的系统API大于等于11，即Android 3.0及其以上，那么就会在动画的duration期间内连续地通过反射来调用该属性的set方法来修改它的值。
例如上面的 scaleX属性，该动画库会在内部构造 scaleX 的set方法，格式如下为set + 首字母大写属性名 + 参数，例如setS caleX (float scale)，这样在一段时间内连续的修改myView的缩放值就达到了动画的效果。

1.ObjectAnimator(系统也有这个类)

2.ValueAnimator(系统也有这个类)

3.ViewHelper(系统没有这个类)
                  /**下面方法都返回:void*/
//                ViewHelper.setAlpha(View view, float alpha);//设置透明度[0, 1]

                /**下面2个属性只是设置点,需要配合rotation,scale等才有效果(侧滑菜单 ——仿QQ实现动画效果)*/
//                ViewHelper.setPivotX(View view, float pivotX);//view以pivotX处X轴为中心旋转/缩放
//                ViewHelper.setPivotY(View view, float pivotY);//view以pivotY处Y轴为中心旋转/缩放

//                ViewHelper.setRotation(View view, float rotation);//以view中心,按Z轴旋转rotation°
//                ViewHelper.setRotationX(View view, float rotationX);//以view高度/2的X轴为中心,旋转rotationX°
//                ViewHelper.setRotationY(View view, float rotationY);//以view宽度/2的Y轴为中心,旋转rotationY°
//                ViewHelper.setScaleX(View view, float scaleX);//以view宽度/2的Y轴为中心,缩放scaleX倍
//                ViewHelper.setScaleY(View view, float scaleY);//以view高度/2的X轴为中心,缩放scaleY倍
//                ViewHelper.setScrollX(View view, int scrollX);//view以X轴←滑动scrollX距离(背景不变)
//                ViewHelper.setScrollY(View view, int scrollY);//view以Y轴↑滑动scrollY距离
//                ViewHelper.setTranslationX(View view, float translationX);//view在X轴平移xx距离(view.setTranslationX(value);)
//                ViewHelper.setTranslationY(View view, float translationY);//view在Y轴平移xx距离
//                ViewHelper.setX(View view, float x);//设置view↖的X轴坐标
//                ViewHelper.setY(View view, float y);//设置view↖的Y轴坐标

4.ViewPropertyAnimator(系统也有这个类)
                /**没有注释的都返回:ViewPropertyAnimator*/
//                ViewPropertyAnimator.animate(view).alpha(float value);//设置透明度[0, 1]
//                ViewPropertyAnimator.animate(view).alphaBy(float value);//在已有透明度上偏移value
//                ViewPropertyAnimator.animate(view).cancel();//void 取消动画
//                ViewPropertyAnimator.animate(view).getDuration();//long 动画时间
//                ViewPropertyAnimator.animate(view).getStartDelay();//long 获取动画默认启动延时
//                ViewPropertyAnimator.animate(view).rotation(float value);//以view中心,按Z轴旋转
//                ViewPropertyAnimator.animate(view).rotationBy(float value);//以view中心,按Z轴旋转偏移量value
//                ViewPropertyAnimator.animate(view).rotationX(float value);//以view高度/2的X轴为中心,旋转value°
//                ViewPropertyAnimator.animate(view).rotationXBy(float value);//旋转偏移量value
//                ViewPropertyAnimator.animate(view).rotationY(float value);//以view宽度/2的Y轴为中心,旋转value°
//                ViewPropertyAnimator.animate(view).rotationYBy(float value);//旋转偏移量value
//                ViewPropertyAnimator.animate(view).scaleX(float value);//以view宽度/2的Y轴为中心,缩放value倍
//                ViewPropertyAnimator.animate(view).scaleXBy(float value);//缩放偏移量value
//                ViewPropertyAnimator.animate(view).scaleY(float value);//以view高度/2的X轴为中心,缩放value倍
//                ViewPropertyAnimator.animate(view).scaleYBy(float value);//缩放偏移量value
//                ViewPropertyAnimator.animate(view).setDuration(long duration);//设置动画时间
//                ViewPropertyAnimator.animate(view).setInterpolator(Interpolator interpolator);//设置插值器
//                ViewPropertyAnimator.animate(view).setListener(Animator.AnimatorListener listener);//设置监听
//                ViewPropertyAnimator.animate(view).setStartDelay(long startDelay);//设置延时
//                ViewPropertyAnimator.animate(view).start();//void
//                ViewPropertyAnimator.animate(view).translationX(float value);//view在X轴平移value距离
//                ViewPropertyAnimator.animate(view).translationXBy(float value);//平移偏移量value
//                ViewPropertyAnimator.animate(view).translationY(float value);//view在Y轴平移value距离
//                ViewPropertyAnimator.animate(view).translationYBy(float value);//平移偏移量value
//                ViewPropertyAnimator.animate(view).x(float value);//设置view↖的X轴坐标
//                ViewPropertyAnimator.animate(view).xBy(float value);//偏移量
//                ViewPropertyAnimator.animate(view).y(float value);//设置view↖的Y轴坐标
//                ViewPropertyAnimator.animate(view).yBy(float value);//偏移量

5.TimeAnimator(系统也有这个类,感觉没什么用)
                /**TimeAnimator extends ValueAnimator*/
//                TimeAnimator.clearAllAnimations();//void
//                TimeAnimator.getCurrentAnimationsCount();//int
//                TimeAnimator.getFrameDelay();//long
//                TimeAnimator.ofArgb(int... values);/**ofArgb是Android原生,nineoldandroids没有*/
//                TimeAnimator.ofInt(int... values);
//                TimeAnimator.ofFloat(float... values);
//                TimeAnimator.ofObject(TypeEvaluator evaluator, Object... values);
//                TimeAnimator.ofPropertyValuesHolder(PropertyValuesHolder... values);
//                TimeAnimator.setFrameDelay(long frameDelay);//void

//                TimeAnimator timeAnimator = new TimeAnimator();
//                timeAnimator.setTimeListener(TimeAnimator.TimeListener listener);
//                timeAnimator.start();
//                ...
