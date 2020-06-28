https://github.com/RuffianZhong/RWidgetHelper (�����Բü�VideoView)
Android UI ���ٿ�����ר��ԭ���ؼ����ֲ���

compile 'com.ruffian.library:RWidgetHelper:1.1.0'

1.�ؼ�
RTextView
REditText
RLinearLayout / RRelativeLayout / RFrameLayout / RView
RRadioButton / RCheckBox  {��ҪĬ��ѡ��/δѡ��ͼ��: android:button="@null"}
RImageView

2.��������
����		����ֵ					����State״̬		����
Բ��		��ɫ					Ĭ��/����/������	����Բ��/��������Բ��
�߿���	��ֵ					Ĭ��/����/������	ʵ��/���߱߿�
�߿���ɫ	��ɫ					Ĭ��/����/������	ʵ��/���߱߿�
����		��ɫ/��ɫ����/drawable	Ĭ��/����/������	��ɫ/����/Drawable

3.���Խ���
corner_radius				Բ��	����	>=�����ο��/2ʵ��Բ��
corner_radius_top_left		Բ��	����
corner_radius_top_right		Բ��	����
corner_radius_bottom_left	Բ��	����
corner_radius_bottom_right	Բ��	����
border_dash_width			���߱߿� ���
border_dash_gap				���߱߿� ���
border_width_normal			�߿��� Ĭ��
border_width_pressed		�߿��� ����
border_width_unable			�߿��� ���ɵ��
border_width_checked		�߿��� ѡ��
border_color_normal			�߿���ɫ Ĭ��
border_color_pressed		�߿���ɫ ����
border_color_unable			�߿���ɫ ���ɵ��
border_color_checked		�߿���ɫ ѡ��
background_normal			���� Ĭ��(#555555/color/drawable/mipmap/@array/array_b_t)
background_pressed			����	����(TextView��������android:clickable="true", EditText������������Ժ�û�й��)
background_unable			����	���ɵ��
background_checked			����	ѡ��
gradient_orientation		����ķ���	�ο� GradientDrawable.Orientation:TOP_BOTTOM��TR_BL...
gradient_type				�������ʽ	linear���ԣ�radial����sweepɨ��ʽ	Ĭ�ϣ�linear
gradient_radius				����뾶	Ĭ��:��������Сֵ��/ 2
gradient_centerX			�������ĵ�X���꣨0.0-1.0��	0.5��ʾ�м�	Ĭ��:0.5
gradient_centerY			�������ĵ�Y���꣨0.0-1.0��	0.5��ʾ�м�	Ĭ��:0.5

<!--�µ���, �ɶ����ɫ, >=2-->
arrays.xml
<array name="array_b_t">
	<item>#D9AFD9</item>
	<item>#97D9E1</item>
</array>

<!--�ʺ���ɫֵ-->
<array name="rainbow_color">
	<item>@color/purple</item>
	<item>@color/blue</item>
	<item>@color/navy</item>
	<item>@color/green</item>
	<item>@color/yellow</item>
	<item>@color/orange</item>
	<item>@color/red</item>
</array>

4.ʾ��java
	        RView view = (RView) findViewById(R.id.view);
	        //��ȡHelper
	        RBaseHelper helper = view.getHelper();
	        helper.setBackgroundColorNormal(getResources().getColor(R.color.blue))
	                .setBorderColorNormal(getResources().getColor(R.color.red))
	                .setBorderWidthNormal(12)
	                .setCornerRadius(25);


5.RTextView
 drawableLeft/Right/Top/Bottom icon��С
 drawableLeft/Right/Top/Bottom icon״̬
 drawableLeft �� text һ�����
 ���ָ���״̬��ɫ Ĭ��/����/���ɵ��
 ����������ʽ

text_color_normal	������ɫ Ĭ��
text_color_pressed	������ɫ ����
text_color_unable	������ɫ ���ɵ��
icon_src_normal	drawable icon Ĭ��
icon_src_pressed	drawable icon ����
icon_src_unable	drawable icon ���ɵ��
icon_height	drawable icon ��
icon_width	drawable icon ��
icon_direction	drawable icon λ��{left,top,right,bottom}
icon_with_text	ͼƬ���ı�һ���TextView����(��ʱ������Ҫ���android:gravity) true/false
android:drawablePadding="5dp"	//icon�����ּ��
text_typeface	������ʽ

6.REditText ʹ�÷����� RTextView һ��

7.RLinearLayout / RRelativeLayout / RFrameLayout / RView	�鿴: 2.��������

8.RRadioButton / RCheckBox
	�鿴: 2.��������
	�鿴 RTextView ���й���
	��ʹ��ѡ������ checked
	֧�� RTextView �Ļ������� �Զ������״̬ drawableLeft �Լ� icon���ı����е�
����					˵��
border_width_checked	�߿��� ѡ��
border_color_checked	�߿���ɫ ѡ��
background_checked		����	ѡ��
text_color_checked		������ɫ ѡ��
icon_src_normal			ͼ�� δѡ��
icon_src_checked		ͼ�� ѡ��

9.RImageView
RImageView �����ṩ��������state������ɫ
	1.Բ��ͼƬ
	2.Բ��ͼƬ
	3.ָ��ĳһ����Բ��ͼƬ
	4.�߿�
	5.����state״̬��ͼƬ Ĭ��/����/���ɵ��

����						˵��
corner_radius				Բ��	����
corner_radius_top_left		Բ��	����
corner_radius_top_right		Բ��	����
corner_radius_bottom_left	Բ��	����
corner_radius_bottom_right	Բ��	����
border_width				�߿���
border_color				�߿���ɫ
icon_src_normal	icon 		Ĭ��
icon_src_pressed			icon ����
icon_src_unable	icon 		���ɵ��
is_circle					�Ƿ�Բ��ͼƬ

