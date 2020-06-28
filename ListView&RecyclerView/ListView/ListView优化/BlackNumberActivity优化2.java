
import com.mobilesafe_work.R;
import com.mobilesafe_work.bean.BlackNumberInfo;
import com.mobilesafe_work.db.dao.BlackNumberDao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 本例优化方式2:直接用ButterKnife,与第一种优化方式稍微不同
 *  在这里右击item_blacknumber→Generate(生成)...→ButterKnife→点击左下角→creat ViewHolder,直接生成(139行)
 */
public class BlackNumberActivity extends AppCompatActivity {

    //listView
    @BindView(R.id.lv_list)
    ListView lvList;

    //自定义转动进度条
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;

    //如果listView里面没有条目,就显示这张图片(空)
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    private boolean isLoading;
    private BlackNumberDao mDao;    //数据库对象
    private ArrayList<BlackNumberInfo> mList = new ArrayList<>();//黑名单集合,先new号,因为初始化要用
    private BlackNumberAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_number);
        ButterKnife.bind(this);

        //加载第一页数据
        initData();
    }

    //初始化数据
    private void initData() {
        //表示正在加载更多
        isLoading = true;

        //显示进度条
        llLoading.setVisibility(View.VISIBLE);

        //耗时操作:数据库查询; 网络访问
        new Thread() {
            @Override
            public void run() {
                //模拟耗时操作
                SystemClock.sleep(1000);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//            }
                //获取数据库的对象
                mDao = BlackNumberDao.getInstance(BlackNumberActivity.this);

                //mList = mDao.findAll();查询所有黑名单
                //System.out.println("黑名单集合:" + mList);
                //0->20->40->60
                //集合大小也是从0->20->40...
                //所以,下一页的位置可以传集合当前的大小, 不用单独维护一个全局的变量
                //moreList:是下一页的数据, 大小20条
                mList = mDao.findPart(mList.size());//分页查询黑名单
                //在主线程更新ui
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mAdapter == null) {
                            mAdapter = new BlackNumberAdapter();
                            lvList.setAdapter(mAdapter);

                            lvList.setEmptyView(ivEmpty);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                        //必须写在子线程里面,否则会一闪而过,根本看不见加载的进度条
                        //隐藏进度条
                        llLoading.setVisibility(View.INVISIBLE);
                        //加载完毕
                        isLoading = false;
                    }
                });
            }
        }.start();
    }


    //listView的适配器
    private class BlackNumberAdapter extends BaseAdapter {
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
         * ListView优化->方向: 提高getView方法内部代码执行效率和性能
         * <p>
         * 1. convertView: 大大减少inflate布局的次数
         * 2. ViewHolder: 大大减少findViewById的次数
         * 3. ViewHolder类声明为静态, 内存只加载一次
         * 4. 不用单独声明view对象, 直接操作convertView就可以
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {//.......................................................①
                //初始化布局对象
                //注意:在这里右击item_blacknumber→Generate(生成)...→ButterKnife→点击左下角→creat ViewHolder,直接生成
                convertView = View.inflate(BlackNumberActivity.this, R.layout.item_blacknumber,null);

                //把convertView直接传进去,表示convertView.findViewById()
                holder = new ViewHolder(convertView);//........................................②
                //给View设置标记->将holder对象保存在了view中, holder和view绑定在了一起
                convertView.setTag(holder);//..................................................②
            } else {

                //不用重新加载布局对象, 而是直接重用convertView
                //view = convertView;
                //System.out.println("重用布局了....");
                holder = (ViewHolder) convertView.getTag();//..................................②
            }
            //根据数据更新控件
            BlackNumberInfo info = getItem(position);

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
                    holder.tvMode.setText("拦截电话+短信");
                    break;
                }
            return convertView;
        }
    }

    //BufferKnife自动生成的ViewHolder
    static class ViewHolder {
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_mode)
        TextView tvMode;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //右上角的添加黑名单按钮
    @OnClick(R.id.btn_add)
    public void onClick() {
    }
}
