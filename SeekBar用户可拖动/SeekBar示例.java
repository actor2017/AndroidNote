<android.support.v7.widget.AppCompatSeekBar
    android:id="@+id/sb_seekbar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:max="100"				�������ֵ,������ֵС��,����������������Ч��
    android:thumb=��@drawable/ic_launcher��	����,��д��selector
    //android:thumbOffset="-5dp"	�϶�ͼ����SeekBar���ߵ�ƫ����,����ƫ�Ƶ�SeekBar�������ȥ
    android:progress="50"			����seekbar�Ľ��ȣ���Χ0��max֮��.
    android:secondaryProgress="55"	���õڶ����ȣ�ͨ��������ʾ��Ƶ�ȵĻ���Ч��
    android:maxHeight="8dp"			�������progress�ĸ߶�,���ǻ���ĸ߶ȡ��
    android:minHeight="8dp"
    android:progressDrawable="@drawable/xxx"���ý�������ͼ��/��ɫ,��ʾ��
	android:splitTrack="false"		//����͸�������Ƿ��ܿ����ײ�(Ĭ��true,��͸��,���ÿ�)
    android:indeterminateDrawable="@android:drawable/progress_indeterminate_horizontal"//����Ū������,��Դ����Ѱٶ�
    android:indeterminateOnly="false"				//��ȷ������,�������ض�����ʱ��û�н��Ȼص�,һֱ�и�����������
    android:layout_margin="5dp"/>

progress_indeterminate_horizontal.xml(ϵͳԴ��)
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
        //fromUser��true ��ʾ�û���ק��false����ʾseekBar�Լ��ڼ���
        if(fromUser) progressBar.setProgress(i);
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Toast.makeText(MainActivity.this,"��ʼ��ק",Toast.LENGTH_SHORT).show();
    }
     @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Toast.makeText(MainActivity.this,"������ק",Toast.LENGTH_SHORT).show();
    }
});

���������������
1��Seekbar����������ʾ��ȫ������ thumboffset =0�Ϳ����ˡ�
2��Seekbar�м�ԲȦ������ʾ��ȫ���������Seekbar�ĸ߶����ó�����Ӧ�Ϳ�����
