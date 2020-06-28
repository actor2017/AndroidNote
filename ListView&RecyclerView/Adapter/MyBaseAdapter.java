package com.shijing.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Description: ListView的BaseAdapter的基类
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/4/3 on 11:11.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private List<T> dataList;

    //Objects.requireNonNull(T);
    public MyBaseAdapter(List<T> arrayList) {
        this.dataList = arrayList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder;
        if (convertView == null) {
            holder = getHolder();
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }
        holder.setData(dataList.get(position));
        return holder.convertView;
    }

    /**
     * 返回一个ViewHolder
     */
    protected abstract BaseViewHolder<T> getHolder();


    /**
     * Created by zhengping on 2017/4/3,10:10.
     * /**
     * 分析一下getView的核心工作
     * 1、加载布局文件  convertView、布局文件的id
     * 2、初始化控件  holder、tvContent、convertView
     * 3、存储holder，把holder存在convertView里面去   convertView 、holder
     * 4、刷新控件的数据显示 holder、tvContent、数据
     *
     * convertView、布局id、holder、tvContent、数据
     */

    /**
     * 抽象方法,子类直接return new BaseViewHolder();
     */
    public static abstract class BaseViewHolder<T> {

        private View convertView;
        //public T data;

        //  new BaseHolder()的时候初始化
        public BaseViewHolder(){
            convertView = initView();
            convertView.setTag(BaseViewHolder.this);
        }

        private void setData(T data) {
            //this.data = data;
            refreshView(data);
        }

        /**
         * 填充item条目布局,并且找出控件(findViewById())
         * 注意:填充的时候传context不能是applicationContext,否则空指针.
         * @return
         */
        protected abstract View initView();

        /**
         * itemData:每个条目的数据,例:加载布局的内容(tv_content.setText(itemData);)
         */
        protected abstract void refreshView(T itemData);
    }
}
