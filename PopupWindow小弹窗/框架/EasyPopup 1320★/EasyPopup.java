https://github.com/zyyoona7/EasyPopup

PopupWindow Wrapper. 对 PopupWindow 的封装。可指定相对于 anchor view 各个方位弹出，设置背景变暗，指定 ViewGroup 背景变暗等特性。

项目特性
链式调用：除了在传统的 PopupWindow 使用方法之外还加入了更多的方法
带有相对于 AnchorView 的各个方位弹出的方法，弹出 PopupWindow 更轻松、更简单
支持 PopupWindow 弹出时背景变暗、指定 ViewGroup 背景变暗、设置变暗颜色等 (API>=18)
加入了简单的生命周期方法，自定义 PopupWindow、处理逻辑更方便、更清晰

maven { url 'https://jitpack.io' }
//最新的VERSION_CODE: https://github.com/zyyoona7/EasyPopup/releases
implementation 'com.github.zyyoona7:EasyPopup:VERSION_CODE'//1.1.2//popup,可以有dialog样式

1.创建 EasyPopup 对象
private EasyPopup easyPopup;
easyPopup = EasyPopup.create()
        .setContentView(this, R.layout.layout_circle_comment)
        .setAnimationStyle(R.style.RightPopAnim)
  	    //是否允许点击PopupWindow之外的地方消失,默认true.如果设置为false点击之外的地方不会消失，但是会响应返回按钮事件
        .setFocusAndOutsideEnable(true)
		//是否允许背景变暗,默认false,只支持 4.2 以上的版本
		.setBackgroundDimEnable(false)
		//变暗的透明度(0-1),默认0.7. 0为完全透明
		.setDimValue(0.7f)
		//变暗的背景颜色
		.setDimColor(Color.YELLOW)
		//指定哪个ViewGroup背景变暗,可不设置
		.setDimView(viewGroup)
        .apply();

2.初始化View
TextView tvZan=easyPopup.findViewById(R.id.tv_zan);
TextView tvComment=easyPopup.findViewById(R.id.tv_comment);
tvZan.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ToastUtils.showShort("赞");
        easyPopup.dismiss();
    }
});
tvComment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ToastUtils.showShort("评论");
        easyPopup.dismiss();
    }
});

3.显示
/**
 * 相对anchor view显示，适用 宽高不为match_parent
 * @param view 相对于哪个view显示
 * @param anchor
 * @param yGravity  垂直方向的对齐方式
 * @param xGravity  水平方向的对齐方式
 * @param x            水平方向的偏移
 * @param y            垂直方向的偏移
 */
easyPopup.showAtAnchorView(view, YGravity.CENTER, XGravity.LEFT, 0, 0);
easyPopup.showAsDropDown();
easyPopup.showAtLocation();

4.方位注解介绍
4.1.垂直方向对齐：YGravity
YGravity.CENTER,//垂直居中
YGravity.ABOVE,//anchor view之上
YGravity.BELOW,//anchor view之下
YGravity.ALIGN_TOP,//与anchor view顶部对齐
YGravity.ALIGN_BOTTOM,//pop的底部与anchor view底部对齐

4.2.水平方向对齐：XGravity
XGravity.CENTER,//水平居中
XGravity.LEFT,//anchor view左侧
XGravity.RIGHT,//anchor view右侧
XGravity.ALIGN_LEFT,//与anchor view左边对齐
XGravity.ALIGN_RIGHT,//与anchor view右边对齐

5.自定义 PopupWindow
EasyPopup中自定义了三个生命周期：

6.其他方法介绍
方法名														作用						备注
setContentView(View contentView)							设置 contentView
setContentView(@LayoutRes int layoutId)
setContentView(Context context, @LayoutRes int layoutId)
setContentView(View contentView, int width, int height)
setContentView(@LayoutRes int layoutId, int width, int height)
setContentView(Context context, @LayoutRes int layoutId, int width, int height)//用这个
setWidth(int width)											设置宽	
setHeight(int height)										设置高	
setAnchorView(View view)									设置目标 view	
setYGravity(@YGravity int yGravity)							设置垂直方向对齐	
setXGravity(@XGravity int xGravity)							设置水平方向对齐	
setOffsetX(int offsetX)										设置水平偏移	
setOffsetY(int offsetY)										设置垂直	
setAnimationStyle(@StyleRes int animationStyle)				设置动画风格	
getContentView()											获取PopupWindow中加载的view	@Nullable
getContext()												获取context					@Nullable
getPopupWindow()											获取PopupWindow对象			@Nullable
dismiss()	消失	

