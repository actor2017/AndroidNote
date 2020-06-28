1.添加依赖:    compile 'com.android.support:design:25.1.1'//21及以上,5.0

FloatingActionButton extends ImageView 继承图片

1.可直接当成ImageView来用即可。
<android.support.design.widget.FloatingActionButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="right|bottom"
            android:src="@android:drawable/ic_dialog_email"//最好是24dp的?
            android:layout_margin="@dimen/fab_margin"默认16dp,如果不设置,有矩形的边界（未设置margin时，可以看出）
            //android:background="@drawable/selector..."可以设置selector?,同时app:backgroundTint=""属性也可以不再设置
            app:borderWidth="0dp"		内边框宽度,通常设置为0,用于解决Android5.X设备上阴影无法正常显示的问题
            //android:clickable="true"		默认能点击
            //app:backgroundTint="#ff87ffeb"	背景颜色,默认有一个背景
            //app:rippleColor="#33728dff"	按下后波纹颜色
            //app:elevation="6dp"		正常显示的阴影大小
            //app:pressedTranslationZ="12dp"	点击时显示的阴影大小
            //app:fabSize="normal"		可设置大小，normal,mini,auto（对应56dp和40dp）?
            //app:layout_anchor="@id/app_bar"   设置锚点，以哪个控件为参照点设置位置(AppBarLayout等...)
            //app:layout_anchorGravity=""	设置相对锚点的位置，值有
		bottom					↓底部
		center,center_horizontal,center_vertical在所依附的view的中心(如果view在屏幕高度发生变化,会位移)
		clip_horizontal,clip_vertical,
		end					→右边
		fill,fill_horizontal,fill_vertical,
		left					←左边
		right					→右边
		start					←左边
		top					↑顶部
            />


2.和RecyclerView配合使用()
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
30             app:layout_behavior="com.example.administrator.testfab.ScrollBehavior" />// 使用了一个我们自定义的behavior:http://www.tuicool.com/articles/u2aauiQ
		如果不加这个属性,不知道有没有效果
31     </android.support.design.widget.CoordinatorLayout>
32 
33 
34 </RelativeLayout>