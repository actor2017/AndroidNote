1.�������:compile 'com.android.support:design:23.1.1'

������Ϊ�ҹ������õ� compileSdkVersion �� 23 ��������Ҫ���� com.android.support:design��23.x.x �İ汾����Ҫ�²۵��ǣ���������������� com.android.support:design��23.1.0 ���������к� NavigationView �ᱨһ�� android.view.InflateException��xxxxxx �Ĵ�������һ����ӣ���

AndroidStudioѡ��Activity�Զ�����

2.NavigationView��Ҫ��DrawerLayout��ܽ��ʹ�ã�Ϊ���뵼��ʵ�ֲ�����ṩ���򵥸���������ɷ�ʽ������,�ȿ�Ч��ͼ:
<android.support.v4.widget.DrawerLayout
xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:openDrawer="start">

        <!-- content����-->
        <include layout="@layout/app_bar_main"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

        <!-- NavigationView �����-->
        <android.support.design.widget.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        app:headerLayout="@layout/nav_header_main"
        app:insetForeground="@drawable/naruto_hinata"//״̬����ɫ or ��ʾͼƬ
        app:menu="@menu/activity_main_drawer"/>
</android.support.v4.widget.DrawerLayout>

//item����¼�
navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mDrawerLayout.closeDrawers();//V4���໬�ر�
        //menuItem.setChecked(true);//menu����ѡ��
        getSupportActionBar().setTitle(menuItem.getTitle());//���ñ���
        return true;//true to display the item as the selected item
    }
});