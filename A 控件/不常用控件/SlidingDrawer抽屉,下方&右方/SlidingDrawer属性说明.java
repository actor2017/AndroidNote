<SlidingDrawer						//不能设置背景, 否则会遮盖一大片,应该在 handle&content 中设置背景
    android:layout_width="match_parent"
    android:layout_height="322dp"
	
    android:allowSingleTap="true"	//是否可通过单击handle打开或关闭. 如果是false,则用户必须通过拖动,滑动,默认true
    android:animateOnClick="true"	//当使用者点击手柄打开关闭抽屉时,是否有动画
    android:bottomOffset="0dp"		//Handle距离SlidingDrawer底部的额外距离, 把手的marginTop, 默认=0dp
    android:content="@id/content"	//抽屉里的内容,必须设置
    android:handle="@id/handle"		//把手,必须设置
    android:orientation="vertical"	//下方/右方, 默认vertical下方
    android:topOffset="0dp">		//content的marginTop, 默认=0dp
    
    <TextView
        android:id="@+id/handle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="把手"/>
    <TextView
        android:id="@+id/content"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
		android:background="@color/white"//背景
		android:clickable="true"	//如果不设置, 会造成 点击穿透
        android:text="内容"/>
</SlidingDrawer>


animateClose();//关闭时有动画
close();//即时关闭
getHandle();//获取把手
getContent();//获取内容
isMoving();//指示SlidingDrawer是否在移动
isOpened();//指示SlidingDrawer是否已全部打开
lock();//屏蔽触摸事件
unlock();//解除屏蔽触摸事件
toggle();//切换打开和关闭的抽屉SlidingDrawer

slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {//打开抽屉
	public void onDrawerOpened(){                 
	}});

slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {//关闭抽屉
	@Override  
	public void onDrawerClosed() {
	}  
}); 

slidingDrawer.setOnDrawerScrollListener(new OnDrawerScrollListener(){//滑动监听
	@Override
	public void onScrollEnded() {
	}
	@Override
	public void onScrollStarted() {
	}
});
