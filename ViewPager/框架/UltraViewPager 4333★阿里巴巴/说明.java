https://github.com/alibaba/UltraViewPager
https://github.com/alibaba/UltraViewPager/blob/master/README-ch.md

UltraViewPager��һ����װ�������Ե�ViewPager����Ҫ��Ϊ��ҳ���л������ṩͳһ���������

��Ҫ����

֧�ֺ��򻬶������򻬶�
֧��һ������ʾ��ҳ
֧��ѭ������
֧�ֶ�ʱ��������ʱ��ʹ��Handlerʵ��
֧������ViewPager�������
setRatio��������ʾUltraviewPager
����indicator��ֻ������ü������ԾͿ������չʾ��֧��Բ���Icon��
��������ҳ���л���Ч
��������֧��ͬʱʹ�ã�

���˼·

UltraViewPager�̳���RelativeLayout����ViewPager��indicator�������У�ͬʱUltraViewPager�ṩ��һЩViewPager���÷����Ĵ����������ճ�ʹ���ϱ��ֺ�ViewPager�޲��죬�����Ҫ����ViewPager�����з�������ͨ��getViewPager()�����õ�������ViewPager���в���.

���򻬶���ͨ����ViewPager��onInterceptTouchEvent��onTouchEvent�н����������event location��ͬʱ��������PageTransformerʵ�֣����Դ��.

ʹ�÷���

�汾��ο�mvn repository�ϵ����°汾��Ŀǰ���°汾��1.0.4�������µ� aar ���ᷢ���� jcenter �� MavenCentral �ϣ�ȷ���������������ֿ�Դ��Ȼ������aar������

//gradle
compile ('com.alibaba.android:ultraviewpager:1.0.4@aar') {
	transitive = true
}

����maven

//pom.xml in maven
<dependency>
  <groupId>com.alibaba.android</groupId>
  <artifactId>ultraviewpager</artifactId>
  <version>1.0.4</version>
  <type>aar</type>
</dependency>

��layout��ʹ��UltraViewPager: activity_pager.xml

<com.tmall.ultraviewpager.UltraViewPager
    android:id="@+id/ultra_viewpager"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_centerInParent="true"
    android:background="@android:color/darker_gray" />
���Բο����²���ʹ��UltraViewPager��

UltraViewPager ultraViewPager = (UltraViewPager)findViewById(R.id.ultra_viewpager);
ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
//UltraPagerAdapter ����view��UltraViewPager
PagerAdapter adapter = new UltraPagerAdapter(false);
ultraViewPager.setAdapter(adapter);

//����indicator��ʼ��
ultraViewPager.initIndicator();
//����indicator��ʽ
ultraViewPager.getIndicator()
    .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
    .setFocusColor(Color.GREEN)
    .setNormalColor(Color.WHITE)
    .setRadius((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
//����indicator���뷽ʽ
ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
//����indicator,�󶨵�UltraViewPager
ultraViewPager.getIndicator().build();

//�趨ҳ��ѭ������
ultraViewPager.setInfiniteLoop(true);
//�趨ҳ���Զ��л�  ���2��
ultraViewPager.setAutoScroll(2000);

//###############################################################
Api�ӿ�������ο��ĵ�:https://github.com/alibaba/UltraViewPager/blob/master/ATTRIBUTES-ch.md
