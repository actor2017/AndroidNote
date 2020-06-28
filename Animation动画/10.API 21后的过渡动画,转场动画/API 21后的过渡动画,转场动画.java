2.2、API 21 之后Activity过渡动画使用
在API 21之后google又推出了一种比之前效果更加赞的过渡动画。 通过ActivityOptions + Transition来实现Activity过渡动画。
在讲使用ActivityOptions + Transition来实现Activity过渡动画之前先来了看下ActivityOptions里面几个函数代表啥意思。

/**
 * 和overridePendingTransition类似,设置跳转时候的进入动画和退出动画
 */
public static ActivityOptions makeCustomAnimation(Context context, int enterResId, int exitResId);

/**
 * 通过放大一个View过渡到新的Activity
 * 举一个简单的例子来理解source=view,startX=view.getWidth(),startY=view.getHeight(),startWidth=0,startHeight=0
 * 表明新的Activity从view的中心从无到有慢慢放大的过程
 */
public static ActivityOptions makeScaleUpAnimation(View source, int startX, int startY, int width, int height);

/**
 * 通过放大一个Bitmap过渡到新的Activity
 */
public static ActivityOptions makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY);

/**
 * 场景动画，体现在两个Activity中的某些view协同去完成过渡动画效果，等下在例子中能更好的看到效果
 * 经测试,对 android:launchMode="singleTask",当调回singleTask页面时,元素共享衔接不完美
 * 经测试,对 android:launchMode="singleInstance" 不兼容,应该是2个任务栈之间Activity不能共享元素?需百度怎么解决?
 */
public static ActivityOptions makeSceneTransitionAnimation(Activity activity, View sharedElement, String sharedElementName);

/**
 * 场景动画，同上是对多个View同时起作用
 */
public static ActivityOptions makeSceneTransitionAnimation(Activity activity, android.util.Pair<View, String>... sharedElements);


2.2.2、定义过渡动画,系统给我们提供了三种Transition过渡动画，可以拿来直接使用。
    系统默认动画		解释
爆炸,分解(explode)	从场景中心移入或移出视图 
滑动(slide)			从场景边缘移入或移出视图 
淡入淡出(fade)		通过调整透明度在场景中增添或移除视图

我们有两种方式来定义过渡动画：资源文件的方式、代码的方式。代码和资源文件几个对应函数
代码对应				xml对应				解释
addTarget(int targetId)	android:targetId	指定目标View，让目标View参与动画
excludeTarget()			android:excludeId	是否过滤指定View(是否参与动画)
excludeChildren()		无					是否过滤指定ViewGroup的子View(是否参与动画)


2.2.3、设置过渡动画
Transition过渡动画的设置可以在style文件中统一设置也可以在代码中设置(代码中设置的优先级比style主题文件优先级高)。
代码指定							style主题指定					解释
getWindow().setEnterTransition()	android:windowEnterTransition	A启动B，B中的View进入场景的transition(代码所在位置B)
getWindow().setExitTransition()		android:windowExitTransition	A启动B，A中的View退出场景的transition(代码所在位置A)
getWindow().setReturnTransition()	android:windowReturnTransition	B返回A，B中的View退出场景的transition(代码所在位置B)
getWindow().setReenterTransition()	android:windowReenterTransition	B返回A，A中的View重新进入场景的transition(代码所在位置A)


<!-- 想使用5.0的Activity过渡动画，就要加上这个，或者在代码里面设置:getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); -->
<item name="android:windowContentTransitions">true</item>
<!--下面的内容要在5.0上才好用,启用转换过程都会更加流畅自然,指定进入和退出的动画可以重叠 -->
<item name="android:windowAllowEnterTransitionOverlap">true</item>
<item name="android:windowAllowReturnTransitionOverlap">true</item>

<!-- 指定进入和退出transitions,注意只对makeSceneTransitionAnimation -->
<!--A启动B，B中的View进入场景的transition(代码所在位置B)-->
<item name="android:windowEnterTransition">@transition/fade</item>
<!--A启动B，A中的View退出场景的transition(代码所在位置A)-->
<item name="android:windowExitTransition">@transition/fade</item>
<!--B返回A，A中的View重新进入场景的transition(代码所在位置A)-->
<item name="android:windowReenterTransition">@transition/fade</item>
<!--B返回A，B中的View退出场景的transition(代码所在位置B)-->
<item name="android:windowReturnTransition">@transition/fade</item>












