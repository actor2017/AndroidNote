
pbProgress.setProgress(progress);	//���ý���(ˮƽ����,��֪��Բ���ܲ���)
pbProgress.setMax(maxProgress);		//����������,������趨,Ĭ�������ֵ100(ˮƽ����,��֪��Բ���ܲ���)




		//ˮƽ������,2������
        <ProgressBar
            android:id="@+id/pb_progress"
            style="?android:attr/progressBarStyleHorizontal"		//ˮƽ,Ĭ��Բ��
            style="@android:style/Widget.Material.ProgressBar.Horizontal"
            android:layout_width="match_parent"
            android:layout_height="25dp"
            android:progress="50"
            android:max="100"
            android:indeterminate="true"				//���ض���/��װapk��֪������
            android:progressDrawable="@drawable/custom_progress"/>	//�Զ��屳��ͼƬ,2������


//----------------------------------------�Զ�����ת������ProgressBar-----------------
    <!--�Զ�����ת�Ľ�����,�����������������������
        indeterminateDrawable:��ȷ����drawable
        ��drawable��д��ת-->
    <ProgressBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:indeterminateDrawable="@drawable/custom_loading"/>	��:layout/�Զ�����ת������


//1.ʹ��ͼƬ custom_loading, ��ȫ��ʿ,תȦȦ:
<rotate xmlns:android="http://schemas.android.com/apk/res/android"
        android:drawable="@drawable/kprogresshud_spinner"			//����һ��ͼƬ
        android:fromDegrees="0"
        android:toDegrees="1440">
</rotate>

//2. GooglePlay, 2��ͼƬ ����תȦ
<layer-list

//3.ֱ��ʹ�� shape, ��"�ǻ����� -> Ч��1"
<shape

//4.Ҳ�����Լ�д rotate
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

//5. �� ios α��ת, ��"�Զ���Dialog(�л�֡,α��ת)"
<animation-list
