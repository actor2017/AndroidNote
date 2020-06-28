DrawerLayout布局分两部分，第一用户内容，就是非菜单部分。第二是菜单。

DrawerLayout可以轻松的实现抽屉效果、在DrawerLayout中，第一个子View必须是显示内容的view，
第二个view是抽屉view,设置属性layout_gravity=”left|right”,表示是从左边滑出还是右边滑出。

<?xml version="1.0" encoding="utf-8"?>
<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
	android:id="@+id/simple_navigation_drawer"
	tools:openDrawer="start">				//打开侧边栏

	<!--内容视图, 不一定是include, 可以写成各种 Layout, fragment ...等-->
	<include
		android:id="@+id/tv_content"
		layout="@layout/drawer_content_layout"//自定义布局
		android:layout_width="match_parent"
		android:layout_height="match_parent" />

	<!--左侧滑菜单栏, 不一定是include, 可以写fragment, 或 RecyclerView ...等-->
	<include
		layout="@layout/drawer_menu_layout"	//自定义布局
		android:layout_width="match_parent"	//默认会有一个marginRight, DrawerLayout中 "MIN_DRAWER_MARGIN" 字段 = 64dp
		android:layout_height="match_parent"
		android:layout_gravity="start"		//主要是这个属性
        android:layout_marginRight="-65dp" />//如果想划出的是全屏, 设置这个属性即可

	<!--右侧滑菜单栏-->
	<include
		layout="@layout/drawer_menu_layout"
		android:layout_width="250dp"		//也可以把宽度写成一个固定的值
		android:layout_height="match_parent"
		android:layout_gravity="end" />
</android.support.v4.widget.DrawerLayout>

//锁定 & 关闭状态(不能滑动打开, 能滑动关闭, 能代码打开/关闭)
drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

drawerLayout.openDrawer(Gravity.START);	//打开
drawerLayout.isDrawerOpen(Gravity.START);//左边是否打开
drawerLayout.closeDrawer(Gravity.START);
drawerLayout.closeDrawers();			//关闭


2.设置滑动边距:
1.设置一个marginRight, 比如:
android:layout_marginRight="-65dp"	//全屏

2.反射
public static void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
    if (activity == null || drawerLayout == null) return;
    try {
        Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
        leftDraggerField.setAccessible(true);
        ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
        Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
        edgeSizeField.setAccessible(true);
        int edgeSize = edgeSizeField.getInt(leftDragger);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (dm.widthPixels * displayWidthPercentage)));
    } catch (Exception e) {
    }
}

