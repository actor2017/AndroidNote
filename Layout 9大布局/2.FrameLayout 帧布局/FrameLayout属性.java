帧布局会一层一层往上叠, 默认会在左上角
只有1个属性: measureAllChildren ,如果=true, 即使child的visibility=gone, 也会测量子类.  默认false.  详情见源码

<FrameLayout
	android:layout_width="match_parent"
	android:measureAllChildren="false"//
	android:layout_height="100dp">

	<ImageView
		android:layout_width="30dp"
		android:layout_height="30dp"
		android:layout_gravity="bottom|end"//在父类的什么位置, 见FrameLayout的LayoutParams
		android:src="@mipmap/ic_launcher"/>
</FrameLayout>
