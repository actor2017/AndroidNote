https://github.com/RuffianZhong/RWidgetHelper (不可以裁剪VideoView)
Android UI 快速开发，专治原生控件各种不服

compile 'com.ruffian.library:RWidgetHelper:1.1.0'

1.控件
RTextView
REditText
RLinearLayout / RRelativeLayout / RFrameLayout / RView
RRadioButton / RCheckBox  {不要默认选择/未选中图标: android:button="@null"}
RImageView

2.基础功能
功能		属性值					可用State状态		特性
圆角		颜色					默认/按下/不可用	四周圆角/单个方向圆角
边框宽度	数值					默认/按下/不可用	实线/虚线边框
边框颜色	颜色					默认/按下/不可用	实线/虚线边框
背景		颜色/颜色数组/drawable	默认/按下/不可用	纯色/渐变/Drawable

3.属性介绍
corner_radius				圆角	四周	>=正方形宽度/2实现圆形
corner_radius_top_left		圆角	左上
corner_radius_top_right		圆角	右上
corner_radius_bottom_left	圆角	左下
corner_radius_bottom_right	圆角	右下
border_dash_width			虚线边框 宽度
border_dash_gap				虚线边框 间隔
border_width_normal			边框宽度 默认
border_width_pressed		边框宽度 按下
border_width_unable			边框宽度 不可点击
border_width_checked		边框宽度 选中
border_color_normal			边框颜色 默认
border_color_pressed		边框颜色 按下
border_color_unable			边框颜色 不可点击
border_color_checked		边框颜色 选中
background_normal			背景 默认(#555555/color/drawable/mipmap/@array/array_b_t)
background_pressed			背景	按下(TextView必须设置android:clickable="true", EditText设置了这个属性后没有光标)
background_unable			背景	不可点击
background_checked			背景	选中
gradient_orientation		渐变的方向	参考 GradientDrawable.Orientation:TOP_BOTTOM，TR_BL...
gradient_type				渐变的样式	linear线性，radial径向，sweep扫描式	默认：linear
gradient_radius				渐变半径	默认:（宽或高最小值）/ 2
gradient_centerX			渐变中心点X坐标（0.0-1.0）	0.5表示中间	默认:0.5
gradient_centerY			渐变中心点Y坐标（0.0-1.0）	0.5表示中间	默认:0.5

<!--下到上, 可多个颜色, >=2-->
arrays.xml
<array name="array_b_t">
	<item>#D9AFD9</item>
	<item>#97D9E1</item>
</array>

<!--彩虹颜色值-->
<array name="rainbow_color">
	<item>@color/purple</item>
	<item>@color/blue</item>
	<item>@color/navy</item>
	<item>@color/green</item>
	<item>@color/yellow</item>
	<item>@color/orange</item>
	<item>@color/red</item>
</array>

4.示例java
	        RView view = (RView) findViewById(R.id.view);
	        //获取Helper
	        RBaseHelper helper = view.getHelper();
	        helper.setBackgroundColorNormal(getResources().getColor(R.color.blue))
	                .setBorderColorNormal(getResources().getColor(R.color.red))
	                .setBorderWidthNormal(12)
	                .setCornerRadius(25);


5.RTextView
 drawableLeft/Right/Top/Bottom icon大小
 drawableLeft/Right/Top/Bottom icon状态
 drawableLeft 和 text 一起居中
 文字根据状态变色 默认/按下/不可点击
 设置字体样式

text_color_normal	文字颜色 默认
text_color_pressed	文字颜色 按下
text_color_unable	文字颜色 不可点击
icon_src_normal	drawable icon 默认
icon_src_pressed	drawable icon 按下
icon_src_unable	drawable icon 不可点击
icon_height	drawable icon 高
icon_width	drawable icon 宽
icon_direction	drawable icon 位置{left,top,right,bottom}
icon_with_text	图片和文本一起居TextView中央(有时候还是需要添加android:gravity) true/false
android:drawablePadding="5dp"	//icon和文字间距
text_typeface	字体样式

6.REditText 使用方法跟 RTextView 一致

7.RLinearLayout / RRelativeLayout / RFrameLayout / RView	查看: 2.基础功能

8.RRadioButton / RCheckBox
	查看: 2.基础功能
	查看 RTextView 所有功能
	常使用选择属性 checked
	支持 RTextView 的基础功能 自定义各个状态 drawableLeft 以及 icon与文本居中等
属性					说明
border_width_checked	边框宽度 选中
border_color_checked	边框颜色 选中
background_checked		背景	选中
text_color_checked		文字颜色 选中
icon_src_normal			图标 未选中
icon_src_checked		图标 选中

9.RImageView
RImageView 不再提供背景各个state背景颜色
	1.圆形图片
	2.圆角图片
	3.指定某一方向圆角图片
	4.边框
	5.各个state状态的图片 默认/按下/不可点击

属性						说明
corner_radius				圆角	四周
corner_radius_top_left		圆角	左上
corner_radius_top_right		圆角	右上
corner_radius_bottom_left	圆角	左下
corner_radius_bottom_right	圆角	右下
border_width				边框宽度
border_color				边框颜色
icon_src_normal	icon 		默认
icon_src_pressed			icon 按下
icon_src_unable	icon 		不可点击
is_circle					是否圆形图片

