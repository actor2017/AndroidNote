package cn.itcast.zhxa12.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import cn.itcast.zhxa12.R;
import cn.itcast.zhxa12.fragment.ContentFragment;
import cn.itcast.zhxa12.fragment.LeftMenuFragment;
import cn.itcast.zhxa12.utils.UiUtils;
import cn.itcast.zhxa12.widget.RefreshListView;

/**
 * 如何使用SlidingMenu
 * 1、继承SlidingActivity或者SlidingFragmentActivity
 * 2、将onCreate的访问权限改成public
 * 3、开始调用SlidingMenu相关的一些api
 *  setBehindContentView
 *
 *  FrameLayout
 *  Fragment:碎片
 *
 */
public class MainActivity extends SlidingFragmentActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置一开始就显示出来的布局
        setContentView(R.layout.layout_content);
        //设置一开始就隐藏起来的布局
        setBehindContentView(R.layout.layout_left);
        //获取整个控件
        SlidingMenu slidingMenu = getSlidingMenu();
        //设置触摸的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置右面板的布局
        //slidingMenu.setSecondaryMenu(R.layout.layout_right);
        //设置SlidingMenu拉开的模式，从左拉开还是从右拉开
        slidingMenu.setMode(SlidingMenu.LEFT);

        int screenWidth = UiUtils.getScreenWidth(this);
        slidingMenu.setBehindOffset((int) (screenWidth*0.625f +0.5f));//将侧边栏拉开之后，屏幕的剩余空间

        initFragment();

        /*RefreshListView refreshListView = new RefreshListView(this);
        //refreshListView.setTabDetailPager(this);
        refreshListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }


        });*/
        /*Button btn;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    //将Fragment显示出来
    //用Fragment将两个布局文件中的空的相对布局填充满
    //Fragment有两种   android.app.Fragment
    //v4包下也有一种Fragment  android.support.v4.app.Fragment
    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();//getFragmentManager();//获取Fragment的管理器
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//开启事务
        //用ContentFragment所包装的View对象（TextView）,放到rlContent中
        fragmentTransaction.add(R.id.rlContent,new ContentFragment(),"content");
        ///用LeftMenuFragment所包装的View对象（TextView）,放到rlLeft中
        fragmentTransaction.add(R.id.rlLeft, new LeftMenuFragment(),"left");
        fragmentTransaction.commit();
    }

    public LeftMenuFragment getLeftMenuFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        return (LeftMenuFragment) supportFragmentManager.findFragmentByTag("left");
    }

    public ContentFragment getContentFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        return (ContentFragment) supportFragmentManager.findFragmentByTag("content");
    }
}
