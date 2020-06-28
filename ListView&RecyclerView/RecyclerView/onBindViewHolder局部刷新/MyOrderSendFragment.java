package com.ly.hihifriend.fragment;

public class MyOrderSendFragment extends BaseFragment {

    private Timer timer;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!swipeRefreshLayout.isRefreshing()) {
                    for (int i = 0; i < items.size(); i++) {
                        SendOrderListInfo.RowsBean rowsBean = items.get(i);
                        if (rowsBean != null && rowsBean.status == 1) {//1为已创建，2为已完成，3为已取消
                            Date startTime = rowsBean.startTime;
                            String countDown = calculateCountDown(startTime);
                            rowsBean.countDownTime = countDown;
                            if (countDown != null) {
                                int finalI = i;
                                if ("".equals(countDown)) {//说明倒计时 = 0, 做最后一次刷新Item
                                    activity.runOnUiThread(() -> myAdapter.notifyItemChanged(finalI));
                                } else {//说明倒计时 > 0, 局部刷新
                                    activity.runOnUiThread(() -> {
										myAdapter.notifyItemChanged(finalI, countDown);
										//myAdapter.notifyItemChanged(finalI, countDown + "1");//多个参数多次调用
									});
                                }
                            }
                        }
                    }
                }
            }
        }, 1000, 1000);

        getMySendOrderList(page = 1);
    }

    //获取列表
    private void getMySendOrderList(int page) {
        MyOkHttpUtils.post(getUrl(Global.GET_MY_SEND_ORDER_LIST), params, new BaseCallback<SendOrderListInfo>(this) {

            @Override
            public void onOk(@NonNull SendOrderListInfo info, int id) {
                if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
                if (checkCode(info.code)) {
                    total = info.total;
                    List<SendOrderListInfo.RowsBean> rows = info.rows;
                    if (rows != null) {
                        if (page == 1) items.clear();
                        for (SendOrderListInfo.RowsBean row : rows) {
                            if (row != null) {
                                if (row.status == 1) {
                                    row.countDownTime = calculateCountDown(row.startTime);//计算倒计时
                                }
                                myAdapter.addData(row);
                            }
                        }
                    }
                } else toast(info.msg);
            }
        });
    }

    private class MyAdapter extends BaseQuickAdapter<SendOrderListInfo.RowsBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<SendOrderListInfo.RowsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SendOrderListInfo.RowsBean item) {
            //正常填充数据逻辑, 重点是下面个方法↓...
        }

        /**
         * payloads是从notifyItemChanged(int, Object)中，或从notifyItemRangeChanged(int, int, Object)
         * 中传进来的Object集合
         * 如果payloads不为空并且viewHolder已经绑定了旧数据了，那么adapter会使用payloads参数进行布局刷新
         * 如果payloads为空，adapter就会重新绑定数据，也就是刷新整个item
         */
        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
//            super.onBindViewHolder(holder, position, payloads);
            if (payloads.isEmpty()) {
                onBindViewHolder(holder, position);
            } else {
                String countDownTime = (String) payloads.get(0);
                holder.setText(R.id.tv_count_down, countDownTime);
            }
        }
    }

    /**
     * 计算倒计时
     * 如果倒计时 > 0, 返回真实值
     * 如果 = 0, 返回""
     * 否则 返回null
     */
    private String calculateCountDown(Date startTime) {
        if (startTime != null) {
            long timeSpanByNow = TimeUtils.getTimeSpanByNow(startTime, TimeConstants.SEC);
            if (timeSpanByNow > 0) {
                long hour = timeSpanByNow / 3600;
                long min = (timeSpanByNow - hour * 3600) / 60;
                long sec = timeSpanByNow % 60;
                return getStringFormat("%02d:%02d:%02d", hour, min, sec);
            } else if (timeSpanByNow > -1) {
                return "";
            }
        }
        return null;
    }
}
