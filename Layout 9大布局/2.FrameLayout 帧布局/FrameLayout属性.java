֡���ֻ�һ��һ�����ϵ�, Ĭ�ϻ������Ͻ�
ֻ��1������: measureAllChildren ,���=true, ��ʹchild��visibility=gone, Ҳ���������.  Ĭ��false.  �����Դ��

<FrameLayout
	android:layout_width="match_parent"
	android:measureAllChildren="false"//
	android:layout_height="100dp">

	<ImageView
		android:layout_width="30dp"
		android:layout_height="30dp"
		android:layout_gravity="bottom|end"//�ڸ����ʲôλ��, ��FrameLayout��LayoutParams
		android:src="@mipmap/ic_launcher"/>
</FrameLayout>
