package com.itheima.androidl;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.itheima.androidl.fragment.BaseFragment;
import com.itheima.androidl.fragment.FragmentFactory;


public class MainActivity extends Activity implements
        DrawerLayout.DrawerListener, DrawerFragment.OnDrawerItemSelectedListener {

    //抽屉菜单布局
    private DrawerLayout mDrawerLayout;
    private DrawerFragment mDrawerFragment;
    private ActionBarDrawerToggle mToggle;
    //设置样式子类StyleFragment
    private BaseFragment mCurrentFragment;
    private CharSequence mTitle;
    public static int mTheme = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (mTheme != -1) {
            //点击修改主题后，会重新启动MainActivity
            //加载新的Material Design主题
            setTheme(mTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawer();
        initActionBar();

    }

    /**
     * 初始化侧边栏菜单
     */
    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //从左往右滑
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerListener(this);

        //侧边栏菜单
        mDrawerFragment = (DrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        //设置点击菜单条目监听
        mDrawerFragment.setOnDrawerItemSelectedListener(this);
        //默认选中第一个
        mDrawerFragment.selectItem(0);
    }

    /**
     * 初始化toolbar
     */
    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        //设置显示左侧按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        //设置左侧按钮可点
        actionBar.setHomeButtonEnabled(true);
        //设置显示标题
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        //初始化开关，并和drawer关联
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //该方法会自动和actionBar关联
        mToggle.syncState();

        //设置标题
        actionBar.setTitle(getString(R.string.app_name));
    }

    /**
     * 设置菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.main, menu);
            mCurrentFragment.onCreateOptionsMenu(menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 选中菜单
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || mCurrentFragment.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * Drawer的回调方法
     * 打开drawer
     * 需要在该方法中对Toggle做对应的操作
     */
    @Override
    public void onDrawerOpened(View drawerView) {
        mToggle.onDrawerOpened(drawerView);//需要把开关也变为打开
        invalidateOptionsMenu();
    }

    /**
     * 关闭drawer
     */
    @Override
    public void onDrawerClosed(View drawerView) {
        mToggle.onDrawerClosed(drawerView);//需要把开关也变为关闭
        invalidateOptionsMenu();
    }

    /**
     * drawer滑动的回调
     */
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        mToggle.onDrawerSlide(drawerView, slideOffset);
    }

    /**
     * drawer状态改变的回调
     */
    @Override
    public void onDrawerStateChanged(int newState) {
        mToggle.onDrawerStateChanged(newState);
    }


    /**
     * 侧边栏 抽屉菜单 的回调方法
     * 需要在该方法中添加对应的Framgment
     */
    @Override
    public void onDrawerItemSelected(int position) {
        //点击条目后关闭抽屉菜单
        mDrawerLayout.closeDrawer(GravityCompat.START);
        //获取fragment管理器
        FragmentManager fragmentManager = getFragmentManager();

        //创建fragment
        BaseFragment fragment = FragmentFactory.createFragment(position);
        mCurrentFragment = fragment;

        //用事务加载fragment，显示布局
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
