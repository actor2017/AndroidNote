1、ScrollView和HorizontalScrollView是为控件或者布局添加滚动条
2、上述两个控件只能有一个孩子，但是它并不是传统意义上的容器
3、上述两个控件可以互相嵌套
4、滚动条的位置现在的实验结果是：可以由layout_width和layout_height设定
5、ScrollView用于设置垂直滚动条，HorizontalScrollView用于设置水平滚动条：
   需要注意的是，有一个属性是    scrollbars 可以设置滚动条的方向:但是ScrollView设置成horizontal是和设置成none是效果同，HorizontalScrollView设置成vertical和none的效果同。

<?xml version="1.0" encoding="utf-8"?>
<HorizontalScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent" >

    <ScrollView
        android:layout_width="fill_parent"
        android:layout_height="wrap_content" >

        <LinearLayout
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical" >

            <TextView
                android:layout_width="fill_parent"
                android:layout_height="wrap_content"
                android:text="@string/hello"
                android:textSize="90sp" />

		... ...许多textview

scrollEnabled 是否可以滚动

int i = nsv.computeVerticalScrollRange();//scrollview全部高度
scrollview.fullScroll(View.FOCUS_DOWN);//scroll滑动到底部
scrollview.pageScroll(View.FOCUS_DOWN);//向下滑动一页

Nestedscrollview 嵌套 Recyclerview,当RecyclerView数据增加后不能滑动到底部问题:
ThreadUtils.handler.postDelayed(new Runnable() {//要延时下滑, 最后一条数据才能显示出来...
    @Override
    public void run() {
        nsv.fullScroll(View.FOCUS_DOWN);
    }
}, 500);


contentSize 里面内容的大小，即可以滚动的大小，默认是0，没有滚动效果
showsHorizontalScrollIndicator 滚动时是否显示水平滚动条
showsVerticalScrollIndicator 滚动时是否显示垂直滚动条
bounces 默认是YES，就是滚动超过边界会反弹，即有反弹回来的效果。若是NO，则滚动到达边界会立刻停止

directionalLockEnabled 默认是NO，可以在垂直和水平方向同时运动。当值是YES时，视哪个方向开始则锁定另外一个方向的滚动。

indicatorStyle 滚动条的样式。总共3色：默认、黑、白
scrollIndicatorInsets 设置滚动条位置
decelerating 当滚动后，手指放开但还在继续滚动中。此时是YES，其它时候都是NO
decelerationRate 设置手指放开后的减速率


属性汇总
编辑
android:scrollbars
设置滚动条显示。none（隐藏），horizontal（水平），vertical（垂直）。

android:scrollbarFadeDuration
设置滚动条淡出效果（从有到慢慢的变淡直至消失）时间，以毫秒为单位。Android2.2中滚动条滚动完之后会消失，再滚动又会出来，在1.5、1.6版本里面会一直显示着。

android:scrollbarSize
设置滚动条的宽度。

android:scrollbarStyle
设置滚动条的风格和位置。设置值：insideOverlay、insideInset、outsideOverlay、outsideInset

android:scrollbarThumbHorizontal
设置水平滚动条的drawable。

android:scrollbarThumbVertical
设置垂直滚动条的drawable.

android:scrollbarTrackHorizontal
设置水平滚动条背景（轨迹）的色drawable

android:soundEffectsEnabled
设置点击或触摸时是否有声音效果


在android开发过程中，我们而你会发现当你在ScrollView中包含了EditText试图的时候，程序页面加载的时候，会自动滚动到编辑框所在的位置。那么怎么彻底解决这种现象呢？
ScrollView view = (ScrollView)rootView.findViewById(R.id.home_scrollview);
        view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                v.requestFocusFromTouch();
                return false;
            }           
        });

这是完美解决方案

补充：
即使内部没有EditText，也出现了这种情况，此方法同样适用。

//滑动监听
nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int
            oldScrollX, int oldScrollY) {
        if (scrollY > oldScrollY) {
            LogUtils.error("向下滑");
        }
        if (scrollY < oldScrollY) {
            LogUtils.error("向上滑");
        }
        if (scrollY == 0) {
            LogUtils.error("顶部");
        }
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            LogUtils.error("滑动到了底部");
        }
    }
});

