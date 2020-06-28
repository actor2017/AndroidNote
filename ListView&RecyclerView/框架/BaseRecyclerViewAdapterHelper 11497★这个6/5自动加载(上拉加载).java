��������������������¼�,���Զ�����ز��֣���ʾ�쳣��ʾ���Զ����쳣��ʾ��

�����Ŀ�:
java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid item position 7(offset:7).state:21
�������:
ˢ�µ�ʱ���Ƿ�����������?һ��Ҫ��֤���ݺ�adapter������һ��!!!

��������������غ�һֱ�ڼ��ص�����?
����������ص�����data != null && data.size > 0.���Ϊ�ջ���size = 0,��һֱתȦȦ����.


https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E4%B8%8A%E6%8B%89%E5%8A%A0%E8%BD%BD

��������
// �������һ��Item��ʱ��ص�onLoadMoreRequested����
setOnLoadMoreListener(RequestLoadMoreListener);

//�������ʧ��, ���"����"��ʱ��, ����� setOnLoadMoreListener �� onLoadMoreRequested����!!!

mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= TOTAL_COUNTER) {
                            //����ȫ���������
                            mQuickAdapter.loadMoreEnd();
                        } else {
                            if (isErr) {
                                //�ɹ���ȡ��������
                                mQuickAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
                                mCurrentCounter = mQuickAdapter.getData().size();
                                mQuickAdapter.loadMoreComplete();
                            } else {
                                //��ȡ��������ʧ��
                                isErr = true;
                                Toast.makeText(PullToRefreshUseActivity.this, R.string.network_err, Toast.LENGTH_LONG).show();
                                mQuickAdapter.loadMoreFail();

                            }
                        }
                    }

                }, delayMillis);
            }
        }, mReyclerView);

mQuickAdapter.loadMoreComplete();//�������
mQuickAdapter.loadMoreFail();//����ʧ��
mQuickAdapter.loadMoreEnd();//û�и�������
mQuickAdapter.setEnableLoadMore(boolean);//�򿪻�رռ���
mQuickAdapter.disableLoadMoreIfNotFullPage();//����Ƿ���һ�������������ر�loadMore

Ԥ����
// ���б�����������N��Item��ʱ��(Ĭ����1)�ص�onLoadMoreRequested����
mQuickAdapter.setAutoLoadMoreSize(int);


�����Զ�����ز���
mQuickAdapter.setLoadMoreView(new CustomLoadMoreView());

�Զ�����ز���
public final class CustomLoadMoreView extends LoadMoreView {

    @Override public int getLayoutId() {
        return R.layout.view_load_more;
    }

    /**
     * �������true������ȫ��������Ϻ�����ؼ��ظ���
     * �������false������ȫ��������Ϻ����ʾgetLoadEndViewId()����
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
     * isLoadEndGone()Ϊtrue�����Է���0
     * isLoadEndGone()Ϊfalse�����ܷ���0
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
hihi����ʾ��
private List<CoinRecordListInfo.RowsBean> items = new ArrayList<>();
private int total;
private int page = 1;
private MyAdapter myAdapter;

swipeRefreshLayout.setOnRefreshListener(() -> getCoinList(page = 1));
myAdapter = new MyAdapter(R.layout.item_money_record, items);
myAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {//���ظ������
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
				myAdapter.loadMoreComplete();//�������
			} else myAdapter.loadMoreEnd();//�Ѿ�û��������
		}

		@Override
		public void onError(Call call, Exception e, int id) {
			super.onError(call, e, id);
			swipeRefreshLayout.setRefreshing(false);
			if(myAdapter != null) myAdapter.loadMoreFail();//����ʧ��
		}
	});
}
