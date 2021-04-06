1. ��gradle�ļ����������
compile 'com.android.support:recyclerview-v7:25.0.1'

2. �ڲ����ļ���
ʾ��:
android:descendantFocusability="blocksDescendants"//д��parent��,���recyclerview�Զ���ת����������

3.NestedScrollView �����һЩView, Ȼ������� RecyclerView, �����һֱ�������ص�����:
https://github.com/CymChad/BaseRecyclerViewAdapterHelper/issues/2229
//���ظ���(��ʵ��, Ϊ�˴���������ʽ)
setLoadMore$Empty(mAdapter, recyclerView, () -> {
});

//����scrollview�ļ��ظ�������������ײ����м�������
nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
        //���ظ���
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
	//android:orientation="horizontal"	//���÷���,Ĭ�ϴ�ֱ����
	//android:overScrollMode="never"	//ȥ���������߽���Ӱ
	//android:nestedScrollingEnabled="false"//��ScrollView���ʹ��ʱ��Ҫ����
	//android:scrollbars="none"			//ȥ��������
	//android:pointerIcon="alias"		//ָ�룻ָʾ��ͼ��?.>=api24
	//android:fadeScrollbars="true"		//���������Զ����غ���ʾ
	//app:stackFromEnd="true"			//�б��ٵײ���ʼչʾ,�����ת�������濪ʼչʾ...  ����.20������,չʾ10-19.��.�Զ������ײ����
	//app:reverseLayout="false"			//�Ƿ����򲼾֣��Ƿ����ݷ�����ʾ, ��������ͼ�������,����ʾ�ڵײ�,�����հ�)
	app:layoutManager="GridLayoutManager"//�����ò��ֹ�����������
	app:spanCount="4"					//Grid����4�С�����

	tools:visibility="gone"			//���RecyclerView��xml�е�ס����,��������,���к�����ʾ
	tools:listitem="@layout/item"	//����Ԥ��
	tools:itemCount="3"				//Ԥ��ʱ,��ʾ������
	>
</android.support.v7.widget.RecyclerView>
//----------------------------------------------------
3.һ�㷽��
recyclerView.scrollToPosition(int position);//������ĳ��Item
recyclerView.smoothScrollToPosition(int position);//ƽ���Ĺ�����ĳ��Item?
ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);//��ȡpos����ViewHolder

4.viewHolder
viewHolder.itemView.getContext();//���Ի�ȡitemView&Context���
viewHolder.getAdapterPosition();//position
viewHolder.getItemId();
viewHolder.getItemViewType();
viewHolder.getLayoutPosition();//��header�����
viewHolder.getOldPosition();
viewHolder.isRecyclable();
viewHolder.setIsRecyclable(boolean recyclable);

5.��ʾ����Item
ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
layoutParams.height = 0;//����Item
layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;//��ʾItem
helper.itemView.setLayoutParams(layoutParams);

6.ע��setTag & getTag��ʹ��,������Ҫ��final,��ʱ�����ɿ�ָ��(����itemɾ����ʱ��)

6.���ò��ֹ�����֮��RecyclerView������������ʾ(����xml������)
recyclerView.setLayoutManager(new LinearLayoutManager(this));//��ʾ���Բ��ֵ�����,����Ĭ�ϴ�ֱ
recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));//ˮƽ����,���򲼾�=false
recyclerView.setLayoutManager(new GridLayoutManager(this,3));//��ʾ���񲼾ֵ�����,3��
recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));//��ʾ�ٲ���������,3��

7���Ĭ�Ϸָ���(��setAdapter֮ǰ/�󶼿���)������
//���ˮƽ�ָ���,���Ż�����һ��
rvItems.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));
//��Ӵ�ֱ�ָ���,���Ż�����һ��
rvItems.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

8.�ı�ĳһ��item��ֵ
adapter.notifyItemChanged(int position);//BaseQuickAdapter ��ص� onBindViewHolder(BaseViewHolder, int, List<Object>) 3�������ķ���
adapter.notifyItemChanged(int position, @Nullable Object payload);//BaseQuickAdapter ��ص� onBindViewHolder(BaseViewHolder, int, List<Object>) 3�������ķ���
                        //����BaseQuickAdapter Ҳ��ص� convert(@NonNull BaseViewHolder helper, String item)����, ���Բ�����д onBindViewHolder ����!!!

adapter.notifyItemInserted(position);
adapter.notifyItemRemoved2(position);

//@Override ������final, ����@Override	(��brva��ܵ�remove����!)
public void notifyItemRemoved2(int position) {//д��Adapter��
    /**
     * 1.���봫int, ����������һ������
     * 2.������������ô���, ����Activity/Fragment �� �����ʵɾ������ͬһ��List
     */
    listData.remove(position);
    super.notifyItemRemoved(position);
    if (position < getItemCount()) {
        notifyItemRangeChanged(position, getItemCount() - position);
    }
}

9.����Item���ӡ��Ƴ�����(��дҲ��Ĭ�϶���)
//recyclerView.setItemAnimator(new DefaultItemAnimator());
//recyclerView.setOrientation(LinearLayoutManager.HORIZONTAL);//�������ó�ˮƽ��(ListView,GridView,�ٲ�����)

10.�������ȷ��ÿ��item�ĸ߶��ǹ̶��ģ��������ѡ������������
recyclerView.setHasFixedSize(true);


ע��:������item��ʱ�򱨿�ָ��,���鲻�����ǲ��ǰ�Viewд����view������
     ��ò�Ҫ��View,���԰�Item����һ��MarginTop

11.Adapter������ʾ��д��
    //������д��extends RecyclerView.Adapter<MyViewHolder>��
    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //ListViewʱ,ʹ�ô˷�ʽ����û����,�����ʹ��RecyclerView,�˷��������п��ܵ�����Ŀ�Ŀ�Ȳ��������Ļ
            //View view = View.inflate(parent.getContext(),R.layout.item_contacts,null);
			
            //��1:�����ļ�id.��2:��ǰ��Ŀ�ĸ��ؼ�.��3:�Ƿ񽫵�ǰ��Ŀ�Ĳ��ּ��ص����ؼ���
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_contacts,parent,false);
			
			//3.activity/fragment ��
			getLayoutInflater().inflate(R.layout.xxx, parent, false);
			
			//4.getSystemService
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.layout_face_grid, null);//���鲼��
			
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            String name = goodFriends.get(position);                //item��ʾ������
            String letter = name.substring(0,1).toUpperCase();      //item�Ϸ���ʾ����ĸ
            myViewHolder.tvLetter.setText(letter);
            myViewHolder.tvName.setText(name);

            if (position == 0 && goodFriends.size() > 0) {      //��һ��item
                myViewHolder.tvLetter.setVisibility(View.VISIBLE);
            } else {                                            //�����item
                String nextLetter = goodFriends.get(position-1).substring(0,1).toUpperCase();//��һ��λ����ʾ������
                if (letter.equals(nextLetter)) {                //������Ϸ���һ��
                    myViewHolder.tvLetter.setVisibility(View.GONE);//����
                }else {
                    myViewHolder.tvLetter.setVisibility(View.VISIBLE);//��ʾ
                }
            }
            myViewHolder.itemView.setTag(name);             //itemView�̶�д��
            myViewHolder.itemView.setOnClickListener(listener());//ctrl+alt+m��ȡ����
            myViewHolder.itemView.setOnLongClickListener(onLongClickListener());
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
        }

        //�ֲ�ˢ��, ��̬ˢ�� myAdapter.notifyItemChanged(position, countDownTime);
		//					 myAdapter.notifyItemChanged(position, countDownTime + "1");//���������ε���
        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
            if (payloads.isEmpty()) {//myAdapter.notifyItemChanged() ���봫2����, �Ų�Ϊ��!!!
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

    //�������
    @NonNull
    private View.OnClickListener listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //�����������
                String name = (String) view.getTag();
                Intent intent = new Intent(getActivity(),ChatActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        };
    }

    //��������
    @NonNull
    private View.OnLongClickListener onLongClickListener() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final String name = (String) view.getTag();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("ɾ������");
                alertDialog.setMessage("ȷ��ɾ��"+name+"��?");
                alertDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            EMClient.getInstance().contactManager().deleteContact(name);
                            ToastUtils.show(getActivity(),"ɾ���ɹ�");
                            EventBus.getDefault().post(new ContactsNeedToUpdate(true));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            ToastUtils.show(getActivity(),"ɾ��ʧ��");
                        }
                    }
                });
                alertDialog.setNegativeButton("ȡ��",null);
                alertDialog.show();
                return false;
            }
        };
    }

//=====================================================================
��Ŀ	ע������:
1. ����Ŀ����¼�
2. ����Ŀ״̬ѡ����	������Ŀ����ȥ֮��,û�б���ͼƬ(background)
3. ����Ŀ�ӷָ���	��һ����view,(��ʵ��Ĭ���Դ���),����item�Ķ���or�ײ�,���������

    <View
        android:layout_width="match_parent"
        android:layout_height="1px"//ע��, ��λ��px, ���������
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

	2�ֽ������:
	3.1 �Զ���ָ��������,���Ʒָ���,���ø�RecyclerView
	3.2 ֱ���޸Ĳ����ļ�, �ڲ����ļ��мӷָ���


4. RecyclerView�ŵ�:��չ�Լ�ǿ
