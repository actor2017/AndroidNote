https://github.com/jfeinstein10/SlidingMenu

1.example是一个示例,可以看ExampleListActivity.apk的实际效果,可不用导入工程查看

2.把SlidingMenu-master当成模块Module导入到工程

3.导入的时候,把名字改成SlidingMenuLibrary

4.修改错误,把build.gradle中的一些报错的地方修改掉:
3	行:	mavenCentral()			改成:	jcenter()
6	行:classpath 'com.android.tools.build:gradle:0.4.+'
      改成:classpath 'com.android.tools.build:gradle:2.2.2'()参照自己项目

9	行:	apply plugin: 'android-library'	改成:	apply plugin: 'com.android.library'
16	行:	compileSdkVersion 17	改成自己项目的版本,例:24
17	行:buildToolsVersion "17.0.0"	改成自己项目的版本,例:buildToolsVersion "25.0.2"
21	行:targetSdkVersion 16	改成自己项目的版本,例:targetSdkVersion 24

5.添加项目依赖

6.运行,会报错,把FloatMath.sin(f);	改成	Math.sin(f);

7.如何使用SlidingMenu
	1、你的Activity继承SlidingActivity或者SlidingFragmentActivity(看你的Activity有没有fragment)

	2、将onCreate的访问权限改成public

	3、开始调用SlidingMenu相关的一些api
	setBehindContentView(必须调用这个,否则报错)

8.一些API:★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
下面示例写法:
public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
        //设置没有标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //1.设置一开始就显示出来的布局
        setContentView(R.layout.layout_content);
        //2.设置一开始就隐藏起来的布局
        setBehindContentView(R.layout.layout_left);
        //3.获取整个控件
        SlidingMenu slidingMenu = getSlidingMenu();
        //4.设置触摸的模式(全屏,边缘,不能滑动)TOUCHMODE_MARGIN,TOUCHMODE_NONE
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //5.设置右面板的布局(本例没有右面面板)
        //slidingMenu.setSecondaryMenu(R.layout.layout_right);
        //6.设置SlidingMenu拉开的模式，从左拉开还是从右拉开
        slidingMenu.setMode(SlidingMenu.LEFT);
		//7.将侧边栏拉开之后，屏幕的剩余空间
        slidingMenu.setBehindOffset(200);
		
        initFragment();
    }

	//8.将SlidingMenu关闭(关闭左边侧边栏)
    mainActivity.getSlidingMenu().toggle();

    //将Fragment显示出来(LeftMenuFragment为何不用ListView???因为Fragment有生命周期,更好控制网络连接等...)
    //用Fragment将两个布局文件中的空的相对布局填充满
    //Fragment有两种   android.app.Fragment
    //v4包下也有一种Fragment  android.support.v4.app.Fragment(用这个,兼容)
    private void initFragment() {
		//获取兼容的Fragment管理器
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        //开始事务  //另外一种getFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        //用ContentFragment所包装的View对象（TextView）,放到rlContent中
        /**
         * 注意:这里R.id.layout_content,不能写成R.layout.layout_content★★★★★★★★★★★★★★
         */
        fragmentTransaction.add(R.id.layout_content, new ContentFragment(),"ContentFragment");
        //用LeftMenuFragment所包装的View对象（TextView）,放到rlLeft中
        fragmentTransaction.add(R.id.layout_left, new LeftMenuFragment(),"LeftMenuFragment");
        fragmentTransaction.commit();
    }

	//对外界提供LeftMenuFragment的对象	注意这种写法!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public LeftMenuFragment getLeftMenuFragment(){
        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag("LeftMenuFragment");
    }
}

