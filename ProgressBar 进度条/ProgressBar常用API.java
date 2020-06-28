
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


