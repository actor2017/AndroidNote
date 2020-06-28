<android.support.v7.widget.RecyclerView
	android:id="@+id/rv_rvpics"
	android:layout_width="match_parent"
	android:layout_height="300dp">
</android.support.v7.widget.RecyclerView>

/**
 * 详情页
 */
public class DetailActivity extends BaseActivity {

    @BindView(R.id.rv_rvpics)
    RecyclerView rvRvpics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        rvRvpics.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
		
		rvRvpics.setAdapter(new MyDetailPicsAdapter(commentPicsListGson.data));
    }

    //图片的adapter
    private class MyDetailPicsAdapter extends RecyclerView.Adapter {
        List<CommentPicsListGson.DataBean> data;

        public MyDetailPicsAdapter(List<CommentPicsListGson.DataBean> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.item_comment_pic, parent, false);
            return new MyPicsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyPicsViewHolder viewHolder = (MyPicsViewHolder) holder;
            loadImage(DetailActivity.this, data.get(position).imgUrl, viewHolder.ivPic, null, null);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    static class MyPicsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;

        MyPicsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
