https://github.com/lygttpod/SuperTextView

1.SuperTextView是一个功能强大的View，可以满足日常大部分布局样式，开发者可已自行组合属性配置出属于自己风格的样式!
2.SuperButton拥有shape文件的大部分属性，从此写shape属性变得非常简单.
3.CommonTextView只是SuperTextView的逻辑简化，其实功能并不差少哦，有兴趣的可以看看

4.先在项目根目录的 build.gradle 的 repositories 添加:
allprojects{
	repositories{
		maven{url"https://jitpack.io"}
	}
}
5.然后在dependencies添加:
dependencies{
	compile'com.github.lygttpod:SuperTextView:1.1.2'
}


3.2.1、布局中如何使用
<com.allen.library.SuperTextView
	android:layout_width="match_parent"
	android:layout_height="80dp"

	1.背景颜色 间距 水波纹
	stv:sBackgroundDrawableRes 								//reference SuperTextView的背景资源
	stv:sCenterSpaceHeight="5dp"							//dimension 上中下三行文字的间距 默认5dp
	stv:sUseRipple="true" 									//boolean	SuperTextView是否开启点击出现水波效果 默认 true
	stv:sLeftViewGravity="center"                       	//enum		左边View(不是指TextView)对齐方式,left_center, center, right_center 默认center
	stv:sCenterViewGravity="center"                     	//enum
	stv:sRightViewGravity="center"                      	//enum
	
	2.左中右3大块margin
	stv:sLeftViewMarginLeft="10dp"                          //dimension 左边view的MarginLeft 默认10dp
    stv:sLeftViewMarginRight="10dp"                         //dimension 左边view的MarginRight 默认10dp
    stv:sCenterViewMarginLeft="10dp"                        //dimension 中间view的MarginLeft 默认10dp
    stv:sCenterViewMarginRight="10dp"                       //dimension 中间view的MarginRight 默认10dp
    stv:sRightViewMarginLeft="10dp"                         //dimension 右边view的MarginLeft 默认10dp
    stv:sRightViewMarginRight="10dp"                        //dimension 右边view的MarginRight 默认10dp

	2.分割线
	stv:sDividerLineColor="#FFE8E8E8"                       //color 	分割线的颜色 默认0xFFE8E8E8
    stv:sDividerLineHeight="0.5dp"                          //dimension 分割线的高度 默认0.5dp
    stv:sDividerLineType="none|top|bottom|both"             //enum  	分割线显示方式,默认bottom
	stv:sTopDividerLineMarginLR="0dp"                  		//dimension 上边分割线的MarginLeft和MarginRight 默认0dp
    stv:sTopDividerLineMarginLeft="0dp"                  	//dimension 上边分割线的MarginLeft 默认0dp
    stv:sTopDividerLineMarginRight="0dp"                  	//dimension 上边分割线的MarginRight 默认0dp
    stv:sBottomDividerLineMarginLR="0dp"                  	//dimension 下边分割线的MarginLeft和MarginRigh 默认0dp
    stv:sBottomDividerLineMarginLeft="0dp"                  //dimension 下边分割线的MarginLeft 默认0dp
    stv:sBottomDividerLineMarginRight="0dp"                 //dimension 下边分割线的MarginRight 默认0dp


	3.TextView
	stv:sTextViewDrawablePadding                        	//dimension TextView的drawable对应的Padding 默认10dp
    stv:sLeftViewWidth                                  	//dimension 左边textView的宽度 为了中间文字左对齐的时候使用

	//3.1.setText
	stv:sLeftTextString="←"                      			//string	左边文字
	stv:sLeftTopTextString="↖"                       		//string	左上文字
	stv:sLeftBottomTextString="↙"                        	//string	左下文字
	stv:sCenterTextString="|"                            	//string	中间文字
	stv:sCenterTopTextString="↑"                        	//string	中上文字
	stv:sCenterBottomTextString="↓"                         //string	中下文字
	stv:sRightTextString="→"                            	//string	右边文字
	stv:sRightTopTextString="↗"                        	//string	右上文字
	stv:sRightBottomTextString="↘"                         //string		右下文字

	//3.2.textColor
	stv:sLeftTextColor="#FF373737"                        	//color		左边文字颜色	默认0xFF373737
	stv:sLeftTopTextColor="#FF373737"                    	//color		左上文字颜色	默认0xFF373737
	stv:sLeftBottomTextColor="#FF373737"                	//color		左下文字颜色	默认0xFF373737
	stv:sCenterTextColor="#FF373737"                    	//color		中间文字颜色	默认0xFF373737
	stv:sCenterTopTextColor="#FF373737"                     //color		中上文字颜色	默认0xFF373737
	stv:sCenterBottomTextColor="#FF373737"                	//color		中下文字颜色	默认0xFF373737
	stv:sRightTextColor="#FF373737"                         //color		左边文字颜色	默认0xFF373737
	stv:sRightTopTextColor="#FF373737"                    	//color		右上文字颜色	默认0xFF373737
	stv:sRightBottomTextColor="#FF373737"                	//color		右下文字颜色	默认0xFF373737

	//3.3.textSize
	stv:sLeftTextSize="15sp"                            	//dimension	左边字体大小	默认15sp
	stv:sLeftTopTextSize="15sp"                             //dimension	左上字体大小	默认15sp
	stv:sLeftBottomTextSize="15sp"                        	//dimension	左下字体大小	默认15sp
	stv:sCenterTextSize="15sp"                            	//dimension	中间字体大小	默认15sp
	stv:sCenterTopTextSize="15sp"                        	//dimension	中上字体大小	默认15sp
	stv:sCenterBottomTextSize="15sp"                    	//dimension	中下字体大小	默认15sp
	stv:sRightTextSize="15sp"                            	//dimension	右边字体大小	默认15sp
	stv:sRightTopTextSize="15sp"                        	//dimension	右上字体大小	默认15sp
	stv:sRightBottomTextSize="15sp"                         //dimension	右下字体大小	默认15sp

	//3.4.maxLength
	stv:sLeftMaxEms                                         //integer	左边文字显示个数	默认不设置
	stv:sLeftTopMaxEms                                    	//integer	左上文字显示个数	默认不设置
	stv:sLeftBottomMaxEms                                	//integer	左下文字显示个数	默认不设置
	stv:sCenterMaxEms                                    	//integer	中间文字显示个数	默认不设置
	stv:sCenterTopMaxEms                                	//integer	中上文字显示个数	默认不设置
	stv:sCenterBottomMaxEms                                 //integer	中下文字显示个数	默认不设置
	stv:sRightMaxEms                                    	//integer	右边文字显示个数	默认不设置
	stv:sRightTopMaxEms                                     //integer	右上文字显示个数	默认不设置
	stv:sRightBottomMaxEms                                  //integer	右下文字显示个数	默认不设置

    //3.5.gravity[left_center, center, right_center]        //左对齐,居中,右对齐
	stv:sLeftTextGravity="left"								//enum		左边TextView内文字对齐方式 left,center,right	默认left
	stv:sCenterTextGravity="left"							//enum		中间TextView内文字对齐方式	默认left
	stv:sRightTextGravity="left"							//enum		右边TextView内文字对齐方式	默认left

	//3.6.setMaxLines
	stv:sLeftLines                                          //integer	左边文字显示行数	默认不设置
	stv:sLeftTopLines                                    	//integer	左上文字显示行数	默认不设置
	stv:sLeftBottomLines                                	//integer	左下文字显示行数	默认不设置
	stv:sCenterLines                                    	//integer	中间文字显示行数	默认不设置
	stv:sCenterTopLines                                     //integer	中上文字显示行数	默认不设置
	stv:sCenterBottomLines                                	//integer	中下文字显示行数	默认不设置
	stv:sRightLines                                         //integer	右边文字显示行数	默认不设置
	stv:sRightTopLines                                      //integer	右上文字显示行数	默认不设置
	stv:sRightBottomLines                                	//integer	右下文字显示行数	默认不设置

    //3.7.bold加粗
	stv:sLeftTextIsBold="false"                         	//boolean	左边文字是否加粗 默认false（暂时去除此属性改为代码动态配置）
    stv:sLeftTopTextIsBold="false"                          //boolean	左上文字是否加粗 默认false（暂时去除此属性改为代码动态配置）
    stv:sLeftBottomTextIsBold="false"                       //boolean	左下文字是否加粗 默认false（暂时去除此属性改为代码动态配置）
    stv:sCenterTextIsBold="false"                         	//boolean	中间文字是否加粗 默认false（暂时去除此属性改为代码动态配置）
    stv:sCenterTopTextIsBold="false"                        //boolean	中上文字是否加粗 默认false（暂时去除此属性改为代码动态配置）
    stv:sCenterBottomTextIsBold="false"                     //boolean	中下文字是否加粗 默认false（暂时去除此属性改为代码动态配置）
    stv:sRightTextIsBold="false"                         	//boolean	右边文字是否加粗 默认false（暂时去除此属性改为代码动态配置）
    stv:sRightTopTextIsBold="false"                         //boolean	右上文字是否加粗 默认false（暂时去除此属性改为代码动态配置）
    stv:sRightBottomTextIsBold="false"                      //boolean	右下文字是否加粗 默认false（暂时去除此属性改为代码动态配置）


    //4.图片
    //drawable
    stv:sLeftTvDrawableLeft                             	//reference	 左边TextView左侧的drawable
    stv:sLeftTvDrawableRight                             	//reference  左边TextView右侧的drawable
    stv:sCenterTvDrawableLeft                             	// reference 中间TextView左侧的drawable
    stv:sCenterTvDrawableRight                              // reference 中间TextView右侧的drawable
    stv:sRightTvDrawableLeft                             	// reference 右边TextView左侧的drawable
    stv:sRightTvDrawableRight                             	// reference 右边TextView右侧的drawable

    //4.1.加载网络图片,可用于ListView
    stv:sLeftIconRes                                    	//reference 左边图片资源 可以用来显示网络图片或者本地
    stv:sRightIconRes                                    	//reference 右边图片资源 可以用来显示网络图片或者本地
    stv:sLeftIconWidth                                    	//dimension 左边图片资源的宽度 用于固定图片大小的时候使用
    stv:sLeftIconHeight                                     //dimension 左边图片资源的高度 用于固定图片大小的时候使用
    stv:sRightIconWidth                                     //dimension 右边图片资源的宽度 用于固定图片大小的时候使用
    stv:sRightIconHeight                                    //dimension 右边图片资源的高度 用于固定图片大小的时候使用
	stv:sLeftIconShowCircle="false"                      	//boolean	左边ImageView是否显示为圆形 默认false
	stv:sRightIconShowCircle="false"                      	//boolean	右边ImageView是否显示为圆形 默认false

    //4.2.drawable' width & height
    stv:sLeftTvDrawableWidth                             	//dimension 左边TextView的drawable的宽度
    stv:sLeftTvDrawableHeight                             	//dimension 左边TextView的drawable的高度
    stv:sCenterTvDrawableWidth                              //dimension 中间TextView的drawable的宽度
    stv:sCenterTvDrawableHeight                             //dimension 中间TextView的drawable的高度
    stv:sRightTvDrawableWidth                             	//dimension 右边TextView的drawable的宽度
    stv:sRightTvDrawableHeight                              //dimension 右边TextView的drawable的高度

    //4.3.drawable'margin
	stv:sLeftIconMarginLeft="10dp"                        	//dimension 左边图片资源的MarginLeft 默认10dp
	stv:sRightIconMarginRight="10dp"                        //dimension 右边图片资源的MarginLeft 默认10dp
	
	
	//5.背景
	stv:sUseShape="false"									//boolean	是否使用shape设置圆角及触摸反馈,设为true之后才能使用以下属性 默认false
	stv:sShapeSolidColor="#ffffffff"						//color 	填充色 默认0xffffffff
	stv:sShapeSelectorPressedColor="#ffffffff"				//color 	按下时候的颜色 默认0xffffffff
	stv:sShapeSelectorNormalColor="#ffffffff"				//color 	正常显示的颜色 默认0xffffffff
	stv:sShapeCornersRadius="0dp"							//dimension 四个角的圆角半径 默认0dp
	stv:sShapeCornersTopLeftRadius="0dp"					//dimension 左上角的圆角半径 默认0dp
	stv:sShapeCornersTopRightRadius="0dp"					//dimension 右上角的圆角半径 默认0dp
	stv:sShapeCornersBottomLeftRadius="0dp"					//dimension 左下角的圆角半径 默认0dp
	stv:sShapeCornersBottomRightRadius="0dp"				//dimension 右下角的圆角半径 默认0dp
	stv:sShapeStrokeWidth="0dp"								//dimension 边框宽度 默认0dp
	stv:sShapeStrokeDashWidth="0dp"							//dimension 虚线宽度 默认0dp
	stv:sShapeStrokeDashGap="0dp"							//dimension 虚线间隙宽度 默认0dp
	stv:sShapeStrokeColor="#ffffffff"						//color 	边框颜色 默认0xffffffff
	stv:sLeftTextBackground									//reference 左边textView的背景
	stv:sCenterTextBackground								//reference 中间textView的背景
	stv:sRightTextBackground								//reference 右边textView的背景


    //6.右边显示的特殊View
    stv:sRightViewType="checkbox|switchBtn"                 //enum  	默认都不显示
	stv:sRightCheckBoxRes 									//reference 右边CheckBox的资源
	stv:sRightCheckBoxMarginRight							//dimension 右边CheckBox的MarginRight 默认10dp
	stv:sIsChecked="false"							 		//boolean 	右边CheckBox是否选中 默认 false
	stv:sRightSwitchMarginRight								//dimension 右边SwitchBtn的MarginRight 默认10dp
	stv:sSwitchIsChecked="false"							//boolean	右边SwitchBtn是否选中	默认 false
	stv:sTextOff=""											//string	TextOff 默认""
	stv:sTextOn=""											//string	TextOn 默认""
	stv:sSwitchMinWidth										//dimension SwitchMinWidth 系统默认
	stv:sSwitchPadding										//dimension SwitchPadding 系统默认
	stv:sThumbTextPadding									//dimension ThumbTextPadding 系统默认
	stv:sThumbResource										//reference 右边SwitchBtn自定义选中资源 系统默认
	stv:sTrackResource										//reference 右边SwitchBtn自定义未选中资源 系统默认
/>


3.2.2、代码中如何使用
/**
 * 可以通过链式设置大部分常用的属性值
 */
    superTextView.setLeftTopString("")
        .setLeftString("")
        .setLeftBottomString("")
        .setCenterTopString("")
        .setCenterString("")
        .setCenterBottomString("")
        .setRightTopString("")
        .setRightString("")
        .setRightBottomString("")
        .setLeftIcon(0)
        .setRightIcon(0)
        .setCbChecked(true)
        .setCbBackground(null)
        .setLeftTvDrawableLeft(null)
        .setLeftTvDrawableRight(null)
        .setCenterTvDrawableLeft(null)
        .setCenterTvDrawableRight(null)
        .setRightTvDrawableLeft(null)
        .setRightTvDrawableRight(null);

        superTextView.setShapeCornersRadius(20)
        .setShapeCornersTopLeftRadius(20)
        .setShapeCornersBottomLeftRadius(20)
        .setShapeCornersTopRightRadius(20)
        .setShapeCornersBottomRightRadius(20)
        .setShapeStrokeColor(getResources().getColor(R.color.colorPrimary))
        .setShapeStrokeWidth(1)
        .setShapeSrokeDashWidth(1)
        .setShapeStrokeDashGap(5)
        .setShapeSolidColor(getResources().getColor(R.color.white))
        .setShapeSelectorNormalColor(getResources().getColor(R.color.red_btn))
        .setShapeSelectorPressedColor(getResources().getColor(R.color.gray))
        .useShape();//设置完各个参数之后这句调用才生效


3.2.3点击事件
superTextView.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener(){
	@Override
	public void onClickListener(SuperTextView superTextView){
        string="整个item的点击事件";
        Toast.makeText(ClickActivity.this,string,Toast.LENGTH_SHORT).show();
    }
}).setLeftTopTvClickListener(new SuperTextView.OnLeftTopTvClickListener(){
	@Override
	public void onClickListener(){
        string=superTextView.getLeftTopString();
        Toast.makeText(ClickActivity.this,string,Toast.LENGTH_SHORT).show();
    }
}).setLeftTvClickListener(new SuperTextView.OnLeftTvClickListener(){
	@Override
	public void onClickListener(){
        string=superTextView.getLeftString();
        Toast.makeText(ClickActivity.this,string,Toast.LENGTH_SHORT).show();
    }
}).setLeftBottomTvClickListener(new SuperTextView.OnLeftBottomTvClickListener(){
	@Override
	public void onClickListener(){
        string=superTextView.getLeftBottomString();
        Toast.makeText(ClickActivity.this,string,Toast.LENGTH_SHORT).show();
    }
}).setCenterTopTvClickListener(new SuperTextView.OnCenterTopTvClickListener(){
	@Override
	public void onClickListener(){
        string=superTextView.getCenterTopString();
        Toast.makeText(ClickActivity.this,string,Toast.LENGTH_SHORT).show();
    }
}).setCenterTvClickListener(new SuperTextView.OnCenterTvClickListener(){
	@Override
	public void onClickListener(){
        string=superTextView.getCenterString();
        Toast.makeText(ClickActivity.this,string,Toast.LENGTH_SHORT).show();
    }
}).setCenterBottomTvClickListener(new SuperTextView.OnCenterBottomTvClickListener(){
	@Override
	public void onClickListener(){
        string=superTextView.getCenterBottomString();
        Toast.makeText(ClickActivity.this,string,Toast.LENGTH_SHORT).show();
    }
}).setRightTopTvClickListener(new SuperTextView.OnRightTopTvClickListener(){
	@Override
	public void onClickListener(){
        string=superTextView.getRightTopString();
        Toast.makeText(ClickActivity.this,string,Toast.LENGTH_SHORT).show();
    }
}).setRightTvClickListener(new SuperTextView.OnRightTvClickListener(){
	@Override
	public void onClickListener(){
        string=superTextView.getRightString();
        Toast.makeText(ClickActivity.this,string,Toast.LENGTH_SHORT).show();
    }
}).setRightBottomTvClickListener(new SuperTextView.OnRightBottomTvClickListener(){
	@Override
	public void onClickListener(){
        string=superTextView.getRightBottomString();
        Toast.makeText(ClickActivity.this,string,Toast.LENGTH_SHORT).show();
    }
}).setLeftImageViewClickListener(new SuperTextView.OnLeftImageViewClickListener(){
	@Override
	public void onClickListener(ImageView imageView){
        Toast.makeText(ClickActivity.this,"左边图片",Toast.LENGTH_SHORT).show();
    }
}).setRightImageViewClickListener(new SuperTextView.OnRightImageViewClickListener(){
	@Override
	public void onClickListener(ImageView imageView){
        Toast.makeText(ClickActivity.this,"右边图片",Toast.LENGTH_SHORT).show();
    }
});

superTextView_cb.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener(){
	@Override
	public void onClickListener(SuperTextView superTextView){
        superTextView.setCbChecked(!superTextView.getCbisChecked());
    }
}).setCheckBoxCheckedChangeListener(new SuperTextView.OnCheckBoxCheckedChangeListener(){
	@Override
	public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
        Toast.makeText(ClickActivity.this,""+isChecked,Toast.LENGTH_SHORT).show();
    }
});

superTextView_switch.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener(){
	@Override
	public void onClickListener(SuperTextView superTextView){
        superTextView.setSwitchIsChecked(!superTextView.getSwitchIsChecked());
    }
}).setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener(){
	@Override
	public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
        Toast.makeText(ClickActivity.this,""+isChecked,Toast.LENGTH_SHORT).show();
    }
});













