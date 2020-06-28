package com.kuchuanyun.rxjava2sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kuchuanyun.rxjava2sample.R;
import com.kuchuanyun.rxjava2sample.bean.Items;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: Items的Adapter
 * Copyright  : Copyright (c) 2018
 * Company    : 重庆酷川科技有限公司 www.kuchuanyun.com
 * Author     : 李大发
 * Date       : 2018/1/11 on 10:53
 */

public abstract class ItemsAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Items> items;
    public ItemsAdapter(Context context, ArrayList<Items> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv.setText(items.get(position).itemContent);
        viewHolder.btn.setText(items.get(position).btnContent);
        viewHolder.btn.setTag(position);

        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();//1................getTag获取position
                onClicked(position);//2............................点击之后调用方法
            }
        });
    }

    public abstract void onClicked (int position);//3..............这儿是抽象方法,强制重写!

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.btn)
        Button btn;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}