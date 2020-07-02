compile 'com.android.support:cardview-v7:25.2.0'

CardView extends FrameLayout

setElevation��֧��api21(5.0)���µ��ֻ�,Java������ʹ��setCardElevation(int elevation)������(û�Թ�)��

<!--
	app:cardBackgroundColor				//����ɫ
	app:cardCornerRadius			//Բ�Ǵ�С
	app:cardElevation				//z�����Ӱ,���Ӧ����gridview�з��ּ������,�ɰ����ֵ��Сһ��.
	app:cardMaxElevation			//z������߶�ֵ
	app:cardUseCompatPadding="true"	//�Ƿ�ʹ��CompatPadding �����ڱ߾�,5.0���µ�Ĭ����true,��5.0��Ĭ��false,���ܿ�����Ӱ(���ֻ�������������Իᵼ�¿�ƬЧ������ʧ��������ҫ6��6.0ϵͳ��)
	app:cardPreventCornerOverlap="false"//��ֹԲ���ص�, �Ƿ�ü��߽��Է�ֹ�ص�. ���=false, ��һ��Ĭ���ڱ߾�
	app:contentPadding				//�������ݵ�padding
	app:contentPaddingLeft			//�������ݵ���padding
	app:contentPaddingTop			//�������ݵ���padding
	app:contentPaddingRight			//�������ݵ���padding
	app:contentPaddingBottom		//�������ݵĵ�padding
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
 1����ͨʹ��Ч��
<android.support.v7.widget.CardView  
        android:layout_width="match_parent"  
        android:layout_height="wrap_content">  
        <TextView  
            android:layout_width="match_parent"  
            android:layout_height="70dp"  
            android:text="����ʹ��Ч��"  
            android:gravity="center_horizontal|center_vertical"  
            android:textColor="#000000"  
            android:textSize="20sp"  
            android:padding="10dp"  
            android:layout_margin="10dp"/>  
    </android.support.v7.widget.CardView> 

2�����ӱ���ɫ��Բ�ǵ�Ч����ע�����Ǵ�ʱʹ��background������ûЧ���ģ�
<android.support.v7.widget.CardView  
        android:layout_width="match_parent"  
        android:layout_height="wrap_content"  
        app:cardBackgroundColor="#669900"  
        app:cardCornerRadius="10dp">  
        ...  
    </android.support.v7.widget.CardView>  

3������z����Ӱ
<android.support.v7.widget.CardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardBackgroundColor="#669900"
    app:cardElevation="20dp"
    app:cardCornerRadius="10dp">
    ...
</android.support.v7.widget.CardView>