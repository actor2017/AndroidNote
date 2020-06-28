package com.kuchuan.education.view.impl.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.kuchuan.education.R;
import com.kuchuan.education.bean.UpdateDownloadStatus;
import com.kuchuan.education.global.Global;
import com.kuchuan.education.gson.HotSearchGson;
import com.kuchuan.education.gson.SearchResultGson;
import com.kuchuan.education.presenter.SearchPresenter;
import com.kuchuan.education.utils.ApkUtil;
import com.kuchuan.education.utils.UiUtils;
import com.kuchuan.education.view.impl.ISearchView;
import com.nex3z.flowlayout.FlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.base.Status;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kuchuan.education.application.MyApplication.aCache;

public class SearchActivity extends BaseActivity implements ISearchView {

    @BindView(R.id.tb_toolBar)
    Toolbar tbToolBar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
//    @BindView(R.id.rv_Recyclerview)
//    RecyclerView rvRecyclerview;
    @BindView(R.id.fl_flowlayout)
    FlowLayout flFlowlayout;
    @BindView(R.id.rv_search_result)//点击搜索后的搜索结果
    RecyclerView rvSearchResult;
    @BindView(R.id.tv_hotsearch)
    TextView tvHotsearch;
    @BindView(R.id.srl_swiperefreshlayout)
    SwipeRefreshLayout srlSwiperefreshlayout;
    private List<String> hotSearchs = new ArrayList<>();
    private List<SearchResultGson.DataBean> searchResultList = new ArrayList<>();//搜索结果列表
    private ProgressDialog progressDialog;
    private InputMethodManager imm;//虚拟键盘(输入法)
    private SearchPresenter searchPresenter;
    private MySearchResultAdapter mySearchResultAdapter;
    private String labelName;
    private int searchCount;
//    private MyRecyclerViewAdapter myHotSearchAdapter;//热搜的adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initSupportToolBar(tbToolBar,true);
        srlSwiperefreshlayout.setVisibility(View.GONE);
        rvSearchResult.setVisibility(View.GONE);
        llEmpty.setVisibility(View.GONE);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//虚拟键盘(输入法)
        progressDialog = createProgressDialog(this, "搜索中,请稍后...");
        searchPresenter = new SearchPresenter(this);

        //如果是从"分类"页面点击过来
        labelName = getIntent().getStringExtra("labelName");
        if (!TextUtils.isEmpty(labelName)) {
            etSearch.setText(labelName);
            startSearch(labelName);//搜索
        } else {
            searchPresenter.requestHotSearch();//请求热搜
        }

        searchPresenter.setFileDownloadListener();//设置监听

        int[] colors = new int[100];//100种颜色
        for (int i = 0; i < colors.length; i++) {
            colors[i] = UiUtils.getRandomColor();
        }
        srlSwiperefreshlayout.setColorSchemeColors(colors);
        srlSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNoEmpty(etSearch, "请输入搜索内容")) {
                    labelName = etSearch.getText().toString().trim();
                    startSearch(labelName);//搜索
                } else {
                    srlSwiperefreshlayout.setRefreshing(false);
                }
            }
        });
        EventBus.getDefault().register(this);//①.所谓的注册，其实就是将当前这个对象放到一个集合中保存起来
    }

    //声明和注释你的订阅方法,选择指定线程模式(onCreate之后写)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusUpdateContacts(UpdateDownloadStatus downloadStatus) {    //②.方法名字随便起,随意
        if (downloadStatus.packageName != null) {
            uploadList();
        }
    }

    //热搜json
    @Override
    public void parseHotSearchJson(String response) {
        Gson gson = new Gson();
        HotSearchGson hotSearchGson = gson.fromJson(response, HotSearchGson.class);
        if (hotSearchGson.errCode == 0) {
            aCache.put(Global.HOTSEARCHJSON, response);
            hotSearchs = hotSearchGson.data;

             initFlowLayout();
//            if (myHotSearchAdapter == null) {
//                myHotSearchAdapter = new MyRecyclerViewAdapter();
//                rvRecyclerview.setAdapter(myHotSearchAdapter);
//            } else {
//                myHotSearchAdapter.notifyDataSetChanged();
//            }
        } else {
            toast("获取热搜失败");
        }
    }

    //初始化流布局
    private void initFlowLayout(){
        if (hotSearchs == null) return;
		flFlowlayout.removeAllViews();//每次添加之前需要先清除
        for (int i = 0; i < hotSearchs.size(); i++) {
            TextView tv = new TextView(this);
            tv.setText(hotSearchs.get(i));
//            tv.setTextSize(UiUtils.sp2px(8));
            //tv在代码中只能设置一个背景,不能设置selector
            //tv.setBackgroundColor(UiUtils.getRandomColor());
            //tv.setBackgroundDrawable(UiUtils.getShape(UiUtils.dp2px(5),UiUtils.getRandomColor()));

            //这是selector的实现类,因为textView的背景有selector,不能直接设置,所有用到了这个类
            StateListDrawable selector = UiUtils.getSelector(UiUtils.getShape(UiUtils.dp2px(20),
                    Color.rgb(200, 200, 200)),
                    UiUtils.getShape(UiUtils.dp2px(20), UiUtils.getRandomColor()));
            //设置背景selector
            tv.setBackgroundDrawable(selector);
            tv.setTextColor(Color.WHITE);
            //设置内间距
            tv.setPadding(UiUtils.dp2px(10),UiUtils.dp2px(5),UiUtils.dp2px(10),UiUtils.dp2px(5));
            //设置字体中心显示
            tv.setGravity(Gravity.CENTER);
            //设置点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView v1 = (TextView) v;
                    labelName = v1.getText().toString().trim();//注意:这里一定要赋值,否则上滑加载的时候,labelName=null导致搜索为空.
                    etSearch.setText(labelName);
                    startSearch(labelName);//开始搜索
                }
            });
            flFlowlayout.addView(tv);


        }
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    //DownloadFileInfo downloadFileInfo;

    @Override
    public void installApk(DownloadFileInfo downloadFileInfo) {
        //this.downloadFileInfo = downloadFileInfo;
        ApkUtil.installApk(this, downloadFileInfo.getFilePath());
    }

    //打开应用
    @Override
    public void openApk(String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            if (packageName.equals(getPackageName())) {
                toast("自己不能打开自己");
            } else {
                ApkUtil.openAPK(this, packageName);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        uploadList();
    }

    private void uploadList() {
        try {
            mySearchResultAdapter.notifyDataSetChanged();
            //notifyItemChange(downloadFileInfo, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据包名获取版本号,写在activity/fragment层
    @Override
    public Integer getVersionNo(String packageName) {
        return ApkUtil.getInstalledApkVersionNo(this, packageName);
    }

    //R.id.iv_back,
    @OnClick({R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
            case R.id.iv_search:
                if (isNoEmpty(etSearch, "请输入搜索内容")) {
                    etSearch.clearFocus();
                    imm.hideSoftInputFromWindow(etSearch.getApplicationWindowToken(), 0);
                    labelName = etSearch.getText().toString().trim();
                    startSearch(labelName);//开始搜索
                }
                break;
        }
    }

    //开始搜索
    private void startSearch(String text) {
        tvHotsearch.setVisibility(View.GONE);
//        rvRecyclerview.setVisibility(View.GONE);
        flFlowlayout.setVisibility(View.GONE);
        llEmpty.setVisibility(View.GONE);
        srlSwiperefreshlayout.setVisibility(View.VISIBLE);
        rvSearchResult.setVisibility(View.VISIBLE);
        progressDialog.show();
        searchPresenter.search(text);
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        srlSwiperefreshlayout.setRefreshing(isRefreshing);
    }

    @Override
    public void searchCount(int searchCount) {
        this.searchCount = searchCount;
    }

    @Override
    public void loadMoreAppFail() {
        if (mySearchResultAdapter != null) {
            mySearchResultAdapter.loadMoreFail();
        }
    }

    @Override
    public void setEmptyViewVisibility(boolean visiable) {
        if (visiable) {
            rvSearchResult.setVisibility(View.GONE);//防止第二次搜索没有结果还显示
            llEmpty.setVisibility(View.VISIBLE);
        } else {
            llEmpty.setVisibility(View.GONE);
        }
    }

    //搜索结果
    @Override
    public void parseSearchJson(int page, String response) {
        Gson gson = new Gson();
        SearchResultGson searchResultGson = gson.fromJson(response, SearchResultGson.class);
        if (searchResultGson.errCode == 0) {
            if (page == 1) {//第一次获取/刷新
                searchResultList = searchResultGson.data;
                if (mySearchResultAdapter == null) {
                    mySearchResultAdapter = new MySearchResultAdapter(R.layout.item_search_result,
                            searchResultList);
                    rvSearchResult.setAdapter(mySearchResultAdapter);

                    //条目点击事件
                    mySearchResultAdapter.setOnItemClickListener(new BaseQuickAdapter
                            .OnItemClickListener() {

                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                            intent.putExtra(Global.APPID, searchResultList.get(position).appId);
                            intent.putExtra(Global.APPURL, searchResultList.get(position).appUrl);
                            intent.putExtra(Global.APPSIZE, searchResultList.get(position).appSize);
                            intent.putExtra(Global.APPPACKAGENAME, searchResultList.get(position)
                                    .packageName);
                            startActivity(intent);
                        }
                    });

                    //条目的child点击事件//点击了"下载"按钮
                    mySearchResultAdapter.setOnItemChildClickListener(new BaseQuickAdapter
                            .OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int
                                position) {
                            //点击之后更新状态
                            searchPresenter.clickToUpdateDownloadStatus(searchResultList.get
                                            (position)
                                            .appUrl, searchResultList.get(position).packageName,
                                    (TextView) view);
                        }
                    });

                    //滑动事件监听
                    mySearchResultAdapter.setOnLoadMoreListener(new BaseQuickAdapter
                            .RequestLoadMoreListener() {

                        @Override
                        public void onLoadMoreRequested() {
                            if (mySearchResultAdapter.getData().size() < searchCount) {
                                searchPresenter.searchPage(labelName, mySearchResultAdapter
                                        .getData().size() / 20 + 1, 20);
                            } else {
                                mySearchResultAdapter.loadMoreEnd();//数据全部加载完毕
                            }
                        }
                    }, rvSearchResult);

                } else {//adapter != null
                    mySearchResultAdapter.setNewData(searchResultList);
                }
            } else {//上拉加载更多
                //★★★★★注意大坑:loadMoreComplete会执行addAll的操作,这儿不要写,否则第二个页面会添加2次
                //searchResultList.addAll(searchResultGson.data);
                mySearchResultAdapter.addData(searchResultGson.data);
                mySearchResultAdapter.loadMoreComplete();//加载更多完成
            }

//            if (searchResultList.size() <= 0) {
//                rvSearchResult.setVisibility(View.GONE);
//                llEmpty.setVisibility(View.VISIBLE);
//            } else {
//                rvSearchResult.setVisibility(View.VISIBLE);
//                llEmpty.setVisibility(View.GONE);
//            }
        }
    }

    //热搜的Adapter
//    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search,
//                    parent, false);
//            return new MyViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//            MyViewHolder myViewHolder = (MyViewHolder) holder;
//            myViewHolder.tvItem.setText(hotSearchs.get(position));
//            myViewHolder.tvItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    labelName = hotSearchs.get(position);//注意:这里一定要赋值,否则上滑加载的时候,labelName=null导致搜索为空.
//                    etSearch.setText(labelName);
//                    startSearch(hotSearchs.get(position));//开始搜索
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return hotSearchs.size();
//        }
//    }
//
//    static class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView tvItem;
//        View view;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            this.view = itemView;
//            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
//        }
//    }

    //搜索结果
    private class MySearchResultAdapter extends BaseQuickAdapter<SearchResultGson.DataBean,
            BaseViewHolder> {

        public MySearchResultAdapter(@LayoutRes int layoutResId, @Nullable List<SearchResultGson
                .DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SearchResultGson.DataBean item) {
            helper.setText(R.id.tv_position, helper.getLayoutPosition() + 1 + "")
                    .setText(R.id.tv_appname, item.appName)
                    .setText(R.id.tv_appsize, item.appSize + "MB")
                    .setRating(R.id.rb_ratingbar, item.appScore)
                    .setText(R.id.tv_commentNm, "(" + item.commentNum + ")")//评论数
                    .setText(R.id.tv_detail, item.keyword)//详情
                    .addOnClickListener(R.id.tv_progress);
            loadImage(SearchActivity.this, item.appIcon, (ImageView) helper.getView(R.id.iv_icon)
                    , 5, null, null);

            //初始化下载状态
            searchPresenter.initDownloadStatus(item.appId, item.packageName, item.appUrl, ApkUtil
                    .checkAppInstalled(SearchActivity.this, item.packageName), (TextView) helper
                    .getView(R.id.tv_progress), (ProgressBar) helper.getView(R.id.pb_progress));
        }
    }

    //这个写在activity/fragment中
    @Override
    public void notifyItemChange(DownloadFileInfo downloadFileInfo, boolean neetInstallApk) {
        if (downloadFileInfo != null) {
            String url = downloadFileInfo.getUrl();
            if (url != null) {
                for (int i = 0; i < searchResultList.size(); i++) {
                    if (url.equals(searchResultList.get(i).appUrl)) {
                        mySearchResultAdapter.notifyItemChanged(i);//更新某一条item的状态
                        //如果下载完成就安装
                        if (downloadFileInfo.getStatus() == Status.DOWNLOAD_STATUS_COMPLETED) {
                            if (neetInstallApk) {
                                installApk(downloadFileInfo);
                                //保存下载记录
                                searchPresenter.saveDownloadLog(searchResultList.get(i).appId,
                                        searchResultList.get(i).versionNo);
                            }
                        }
                        return;
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//③.所谓的注销，其实就是将当前这个对象从集合中移除
        searchPresenter.unregisterListener();
    }
}
