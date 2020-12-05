
pbProgress.setProgress(progress);	//设置进度(水平进度,不知道圆形能不能)
pbProgress.setMax(maxProgress);		//设置最大进度,如果不设定,默认是最大值100(水平进度,不知道圆形能不能)




		//水平进度条,2个进度
        <ProgressBar
            android:id="@+id/pb_progress"
            style="?android:attr/progressBarStyleHorizontal"		//水平,默认圆环
            style="@android:style/Widget.Material.ProgressBar.Horizontal"
            android:layout_width="match_parent"
            android:layout_height="25dp"
            android:progress="50"
            android:max="100"
            android:indeterminate="true"				//下载东西/安装apk不知道进度
            android:progressDrawable="@drawable/custom_progress"/>	//自定义背景图片,2个进度


//----------------------------------------自定义旋转进度条ProgressBar-----------------
    <!--自定义旋转的进度条,★★★★★★★★★★★★★★★★★★★★★★
        indeterminateDrawable:不确定的drawable
        在drawable里写旋转-->
    <ProgressBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:indeterminateDrawable="@drawable/custom_loading"/>	见:layout/自定义旋转进度条


//1.使用图片 custom_loading, 安全卫士,转圈圈:
<rotate xmlns:android="http://schemas.android.com/apk/res/android"
        android:drawable="@drawable/kprogresshud_spinner"			//这是一张图片
        android:fromDegrees="0"
        android:toDegrees="1440">
</rotate>

//2. GooglePlay, 2张图片 反向转圈
<layer-list

//3.直接使用 shape, 见"智慧西安 -> 效果1"
<shape

//4.也可以自己写 rotate
<rotate xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromDegrees="0"
    android:pivotX="50%"
    android:pivotY="50%"
    android:toDegrees="360">

    <shape
        android:innerRadiusRatio="3"
        android:shape="ring"
        android:thicknessRatio="8"
        android:useLevel="false">

        <gradient
            android:centerColor="#6699FF"
            android:centerY="0.50"
            android:endColor="#3399FF"
            android:startColor="#3366FF"
            android:type="sweep"
            android:useLevel="false" />
    </shape>
</rotate>

//5. 放 ios 伪旋转, 见"自定义Dialog(切换帧,伪旋转)"
<animation-list
