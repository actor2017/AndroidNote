package com.kuchuan.education.fragment;

/**
 * Description: 类的功能描述//Created by : ＄{USER} on ＄{DATE}.[原Date位置]
 * Copyright  : Copyright (c) 2017
 * Company    : 酷川科技 www.kuchuanyun.com
 * Author     : 李小松
 * Date       : 2017/6/12 on 13:42.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.kuchuan.education.R;
import com.kuchuan.education.bean.CategoryContentGson;
import com.kuchuan.education.global.Global;
import com.kuchuan.education.utils.MyOkHttpUtils;
import com.kuchuan.education.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.kuchuan.education.application.MyApplication.aCache;

/**
 * 分类
 */
public class CategoryFragment extends BaseFragment {

    @BindView(R.id.slhlv)
    StickyListHeadersListView slhlv_listview;
    HashMap<String, String> params = new HashMap<>();
    private List<CategoryContentGson.DataBean> dataBeanList;//返回的数据集合
    private List<CategoryContentGson.DataBean.LabelsBean> labelsBeanList;//每一个分类下有几个应用
    private ArrayList<String> classnames = new ArrayList<>();//每一类的标题集合
    private ArrayList<CategoryContentGson.DataBean.LabelsBean> labels = new ArrayList<>();
    private ArrayList<Integer> witchPosition = new ArrayList<>();
    ;//用来判断app是的标题

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_category, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (aCache.getAsString(Global.CATEGORY_CONTENT) != null) {
            try {
                parseCategoryJson(aCache.getAsString(Global.CATEGORY_CONTENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        params.clear();
        MyOkHttpUtils.requestByGet(Global.INTERNET_IP + Global.CATEGORY_CONTENT, params, new
                MyOkHttpUtils.OnNetResponseListener() {

                    @Override
                    public void onOk(String response, int id) {
                        try {
                            parseCategoryJson(response);
                        } catch (Exception e) {
                            ToastUtils.show(getContext(), "请检查网络");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErro(Call call, Exception e, int id) {
                        ToastUtils.show(getContext(), "请检查网络!");
                    }
                });
    }

    //
    private void parseCategoryJson(String response) {

        if (dataBeanList != null) {
            dataBeanList.clear();
        }
        if (labelsBeanList != null) {
            labelsBeanList.clear();
        }
        classnames.clear();
        witchPosition.clear();
        labels.clear();

        Gson gson = new Gson();
        CategoryContentGson categoryContentGson = gson.fromJson(response, CategoryContentGson
                .class);
        if (categoryContentGson.errCode == 0) {
            dataBeanList = categoryContentGson.data;
            for (int i = 0; i < dataBeanList.size(); i++) {
                classnames.add(dataBeanList.get(i).className);
                labelsBeanList = dataBeanList.get(i).labels;
                witchPosition.add(labelsBeanList.size());
                for (int j = 0; j < labelsBeanList.size(); j++) {
                    labels.add(labelsBeanList.get(j));
                }
            }

            slhlv_listview.setAdapter(new MyListViewAdapter());
            //标题点击事件
            slhlv_listview.setOnHeaderClickListener(new StickyListHeadersListView
                    .OnHeaderClickListener() {
                @Override
                public void onHeaderClick(StickyListHeadersListView l, View header, int
                        itemPosition,
                                          long headerId, boolean currentlySticky) {
                    //ToastUtils.show(getContext(), "title:" + headerId);
                }
            });
        } else {
            ToastUtils.show(getContext(), "请检查网络");
        }
    }

    //粘性ListView的适配器
    private class MyListViewAdapter extends BaseAdapter implements StickyListHeadersAdapter {
        //头布局,即{ 标题栏布局 }
        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            TitleViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout
                        .item_category_title, null);
                viewHolder = new TitleViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (TitleViewHolder) convertView.getTag();
            }
            viewHolder.tvTitle.setText(classnames.get((int) getHeaderId(position)));
            return convertView;
        }

        @Override
        public long getHeaderId(int position) {
            int count = 0;
            for (int i = 0; i < witchPosition.size(); i++) {
                count += witchPosition.get(i);
                if (position < count) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public int getCount() {
            return labels.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //返回条目布局
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ContentViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout
                        .item_category_content, null);
                viewHolder = new ContentViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ContentViewHolder) convertView.getTag();
            }
            viewHolder.tvItem.setText(labels.get(position).labelName);
            Glide
                    .with(getContext()) // 指定Context
                    .load(Global.PIC_IP + labels.get(position).labelIcon)// 指定图片的URL
                    //.placeholder(R.mipmap.ic_launcher)// 指定图片未成功加载前显示的图片,可以不设置(什么都不显示)
                    //.error(R.mipmap.ic_launcher)// 指定图片加载失败显示的图片,可以不设置(什么都不显示)
                    //.override(300, 300)//指定图片的尺寸(不设置可注销)
                    .skipMemoryCache(false)// 跳过内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//跳过磁盘缓存(ALL:不跳过,NONE:跳过)
                    //.transform(new GlideRoundTransform(this,50))//圆形图片,圆角图片...
                    .into(viewHolder.ivIcon);//指定显示图片的ImageView
            viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.show(getContext(), "item:" + position);
                }
            });
            return convertView;
        }
    }

    //条目的viewholder
    static class ContentViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_item)
        TextView tvItem;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        ContentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //标题的viewholder
    static class TitleViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        TitleViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
