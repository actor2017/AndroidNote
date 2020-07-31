<ListView
	android:id="@+id/list_view"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:choiceMode="singleChoice"			//单选
	android:cacheColorHint="@android:color/transparent"//item按下有个默认颜色,所以设置透明
	android:descendantFocusability=""	//afterDescendants:viewgroup只有当其子类控件不需要获取焦点时才获取焦点
										//beforeDescendants:viewgroup会优先其子类控件而获取到焦点
										//blocksDescendants:viewgroup会覆盖子类控件而直接获得焦点

	android:divider="@android:color/transparent"//设置分割线Divider样式(颜色/drawable)
	android:dividerHeight="5dp"			//分隔线高度
	android:layerType="software"		//没有这一句就没有虚线效果
	android:listSelector="@android:color/transparent"	//item 的 selector
	android:stackFromBottom="true"  	//内容只有几条的时候，它会从底部开始依次填充,上部分留白
	android:transcriptMode="alwaysScroll"//总是滚动到最后一条
	android:scrollbars="none"			//总是隐藏滚动条
	android:fadeScrollbars="true"		//滚动条的自动隐藏和显示
	android:fastScrollEnabled="true"	//滑动时显示快速滚动滑块,如果有大量数据,设置这个有用.
	android:scrollbarStyle=""			//insideOverlay:在padding区域内并且覆盖在view上(默认)
										//insideInset:在padding区域内并且插入在view后面
										//outsideOverlay:在padding区域外并且覆盖在view上,推荐
										//outsideInset:在padding区域外并且插入在view后面
    android:entries="@array/list"		//本地数据
    tools:listheader="@layout/item_header"//预览 header
	tools:listfooter="@layout/item_footer"//预览 footer
	tools:listitem="@layout/item_list" >//预览 items
</ListView>

<!--虚线-->
<?xml version="1.0" encoding="utf-8"?>
<layer-list   xmlns:android="http://schemas.android.com/apk/res/android">
    <item>
        <shape android:shape="line" >
            <stroke
                android:dashGap="3dp"
                android:dashWidth="8dp"
                android:width="1dp"
                android:color="@color/gray" />
            <size android:height="1dp" />
        </shape>
    </item>
</layer-list>

View headerView = View.inflate(this, R.layout.settlement_center_header, null);
View footerView = View.inflate(this, R.layout.settlement_center_footer, null);
listview.addHeaderView(headerView);//添加头布局(listview被findviewbyid之后就添加)(头布局是LinearLayout,另外写一个layout)
listview.addFooterView(footerView);//添加脚布局(listview被findviewbyid之后就添加)(脚布局是LinearLayout,另外写一个layout)

listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	//单选(见CheckedTextView的示例写法)
listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);	//多选
listview.setItemChecked(position,true);					//设置选中状态,可单选/多选.true:选中(必须先设置单选/多选)

listview.setSelection(getCount() - 1);	//让ListView滑动到最后一个条目
listview.getFirstVisiablePosition();	//获取第一条可见的条目
listview.getLastVisiblePosition();		//获取最后一条可见的布局
listview.getChildCount();				//获取当前页面可见的item数量,不是总的数量,注意!!
listview.getCount();					//获取listview的总item个数,下面一样
listview.getAdapter().getCount();

ImageView ivEmpty = findViewById(R.id.iv_empty);//这张图片在layout中ListView之上
listview.setEmptyView(ivEmpty);					//给listView设置空布局,只要listView中没有条目了,就自动显示这张图片
listview.setAdapter(myAdapter);					//有数据之后才设置适配器, 或者也可以先设置, 再 myAdapter.notifyDataSetChanged();

footerView.measure(0,0);	//测量脚布局
footerViewHeight = footerView.getMeasuredHeight();//获取已经测量后的高度

footerView.setPadding(0, -footerViewHeight, 0, 0);//初始化隐藏脚布局
footerView.setPadding(0,0,0,0);//显示脚布局


################################################################################################
private class MyAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public ChatingServiceInfo.DataBean.ChatCategoryListBean getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView=getLayoutInflater().inflate(android.R.layout.simple_spinner_dropdown_item, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else viewHolder = (ViewHolder) convertView.getTag();
		ChatingServiceInfo.DataBean.ChatCategoryListBean item = getItem(position);
		viewHolder.textView.setText(item == null ? "" : item.name);
		return convertView;
	}
}

static class ViewHolder {
	private CheckedTextView textView;

	public ViewHolder(View convertView) {
		textView = convertView.findViewById(android.R.id.text1);
	}
}


################################################################################################
下拉刷新:
//在此时获取down事件的坐标最准确，如果在onTouchEvent中获取起点坐标很可能会受制于子控件的onTouchEvent的返回值
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startY = ev.getY();
        }
        return super.dispatchTouchEvent(ev);
    }


    //dispatchTouchEvent--onInterceptTouchEvent--onTouchEvent
    //onTouchEvent的来源：
    //1、自身决定拦截之后，会调用到自身的onTouchEvent
    //2、子控件回传  ，在子控件的onTouchEvent中return false
    //重写触摸事件监听
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //注意这几个的区别,第一个3,4   第2,3个:都是13
        System.out.println(getChildCount()+" "+getCount()+" "+getAdapter().getCount());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取Y轴的起点坐标(无效)
                //startY = ev.getY();     //这儿获取的值无效
                //System.out.println("RefreshListView.ACTION_DOWN.startY=" + startY);//0
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();//获取Y轴的移动坐标
                float dY = moveY - startY;//获取滑动的偏移量
                //获取ListView第一个可见的位置
                if (getFirstVisiblePosition() == 0 && dY > 0) {
                    //改变HeaderView的高度
                    int paddingTop = (int) (dY - headerHeight);//设置后item0的高度
                    headerView.setPadding(0, paddingTop, 0, 0);

                    int oldState = mCurrentState;
                    //判断现在的状态
                    mCurrentState = paddingTop < 0 ? STATE_PULL_TO_REFRESH :
                            STATE_RELEASE_TO_REFRESH;
                    if (oldState != mCurrentState) {
                        //在滑动的过程中根据mCurrentState的值进行ui的刷新
                        refreshUI();
                    }
                    return true;//代表消费了这个事件
                    //如果是最后一条
                }
                //上拉加载更多
                if (getLastVisiblePosition() == this.getCount()-1 && dY < 0) {
                    //int paddingFooter = (int) (dY + footerViewHeight);
                    //懒得写其他逻辑,直接就出来
                    isLoadingMore = true;
                    footerView.setPadding(0,0,0,0);
                } else {
                    isLoadingMore = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                //处于下拉刷新的状态,将头布局完全隐藏起来
                if (mCurrentState == STATE_PULL_TO_REFRESH) {
                    headerView.setPadding(0, -headerHeight, 0, 0);
                } else if (mCurrentState == STATE_RELEASE_TO_REFRESH) {
                    headerView.setPadding(0, 0, 0, 0);
                    mCurrentState = STATE_REFRESHING;
                    refreshUI();
                    //通知父控件LiseView刷新数据
                    notifyRefresh();
                }
                if (isLoadingMore) {
                    //调用上拉加载更多的方法
                    notifyLoadMore();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
################################################################################################
//设置滑动监听也能加载下一页
//监听ListView滑动到最后一个条目的时候，将脚布局显示出来
        setOnScrollListener(new OnScrollListener() {
            //scrollState:最新的状态
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == OnScrollListener.SCROLL_STATE_IDLE ) {
                    int lastVisiblePosition = getLastVisiblePosition();
                    //代表最后一个可见的位置是脚布局(此时脚布局是一条线)
                    if(lastVisiblePosition == getCount() - 1  ) {
                        if(!isLoadingMore) {
                            System.out.println("到底了...");
                            //将脚布局显示出来
                            footerView.setPadding(0, 0 , 0, 0);
                            //虽然脚布局显示出来了，但是我们需要手动的让ListView滑动到最后一个条目
                            setSelection(getCount() - 1);
                            //加载下一页的数据
                            isLoadingMore = true;
                            //通知TabDetailPager加载下一页的数据
                            notifyLoadMore();
                        }

                    }
                }
            }
			
			/**
			 * @param firstVisibleItem 第一条可见item的position
			 * @param visibleItemCount 屏幕可见item的个数
			 */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastVisibleItem = firstVisibleItem + visibleItemCount;//最后一条可见的item的position
            }
        });


