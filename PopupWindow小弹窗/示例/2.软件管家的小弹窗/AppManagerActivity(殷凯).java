
public class AppManagerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.lv_list)
    ListView lvList;

    private PopupWindow mPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        ButterKnife.bind(this);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentInfo = mList.get(position);
                if (!mCurrentInfo.isTitle) {
                    showPopup(view);
                }
            }
        });
    }

    //显示小弹窗
    private void showPopup(View itemView) {
        if (mPopup == null) {
            View view = View.inflate(this, R.layout.popup_appinfo, null);

            view.findViewById(R.id.tv_uninstall).setOnClickListener(this);
            view.findViewById(R.id.tv_open).setOnClickListener(this);
            view.findViewById(R.id.tv_share).setOnClickListener(this);
            view.findViewById(R.id.tv_info).setOnClickListener(this);

            //宽高包裹内容
            mPopup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            //必须设置背景图片,点击弹窗外面和返回键,小弹窗才会消失(这儿点进去是什么都没设置)
            mPopup.setBackgroundDrawable(new ColorDrawable());

            //设置动画样式
            mPopup.setAnimationStyle(R.style.PopupAnim);
        }
        //在什么的正下方显示()   showAsDropDown(View anchor, int xoff, int yoff)
        mPopup.showAsDropDown(itemView, 70, -itemView.getHeight());
    }

    @Override
    public void onClick(View v) {
        //弹窗消失
        mPopup.dismiss();
    }
}
