package com.kuchuanyun.cpptest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main3Activity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    private List<String> datas = new ArrayList<>(100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);

        for (int i = 0; i < 100; i++) {
            datas.add("data" + i);
        }
        rv.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends RecyclerView.Adapter{

        @Override
        public int getItemViewType(int position) {
            if (position % 10 == 0) {//设置Title条件
                return 0;
            }
            return 1;
        }

        //注意第二个参数:viewType
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate;
            switch (viewType) {
                case 0://标题布局
                    inflate = getLayoutInflater().inflate(R.layout.item_chat_group_layout, parent, false);
                    return new TitleHolder(inflate);
                default://1,正常布局
                    inflate = getLayoutInflater().inflate(R.layout.item_tree_view, parent, false);
                    return new NormalHolder(inflate);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                case 0://标题
                    TitleHolder titleHolder = (TitleHolder) holder;
                    titleHolder.etMsg.setText("Title:" + position);
                    titleHolder.etMsg.setTextColor(Color.RED);
                    break;
                default://1,正常
                    NormalHolder normalHolder = (NormalHolder) holder;
                    normalHolder.tv.setText("Normal:" + position);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    //标题的ViewHolder
    static class TitleHolder extends RecyclerView.ViewHolder{
        private EditText etMsg;
        public TitleHolder(View itemView) {
            super(itemView);
            etMsg = (EditText) itemView.findViewById(R.id.et_msg);
            etMsg.setEnabled(false);
        }
    }

    //正常的ViewHolder
    static class NormalHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public NormalHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_address);
        }
    }
}
