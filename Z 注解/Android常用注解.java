1.方法上
@Override					//表示这个方法是重写的父类方法
@RequiresPermission(INTERNET)	//表示这个方法必须要INTERNET权限

2.参数内
public void test(@NonNull String a) {//表示传入/回调方法的a不能为null @Nullable
public void test(@Nullable String a) {//表示可以传入null/回调方法a可能为null

public void test(@IntRange(from = 0, to = 1) int a) {//表示参数范围[0, 1]
public void test(@FloatRange(from = 0F, to = 1F) float a) {//表示float参数范围[0F, 1F]

public void test(@LayoutRes int resId) {//表示传入布局文件id
public void test(@DrawableRes int drawableId) {//表示传入drawable文件id


public void setColor(@ColorInt int color);//表示传入的颜色是已经转换过的
public void setColor(@ColorRes int colorRes);//表示传入的类型是从资源文件 R.color.xxx

//方法在被混淆的时候应该被保留(or 配置复杂的Proguard文件)
@Keep

//子类方法必须调用这个父类方法
@CallSuper

3.权限
//至少1个权限(会提示在 AndroidMenifest.xml 中声明相应的权限)
@RequiresPermission(Manifest.permission.INTERNET)

//anyOf: 至少需要权限集合中的一个
@RequiresPermission(anyOf = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})

//allOf: 同时需要多个权限
@RequiresPermission(allOf = {Manifest.permission.READ_HISTORY_BOOKMARKS, Manifest.permission.WRITE_HISTORY_BOOKMARKS})

//intent 添加权限
@RequiresPermission(android.Manifest.permission.BLUETOOTH)
public static final String ACTION_REQUEST_DISCOVERABLE = "android.bluetooth.adapter.action.REQUEST_DISCOVERABLE";


4.其它
//指定访问元素权限的范围
@RestrictTo(RestrictTo.Scope.SUBCLASSES)//只能子类能访问










