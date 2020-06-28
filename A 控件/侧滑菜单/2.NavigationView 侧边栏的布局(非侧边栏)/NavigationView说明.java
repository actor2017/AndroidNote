1.添加依赖:compile 'com.android.support:design:23.1.1'

这里因为我工程配置的 compileSdkVersion 是 23 ，所以需要引入 com.android.support:design：23.x.x 的版本。需要吐槽的是，这里如果你引入了 com.android.support:design：23.1.0 ，工程运行后 NavigationView 会报一个 android.view.InflateException：xxxxxx 的错误（又是一个大坑）。

AndroidStudio选择Activity自动生成

2.NavigationView主要和DrawerLayout框架结合使用，为抽屉导航实现侧边栏提供更简单更方便的生成方式。惯例,先看效果图:
<android.support.v4.widget.DrawerLayout
xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:openDrawer="start">

        <!-- content内容-->
        <include layout="@layout/app_bar_main"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

        <!-- NavigationView 侧边栏-->
        <android.support.design.widget.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        app:headerLayout="@layout/nav_header_main"
        app:insetForeground="@drawable/naruto_hinata"//状态栏颜色 or 显示图片
        app:menu="@menu/activity_main_drawer"/>
</android.support.v4.widget.DrawerLayout>

//item点击事件
navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mDrawerLayout.closeDrawers();//V4包侧滑关闭
        //menuItem.setChecked(true);//menu设置选中
        getSupportActionBar().setTitle(menuItem.getTitle());//设置标题
        return true;//true to display the item as the selected item
    }
});