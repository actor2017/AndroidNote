
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.mobilesafe12.R;
import cn.mobilesafe12.bean.BlackNumberInfo;
import cn.mobilesafe12.db.dao.BlackNumberDao;
import cn.mobilesafe12.utils.ToastUtils;

	/**
	 * ListView优化->方向: 提高getView方法内部代码执行效率和性能	第119行开始看优化★★★★★★★★★★
	 * <p>
	 * 1. convertView: 大大减少inflate布局的次数
	 * 2. ViewHolder: 大大减少findViewById的次数
	 * 3. ViewHolder类声明为静态, 内存只加载一次
	 * 4. 不用单独声明view对象, 直接操作convertView就可以
	 */

public class BlackNumberActivity extends AppCompatActivity {

    //listView
    @BindView(R.id.lv_list)
    ListView lvList;

    //进度条
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;

    //Dao的全称是Data Access Object，即数据访问对象
    private BlackNumberDao mDao;
    private ArrayList<BlackNumberInfo> mList = new ArrayList<>();//黑名单集合
    private BlackNumberAdapter mAdapter;

    private boolean isLoading = false;//标记当前是否正在加载更多
    private BlackNumberInfo mUpdateInfo;//要更新的对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_number);
        ButterKnife.bind(this);

        //获取数据库实例对象
        mDao = BlackNumberDao.getInstance(this);

        initData();//加载第一页数据


    //初始化数据
    private void initData() {
        isLoading = true;//表示正在加载更多

        //显示进度条
        llLoading.setVisibility(View.VISIBLE);

        //耗时操作:数据库查询; 网络访问★★★★★★★★★★★★★★★★★★★
        new Thread() {
            @Override
            public void run() {

                //模拟耗时操作
                SystemClock.sleep(1000);

                //mList = mDao.findAll();查询所有黑名单
                //System.out.println("黑名单集合:" + mList);
                //0->20->40->60
                //集合大小也是从0->20->40...
                //所以,下一页的位置可以传集合当前的大小, 不用单独维护一个全局的变量
                //moreList:是下一页的数据, 大小20条
                ArrayList<BlackNumberInfo> moreList = mDao.findPart(mList.size());//分页查询黑名单

                //把下一页的数据,追加到当前的大集合mList中
                mList.addAll(moreList);

                //在主线程更新ui
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mAdapter == null) {//第一页, adapter还没有初始化, 所以会走进来
                            //给ListView设置数据源
                            mAdapter = new BlackNumberAdapter();
                            //给ListView设置数据, 此方法会使ListView重新从第一条开始展示
                            //只需要在加载第一页的时候,设置数据, 之后刷新ListView就可以
                            lvList.setAdapter(mAdapter);
                        } else {//其他页
                            //刷新ListView
                            //当ListView数据源(集合)发生变化时, 调用此方法可以让LIstVIew重新根据最新的集合来刷新数据
                            //此方法不会导致重新跳到第一条开始展示
                            mAdapter.notifyDataSetChanged();
                        }

                        //隐藏进度条
                        llLoading.setVisibility(View.GONE);

                        isLoading = false;//加载更多结束
                    }
                });
            }
        }.start();
    }

    // ViewHolder: 大大减少findViewById的次数....................................................②
    static class ViewHolder {
        public TextView tvNumber;
        public TextView tvMode;
        public ImageView ivDelete;
    }

    //listView的适配器
    class BlackNumberAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }
        @Override
        public BlackNumberInfo getItem(int position) {
            return mList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        /**
         * ListView优化->方向: 提高getView方法内部代码执行效率和性能★★★★★★★★★★★★★★★★★★★★
         * <p>
         * 1. convertView:不用单独声明view对象, 直接操作convertView就可以,大大减少inflate布局的次数(大大节省内存)
         * 2. ViewHolder: 大大减少findViewById的次数(再次大服务提升效率)
         * 3. ViewHolder类声明为静态, 内存只加载一次(再次稍微提高效率)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //View view = null;
            ViewHolder holder;//.................................................................②
            if (convertView == null) {//.........................................................①
                //1. 初始化布局对象
                convertView = View.inflate(BlackNumberActivity.this, R.layout.item_blacknumber,
                        null);
                //System.out.println("初始化布局了....");

                //2. findViewById找到相关控件
                holder = new ViewHolder();//使用viewHolder保存findviewbyid的控件对象

                holder.tvNumber = (TextView) convertView.findViewById(R.id.tv_number);
                holder.tvMode = (TextView) convertView.findViewById(R.id.tv_mode);
                holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);

                //给View设置标记->将holder对象保存在了view中, holder和view绑定在了一起
                convertView.setTag(holder);
            } else {
                //不用重新加载布局对象, 而是直接重用convertView
                //view = convertView;
                //System.out.println("重用布局了....");

                holder = (ViewHolder) convertView.getTag();
            }

            //3. 根据数据更新控件
            final BlackNumberInfo info = getItem(position);

            //info.number:这儿得到的是手机号
            holder.tvNumber.setText(info.number);

            switch (info.mode) {
                case 0:
                    holder.tvMode.setText("拦截电话");
                    break;
                case 1:
                    holder.tvMode.setText("拦截短信");
                    break;
                case 2:
                    holder.tvMode.setText("拦截全部");
                    break;
                default:
                    break;
            }

            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //1. 从数据库中删除数据(info.number:这儿得到的是手机号)
                    boolean success = mDao.delete(info.number);

                    if (success) {
                        //2. 从当前集合中删除对象
                        mList.remove(info);
                        //3. 刷新ListView
                        mAdapter.notifyDataSetChanged();

                        //如果用户频繁在同一个位置点击删除按钮, 会导致不会自动加载下一页数据, 只会把当前页删除完
                        //所以要判断一下:如果1.剩余条数<5 && 数据库中还有未加载的数据
                        //注意:下面这种写法错误!因为这样最后<4条的时候,还是会加载(转圈圈)★★★★★★★★★★★★★★★★
                        //if (mList.size() < 5 && mDao.getTotalCount() > 0) {
                        if (mList.size() < 5 && mList.size() < mDao.getTotalCount()) {
                            //如果当前页剩余条目较少, 自动加载下一页
                            initData();
                        }
                    } else {
                        ToastUtils.show(getApplicationContext(), "删除失败!");
                    }
                }
            });
            //4. 将布局对象返回
            return convertView;
        }
    }
}
