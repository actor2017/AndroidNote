//startActivityForResult & onActivityResult
public class BlackNumberActivity extends AppCompatActivity {

    @BindView(R.id.lv_list)
    ListView lvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_number);
        ButterKnife.bind(this);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取条目对象
                BlackNumberInfo info = mList.get(position);
                Intent intent = new Intent(BlackNumberActivity.this, EditBlackNumberActivity.class);
                intent.putExtra("isUpdate", true);//更新
                intent.putExtra("phoneNum", info.number);//电话号码
                intent.putExtra("mode", info.mode);//拦截模式
                intent.putExtra("position", position);//位置
                startActivityForResult(intent, REQUEST_CODE_UPDATE);
            }
        });
    }

    ////右上角image按钮添加监听,添加黑名单进数据库
    @OnClick(R.id.btn_add)
    public void onClick() {
        //添加黑名单
        startActivityForResult(new Intent(this,EditBlackNumberActivity.class), REQUEST_CODE_ADD);
    }

    //添加/更新 黑名单页面返回的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            String phoneNum = data.getStringExtra("phoneNum");
            int mode = data.getIntExtra("mode",0);

            BlackNumberInfo info = new BlackNumberInfo();
            info.number = phoneNum;
            info.mode = mode;
            //把数据添加到第一条,否则会在list的最后一条
            mList.add(0,info);
            //刷新
            mAdapter.notifyDataSetChanged();
        }

        //更新
        if (requestCode == REQUEST_CODE_UPDATE && resultCode == RESULT_OK) {
            String phoneNum = data.getStringExtra("phoneNum");
            int mode = data.getIntExtra("mode", 0);
            int position = data.getIntExtra("position", Integer.MAX_VALUE);

            mDao.update(phoneNum,mode);
            BlackNumberInfo blackNumberInfo = mList.get(position);
            blackNumberInfo.mode = mode;
            mAdapter.notifyDataSetChanged();
        }
    }
}
