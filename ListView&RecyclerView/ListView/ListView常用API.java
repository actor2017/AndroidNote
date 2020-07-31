<ListView
	android:id="@+id/list_view"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:choiceMode="singleChoice"			//��ѡ
	android:cacheColorHint="@android:color/transparent"//item�����и�Ĭ����ɫ,��������͸��
	android:descendantFocusability=""	//afterDescendants:viewgroupֻ�е�������ؼ�����Ҫ��ȡ����ʱ�Ż�ȡ����
										//beforeDescendants:viewgroup������������ؼ�����ȡ������
										//blocksDescendants:viewgroup�Ḳ������ؼ���ֱ�ӻ�ý���

	android:divider="@android:color/transparent"//���÷ָ���Divider��ʽ(��ɫ/drawable)
	android:dividerHeight="5dp"			//�ָ��߸߶�
	android:layerType="software"		//û����һ���û������Ч��
	android:listSelector="@android:color/transparent"	//item �� selector
	android:stackFromBottom="true"  	//����ֻ�м�����ʱ������ӵײ���ʼ�������,�ϲ�������
	android:transcriptMode="alwaysScroll"//���ǹ��������һ��
	android:scrollbars="none"			//�������ع�����
	android:fadeScrollbars="true"		//���������Զ����غ���ʾ
	android:fastScrollEnabled="true"	//����ʱ��ʾ���ٹ�������,����д�������,�����������.
	android:scrollbarStyle=""			//insideOverlay:��padding�����ڲ��Ҹ�����view��(Ĭ��)
										//insideInset:��padding�����ڲ��Ҳ�����view����
										//outsideOverlay:��padding�����Ⲣ�Ҹ�����view��,�Ƽ�
										//outsideInset:��padding�����Ⲣ�Ҳ�����view����
    android:entries="@array/list"		//��������
    tools:listheader="@layout/item_header"//Ԥ�� header
	tools:listfooter="@layout/item_footer"//Ԥ�� footer
	tools:listitem="@layout/item_list" >//Ԥ�� items
</ListView>

<!--����-->
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
listview.addHeaderView(headerView);//���ͷ����(listview��findviewbyid֮������)(ͷ������LinearLayout,����дһ��layout)
listview.addFooterView(footerView);//��ӽŲ���(listview��findviewbyid֮������)(�Ų�����LinearLayout,����дһ��layout)

listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	//��ѡ(��CheckedTextView��ʾ��д��)
listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);	//��ѡ
listview.setItemChecked(position,true);					//����ѡ��״̬,�ɵ�ѡ/��ѡ.true:ѡ��(���������õ�ѡ/��ѡ)

listview.setSelection(getCount() - 1);	//��ListView���������һ����Ŀ
listview.getFirstVisiablePosition();	//��ȡ��һ���ɼ�����Ŀ
listview.getLastVisiblePosition();		//��ȡ���һ���ɼ��Ĳ���
listview.getChildCount();				//��ȡ��ǰҳ��ɼ���item����,�����ܵ�����,ע��!!
listview.getCount();					//��ȡlistview����item����,����һ��
listview.getAdapter().getCount();

ImageView ivEmpty = findViewById(R.id.iv_empty);//����ͼƬ��layout��ListView֮��
listview.setEmptyView(ivEmpty);					//��listView���ÿղ���,ֻҪlistView��û����Ŀ��,���Զ���ʾ����ͼƬ
listview.setAdapter(myAdapter);					//������֮�������������, ����Ҳ����������, �� myAdapter.notifyDataSetChanged();

footerView.measure(0,0);	//�����Ų���
footerViewHeight = footerView.getMeasuredHeight();//��ȡ�Ѿ�������ĸ߶�

footerView.setPadding(0, -footerViewHeight, 0, 0);//��ʼ�����ؽŲ���
footerView.setPadding(0,0,0,0);//��ʾ�Ų���


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
����ˢ��:
//�ڴ�ʱ��ȡdown�¼���������׼ȷ�������onTouchEvent�л�ȡ�������ܿ��ܻ��������ӿؼ���onTouchEvent�ķ���ֵ
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startY = ev.getY();
        }
        return super.dispatchTouchEvent(ev);
    }


    //dispatchTouchEvent--onInterceptTouchEvent--onTouchEvent
    //onTouchEvent����Դ��
    //1�������������֮�󣬻���õ������onTouchEvent
    //2���ӿؼ��ش�  �����ӿؼ���onTouchEvent��return false
    //��д�����¼�����
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //ע���⼸��������,��һ��3,4   ��2,3��:����13
        System.out.println(getChildCount()+" "+getCount()+" "+getAdapter().getCount());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //��ȡY����������(��Ч)
                //startY = ev.getY();     //�����ȡ��ֵ��Ч
                //System.out.println("RefreshListView.ACTION_DOWN.startY=" + startY);//0
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();//��ȡY����ƶ�����
                float dY = moveY - startY;//��ȡ������ƫ����
                //��ȡListView��һ���ɼ���λ��
                if (getFirstVisiblePosition() == 0 && dY > 0) {
                    //�ı�HeaderView�ĸ߶�
                    int paddingTop = (int) (dY - headerHeight);//���ú�item0�ĸ߶�
                    headerView.setPadding(0, paddingTop, 0, 0);

                    int oldState = mCurrentState;
                    //�ж����ڵ�״̬
                    mCurrentState = paddingTop < 0 ? STATE_PULL_TO_REFRESH :
                            STATE_RELEASE_TO_REFRESH;
                    if (oldState != mCurrentState) {
                        //�ڻ����Ĺ����и���mCurrentState��ֵ����ui��ˢ��
                        refreshUI();
                    }
                    return true;//��������������¼�
                    //��������һ��
                }
                //�������ظ���
                if (getLastVisiblePosition() == this.getCount()-1 && dY < 0) {
                    //int paddingFooter = (int) (dY + footerViewHeight);
                    //����д�����߼�,ֱ�Ӿͳ���
                    isLoadingMore = true;
                    footerView.setPadding(0,0,0,0);
                } else {
                    isLoadingMore = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                //��������ˢ�µ�״̬,��ͷ������ȫ��������
                if (mCurrentState == STATE_PULL_TO_REFRESH) {
                    headerView.setPadding(0, -headerHeight, 0, 0);
                } else if (mCurrentState == STATE_RELEASE_TO_REFRESH) {
                    headerView.setPadding(0, 0, 0, 0);
                    mCurrentState = STATE_REFRESHING;
                    refreshUI();
                    //֪ͨ���ؼ�LiseViewˢ������
                    notifyRefresh();
                }
                if (isLoadingMore) {
                    //�����������ظ���ķ���
                    notifyLoadMore();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
################################################################################################
//���û�������Ҳ�ܼ�����һҳ
//����ListView���������һ����Ŀ��ʱ�򣬽��Ų�����ʾ����
        setOnScrollListener(new OnScrollListener() {
            //scrollState:���µ�״̬
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == OnScrollListener.SCROLL_STATE_IDLE ) {
                    int lastVisiblePosition = getLastVisiblePosition();
                    //�������һ���ɼ���λ���ǽŲ���(��ʱ�Ų�����һ����)
                    if(lastVisiblePosition == getCount() - 1  ) {
                        if(!isLoadingMore) {
                            System.out.println("������...");
                            //���Ų�����ʾ����
                            footerView.setPadding(0, 0 , 0, 0);
                            //��Ȼ�Ų�����ʾ�����ˣ�����������Ҫ�ֶ�����ListView���������һ����Ŀ
                            setSelection(getCount() - 1);
                            //������һҳ������
                            isLoadingMore = true;
                            //֪ͨTabDetailPager������һҳ������
                            notifyLoadMore();
                        }

                    }
                }
            }
			
			/**
			 * @param firstVisibleItem ��һ���ɼ�item��position
			 * @param visibleItemCount ��Ļ�ɼ�item�ĸ���
			 */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastVisibleItem = firstVisibleItem + visibleItemCount;//���һ���ɼ���item��position
            }
        });


