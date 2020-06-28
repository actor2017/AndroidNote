package com.wisdomxian.pager.impl.menu;

import com.wisdomxian.widget.RefreshListView;

/**
 * Description: 本类是NewsMenuDetailPager的12个pager的对象,layout填充的是listview,根据传递的url不同而显示不同的布局,
 * ListView(item1:刷新,item2:头条图片...)
 * 这里面有:刷新的item和ViewPager,没有下方的ListView
 * 咱们此时就是需要一个类来包装View对象
 * <p>
 * 这个类是新闻菜单详情页中ViewPager其中一个item的界面封装，这个东西和BaseMenuDetailPager在逻辑结构上没有关系
 * 但是在代码结构上很类似
 * Copyright  : Copyright (c) 2015
 * Company    : 公司名称
 * Author     : 李小松
 * Date       : 2017/2/27 on 1:48.
 */

public class TabDetailPager extends BaseMenuDetailPager {

    public RefreshListView refreshListView;        //刷新的ListView

    @Override
    public View initView() {
        //刷新的头布局(只是一个item0,没有其它布局,会在下面的代码中添加头布局[注意不要写成viewpager]和设置listview适配器)
        View view = View.inflate(activity, R.layout.pager_tab_detail, null);
        refreshListView = (RefreshListView) view.findViewById(R.id.lvListNews);//刷新的条目

        //给refreshListView设置刷新数据的监听,当下拉刷新的时候,通知给这个父控件,刷新并重新加载数据
        refreshListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新数据
                getDataFromServer();
            }

            //上拉加载更多数据
            @Override
            public void onLoadMore() {
                if (moreNews.equals("") || moreNews == null) {
                    //通知隐藏脚布局
                    refreshListView.hideFooterView();
                    ToastUtils.showDefault(activity,"没有更多数据了!");
                } else {
                    //自定义上拉加载获取更多的方法
                    getMoreDataFormService();
                }
            }
        });

        //头布局加入的时机要在初始化结束的时候就做的一件事情?
        //在初始化ListView的时候就将头布局添加上去
        //把头条新闻当作头布局加入给ListView,增加头布局的时候，必须得保证在ListView设置adapter之前★★★★★★★
        refreshListView.addHeaderView(viewPager);
		refreshListView.setAdapter(mListViewAdapter);//listView设置适配器
        return view;
    }

    //上拉加载
    public void getMoreDataFormService() {
        OkHttpUtils.get().url(GlobalConstants.URL_PREFIX + moreNews).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //通知隐藏脚布局
                refreshListView.hideFooterView();
                ToastUtils.showDefault(activity,"请检查网络");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //重写解析的方法,因为要更新more的网址值
                    Gson gson = new Gson();
                    TabInfo tabInfo = gson.fromJson(response, TabInfo.class);
                    List<TabInfo.DataBean.NewsBean> news = tabInfo.data.news;
                    listViewNews.addAll(news);
                    //刷新数据
                    mListViewAdapter.notifyDataSetChanged();
                    //第二页的more网址
                    String more = tabInfo.data.more;
                    //更新最新的更多
                    moreNews = more;

                    ToastUtils.showDefault(activity,"加载成功");
                } catch (Exception e) {
                    ToastUtils.showDefault(activity,"加载失败");
                    e.printStackTrace();
                }
                //通知隐藏脚布局
                refreshListView.hideFooterView();
            }
        });
    }

    //从服务器获取数据
    public void getDataFromServer() {
        OkHttpUtils.get().url(GlobalConstants.URL_PREFIX + url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //失败，ListView隐藏头布局
                refreshListView.hideHeaderView(false);
            }

            @Override
            public void onResponse(String response, int id) {
                //解析json
                try {
                    parseJson(response);
                    //ListView 隐藏头布局
                    refreshListView.hideHeaderView(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    //ListView 隐藏头布局
                    refreshListView.hideHeaderView(false);
                }
            }
        });
    }

    //解析json
    private void parseJson(String response) {
        refreshListView.notifyDatasetChanged();//listView设置适配器
    }
}
