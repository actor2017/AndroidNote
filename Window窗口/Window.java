唯一子类: PhoneWindow extends Window

1.一堆 public static final 常量

2.一堆回调Interface

3.构造方法
public Window(Context context)

4.普通方法
public final Context getContext()
public final TypedArray getWindowStyle();//Return the {@link android.R.styleable#Window
public void setContainer(Window container)
public final Window getContainer()
public final boolean hasChildren()
public void setWindowManager(WindowManager wm, IBinder appToken, String appName)
public void setWindowManager(WindowManager wm, IBinder appToken, String appName, boolean hardwareAccelerated)
public WindowManager getWindowManager()
public void setCallback(Callback callback)
public final Callback getCallback()
public final void addOnFrameMetricsAvailableListener(@NonNull OnFrameMetricsAvailableListener listener, Handler handler);//设置一个观察者来收集这个窗口中每个帧渲染器的帧统计信息
public final void removeOnFrameMetricsAvailableListener(OnFrameMetricsAvailableListener listener)
public final void setRestrictedCaptionAreaListener(OnRestrictedCaptionAreaChangedListener listener)
public void setLayout(int width, int height);//WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
public void setGravity(int gravity);//设置窗口在屏幕的位置
public void setType(int type)
public void setFormat(int format)
public void setWindowAnimations(@StyleRes int resId);//设置窗口 进入/退出 动画, 示例:dialog.getWindow(), R.style.dialog_notify_animation(应该写在anim文件夹里面吧)
public void setSoftInputMode(int mode)
public void addFlags(int flags)
public void clearFlags(int flags)
public void setFlags(int flags, int mask)
public void setColorMode(@ActivityInfo.ColorMode int colorMode)
public int getColorMode()
public boolean isWideColorGamut()
public void setDimAmount(float amount);//背景透明度

//获取当前窗口的属性, 布局参数
public final WindowManager.LayoutParams getAttributes()
WindowManager.LayoutParams params;
params.width = 150dp;
params.height = 150dp;
params.x = 0;
params.y = 0;//相对上方的偏移,负值忽略.
params.dimAmount = dimAmount;//背景透明度
params.gravity = /*android.view.*/Gravity.CENTER;//设置Window居中!

public void setAttributes(WindowManager.LayoutParams a)


public void setSustainedPerformanceMode(boolean enable)
public boolean requestFeature(int featureId)
public final void makeActive()
public final boolean isActive()
public <T extends View> T findViewById(@IdRes int id);//根据id查找View
public final <T extends View> T requireViewById(@IdRes int id);//根据id查找View, 如果View = null, 会抛异常
public void setElevation(float elevation)
public void setClipToOutline(boolean clipToOutline)
public void setBackgroundDrawableResource(@DrawableRes int resId)
public boolean hasFeature(int feature)
......

5.静态方法
public static int getDefaultFeatures(Context context)

6.抽象方法
public abstract void takeSurface(SurfaceHolder.Callback2 callback);
public abstract void takeInputQueue(InputQueue.Callback callback);
public abstract boolean isFloating();
public abstract void setContentView(@LayoutRes int layoutResID);
public abstract void setContentView(View view);
public abstract void setContentView(View view, ViewGroup.LayoutParams params);
public abstract void addContentView(View view, ViewGroup.LayoutParams params);
public abstract View getCurrentFocus();
public abstract LayoutInflater getLayoutInflater();
public abstract void setTitle(CharSequence title);
public abstract void setTitleColor(@ColorInt int textColor);//过时
public abstract void openPanel(int featureId, KeyEvent event);
public abstract void closePanel(int featureId);
public abstract void togglePanel(int featureId, KeyEvent event);
public abstract void invalidatePanelMenu(int featureId);
public abstract boolean performPanelShortcut(int featureId, int keyCode, KeyEvent event, int flags);
public abstract boolean performPanelIdentifierAction(int featureId, int id, int flags);
public abstract void closeAllPanels();
public abstract boolean performContextMenuIdentifierAction(int id, int flags);
public abstract void onConfigurationChanged(Configuration newConfig);
public abstract void setBackgroundDrawable(Drawable drawable);
public abstract void setFeatureDrawableResource(int featureId, @DrawableRes int resId);
public abstract void setFeatureDrawableUri(int featureId, Uri uri);
public abstract void setFeatureDrawable(int featureId, Drawable drawable);
public abstract void setFeatureDrawableAlpha(int featureId, int alpha);
public abstract void setFeatureInt(int featureId, int value);
public abstract void takeKeyEvents(boolean get);
public abstract boolean superDispatchKeyEvent(KeyEvent event);
public abstract boolean superDispatchKeyShortcutEvent(KeyEvent event);
public abstract boolean superDispatchTouchEvent(MotionEvent event);
public abstract boolean superDispatchTrackballEvent(MotionEvent event);
public abstract boolean superDispatchGenericMotionEvent(MotionEvent event);
public abstract View getDecorView();
public abstract View peekDecorView();
public abstract Bundle saveHierarchyState();
public abstract void restoreHierarchyState(Bundle savedInstanceState);
public abstract void setChildDrawable(int featureId, Drawable drawable);
public abstract void setChildInt(int featureId, int value);
......


    <!--进入/退出动画-->
    <style name="dialog_notify_animation" parent="android:Animation" mce_bogus="1">
        <item name="android:windowEnterAnimation">@anim/notify_in</item>
        <item name="android:windowExitAnimation">@anim/notify_out</item>
    </style>