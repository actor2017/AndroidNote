compile 'com.android.support:cardview-v7:25.2.0'

CardView extends FrameLayout

setElevation不支持api21(5.0)以下的手机,Java代码里使用setCardElevation(int elevation)来设置(没试过)。

<!--
	app:cardBackgroundColor				//背景色
	app:cardCornerRadius			//圆角大小
	app:cardElevation				//z轴的阴影,如果应用在gridview中发现间距变大了,可把这个值设小一点.
	app:cardMaxElevation			//z轴的最大高度值
	app:cardUseCompatPadding="true"	//是否使用CompatPadding 设置内边距,5.0以下的默认是true,在5.0中默认false,不能看到阴影(部分机器不开这个属性会导致卡片效果“消失”，如荣耀6（6.0系统）)
	app:cardPreventCornerOverlap="false"//防止圆角重叠, 是否裁剪边界以防止重叠. 如果=false, 有一个默认内边距
	app:contentPadding				//设置内容的padding
	app:contentPaddingLeft			//设置内容的左padding
	app:contentPaddingTop			//设置内容的上padding
	app:contentPaddingRight			//设置内容的右padding
	app:contentPaddingBottom		//设置内容的底padding
-->

<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="10dp"
    android:background="@color/gray">

    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        app:cardBackgroundColor="@color/blue"
        app:cardCornerRadius="16dp"
        app:cardElevation="16dp">

        <TextView
            android:id="@+id/id_num"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_margin="10dp"
            android:gravity="center"
            android:textColor="@color/white"
            android:textSize="20sp" />
    </android.support.v7.widget.CardView>
</FrameLayout>

//==================================================
 1、普通使用效果
<android.support.v7.widget.CardView  
        android:layout_width="match_parent"  
        android:layout_height="wrap_content">  
        <TextView  
            android:layout_width="match_parent"  
            android:layout_height="70dp"  
            android:text="正常使用效果"  
            android:gravity="center_horizontal|center_vertical"  
            android:textColor="#000000"  
            android:textSize="20sp"  
            android:padding="10dp"  
            android:layout_margin="10dp"/>  
    </android.support.v7.widget.CardView> 

2、增加背景色和圆角的效果，注意我们此时使用background属性是没效果的：
<android.support.v7.widget.CardView  
        android:layout_width="match_parent"  
        android:layout_height="wrap_content"  
        app:cardBackgroundColor="#669900"  
        app:cardCornerRadius="10dp">  
        ...  
    </android.support.v7.widget.CardView>  

3、设置z轴阴影
<android.support.v7.widget.CardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardBackgroundColor="#669900"
    app:cardElevation="20dp"
    app:cardCornerRadius="10dp">
    ...
</android.support.v7.widget.CardView>