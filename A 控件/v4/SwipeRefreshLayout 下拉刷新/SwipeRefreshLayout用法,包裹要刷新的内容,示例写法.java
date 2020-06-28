SwipeRefreshLayout可同时包含多个控件
1.SwipeRefreshLayout 嵌套在 NestedScrollView 外面.


xml:
    <!--可以滑动的一个本地控件v4包,可以有多个不同类型的控件,
        但2个scrollview有bug(教育app的排行页面,第一个rv上滑后,第二个rv不能下滑)-->
注意:如果有scrollview,应该吧scrollview包含在里面
    <android.support.v4.widget.SwipeRefreshLayout
        android:id="@+id/srl"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v7.widget.RecyclerView	//RecyclerView有了刷新功能,刷新逻辑要自己实现
            android:id="@+id/rv_recuclerview"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        </android.support.v7.widget.RecyclerView>
    </android.support.v4.widget.SwipeRefreshLayout>


代码中:
SwipeRefreshLayout srl= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
srl.setColorSchemeColors(Color.RED,Color.BLUE,Color.CYAN,Color.YELLOW);//转一圈变一个颜色
srl.setProgressBackgroundColor(R.color.refresh_bg);//设置圈圈背景颜色

        int[] colors = new int[100];//100种颜色
        for (int i = 0; i < colors.length; i++) {
            colors[i] = UiUtils.getRandomColor();
        }
        srl.setColorSchemeColors(colors);


srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置下拉刷新监听
    @Override
    public void onRefresh() {}
});

srl.postDelayed(new Runnable() {//延迟3秒执行run方法
    @Override
    public void run() {
        srl.setRefreshing(false);
    }
},3000);

srl.setRefreshing(true/false);//设置刷新效果

srl.isRefreshing();//检查是否正在刷新

srl.setEnabled(false);//设置不能刷新
