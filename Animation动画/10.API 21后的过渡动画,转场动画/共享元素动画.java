https://blog.csdn.net/wuyuxing24/article/details/78857912
https://github.com/tuacy/TransitionDemo

3.Shared Element Transition(共享元素过渡动画)
共享元素的连接点是所有共享元素View的transition name。它可以在layout文件里面设置(android:transitionName)、
也可以代码设置(View.setTransitionName(ImageConstants.IMAGE_SOURCE[mCurrentPosition]);)。

共享元素的动画也可以通过代码或者主题文件来设置(Fragment里面共享元素动画的设置可以类比Activity里面共享元素动画的设置).

代码指定										style主题指定									解释
getWindow().setSharedElementEnterTransition()	android:windowSharedElementEnterTransition	A启动B，B中的View共享元素的transition(代码所在位置B) 
getWindow().setSharedElementExitTransition()	android:windowSharedElementExitTransition	A启动B，A中的View共享元素transition(代码所在位置A)
getWindow().setSharedElementReturnTransition()	android:windowSharedElementReturnTransition	B返回A，B中的View共享元素的transition(代码所在位置B)
getWindow().setSharedElementReenterTransition()	android:windowSharedElementReenterTransitionB返回A，A中的View重新进入共享元素的transition(代码所在位置A)

同样和Activity过渡动画一样的也可以给Transiton设置回调监听，比如监听Transition开始和结束等等。

/**
 * 共享单个元素
 * 经测试,对 android:launchMode="singleTask",当调回singleTask页面时,元素共享衔接不完美
 * 经测试,对 android:launchMode="singleInstance" 不兼容,应该是2个任务栈之间Activity不能共享元素?需百度怎么解决?
 */
ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(this, view, view.getTransitionName());//TransitionName
startActivity(intent, compat.toBundle());

//第2种, 待完善
Pair pair = new Pair<>(view, PlayActivity.IMG_TRANSITION);
ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair);
ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());

onBackPressed();//返回, finish();没有动画, 所以调用父类的方法

//添加监听示例
getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
    @Override
    public void onTransitionStart(Transition transition) {
        viewBgTop.setVisibility(View.GONE);
        tvInfo.setVisibility(View.GONE);
    }
    @Override
    public void onTransitionEnd(Transition transition) {
        tvInfo.setVisibility(View.VISIBLE);
        viewBgTop.setVisibility(View.VISIBLE);
        //渐变动画
        viewBgTop.animate().alpha(1).setDuration(500L).start();
        //View view, int centerX,  int centerY, float startRadius, float endRadius
        Animator animationBottom = ViewAnimationUtils.createCircularReveal(tvInfo, tvInfo.getWidth() / 2,
                tvInfo.getHeight() / 2, 0, (float) Math.max(tvInfo.getWidth() / 2, tvInfo.getHeight() / 2));
        animationBottom.setDuration(500L);
        animationBottom.start();
        //移除监听
        getWindow().getSharedElementEnterTransition().removeListener(this);
    }
    @Override
    public void onTransitionCancel(Transition transition) {
    }
    @Override
    public void onTransitionPause(Transition transition) {
    }
    @Override
    public void onTransitionResume(Transition transition) {
    }
});


3.1、更新共享元素对应关系
RecyclerView <--> ViewPager
比如我们第一个界面是一个列表(RecyclerView)每个item都是一个图片，点击进入另一个页面详情页面，详情页面呢有是ViewPager的形式。
可以左右滑动。咱们有的时候就想，就算详情界面滑动到了其他照片，在返回到第一个页面的时候也想要有共享元素动画的效果。
这个时候就得更新下共享元素的对应关系了。

怎么更新呢，关键是看SharedElementCallback类的 onMapSharedElements()函数，这个函数是用来装载共享元素的。比如有这么个情况，
还是上面的例子A界面跳转到B界面。那么A界面在B返回的时候要更新下、B界面在返回之前要更新下。
所以给A界面设置setExitSharedElementCallback(SharedElementCallback);、
给B界面设置setEnterSharedElementCallback(SharedElementCallback)。其他更多的细节可以参考下实例代码中的实现。

//SharedElementCallback里面的onMapSharedElements()函数在Activity exit(跳到下个界面)和reenter(从下个界面返回)时都会触发
setExitSharedElementCallback(SharedElementCallback);
//SharedElementCallback里面的onMapSharedElements()函数在Activity enter(进入这个界面)和return(从这个界面返回)时都会触发
setEnterSharedElementCallback(SharedElementCallback);

3.1、延时共享元素动画
有的时候又有这种情况，比如要展示一个网络图片，在网络图片获取到之前，这个共享元素的动画效果没啥作用。
怎么的也得等我图片获取完成之后在开始共享元素的动画效果吧，这个时候延时元素动画就派上大用场了。

postponeEnterTransition()函数用于延时动画，postpone: 使…延期；把…放在次要地位；把…放在后面
startPostponedEnterTransition()函数用于开始延时的共享动画。
那咱们就可以这么干了，在Activity进入的时候先调用postponeEnterTransition()延时动画，
在网络图片获取完成之后在调用startPostponedEnterTransition()开始动画。具体可以参考下实例代码中的实现。

<!-- 指定我们使用sharedElement时的进入和退出动画 -->
<item name="android:windowSharedElementEnterTransition">@transition/change_bounds</item>
<item name="android:windowSharedElementExitTransition">@transition/change_bounds</item>
<item name="android:windowSharedElementReenterTransition">@transition/change_bounds</item>
<item name="android:windowSharedElementReturnTransition">@transition/change_bounds</item>









