package com.kuchuanyun.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolBar);//要設定在setNavigationIcon之前,否則會出現 back button(代码中,没试过)
//        toolBar.inflateMenu(R.menu.action_menu);//这个方法不知道为何没用
        //设置返回键,在theme中可以设置颜色,一定要在setSupportActionBar(Toolbar toolbar)之后调用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 设置溢出菜单的图标,也可以在styles中设置
        toolBar.setOverflowIcon(getResources().getDrawable(R.drawable.add));
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("Navigation");
            }
        });

        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_edit:
                        toast("编辑");
                        break;
                    case R.id.menu_search:
                        toast("搜索");
                        break;
                    case R.id.menu_add:
                        toast("退出");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

//        setSupportActionBar(toolBar);
    }

    //这个方法设置没用了
//    @Override
//    public boolean onMenuOpened(int featureId, Menu menu) {
//        System.out.println("onMenuOpened:"+menu.getClass().getName());
//        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
//            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
//                try {
//                    Method m = menu.getClass().getDeclaredMethod(
//                            "setOptionalIconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, true);
//                } catch (Exception e) {
//                    Log.d("OverflowIconVisible", e.getMessage());
//                }
//            }
//        }
//        return super.onMenuOpened(featureId, menu);
//    }

    //通过反射,让item同时显示icon & text
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null && menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean
                        .TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for " +
                        "overflow menu", e);
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    private void toast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    //这个方法一定要写,否则右侧3个点不显示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }
}
