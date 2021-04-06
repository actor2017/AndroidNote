1. 在gradle文件中添加依赖
compile 'com.android.support:recyclerview-v7:25.0.1'

2. 在布局文件中
示例:
android:descendantFocusability="blocksDescendants"//写在parent上,解决recyclerview自动跳转到最后的问题

3.NestedScrollView 中添加一些View, 然后再添加 RecyclerView, 会造成一直上拉加载的问题:
https://github.com/CymChad/BaseRecyclerViewAdapterHelper/issues/2229
//加载更多(空实现, 为了触发加载样式)
setLoadMore$Empty(mAdapter, recyclerView, () -> {
});

//设置scrollview的加载更多监听，滑动底部进行加载数据
nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
        //加载更多
        getList(false);
    }
});



<android.support.v7.widget.RecyclerView
	android:id="@+id/rlv_recyclerview"
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	//<attr format="boolean" name="fastScrollEnabled"/>
	//<attr format="reference" name="fastScrollVerticalThumbDrawable"/>
	//<attr format="reference" name="fastScrollVerticalTrackDrawable"/>
	//<attr format="reference" name="fastScrollHorizontalThumbDrawable"/>
	//<attr format="reference" name="fastScrollHorizontalTrackDrawable"/>
	//android:orientation="horizontal"	//设置方向,默认垂直方向
	//android:overScrollMode="never"	//去掉滑动到边界阴影
	//android:nestedScrollingEnabled="false"//和ScrollView配合使用时需要设置
	//android:scrollbars="none"			//去掉滚动条
	//android:pointerIcon="alias"		//指针；指示器图标?.>=api24
	//android:fadeScrollbars="true"		//滚动条的自动隐藏和显示
	//app:stackFromEnd="true"			//列表再底部开始展示,如果反转后由上面开始展示...  ★★①.20条数据,展示10-19.②.自动滑到底部★★
	//app:reverseLayout="false"			//是否逆向布局（是否将数据反向显示, 并且如果就几条数据,会显示在底部,顶部空白)
	app:layoutManager="GridLayoutManager"//可设置布局管理器★★★★★
	app:spanCount="4"					//Grid布局4列★★★★★

	tools:visibility="gone"			//如果RecyclerView在xml中挡住视线,可以这样,运行后能显示
	tools:listitem="@layout/item"	//可以预览
	tools:itemCount="3"				//预览时,显示多少条
	>
</android.support.v7.widget.RecyclerView>
//----------------------------------------------------
3.一般方法
recyclerView.scrollToPosition(int position);//滚动到某个Item
recyclerView.smoothScrollToPosition(int position);//平滑的滚动到某个Item?
ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);//获取pos处的ViewHolder

4.viewHolder
viewHolder.itemView.getContext();//可以获取itemView&Context★★
viewHolder.getAdapterPosition();//position
viewHolder.getItemId();
viewHolder.getItemViewType();
viewHolder.getLayoutPosition();//有header的情况
viewHolder.getOldPosition();
viewHolder.isRecyclable();
viewHolder.setIsRecyclable(boolean recyclable);

5.显示隐藏Item
ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
layoutParams.height = 0;//隐藏Item
layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;//显示Item
helper.itemView.setLayoutParams(layoutParams);

6.注意setTag & getTag的使用,尽量不要用final,有时候会造成空指针(比如item删除的时候)

6.设置布局管理器之后，RecyclerView才能正常的显示(可在xml中设置)
recyclerView.setLayoutManager(new LinearLayoutManager(this));//显示线性布局的样子,方向默认垂直
recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));//水平方向,逆向布局=false
recyclerView.setLayoutManager(new GridLayoutManager(this,3));//显示网格布局的样子,3列
recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));//显示瀑布流的样子,3列

7添加默认分割线(在setAdapter之前/后都可以)★★★★★
//添加水平分割线,竖着画的那一条
rvItems.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));
//添加垂直分割线,横着画的那一条
rvItems.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

8.改变某一个item的值
adapter.notifyItemChanged(int position);//BaseQuickAdapter 会回调 onBindViewHolder(BaseViewHolder, int, List<Object>) 3个参数的方法
adapter.notifyItemChanged(int position, @Nullable Object payload);//BaseQuickAdapter 会回调 onBindViewHolder(BaseViewHolder, int, List<Object>) 3个参数的方法
                        //★★★BaseQuickAdapter 也会回调 convert(@NonNull BaseViewHolder helper, String item)方法, 所以不用重写 onBindViewHolder 方法!!!

adapter.notifyItemInserted(position);
adapter.notifyItemRemoved2(position);

//@Override 父类是final, 不能@Override	(用brva框架的remove方法!)
public void notifyItemRemoved2(int position) {//写在Adapter中
    /**
     * 1.必须传int, 否则会调用另一个方法
     * 2.由于这儿是引用传递, 所以Activity/Fragment 和 这儿其实删除的是同一个List
     */
    listData.remove(position);
    super.notifyItemRemoved(position);
    if (position < getItemCount()) {
        notifyItemRangeChanged(position, getItemCount() - position);
    }
}

9.设置Item增加、移除动画(不写也有默认动画)
//recyclerView.setItemAnimator(new DefaultItemAnimator());
//recyclerView.setOrientation(LinearLayoutManager.HORIZONTAL);//可以设置成水平的(ListView,GridView,瀑布流等)

10.如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
recyclerView.setHasFixedSize(true);


注意:如果填充item的时候报空指针,请检查不居中是不是把View写成了view★★★★★
     最好不要用View,可以把Item设置一个MarginTop

11.Adapter适配器示例写法
    //★★可以写成extends RecyclerView.Adapter<MyViewHolder>★
    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //ListView时,使用此方式加载没问题,但如果使用RecyclerView,此方法加载有可能导致条目的宽度不能填充屏幕
            //View view = View.inflate(parent.getContext(),R.layout.item_contacts,null);
			
            //参1:布局文件id.参2:当前条目的父控件.参3:是否将当前条目的布局加载到父控件中
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_contacts,parent,false);
			
			//3.activity/fragment 中
			getLayoutInflater().inflate(R.layout.xxx, parent, false);
			
			//4.getSystemService
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.layout_face_grid, null);//表情布局
			
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            String name = goodFriends.get(position);                //item显示的姓名
            String letter = name.substring(0,1).toUpperCase();      //item上方显示的字母
            myViewHolder.tvLetter.setText(letter);
            myViewHolder.tvName.setText(name);

            if (position == 0 && goodFriends.size() > 0) {      //第一个item
                myViewHolder.tvLetter.setVisibility(View.VISIBLE);
            } else {                                            //后面的item
                String nextLetter = goodFriends.get(position-1).substring(0,1).toUpperCase();//上一个位置显示的名称
                if (letter.equals(nextLetter)) {                //如果和上方的一样
                    myViewHolder.tvLetter.setVisibility(View.GONE);//隐藏
                }else {
                    myViewHolder.tvLetter.setVisibility(View.VISIBLE);//显示
                }
            }
            myViewHolder.itemView.setTag(name);             //itemView固定写法
            myViewHolder.itemView.setOnClickListener(listener());//ctrl+alt+m抽取方法
            myViewHolder.itemView.setOnLongClickListener(onLongClickListener());
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
        }

        //局部刷新, 静态刷新 myAdapter.notifyItemChanged(position, countDownTime);
		//					 myAdapter.notifyItemChanged(position, countDownTime + "1");//多个参数多次调用
        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
            if (payloads.isEmpty()) {//myAdapter.notifyItemChanged() 必须传2个参, 才不为空!!!
                onBindViewHolder(holder, position);
            } else {
                String countDownTime = (String) payloads.get(0);
                holder.setText(R.id.tv_count_down, countDownTime);
            }
        }

        @Override
        public int getItemCount() {
            return goodFriends.size();
        }
    }

    //viewHolder
    static class MyViewHolder extends RecyclerView.ViewHolder{

        private  TextView tvName;
        private  TextView tvLetter;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvLetter = (TextView) itemView.findViewById(R.id.tvLetter);
        }
    }

    //点击监听
    @NonNull
    private View.OnClickListener listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //跳到聊天界面
                String name = (String) view.getTag();
                Intent intent = new Intent(getActivity(),ChatActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        };
    }

    //长按监听
    @NonNull
    private View.OnLongClickListener onLongClickListener() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final String name = (String) view.getTag();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("删除好友");
                alertDialog.setMessage("确定删除"+name+"吗?");
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            EMClient.getInstance().contactManager().deleteContact(name);
                            ToastUtils.show(getActivity(),"删除成功");
                            EventBus.getDefault().post(new ContactsNeedToUpdate(true));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            ToastUtils.show(getActivity(),"删除失败");
                        }
                    }
                });
                alertDialog.setNegativeButton("取消",null);
                alertDialog.show();
                return false;
            }
        };
    }

//=====================================================================
条目	注意事项:
1. 加条目点击事件
2. 加条目状态选择器	否则条目按下去之后,没有背景图片(background)
3. 给条目加分割线	画一条线view,(其实有默认自带线),画在item的顶部or底部,视情况而定

    <View
        android:layout_width="match_parent"
        android:layout_height="1px"//注意, 单位是px, 视情况而定
        android:layout_alignParentBottom="true"
        android:background="#ccc"/>
or:
        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_marginBottom="10dp"
            android:layout_marginLeft="77dp"
            android:layout_marginRight="5dp"
            android:background="@drawable/line"/>

	2种解决方法:
	3.1 自定义分割线这个类,绘制分割线,设置给RecyclerView
	3.2 直接修改布局文件, 在布局文件中加分割线


4. RecyclerView优点:扩展性极强
