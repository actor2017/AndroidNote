��ԭʼ��adapter��ԣ�����70%�Ĵ�������

https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E4%BC%98%E5%8C%96Adapter%E4%BB%A3%E7%A0%81
����Adapter
    //1.�̳�BaseQuickAdapter����
    public class QuickAdapter extends BaseQuickAdapter<ItemInfo, BaseViewHolder> {
        public QuickAdapter() {
            super(int layoutResId, List<ItemInfo> list);
        }

        //2.��дconvert����
        //ͨ��viewHolder������ֳ��÷�����������Զ���ؼ�����viewHolderû���ṩ�ķ�����
        //�Ϳ���ͨ��getView������ʵ�ֻ�ȡ�ؼ���Ȼ����еĲ�����
        @Override
        protected void convert(BaseViewHolder viewHolder, ItemInfo item) {

            //3.�����Ҫ��ȡ��ǰposition,����ͨ��viewHolder.getLayoutPosition()����ȡ
            int position = viewHolder.getLayoutPosition();

            viewHolder.setText(R.id.tweetName, item.getUserName())
                    .setText(R.id.tweetText, item.getText())
                    .setText(R.id.tweetDate, item.getCreatedAt())
                    .setVisible(R.id.tweetRT, item.isRetweet())
                    .linkify(R.id.tweetText);//�������
            Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) helper.getView(R.id.iv));
        }
    }
