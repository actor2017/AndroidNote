package com.zhy.toolbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private DrawerLayout mDrawerLayout;

    private Toolbar mToolbar;

    private LeftMenuFragment mLeftMenuFragment;
    private ContentFragment mCurrentFragment;

    private String mTitle;

    private static final String TAG = "com.zhy.toolbar";
    private static final String KEY_TITLLE = "key_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();
        initViews();

        //�ָ�title
        restoreTitle(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();
        //���ҵ�ǰ��ʾ��Fragment
        mCurrentFragment = (ContentFragment) fm.findFragmentByTag(mTitle);

        if (mCurrentFragment == null) {
            mCurrentFragment = ContentFragment.newInstance(mTitle);
            fm.beginTransaction().add(R.id.id_content_container, mCurrentFragment, mTitle).commit();
        }

        mLeftMenuFragment = (LeftMenuFragment) fm.findFragmentById(R.id.id_left_menu_container);
        if (mLeftMenuFragment == null) {
            mLeftMenuFragment = new LeftMenuFragment();
            fm.beginTransaction().add(R.id.id_left_menu_container, mLeftMenuFragment).commit();
        }

        //���ر��Fragment��������ڵĻ�
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null)

            for (Fragment fragment : fragments) {
                if (fragment == mCurrentFragment || fragment == mLeftMenuFragment) continue;
                fm.beginTransaction().hide(fragment).commit();
            }

        //����MenuItem��ѡ��ص�
        mLeftMenuFragment.setOnMenuItemSelectedListener(new LeftMenuFragment.OnMenuItemSelectedListener() {
            @Override
            public void menuItemSelected(String title) {

                FragmentManager fm = getSupportFragmentManager();
                ContentFragment fragment = (ContentFragment) getSupportFragmentManager().findFragmentByTag(title);
                if (fragment == mCurrentFragment) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    return;
                }

                FragmentTransaction transaction = fm.beginTransaction();
                transaction.hide(mCurrentFragment);

                if (fragment == null) {
                    fragment = ContentFragment.newInstance(title);
                    transaction.add(R.id.id_content_container, fragment, title);
                } else {
                    transaction.show(fragment);
                }
                transaction.commit();

                mCurrentFragment = fragment;
                mTitle = title;
                mToolbar.setTitle(mTitle);
                mDrawerLayout.closeDrawer(Gravity.LEFT);


            }
        });

    }

    private void restoreTitle(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            mTitle = savedInstanceState.getString(KEY_TITLLE);

        if (TextUtils.isEmpty(mTitle)) {
            mTitle = getResources().getStringArray(
                    R.array.array_left_menu)[0];
        }

        mToolbar.setTitle(mTitle);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TITLLE, mTitle);
    }

    private void initToolBar() {

        Toolbar toolbar = mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        // App Logo
        // toolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        toolbar.setTitle(getResources().getStringArray(R.array.array_left_menu)[0]);
        // Sub Title
        // toolbar.setSubtitle("Sub title");

//        toolbar.setTitleTextAppearance();


        setSupportActionBar(toolbar);


        //Navigation Icon
        toolbar.setNavigationIcon(R.drawable.ic_toc_white_24dp);
        /*
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });*/

    }

    private void initViews() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);


    }
}
/**
����������л���ͨ��Fragment hide��showʵ�ֵģ��Ͼ������replace�����Fragment��view�ṹ�Ƚϸ��ӣ����ܻ��п��١���Ȼ�ˣ�ע��ÿ��Fragmentռ�ݵ��ڴ����������ڴ治�㣬������Ҫ�ı�ʵ�ַ�ʽ�� 
������ת��Ļ����Ӧ�ó�ʱ�����ں�̨��Activity�ؽ������⣬���˼򵥵Ĵ���
*/