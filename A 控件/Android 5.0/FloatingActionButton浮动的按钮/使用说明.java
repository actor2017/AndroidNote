1.�������:    compile 'com.android.support:design:25.1.1'//21������,5.0

FloatingActionButton extends ImageView �̳�ͼƬ

1.��ֱ�ӵ���ImageView���ü��ɡ�
<android.support.design.widget.FloatingActionButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="right|bottom"
            android:src="@android:drawable/ic_dialog_email"//�����24dp��?
            android:layout_margin="@dimen/fab_margin"Ĭ��16dp,���������,�о��εı߽磨δ����marginʱ�����Կ�����
            //android:background="@drawable/selector..."��������selector?,ͬʱapp:backgroundTint=""����Ҳ���Բ�������
            app:borderWidth="0dp"		�ڱ߿���,ͨ������Ϊ0,���ڽ��Android5.X�豸����Ӱ�޷�������ʾ������
            //android:clickable="true"		Ĭ���ܵ��
            //app:backgroundTint="#ff87ffeb"	������ɫ,Ĭ����һ������
            //app:rippleColor="#33728dff"	���º�����ɫ
            //app:elevation="6dp"		������ʾ����Ӱ��С
            //app:pressedTranslationZ="12dp"	���ʱ��ʾ����Ӱ��С
            //app:fabSize="normal"		�����ô�С��normal,mini,auto����Ӧ56dp��40dp��?
            //app:layout_anchor="@id/app_bar"   ����ê�㣬���ĸ��ؼ�Ϊ���յ�����λ��(AppBarLayout��...)
            //app:layout_anchorGravity=""	�������ê���λ�ã�ֵ��
		bottom					���ײ�
		center,center_horizontal,center_vertical����������view������(���view����Ļ�߶ȷ����仯,��λ��)
		clip_horizontal,clip_vertical,
		end					���ұ�
		fill,fill_horizontal,fill_vertical,
		left					�����
		right					���ұ�
		start					�����
		top					������
            />


2.��RecyclerView���ʹ��()
 1 <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
 2     xmlns:app="http://schemas.android.com/apk/res-auto"
 3     xmlns:tools="http://schemas.android.com/tools"
 4     android:layout_width="match_parent"
 5     android:layout_height="match_parent"
 6     android:paddingBottom="@dimen/activity_vertical_margin"
 7     android:paddingLeft="@dimen/activity_horizontal_margin"
 8     android:paddingRight="@dimen/activity_horizontal_margin"
 9     android:paddingTop="@dimen/activity_vertical_margin"
10     tools:context=".MainActivity">
11 
12     <android.support.design.widget.CoordinatorLayout
13         android:layout_width="match_parent"
14         android:layout_height="match_parent">
15 
16         <android.support.v7.widget.RecyclerView
17             android:id="@+id/recyclerView"
18             android:layout_width="match_parent"
19             android:layout_height="match_parent">
20 
21         </android.support.v7.widget.RecyclerView>
22 
23         <android.support.design.widget.FloatingActionButton
24             android:layout_width="wrap_content"
25             android:layout_height="wrap_content"
26             android:layout_gravity="bottom|right"
27             android:layout_margin="16dp"
28             app:layout_anchor="@id/recyclerView"
29             app:layout_anchorGravity="bottom|right|end"
30             app:layout_behavior="com.example.administrator.testfab.ScrollBehavior" />// ʹ����һ�������Զ����behavior:http://www.tuicool.com/articles/u2aauiQ
		��������������,��֪����û��Ч��
31     </android.support.design.widget.CoordinatorLayout>
32 
33 
34 </RelativeLayout>