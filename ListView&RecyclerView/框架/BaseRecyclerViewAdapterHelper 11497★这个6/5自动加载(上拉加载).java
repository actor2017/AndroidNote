上拉加载无需监听滑动事件,可自定义加载布局，显示异常提示，自定义异常提示。

遇到的坑:
java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid item position 7(offset:7).state:21
解决方法:
刷新的时候是否把数据清除了?一定要保证数据和adapter的数据一致!!!

如果遇到上拉加载后一直在加载的问题?
检查上拉加载的数据data != null && data.size > 0.如果为空或者size = 0,会一直转圈圈加载.


https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E4%B8%8A%E6%8B%89%E5%8A%A0%E8%BD%BD

上拉加载
// 滑动最后一个Item的时候回调onLoadMoreRequested方法
setOnLoadMoreListener(RequestLoadMoreListener);

//如果加载失败, 点击"重试"的时候, 会调用 setOnLoadMoreListener 的 onLoadMoreRequested方法!!!

mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= TOTAL_COUNTER) {
                            //数据全部加载完毕
                            mQuickAdapter.loadMoreEnd();
                        } else {
                            if (isErr) {
                                //成功获取更多数据
                                mQuickAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
                                mCurrentCounter = mQuickAdapter.getData().size();
                                mQuickAdapter.loadMoreComplete();
                            } else {
                                //获取更多数据失败
                                isErr = true;
                                Toast.makeText(PullToRefreshUseActivity.this, R.string.network_err, Toast.LENGTH_LONG).show();
                                mQuickAdapter.loadMoreFail();

                            }
                        }
                    }

                }, delayMillis);
            }
        }, mReyclerView);

mQuickAdapter.loadMoreComplete();//加载完成
mQuickAdapter.loadMoreFail();//加载失败
mQuickAdapter.loadMoreEnd();//没有更多数据
mQuickAdapter.setEnableLoadMore(boolean);//打开或关闭加载
mQuickAdapter.disableLoadMoreIfNotFullPage();//检查是否满一屏，如果不满足关闭loadMore

预加载
// 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
mQuickAdapter.setAutoLoadMoreSize(int);


设置自定义加载布局
mQuickAdapter.setLoadMoreView(new CustomLoadMoreView());

自定义加载布局
public final class CustomLoadMoreView extends LoadMoreView {

    @Override public int getLayoutId() {
        return R.layout.view_load_more;
    }

    /**
     * 如果返回true，数据全部加载完毕后会隐藏加载更多
     * 如果返回false，数据全部加载完毕后会显示getLoadEndViewId()布局
     */
    @Override public boolean isLoadEndGone() {
        return true;
    }

    @Override protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    /**
     * isLoadEndGone()为true，可以返回0
     * isLoadEndGone()为false，不能返回0
     */
    @Override protected int getLoadEndViewId() {
        return 0;
    }
}

<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="@dimen/dp_40">

    <LinearLayout
        android:id="@+id/load_more_loading_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:orientation="horizontal">

        <ProgressBar
            android:id="@+id/loading_progress"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            style="?android:attr/progressBarStyleSmall"
            android:layout_marginRight="@dimen/dp_4"
            android:indeterminateDrawable="@drawable/sample_footer_loading_progress"/>

        <TextView
            android:id="@+id/loading_text"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="@dimen/dp_4"
            android:text="@string/loading"
            android:textColor="#0dddb8"
            android:textSize="@dimen/sp_14"/>
    </LinearLayout>

    <FrameLayout
        android:id="@+id/load_more_load_fail_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="gone">


        <TextView
            android:id="@+id/tv_prompt"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:textColor="#0dddb8"
            android:text="@string/load_failed"/>

    </FrameLayout>

</FrameLayout>

//#################################################################################
hihi交友示例
private List<CoinRecordListInfo.RowsBean> items = new ArrayList<>();
private int total;
private int page = 1;
private MyAdapter myAdapter;

swipeRefreshLayout.setOnRefreshListener(() -> getCoinList(page = 1));
myAdapter = new MyAdapter(R.layout.item_money_record, items);
myAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {//加载更多监听
	@Override
	public void onLoadMoreRequested() {
		if (items.size() < total) getCoinList(++ page);
	}
}, recyclerView);
myAdapter.setEmptyView(R.layout.empty_layout);
recyclerView.setAdapter(myAdapter);

getCoinList(1);

private void getCoinList(int page) {
	params.clear();
	params.put(Global.page, page);
	params.put(Global.size, 20);
	MyOkHttpUtils.post(getUrl(Global.GET_COIN_RECORD_LIST), params, new BaseCallback<CoinRecordListInfo>() {
		@Override
		public void onResponse(CoinRecordListInfo response, int id) {
			swipeRefreshLayout.setRefreshing(false);
			if(myAdapter == null) return;
			if (response != null) {
				if (checkCode(response.code)) {
					total = response.total;
					List<CoinRecordListInfo.RowsBean> rows = response.rows;
					if (rows != null) {
						if (page == 1) items.clear();
						myAdapter.addData(rows);
					}
				} else toast(response.msg);
			}
			if (items.size() < total) {
				myAdapter.loadMoreComplete();//加载完成
			} else myAdapter.loadMoreEnd();//已经没有数据了
		}

		@Override
		public void onError(Call call, Exception e, int id) {
			super.onError(call, e, id);
			swipeRefreshLayout.setRefreshing(false);
			if(myAdapter != null) myAdapter.loadMoreFail();//加载失败
		}
	});
}
