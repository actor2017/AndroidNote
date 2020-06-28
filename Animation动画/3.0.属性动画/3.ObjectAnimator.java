1.ObjectAnimator extends ValueAnimator extends Animator,属性动画

2.构造方法
public ObjectAnimator();

3.静态方法
/**
 * @param target view控件等,但不止view,包括所有java类
 * @param propertyName target里面的setPropertyName();方法,比如target是view,name=alpha,就是view.setAlpha(中间值);
 *                     1.width,height(1.TextView里的方法,View里居然没有.2.必须是ofInt,ofFloat不行没效果)
 *                     2.alpha
 *                     3.translationX,translationY,translationZ(必须是ofFloat, ofInt没效果)
 *                     4.rotation,rotationX,rotationY
 *                     5.scaleX,scaleY
 *                     上面这些全是view.setxxx方法,属性动画产生中间值时,会不断调用target.setPropertyName(中间值)
 *                     自定义示例:height,那么target.setHeight()(这个方法需自己重写)
 * @param values 0,300.在规定时间内会产生一系列0-300的中间值
 */
ObjectAnimator ofInt(Object target, String propertyName, int... values);
ObjectAnimator ofInt(Object target, String xPropertyName, String yPropertyName, Path path);
ObjectAnimator ofInt(T target, Property<T, Integer> property, int... values);
ObjectAnimator ofInt(T target, Property<T, Integer> xProperty, Property<T, Integer> yProperty, Path path);

ObjectAnimator ofMultiInt(Object target, String propertyName, int[][] values);
ObjectAnimator ofMultiInt(Object target, String propertyName, Path path);
ObjectAnimator ofMultiInt(Object target, String propertyName, TypeConverter<T, int[]> converter, TypeEvaluator<T> evaluator, T... values);

//父类还有一个ofArgb方法
//(tvTitle, "TextColor", startValue, endValue);//微信Tab文字渐变
//实际调用方法: android.animation.ArgbEvaluator#evaluate(float, Object, Object)//评估, 估值器, 不同版本写法不一样
ObjectAnimator ofArgb(Object target, String propertyName, int... values);
ObjectAnimator ofArgb(T target, Property<T, Integer> property, int... values);

ObjectAnimator ofFloat(Object target, String propertyName, float... values);//view, "rotationY", 0, 360,Logo旋转
ObjectAnimator ofFloat(Object target, String xPropertyName, String yPropertyName, Path path);
ObjectAnimator ofFloat(T target, Property<T, Float> property, float... values);
ObjectAnimator ofFloat(T target, Property<T, Float> xProperty, Property<T, Float> yProperty, Path path);

ObjectAnimator ofMultiFloat(Object target, String propertyName, float[][] values);
ObjectAnimator ofMultiFloat(Object target, String propertyName, Path path);
ObjectAnimator ofMultiFloat(Object target, String propertyName, TypeConverter<T, float[]> converter, TypeEvaluator<T> evaluator, T... values);

ObjectAnimator ofObject(Object target, String propertyName, TypeEvaluator evaluator, Object... values);
ObjectAnimator ofObject(Object target, String propertyName, @Nullable TypeConverter<PointF, ?> converter, Path path);
ObjectAnimator ofObject(T target, Property<T, V> property, TypeEvaluator<V> evaluator, V... values);
ObjectAnimator ofObject(T target, Property<T, P> property, TypeConverter<V, P> converter, TypeEvaluator<V> evaluator, V... values);
ObjectAnimator ofObject(T target, @NonNull Property<T, V> property, @Nullable TypeConverter<PointF, V> converter, Path path);

ObjectAnimator ofPropertyValuesHolder(Object target, PropertyValuesHolder... values);
			
4.一般方法
void setPropertyName(@NonNull String propertyName);
void setProperty(@NonNull Property property);
String getPropertyName();
void setAutoCancel(boolean cancel);
ObjectAnimator setDuration(long duration);
Object getTarget();

5.Override
void setIntValues(int... values);
void setFloatValues(float... values);
void setObjectValues(Object... values);
void start();
void setTarget(@Nullable Object target);
void setupStartValues();
void setupEndValues();
ObjectAnimator clone();

6.示例写法
如果要在2.x版本也要支持属性动画,则需要加入兼容包:nineoldandroids-2.4.0.jar
com.nineoldandroids.animation.ObjectAnimator.ofFloat(Object target, String propertyName, float... values);







