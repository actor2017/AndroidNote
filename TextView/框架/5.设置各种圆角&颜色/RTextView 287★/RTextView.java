https://github.com/RuffianZhong/RTextView

基于TextView 1.直接设置selector背景2.直接设置drawableLeft大小 3.圆角，圆形，背景/边框/文字根据状态变色

1.特点
RTextView 让你从此不再编写和管理大量 selector 文件（这个太可恨了）

RTextView 改造了 drawableLeft/drawableXXX 图片的大小，从此你不在需要使用 LinearLayout + ImageView + TextView 就能够直接实现文字带图片的功能，关键还能设置icon大小

RTextView 能够直接设置各种圆角效果： 四周圆角，某一方向圆角，甚至椭圆，圆形都简单实现。 边框效果，虚线边框都是一个属性搞定

RTextView 不仅能够定义默认状态的背景，边框，连按下/点击状态通通一起搞定

RTextView 按下变色支持：背景色，边框，文字，drawableLeft/xxx （这个赞啊）


3.属性说明
属性							说明
corner_radius				圆角	四周
corner_radius_top_left		圆角	左上
corner_radius_top_right		圆角	右上
corner_radius_bottom_left	圆角	左下
corner_radius_bottom_right	圆角	右下
border_dash_width			虚线边框 宽度
border_dash_gap				虚线边框 间隔
border_width_normal			边框宽度 默认
border_width_pressed		边框宽度 按下
border_width_unable			边框宽度 不可点击
border_color_normal			边框颜色 默认
border_color_pressed		边框颜色 按下
border_color_unable			边框颜色 不可点击
background_normal			背景颜色 默认
background_pressed			背景颜色 按下
background_unable			背景颜色 不可点击
text_color_normal			文字颜色 默认
text_color_pressed			文字颜色 按下
text_color_unable			文字颜色 不可点击
icon_src_normal				drawable icon 默认
icon_src_pressed			drawable icon 按下
icon_src_unable				drawable icon 不可点击
icon_height					drawable icon 高
icon_width					drawable icon 宽
icon_direction				drawable icon 位置{left,top,right,bottom}
text_typeface				字体样式
