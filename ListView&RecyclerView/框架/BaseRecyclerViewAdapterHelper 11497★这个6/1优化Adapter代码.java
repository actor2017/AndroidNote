和原始的adapter相对，减少70%的代码量。

https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E4%BC%98%E5%8C%96Adapter%E4%BB%A3%E7%A0%81
创建Adapter
    //1.继承BaseQuickAdapter基类
    public class QuickAdapter extends BaseQuickAdapter<ItemInfo, BaseViewHolder> {
        public QuickAdapter() {
            super(int layoutResId, List<ItemInfo> list);
        }

        //2.重写convert方法
        //通过viewHolder点出各种常用方法，如果有自定义控件或者viewHolder没有提供的方法，
        //就可以通过getView方法来实现获取控件，然后进行的操作。
        @Override
        protected void convert(BaseViewHolder viewHolder, ItemInfo item) {

            //3.如果需要获取当前position,可以通过viewHolder.getLayoutPosition()来获取
            int position = viewHolder.getLayoutPosition();

            viewHolder.setText(R.id.tweetName, item.getUserName())
                    .setText(R.id.tweetText, item.getText())
                    .setText(R.id.tweetDate, item.getCreatedAt())
                    .setVisible(R.id.tweetRT, item.isRetweet())
                    .linkify(R.id.tweetText);//添加链接
            Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) helper.getView(R.id.iv));
        }
    }
