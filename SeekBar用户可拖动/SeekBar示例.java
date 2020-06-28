<android.support.v7.widget.AppCompatSeekBar
    android:id="@+id/sb_seekbar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:max="100"				设置最大值,如果这个值小了,滑动会有明显跳动效果
    android:thumb=”@drawable/ic_launcher”	滑块,可写成selector
    //android:thumbOffset="-5dp"	拖动图标向SeekBar两边的偏移量,可以偏移到SeekBar左右外侧去
    android:progress="50"			设置seekbar的进度，范围0到max之间.
    android:secondaryProgress="55"	设置第二进度，通常用做显示视频等的缓冲效果
    android:maxHeight="8dp"			★★设置progress的高度,不是滑块的高度★★
    android:minHeight="8dp"
    android:progressDrawable="@drawable/xxx"设置进度条的图样/颜色,见示例
	android:splitTrack="false"		//决定透明滑块是否能看见底部(默认true,不透明,不好看)
    android:indeterminateDrawable="@android:drawable/progress_indeterminate_horizontal"//可以弄个动画,点源码或搜百度
    android:indeterminateOnly="false"				//不确定进度,比如下载东西的时候没有进度回调,一直有个条条左右跑
    android:layout_margin="5dp"/>

progress_indeterminate_horizontal.xml(系统源码)
<animation-list
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:oneshot="false">
    <item android:drawable="@drawable/progressbar_indeterminate1" android:duration="200" />
    <item android:drawable="@drawable/progressbar_indeterminate2" android:duration="200" />
    <item android:drawable="@drawable/progressbar_indeterminate3" android:duration="200" />
</animation-list>


seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
        //fromUser：true 表示用户拖拽，false：表示seekBar自己在加载
        if(fromUser) progressBar.setProgress(i);
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Toast.makeText(MainActivity.this,"开始拖拽",Toast.LENGTH_SHORT).show();
    }
     @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Toast.makeText(MainActivity.this,"结束拖拽",Toast.LENGTH_SHORT).show();
    }
});

可能遇到的情况：
1、Seekbar滑块左右显示不全。设置 thumboffset =0就可以了。
2、Seekbar中间圆圈上下显示不全。这里你把Seekbar的高度设置成自适应就可以了
