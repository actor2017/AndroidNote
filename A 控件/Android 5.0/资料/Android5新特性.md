#Android 5.0新特性

* 系统主题，如何自定义主题

* 阴影和裁剪
	- 高度与阴影
	- 阴影与轮廓
	- 轮廓与裁剪

* 图片和颜色
	- 矢量图
	- drawable染色
	- 取色器

* 全新动画
	- 涟漪动画
	- xml涟漪动画
	- 代码涟漪动画
	- 状态动画
	- 矢量图动画
	- 路径动画
	- 揭示动画
	- Activity转场动画
	- 共享元素转场动画
	- xml配置转场动画


## 主题与样式
- 系统自带的主题
    1. @android:style/Theme.Material
    2. @android:style/Theme.Material.Light
    3. @android:style/Theme.Material.Light.DarkActionBar

- 如何自定义主题
> 参见http://developer.android.com/training/material/theme.html
>  
> 或 ` file:///D:/Android/android-sdk-windows/docs/training/material/theme.html`
> 颜色取色器   
> `http://www.materialpalette.com/`

~~~xml
<resources>
  <!-- inherit from the material theme -->
  <style name="AppTheme" parent="android:Theme.Material">
    <!-- Main theme colors -->
    <!--   your app branding color for the app bar -->
    <item name="android:colorPrimary">@color/primary</item>
    <!--   darker variant for the status bar and contextual app bars -->
    <item name="android:colorPrimaryDark">@color/primary_dark</item>
    <!--   theme UI controls like checkboxes and text fields -->
    <item name="android:colorAccent">@color/accent</item>
  </style>
</resources>
~~~

- 其他样式
>//control类控件puts状态下的配色  
>colorControlNormal   
>//control类控件hightlight状态下的配色  
>colorControlHighlight    
>//control类控件checked/selected状态下的配色   
>colorControlActivated  
>// 控件在活动状态的颜色  
>colorAccent  
>//Switch的滑块在普通或是关闭状态下的颜色  
>colorSwitchThumbNormal  
>// Button在普通状态下的颜色  
> colorButtonNormal

# 阴影和裁剪
-------------------
### 高度
- Z = elevation + translationZ
- elevation 是相对于父控件的高度
- 建议0-5(6个) 不同的层次,2dp一层
- 选中状态提升3层（6dp ）
- 高度决定了View的遮盖关系
- 代码中设置setElevation 的单位是px

### 轮廓
- 带有透明通道的背景默认不会产生阴影，需要在代码中设置
- 代码提供轮廓

		java代码设置
		View.setOutlineProvider(new ViewOutlineProvide(){
		     public void getOutline(View view, Outline outline) {
		         outline.setOval(0, 0, view.getWidth(), view.getHeight());
		    }
		});

- 布局指定轮廓

		xml布局汇总设置
		android:outlineProvider="none|background|bounds|paddedBounds"


### 轮廓与裁剪

- 不是所有的ViewOutlineProvide 返回的Outliew都可以被裁剪。
- 一般来说 path都不行，椭圆型也不行。具体由Outline.canClip()的返回值决定。
- 裁剪View的方法： View.clipToOutline(true)

# 图片和颜色
--------------------
### 图片染色

- 定义染色图片

		<?xml version="1.0" encoding="utf-8"?>
		<bitmap xmlns:android="http://schemas.android.com/apk/res/android"
		    android:src="@drawable/ring"
		    android:tint="#00F"
		    android:tintMode="multiply">
		</bitmap>


- 使用选择器

		<selector xmlns:android="http://schemas.android.com/apk/res/android">
		    <item android:state_pressed="true" android:drawable="@drawable/ring2" />
		    <item android:drawable="@drawable/ring" />
		</selector>


### 取色器
- 	android.support.v7.graphics.Palette

		java
		Palette.Builder builder = Palette.from(bitmap);
		// 同步操作，拖动会卡顿
		// Palette palette = builder.generate();
		// cvh.cv.setCardBackgroundColor(palette.getMutedColor(0));
		// cvh.name.setTextColor(palette.getVibrantColor(Color.BLACK));
		
		// 异步操作
		builder.generate(new Palette.PaletteAsyncListener() {
		    @Override
		    public void onGenerated(Palette palette) {
		        cvh.cv.setCardBackgroundColor(palette.getMutedColor(0));
		        cvh.name.setTextColor(palette.getVibrantColor(Color.BLACK));
		    }
		});

### 矢量图

- SVG
- path
- svg --> vector
- w3: `http://www.w3.org/TR/SVG11/paths.html`
- 转换: `http://inloop.github.io/svg2android/`
- 生成: `http://editor.method.ac/`	

#全新的动画
---------------
### 涟漪动画
- Button自带涟漪动画
    1.  默认效果
    2.  android:background="?android:selectableItemBackground"
    3.  android:background="?android:selectableItemBackgroundBorderless"
- 如何在xml或代码中定义涟漪动画，参见http://developer.android.com/reference/android/graphics/drawable/RippleDrawable.html


### 状态动画
> 参见http://developer.android.com/training/material/animations.html#ViewState  
> 实际上，就是把动画添加到选择器中了

### 矢量图动画
>  http://developer.android.com/training/material/animations.html#AnimVector

1. 定义矢量图Vector，需要对路径设置名称
2. 定义动画anim
3. 定义animated-vector

### 路径动画
> 参见 http://developer.android.com/training/material/animations.html#CurvedMotion

### 揭示动画
> ViewAnimationUtils.createCircularReveal(View, xO, yO, startR, endR);  
> 参见http://developer.android.com/training/material/animations.html#Reveal

### Activity转场动画
> 	android.transition.Transition

- 分类
    1. 进入退出
    > Explode, Fade, Slide

    2. 共享元素
    > ChangeBounds, ChangeClipBounds, ChangeImageTransform, ChangeTransform

- 如何实现
    1. 开启此功能
    >getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

    2. 构造Transition对象

    3. 设置进入和退出动画
    ~~~java
    Window.setEnterTransition()
    Window.setExitTransition()
    Window.setSharedElementEnterTransition()
    Window.setSharedElementExitTransition()
    ~~~
    4. 开启新的Activity
        1. 进入退出类型
        > ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(this)

        2. 共享元素类型
        > ActivityOptions options = ActivityOptions .makeSceneTransitionAnimation(Activity, View, "viewName");

        3. 多共享元素类型
        > ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Activity, Pair.create(View1, "View1Name1"),Pair.create(View2, "ViewName2"));

    > startActivity(intent,option.toBundle());  

    5. 在新的Activiy中也添加进推出动画效果，同步骤1、2、3
    > 对共享的View 设置View.setTransitionName("View1Name1")。

    6. 在新的Activity中，使用finishAfterTransition()替代finish()来结束Acitivity

- 在xml中配置转场动画

    1. 配置主题 实现步骤1、2、3、5（对于非共享元素转场动画）

    ~~~xml
    <style name="BaseAppTheme" parent="android:Theme.Material">
      <!--  1. 开启此功能-->
      <item name="android:windowContentTransitions">true</item>

      <!--   2. 构造Transition对象    3. 设置进入和退出动画-->
      <item name="android:windowEnterTransition">@transition/explode</item>
      <item name="android:windowExitTransition">@transition/explode</item>

    </style>
    ~~~
    2. 在代码中实现4、6

### CardView
- android.support.v7.widget.CardView
- 使用方法
    1. 在布局中使用android.support.v7.widget.CardView
    2. 添加属性

    ~~~xml
    CardView常用属性：
    card_view:cardElevation
     卡片高度
    card_view:cardMaxElevation
     卡片的最大高度
    card_view:cardBackgroundColor
     卡片的背景色
    card_view:cardCornerRadius
     卡片的圆角大小
    card_view:contentPadding

     卡片内容于边距的间隔
    card_view:contentPaddingBottom
    card_view:contentPaddingTop
    card_view:contentPaddingLeft
    card_view:contentPaddingRight
    card_view:contentPaddingStart
    card_view:contentPaddingEnd

    card_view:cardUseCompatPadding
     设置内边距，V21+的版本和之前的版本仍旧具有一样的计算方式
    card_view:cardPreventConrerOverlap
     在V20和之前的版本中添加内边距，这个属性为了防止内容和边角的重叠

    ~~~

### 与旧版本兼容

- 定义不同的布局
    > 将使用了新的API的布局放在res/layout-v21/； 其他的放在res/layout/中

- 主题定义
    1. 继承Theme.AppCompat
    2. 定义颜色的时候去掉android:的命名空间（针对于新的那一些）
    > 小窍门，所有没有见过的，都去掉，aapt会报错，根据aapt的提示添加android的命名空间
- 可以通过兼容包使用的效果
    1. RecyclerView
    1. CardView
    1. Palette
- 不能在之前版本使用的效果
    1. Activity transitions
    1. Touch feedback
    1. Reveal animations
    1. Path-based animations
    1. Vector drawables
    1. Drawable tinting
- 代码要判断运行的环境，使用合适的效果

