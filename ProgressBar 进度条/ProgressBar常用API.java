
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


