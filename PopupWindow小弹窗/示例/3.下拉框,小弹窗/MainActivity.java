package com.heima.dropbox;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 下拉框
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    private ArrayList<String> list;
    private MyAdapter mAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //1.制造假数据用于测试
        list = new ArrayList();
        for (int i = 0; i < 200; i++) {
            list.add("aaabbbccc" + i);
        }

        //2.创建ListView,设置适配器
        listView = new ListView(this);
        listView.setBackgroundResource(R.drawable.listview_background);//给ListView设置背景图片
        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);


        //3.箭头的点击事件,显示下拉框小弹窗
        findViewById(R.id.iv_show_dropbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDown();
            }
        });

        //4.listView设置条目点击事件
        initItemClick();

    }

    //箭头的点击事件
    private void showDropDown() {
        //                              内容填充      宽           高             是否有焦点
        //4.创建popWindle        (View contentView, int width, int height, boolean focusable)
        PopupWindow popupWindow = new PopupWindow(listView, llLayout.getWidth(), 200, true);
        //必须设置背景,否则点击外面和返回键都不管用
        popupWindow.setBackgroundDrawable(new ColorDrawable());//ColorDrawable()里面什么都没有
        //在editText下面显示弹窗
        popupWindow.showAsDropDown(etText, 0, 0);
    }

    //条目点击事件
    private void initItemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etText.setText(list.get(position));
            }
        });
    }

    //适配器
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_list, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvContent.setText(list.get(position));
            viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    //刷新listView
                    mAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    static class ViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
