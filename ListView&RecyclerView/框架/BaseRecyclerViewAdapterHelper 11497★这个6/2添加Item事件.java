Item的点击事件
Item的长按事件
Item子控件的点击事件
Item子控件的长按事件

https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E6%B7%BB%E5%8A%A0Item%E4%BA%8B%E4%BB%B6
Item的点击事件,分为2中:
  ①.adapter.setOnItemClickListener(listener)
  ②.mRecyclerView.addOnItemTouchListener,传入OnItemClickListener

 方式①:一 adapter.setOnItemClickListener(listener)

adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //switch (alreadyReportCheckAdapter.getData().get(position).type) {/**★注意:如果上拉加载中要点击事件,必须getDate,否则用现有加载的data会索引越界★*/
                Log.d(TAG, "onItemClick: ");
                Toast.makeText(ItemClickActivity.this, "onItemClick" + position, Toast.LENGTH_SHORT).show();
            }
        });

6.Item的长按事件
adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemLongClick: ");
                Toast.makeText(ItemClickActivity.this, "onItemLongClick" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

7.Item子控件的点击事件
在adapter的convert方法里面通过viewHolder.addOnClickListener绑定一下的控件id
 @Override
    protected void convert(BaseViewHolder viewHolder, Status item) {
        viewHolder.setText(R.id.tweetName, item.getUserName())
                .setText(R.id.tweetText, item.getText())
                .setText(R.id.tweetDate, item.getCreatedAt())
                .setVisible(R.id.tweetRT, item.isRetweet())
                .addOnClickListener(R.id.tweetAvatar)//这一句★★★★★★★
                .addOnClickListener(R.id.tweetName)  //这一句★★★★★★★
                .linkify(R.id.tweetText);
       
    }

 adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemChildClick: ");
                Toast.makeText(ItemClickActivity.this, "onItemChildClick" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

8.Item子控件的长按事件
在adapter的convert方法里面通过viewHolder.addOnLongClickListener绑定一下的控件id
 @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.tweetName, item.getUserName())
                .setText(R.id.tweetText, item.getText())
                .setText(R.id.tweetDate, item.getCreatedAt())
                .setVisible(R.id.tweetRT, item.isRetweet())
                .addOnLongClickListener(R.id.tweetText)
                .linkify(R.id.tweetText);
      
    }
  adapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemChildLongClick: ");
                Toast.makeText(ItemClickActivity.this, "onItemChildLongClick" + position, Toast.LENGTH_SHORT).show();
            }
        });

//---------------------------------------------------------------------
  方式②:二 通过mRecyclerView.addOnItemTouchListener,传入OnItemClickListener
9.如果不想采用上述方式 ，选用以下的方案也是可行的，我们同样采用缺省适配器模式提供了多态方案组合搭配
通过mRecyclerView.addOnItemTouchListener,传入OnItemClickListener

Item的点击事件
mRecyclerView.addOnItemTouchListener(new OnItemClickListener( ){

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();
                
            }
        });

Item的长按事件
 mRecyclerView.addOnItemTouchListener(new OnItemLongClickListener( ) {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();

            }
        });

Item子控件的点击事件
1.在adapter的convert方法里面通过viewHolder.addOnClickListener绑定一下的控件id
 @Override
    protected void convert(BaseViewHolder viewHolder, Status item) {
        viewHolder.setText(R.id.tweetName, item.getUserName())
                .setText(R.id.tweetText, item.getText())
                .setText(R.id.tweetDate, item.getCreatedAt())
                .setVisible(R.id.tweetRT, item.isRetweet())
                .addOnClickListener(R.id.tweetAvatar)//这一句★★★★★★★
                .addOnClickListener(R.id.tweetName)//这一句★★★★★★★
                .linkify(R.id.tweetText);
       
    }

2.在Activity的mRecyclerView.addOnItemTouchListener,传入OnItemChildClickListener。
   mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener( ) {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();

            }
        });

Item子控件的长按事件
1.在adapter的convert方法里面通过viewHolder.addOnLongClickListener绑定一下的控件id
 @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.tweetName, item.getUserName())
                .setText(R.id.tweetText, item.getText())
                .setText(R.id.tweetDate, item.getCreatedAt())
                .setVisible(R.id.tweetRT, item.isRetweet())
                .addOnLongClickListener(R.id.tweetText)
                .linkify(R.id.tweetText);
      
    }

2.在Activity的mRecyclerView.addOnItemTouchListener,传入OnItemChildLongClickListener。
 mRecyclerView.addOnItemTouchListener(new OnItemChildLongClickListener( ) {
            @Override
            public void onSimpleItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();
            }
        });

★★★如果添加了多种不同的Item事件★★★,这里采用了缺省适配器模式
 mRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();
            }
        });

//-------------------------下面是智慧警务示例写法---------------------------
                    Gson gson = new Gson();
                    newestCheckGson = gson.fromJson(response, NewestCheckGson.class);
                    if (newestCheckGson.errCode == 0) {
                        if (newestCheckGson.data != null && newestCheckGson.data.size() > 0) {
                            if (noCheckAdapter == null) {
                                noCheckAdapter = new NoCheckAdapter(R.layout.item_mainactivity_zxcheck, newestCheckGson.data);
                                //item点击事件
                                noCheckAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        //申请审核类型/类别,1:户籍(已失效).2:房主申请.3:法人申请.4:房屋出租申请
                                        switch (noCheckAdapter.getData().get(position).type) {
                                            case "1"://户籍(已失效)
                                                break;
                                            case "2"://房主申请
                                                Intent intent1 = new Intent(getActivity(), ApplyHouseOwnerActivity.class);
                                                intent1.putExtra(Global.ID, noCheckAdapter.getData().get(position).id);
                                                intent1.putExtra(Global.isCheck,"0");
                                                startActivity(intent1);
                                                break;
                                            case "3"://法人申请
                                                Intent intent2 = new Intent(getActivity(), ApplyLegalPersonActivity.class);
                                                intent2.putExtra(Global.ID, noCheckAdapter.getData().get(position).id);
                                                intent2.putExtra(Global.isCheck,"0");
                                                startActivity(intent2);
                                                break;
                                            case "4"://房屋出租申请
                                                Intent intent3 = new Intent(getActivity(), ApplyRentHouseActivity.class);
                                                intent3.putExtra(Global.ID, noCheckAdapter.getData().get(position).id);
                                                intent3.putExtra(Global.isCheck,"0");
                                                startActivity(intent3);
                                                break;
                                        }
                                    }
                                });
                                //添加滑动监听
                                noCheckAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {

                                    @Override
                                    public void onLoadMoreRequested() {
                                        if (noCheckAdapter.getData().size() % 10 == 0) {
                                            requestNoCheck(noCheckAdapter.getData().size()/10 + 1);
                                        } else {
                                            noCheckAdapter.loadMoreEnd();//数据全部加载完毕
                                        }
                                    }
                                },rvNoFinish);
                                rvNoFinish.setAdapter(noCheckAdapter);//最新警情
                            } else {//adapter != null
                                if (page == 1) {//第一次加载/返回走onResume方法
                                    noCheckAdapter.setNewData(newestCheckGson.data);
                                } else {
                                    noCheckAdapter.loadMoreComplete();//加载更多完成
                                    noCheckAdapter.addData(newestCheckGson.data);//添加数据到adapter中
                                }
                            }
                        } else {
                            noCheckAdapter.loadMoreEnd();//数据全部加载完毕
                        }
                    } else toast(newestCheckGson.errMsg);
