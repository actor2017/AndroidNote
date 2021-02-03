package cn.itcast.testmeasure;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Random;

/**
 * Created by zhengping on 2017/2/28,15:37.
 * 1.dp 和 像素 的互转    dp2px(用的多)  px2dp
 * 2.获取屏幕宽度/高度getScreenWidth
 */

public class UiUtils {

    public static Context getContext() {
        return MyApplication.instance.applicationContext;
    }

    public static Handler getMainThreadHander() {
        return MyApplication.instance.handler;
    }

    public static int getMainThreadId() {
        return MyApplication.instance.mainThreadId;
    }

    /**
     * 获取字符串资源
     */
    public static String getString(@StringRes int resId) {
        return getContext().getResources().getString(resId);
    }

    /**
     * 获取字符串数组
     */
    public static String[] getStringArray(@ArrayRes int resId) {
        return getContext().getResources().getStringArray(resId);
    }

    /**
     * 获取Drawable
     */
    public static Drawable getDrawable(@DrawableRes int resId) {
        return getContext().getResources().getDrawable(resId);
    }

    /**
     * 获取color
     */
    public static int getColor(@ColorRes int resId) {
        return getContext().getResources().getColor(resId);
    }

    /**
     * 获取颜色列表(TextView颜色选择器)
     */
	public static ColorStateList getColorStateList(@ColorRes int resId) {
        return getContext().getResources().getColorStateList(resId);
    }

    /**
     * 颜色随机，也不能够太随机，应该控制在一定的范围之内   30~220
     */
    public static int getRandomColor() {
        Random random = new Random();
        int red = 30 + random.nextInt(191);
        int green = 30 + random.nextInt(191);
        int blue = 30 + random.nextInt(191);
        return Color.rgb(red, green, blue);
    }

    /**
     * 获取随机字体大小,16~25sp之间
     */
    public static int getRandomTextSize() {
        Random random = new Random();
        return 16 + random.nextInt(10);
    }

    /**
     * 获取尺寸
     */
    public static int getDimen(@DimenRes int resId) {
        return getContext().getResources().getDimensionPixelSize(resId);
    }

    /**
     * dp 转换为 像素,传入"dp",输出"像素",java代码中一般用这种
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);//四舍五入
    }

    /**
     * 像素 转换为 dp,传入"像素",输出"dp"
     */
    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px/density + 0.5F);
    }

    /**
     * 字体的sp 转换为 px
     */
    public static int sp2px(float sp) {
        float var2 = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int)(sp * var2 + 0.5F);
    }

    /**
     * px 转换为 sp
     */
    public static int px2sp(float px) {
        float var2 = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int)(px / var2 + 0.5F);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreemHeight(){
        return getContext().getResources().getDisplayMetrics().heightPixels;
        //下面方法也可以
        //return ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }

    /**
     * 获取控件宽度
     * @param view
     * @param widthDp 宽度,单位dp
     * @param mode View.MeasureSpec.AT_MOST, View.MeasureSpec.EXACTLY, View.MeasureSpec.UNSPECIFIED
     * @deprecated 需要传入具体值,并且mode要正确,不大好用,建议使用一下方法获取宽高:
     *
     * 1.在Activity中重写如下方法也能获取:
     * @Override
     * public void onWindowFocusChanged(boolean hasFocus) {
     *     super.onWindowFocusChanged(hasFocus);
     *     System.out.println(view.getWidth() + ":" + view.getHeight());
     * }
     *
     * 2.如果是自定义View
     * @Override
     * protected void onLayout(boolean changed, int l, int t, int r, int b) {
     * super.onLayout(changed, l, t, r, b);
     * view.getWidth();
     * view.getHeight();
     * }
     *
     * 自定义View或者这样
     * @Override
     * protected void onSizeChanged(int w, int h, int oldw, int oldh) {
     *     super.onSizeChanged(w, h, oldw, oldh);
     *     view.getWidth();
     *     view.getHeight();
     * }
     */
    @Deprecated
    public static int[] getViewSize(View view, int widthDp, int mode){
        int[] size = {0,0};
        /**
         * 测试一个TextView:
         * <TextView
         *     android:id="@+id/tv"
         *     android:layout_width="wrap_content"
         *     android:layout_height="wrap_content"
         *     android:text="Hello World!jfklasjflasjdfl;asjdflasjdfljasdlfjasdl;fjlasdjflasdjflasdjfl;asdjflasdjf;ladsfjl;dsafjlksdafjlkadsjflkdsafjadslkfjsdlafjadslfjadslfjasldfjlsdafjladsjflsdka"/>
         */

        /**
         * MeasureSpc类封装了父View传递给子View的布局(layout)要求
         * widthMeasureSpec:mode+size
         * View.MeasureSpec.AT_MOST = -2147483648(wrap_content)
         * View.MeasureSpec.EXACTLY = 1073741824
         * View.MeasureSpec.UNSPECIFIED = 0
         */
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(dp2px(widthDp), mode);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        size[0] = view.getMeasuredWidth();
        size[1] = view.getMeasuredHeight();
		
        /**
         * 测量这个view，最后通过getMeasuredWidth()获取宽度和高度.试了一下, 也是可以的...
         * @param v 要测量的view
         */
//        public static void measureView(View v) {
//            if (v == null) return;
//            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            v.measure(w, h);
//        }

        //1.对view监听视图树,但是onCreate总是0...
//        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
//        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                size[0] = view.getWidth();
//                size[1] = view.getHeight();
//                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//            }
//        });

        //2.组件绘制之前的监听,这种方法也不行
//        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                size[0] = view.getMeasuredWidth();
//                size[1] = view.getMeasuredHeight();
//                view.getViewTreeObserver().removeOnPreDrawListener(this);
//                return true;
//            }
//        });

        //3.也不行
//        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int
//                    oldLeft, int oldTop, int oldRight, int oldBottom) {
//                size[0] = view.getWidth();//获取宽度
//                size[1] = view.getHeight();//获取高度
//                view.removeOnLayoutChangeListener(this);
//            }
//        });

        //4.这种方法即使onCreate中也有值,但是宽高不对[945, 19]
//        view.measure(0, 0);//传0的意思是代表模式是UNSPECIFIED，此时对宽度不做任何限制
//        size[0] = view.getMeasuredWidth();
//        size[1] = view.getMeasuredHeight();

        //5.这种方法也不行
//        view.post(new Runnable() {
//            @Override
//            public void run() {
//                size[0] = view.getWidth();
//                size[1] = view.getHeight();
//            }
//        });
        return size;
    }
	
	/**
     * 设置view宽度
     * https://www.jianshu.com/p/5030624ba66f
     * https://blog.csdn.net/qingxuan521721/article/details/78496550
     */
    public static <T extends View> T setViewWidth(T view, int width) {
        view.getLayoutParams().width = width;
//        view.requestLayout();//必须调用，否则宽度改变但UI没有刷新??
        return view;
    }

    /**
     * 设置view高度
     */
    public static <T extends View> T setViewHeight(T view, int height) {
        view.getLayoutParams().height = height;
//        view.requestLayout();//必须调用，否则宽度改变但UI没有刷新??
        return view;
    }

    /**
     * 设置View的Margin
     */
    public static void setViewMargin(View view, int left, int top, int right, int bottom) {
        //LinearLayout.LayoutParams extends ViewGroup.MarginLayoutParams extends ViewGroup.LayoutParams
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        } else {
            /**
             *  第一种方法,强转:LinearLayout.LayoutParams(ViewGroup.LayoutParams)
             *  如果这个控件实在XML中定义的 比如Textview,验证可行
             *  如果这个控件是我们new出来的，就会发现报空指针错误(没试过.应该是返回null,不是空指针)
             */
//            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();

            /**
             * 第二种方法,new一个对象,验证可行
             */
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);//(ViewGroup.LayoutParams)
        }
    }

    /**
     * xml文件中animated-selector
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static AnimatedStateListDrawable getAnimatedSelector() {
        AnimatedStateListDrawable asld = new AnimatedStateListDrawable();
        return asld;
    }

    //干什么的?
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static AnimatedVectorDrawable getAnimatedVectorDrawable() {
        AnimatedVectorDrawable avd = new AnimatedVectorDrawable();
        return avd;
    }

    /**
     * xml文件中的animation-list
     */
    public static AnimationDrawable getAnimationList() {
        AnimationDrawable ad = new AnimationDrawable();
        return ad;
    }

    /**
     * xml文件中的bitmap
     */
    public static BitmapDrawable getBitmap() {
        BitmapDrawable bd = new BitmapDrawable();//有构造方法
        return bd;
    }

    /**
     * xml文件中的clip
     */
    public static ClipDrawable getClip(Drawable drawable, int gravity, int orientation) {
        ClipDrawable cd = new ClipDrawable(drawable, gravity, orientation);
        return cd;
    }

    /**
     * xml文件中的color
     */
    public static ColorDrawable getColor(@ColorInt int color) {
        ColorDrawable cd = new ColorDrawable(/*color*/);//还有一个构造方法
        return cd;
    }

    /**
     * 渐变 Drawable, xml文件中的shape
     */
    public static GradientDrawable getShape(int radius, @ColorInt int color) {
		//参1: 颜色渐变方向: 从上到下.   参2: 颜色渐变值, 可多个
        //GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, color);
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(radius);
        shape.setColor(color);
        return shape;
    }

    /**
     * xml文件中的inset
     */
    public static InsetDrawable getInset(Drawable drawable, int inset) {
        InsetDrawable id = new InsetDrawable(drawable, inset);//还有一个构造方法
        return id;
    }

    /**
     * xml文件中的layer-list
     */
    public static LayerDrawable getLayerList(@NonNull Drawable[] layers) {
        LayerDrawable ld = new LayerDrawable(layers);
        return ld;
    }

    /**
     * xml文件中的level-list
     */
    public static LevelListDrawable getLevelList() {
        LevelListDrawable lld = new LevelListDrawable();
        return lld;
    }

    /**
     * xml文件中的ripple
     */
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)//21
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static RippleDrawable getRipple(@NonNull ColorStateList color, @Nullable Drawable content,
                                           @Nullable Drawable mask) {
        RippleDrawable rd = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            rd = new RippleDrawable(color, content, mask);
        }
        return rd;
    }

    /**
     * xml文件中的rotate
     */
    public static RotateDrawable getRotate() {
        RotateDrawable rd = new RotateDrawable();
        return rd;
    }

    /**
     * xml文件中的scale
     */
    public static ScaleDrawable getScale(Drawable drawable, int gravity, float scaleWidth, float scaleHeight) {
        ScaleDrawable sd = new ScaleDrawable(drawable, gravity, scaleWidth, scaleHeight);
        return sd;
    }

    /**
     * xml文件中的shape
     */
    public static ShapeDrawable getShape() {
        ShapeDrawable sd = new ShapeDrawable();//还有一个构造方法
        return sd;
    }

    /**
     * xml文件中selector
     */
    public static StateListDrawable getSelector(Drawable pressedDrawable, Drawable normalDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();//代表写了一个selector的标签
        //给selector增加规则
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},pressedDrawable);//增加按下的规则
        stateListDrawable.addState(new int[]{},normalDrawable);//增加默认的规则
        return stateListDrawable;
    }

    /**
     * xml文件中transition
     */
    public static TransitionDrawable getTransition(Drawable[] layers) {
        TransitionDrawable td = new TransitionDrawable(layers);
        return td;
    }

    /**
     * xml文件中vertor
     */
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)//21
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static VectorDrawable getVector() {
        VectorDrawable vd = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            vd = new VectorDrawable();
        }
        return vd;
    }
}
