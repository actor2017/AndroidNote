package com.kuchuanyun.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = (Toolbar) findViewById(R.id.tb_toolbar);
        toolBar.inflateMenu(R.menu.action_menu);
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
    }

    private void toast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
