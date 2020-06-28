DrawerLayout���ַ������֣���һ�û����ݣ����Ƿǲ˵����֡��ڶ��ǲ˵���

DrawerLayout�������ɵ�ʵ�ֳ���Ч������DrawerLayout�У���һ����View��������ʾ���ݵ�view��
�ڶ���view�ǳ���view,��������layout_gravity=��left|right��,��ʾ�Ǵ���߻��������ұ߻�����

<?xml version="1.0" encoding="utf-8"?>
<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
	android:id="@+id/simple_navigation_drawer"
	tools:openDrawer="start">				//�򿪲����

	<!--������ͼ, ��һ����include, ����д�ɸ��� Layout, fragment ...��-->
	<include
		android:id="@+id/tv_content"
		layout="@layout/drawer_content_layout"//�Զ��岼��
		android:layout_width="match_parent"
		android:layout_height="match_parent" />

	<!--��໬�˵���, ��һ����include, ����дfragment, �� RecyclerView ...��-->
	<include
		layout="@layout/drawer_menu_layout"	//�Զ��岼��
		android:layout_width="match_parent"	//Ĭ�ϻ���һ��marginRight, DrawerLayout�� "MIN_DRAWER_MARGIN" �ֶ� = 64dp
		android:layout_height="match_parent"
		android:layout_gravity="start"		//��Ҫ���������
        android:layout_marginRight="-65dp" />//����뻮������ȫ��, ����������Լ���

	<!--�Ҳ໬�˵���-->
	<include
		layout="@layout/drawer_menu_layout"
		android:layout_width="250dp"		//Ҳ���԰ѿ��д��һ���̶���ֵ
		android:layout_height="match_parent"
		android:layout_gravity="end" />
</android.support.v4.widget.DrawerLayout>

//���� & �ر�״̬(���ܻ�����, �ܻ����ر�, �ܴ����/�ر�)
drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

drawerLayout.openDrawer(Gravity.START);	//��
drawerLayout.isDrawerOpen(Gravity.START);//����Ƿ��
drawerLayout.closeDrawer(Gravity.START);
drawerLayout.closeDrawers();			//�ر�


2.���û����߾�:
1.����һ��marginRight, ����:
android:layout_marginRight="-65dp"	//ȫ��

2.����
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

