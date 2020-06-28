//------------------------------------------------------------------横竖屏-----------
### 11 横竖屏切换activity的生命周期
* 先销毁activity，重新创建一个新的activity,全部生命周期重写走一遍

解决方法1.* 固定activity的屏幕朝向
<activity
            android:screenOrientation="sensor"//自适应(横竖切换)
            android:screenOrientation="landscape"//横屏
            android:screenOrientation="portrait"//竖屏,默认

解决方法2.* 使activity不敏感屏幕朝向的变化(不会销毁再创建activity)
	*  android:configChanges="orientation|keyboard|screenSize"//屏幕方向,弹出键盘,屏幕尺寸,keyboardHidden隐藏键盘...

当设置了configChanges了之后切换横竖屏,不会销毁再创建activity,只会回调以下方法:

@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    int orientation = newConfig.orientation;//获取方式1
    //也可以用下面这种获取方式
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        System.out.println("当前为横屏,orientation = 2");
    } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
        System.out.println("当前为竖屏,orientation = 1");
    }
}