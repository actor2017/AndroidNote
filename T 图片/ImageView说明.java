iv.setWillNotDraw(true);//�������


<ImageView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:scaleType="centerInside"	//��������, Ĭ��?
    android��adjustViewBounds="true"	//�Ƿ񱣳�ԭͼ�ĳ����
    android:adjustViewBounds="true"	//���ֿ�߱�
    android:tint="@android:color/white"	//��ͼƬȾ�ɰ�ɫ
    android:tindMode=""			//��: ͼƬ����ɫ.html
/>


XML�������android:adjustViewBounds="true"�Ὣ���ImageView��scaleType��ΪfitCenter���������fitCenter�ᱻ���涨���scaleType���Ը��ǣ���������˵Ļ�����������Java�������ٴ���ʾ����setAdjustViewBounds(true)��

������õ�layout_width��layout_height���Ƕ�ֵ����ô����adjustViewBounds��û��Ч���ģ�ImageView��ʼ�����趨�Ķ�ֵ�Ŀ�ߡ�

������õ�layout_width��layout_height����wrap_content,��ô����adjustViewBounds��û������ģ���ΪImageView��ʼ����ͼƬӵ����ͬ�Ŀ�߱ȣ����ǲ�������ͬ�Ŀ��ֵ��ͨ������Ŵ�һЩ����

���������һ���Ƕ�ֵ��һ����wrap_content������layout_width="100px",layout_height="wrap_content"ʱ��ImageView�Ŀ�ʼ����100px������������������
��1����ͼƬ�Ŀ�С��100pxʱ��layout_height����ͼƬ�ĸ���ͬ����ͼƬ�������ţ�������ʾ��ImageView�У�ImageView�߶���ͼƬʵ�ʸ߶���ͬ��ͼƬû��ռ��ImageView��ImageView���пհס�
��2����ͼƬ�Ŀ���ڵ���100pxʱ����ʱImageView����ͼƬӵ����ͬ�Ŀ�߱ȣ����ImageView��layout_heightֵΪ��100����ͼƬ�Ŀ�߱ȡ�����ͼƬ��500X500�ģ���ôlayout_height��100��ͼƬ�����ֿ�߱����ţ�������ʾ��ImageView�У�������ȫռ��ImageView��
