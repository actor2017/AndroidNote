Item�ĵ���¼�
Item�ĳ����¼�
Item�ӿؼ��ĵ���¼�
Item�ӿؼ��ĳ����¼�

https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E6%B7%BB%E5%8A%A0Item%E4%BA%8B%E4%BB%B6
Item�ĵ���¼�,��Ϊ2��:
  ��.adapter.setOnItemClickListener(listener)
  ��.mRecyclerView.addOnItemTouchListener,����OnItemClickListener

 ��ʽ��:һ adapter.setOnItemClickListener(listener)

adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //switch (alreadyReportCheckAdapter.getData().get(position).type) {/**��ע��:�������������Ҫ����¼�,����getDate,���������м��ص�data������Խ���*/
                Log.d(TAG, "onItemClick: ");
                Toast.makeText(ItemClickActivity.this, "onItemClick" + position, Toast.LENGTH_SHORT).show();
            }
        });

6.Item�ĳ����¼�
adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "onItemLongClick: ");
                Toast.makeText(ItemClickActivity.this, "onItemLongClick" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

7.Item�ӿؼ��ĵ���¼�
��adapter��convert��������ͨ��viewHolder.addOnClickListener��һ�µĿؼ�id
 @Override
    protected void convert(BaseViewHolder viewHolder, Status item) {
        viewHolder.setText(R.id.tweetName, item.getUserName())
                .setText(R.id.tweetText, item.getText())
                .setText(R.id.tweetDate, item.getCreatedAt())
                .setVisible(R.id.tweetRT, item.isRetweet())
                .addOnClickListener(R.id.tweetAvatar)//��һ���������
                .addOnClickListener(R.id.tweetName)  //��һ���������
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

8.Item�ӿؼ��ĳ����¼�
��adapter��convert��������ͨ��viewHolder.addOnLongClickListener��һ�µĿؼ�id
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
  ��ʽ��:�� ͨ��mRecyclerView.addOnItemTouchListener,����OnItemClickListener
9.����������������ʽ ��ѡ�����µķ���Ҳ�ǿ��еģ�����ͬ������ȱʡ������ģʽ�ṩ�˶�̬������ϴ���
ͨ��mRecyclerView.addOnItemTouchListener,����OnItemClickListener

Item�ĵ���¼�
mRecyclerView.addOnItemTouchListener(new OnItemClickListener( ){

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();
                
            }
        });

Item�ĳ����¼�
 mRecyclerView.addOnItemTouchListener(new OnItemLongClickListener( ) {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();

            }
        });

Item�ӿؼ��ĵ���¼�
1.��adapter��convert��������ͨ��viewHolder.addOnClickListener��һ�µĿؼ�id
 @Override
    protected void convert(BaseViewHolder viewHolder, Status item) {
        viewHolder.setText(R.id.tweetName, item.getUserName())
                .setText(R.id.tweetText, item.getText())
                .setText(R.id.tweetDate, item.getCreatedAt())
                .setVisible(R.id.tweetRT, item.isRetweet())
                .addOnClickListener(R.id.tweetAvatar)//��һ���������
                .addOnClickListener(R.id.tweetName)//��һ���������
                .linkify(R.id.tweetText);
       
    }

2.��Activity��mRecyclerView.addOnItemTouchListener,����OnItemChildClickListener��
   mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener( ) {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();

            }
        });

Item�ӿؼ��ĳ����¼�
1.��adapter��convert��������ͨ��viewHolder.addOnLongClickListener��һ�µĿؼ�id
 @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.tweetName, item.getUserName())
                .setText(R.id.tweetText, item.getText())
                .setText(R.id.tweetDate, item.getCreatedAt())
                .setVisible(R.id.tweetRT, item.isRetweet())
                .addOnLongClickListener(R.id.tweetText)
                .linkify(R.id.tweetText);
      
    }

2.��Activity��mRecyclerView.addOnItemTouchListener,����OnItemChildLongClickListener��
 mRecyclerView.addOnItemTouchListener(new OnItemChildLongClickListener( ) {
            @Override
            public void onSimpleItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();
            }
        });

�����������˶��ֲ�ͬ��Item�¼�����,���������ȱʡ������ģʽ
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

//-------------------------�������ǻ۾���ʾ��д��---------------------------
                    Gson gson = new Gson();
                    newestCheckGson = gson.fromJson(response, NewestCheckGson.class);
                    if (newestCheckGson.errCode == 0) {
                        if (newestCheckGson.data != null && newestCheckGson.data.size() > 0) {
                            if (noCheckAdapter == null) {
                                noCheckAdapter = new NoCheckAdapter(R.layout.item_mainactivity_zxcheck, newestCheckGson.data);
                                //item����¼�
                                noCheckAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        //�����������/���,1:����(��ʧЧ).2:��������.3:��������.4:���ݳ�������
                                        switch (noCheckAdapter.getData().get(position).type) {
                                            case "1"://����(��ʧЧ)
                                                break;
                                            case "2"://��������
                                                Intent intent1 = new Intent(getActivity(), ApplyHouseOwnerActivity.class);
                                                intent1.putExtra(Global.ID, noCheckAdapter.getData().get(position).id);
                                                intent1.putExtra(Global.isCheck,"0");
                                                startActivity(intent1);
                                                break;
                                            case "3"://��������
                                                Intent intent2 = new Intent(getActivity(), ApplyLegalPersonActivity.class);
                                                intent2.putExtra(Global.ID, noCheckAdapter.getData().get(position).id);
                                                intent2.putExtra(Global.isCheck,"0");
                                                startActivity(intent2);
                                                break;
                                            case "4"://���ݳ�������
                                                Intent intent3 = new Intent(getActivity(), ApplyRentHouseActivity.class);
                                                intent3.putExtra(Global.ID, noCheckAdapter.getData().get(position).id);
                                                intent3.putExtra(Global.isCheck,"0");
                                                startActivity(intent3);
                                                break;
                                        }
                                    }
                                });
                                //��ӻ�������
                                noCheckAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {

                                    @Override
                                    public void onLoadMoreRequested() {
                                        if (noCheckAdapter.getData().size() % 10 == 0) {
                                            requestNoCheck(noCheckAdapter.getData().size()/10 + 1);
                                        } else {
                                            noCheckAdapter.loadMoreEnd();//����ȫ���������
                                        }
                                    }
                                },rvNoFinish);
                                rvNoFinish.setAdapter(noCheckAdapter);//���¾���
                            } else {//adapter != null
                                if (page == 1) {//��һ�μ���/������onResume����
                                    noCheckAdapter.setNewData(newestCheckGson.data);
                                } else {
                                    noCheckAdapter.loadMoreComplete();//���ظ������
                                    noCheckAdapter.addData(newestCheckGson.data);//������ݵ�adapter��
                                }
                            }
                        } else {
                            noCheckAdapter.loadMoreEnd();//����ȫ���������
                        }
                    } else toast(newestCheckGson.errMsg);
