https://github.com/bingoogolapple/BGAPhotoPicker-Android

�� MeiqiaSDK-Android ���ͼ�ⵥ���������Դ�������е� GridView��ListView �� RelativeLayout ���� RecyclerView �� Toolbar���������Ժ����Ŀ��ֱ������ʹ�á�Demo ��ģ����΢������Ȧ�Ĳ��ֹ��ܣ���ϸ�÷���鿴 Demo��ϣ���ܸÿ���������⼸�����ܵ�Գ�ѽ�ʡ����ʱ�䡣

 ��ͼѡ��
 ��ͼѡ��
 ����ѡ��
 ͼƬѡ��Ԥ����֧��΢����ͼ�������Ų鿴
 ͼƬԤ����֧��΢����ͼ�������Ų鿴
 ֧�� glide��picasso��universal-image-loader��xutils ͼƬ���ؿ�
 ֧�������б����ʱ�Ƿ���ͣ����ͼƬ���б�ֹͣ����ʱ�ָ�����ͼƬ(�� xutils ��ΪͼƬ���ؿ�ʱ��������Ч)
 �����Ρ�Բ��ͷ�񡢴��߿��Բ��ͷ��ؼ�
 ����Ȧ�б����ľŹ���ͼƬ�ؼ�
 ��������Ȧ����Ŀ���ק����ľŹ���ͼƬ�ؼ�
 ������Ӧ����Դ�ļ������ƽ���

1.��� Gradle ����
Download bga-photopicker ����ġ�latestVersion��ָ���������� Download ���º���ġ����֡����������滻���벻Ҫ�������ҡ�latestVersion����ʲô��

������Ҫ֧��΢����ͼԤ�����ÿ����Ѿ������� PhotoView ��Դ�벢�������޸ģ����������Ŀ�оͲ�Ҫ���ظ����� PhotoView ��

dependencies {
    // -------------------- ����4�����Ǳ��������� ----------------------------
    implementation 'cn.bingoogolapple:bga-photopicker:latestVersion@aar'
    implementation 'com.android.support:appcompat-v7:27.0.1'
    implementation 'com.android.support:support-v4:27.0.1'
    implementation 'com.android.support:recyclerview-v7:27.0.1'
    implementation 'cn.bingoogolapple:bga-baseadapter:1.2.7@aar'
    // -------------------- ����4�����Ǳ��������� ----------------------------

    // Ŀǰ֧�ֳ����� 4 ��ͼƬ���ؿ⣬�����������ĸ�ͼƬ���ؿ���ѡ��һ���������
    implementation 'com.github.bumptech.glide:glide:4.3.1'
//    implementation 'com.squareup.picasso:picasso:2.5.2'
//    implementation 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
//    implementation 'org.xutils:xutils:3.5.0'
}

//�Ź���
<cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout
    android:id="@+id/bga_nine_photo"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginTop="18dp"
    android:layout_marginRight="27dp"
    app:bga_npl_itemCornerRadius="3dp"	//Item ��ĿԲ�ǳߴ磬Ĭ��ֵΪ 0dp
    app:bga_npl_itemSpanCount="3"		//����,Ĭ��ֵΪ 3������ֵ���� 3 ��������Դ��ֻ������ͼƬʱ������ʾ�� 2 ��
    app:bga_npl_itemWhiteSpacing="8dp"	//Item ���ˮƽ�ʹ�ֱ��࣬Ĭ��ֵΪ 4dp
    app:bga_npl_itemWidth="0dp"			//item �ĳߴ磬���ȼ����� bga_npl_otherWhiteSpacing��Ĭ��ֵΪ 0dp
	app:bga_npl_otherWhiteSpacing="54dp"//��ȥ�Ź��񲿷ֵĿհ�����ĳߴ磬Ĭ��ֵΪ 100dp(����,�����ұ߻����)
    app:bga_npl_placeholderDrawable="@mipmap/bga_pp_ic_holder_light"//ռλͼ��Դ��Ĭ��ֵΪ R.mipmap.bga_pp_ic_holder_light
	app:bga_npl_showAsLargeWhenOnlyOne="true">//��ֻ��һ��ͼƬʱ���Ƿ���ʾ�ɴ�ͼ��Ĭ��ֵΪ true
</cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout>

//�������:
java.lang.NoSuchMethodError: No virtual method placeholder(I)Lcom/bumptech/glide/request/RequestOptions; in class Lcom/bumptech/glide/request/RequestOptions; or its super classes (declaration of 'com.bumptech.glide.request.RequestOptions' appears in /data/app/com.ly.hihifriend-1/split_lib_dependencies_apk.apk)

��ʹ��������ǰ��ʼ��:
BGAImage.setImageLoader(new MyBGAImageLoader());//Glide���ر���,�Զ������
