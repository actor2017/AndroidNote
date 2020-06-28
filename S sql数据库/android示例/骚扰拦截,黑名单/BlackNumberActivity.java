package com.heima.mobilesafe_work.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.heima.mobilesafe_work.R;
import com.heima.mobilesafe_work.bean.BlackNumberInfo;
import com.heima.mobilesafe_work.db.dao.BlackNumberDao;
import com.heima.mobilesafe_work.utils.ToastUtils;

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
    private boolean isLoading;     //记录是否在更新
    private BlackNumberDao mDao;    //数据库对象
    private ArrayList<BlackNumberInfo> mList = new ArrayList<>();//黑名单集合,先new号,因为初始化要用
    private BlackNumberAdapter mAdapter;
    public static final int REQUEST_CODE_ADD = 1;//右上角添加黑名单
    public static final int REQUEST_CODE_UPDATE = 2;//更新黑名单拦截模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_number);
        ButterKnife.bind(this);

        //加载第一页数据
        initData();

        //给listView设置滑动监听
        lvList.setOnScrollListener(new AbsListView.OnScrollListener() {
            //滑动状态改变就回调
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {

                        //滑动停止
                    case SCROLL_STATE_IDLE:
                        //获取list的最后一个可见的条目
                        int lastVisiblePosition = lvList.getLastVisiblePosition();
                        //如果已经滑到了最后一条条目
                        if (lastVisiblePosition == mList.size() - 1) {
                            //如果现在没有正在加载中
                            if (!isLoading && mList.size() < mDao.getTotalCount()) {
                                    //加载下面的条目
                                    initData();
                            }else {
                                ToastUtils.show(BlackNumberActivity.this,"没有更多数据啦!");
                            }
                        }
                        break;

                    //一般滑动
                    case SCROLL_STATE_TOUCH_SCROLL:

                    break;

                    //飞快滑动
                    case SCROLL_STATE_FLING:

                        break;
                }
            }

            //只要在滑动就回调
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        //更新条目
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取条目对象
                BlackNumberInfo info = mList.get(position);
                //手机号
                String number = info.number;
                //拦截模式
                int mode = info.mode;
                Intent intent = new Intent(BlackNumberActivity.this,EditBlackNumberActivity.class);
                intent.putExtra("isUpdate",true);//更新
                intent.putExtra("phoneNum",number);//电话号码
                intent.putExtra("mode",mode);//拦截模式
                intent.putExtra("position",position);//位置
                startActivityForResult(intent,REQUEST_CODE_UPDATE);
            }
        });
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
                ArrayList<BlackNumberInfo> moreList = mDao.findPart(mList.size());
                mList.addAll(moreList);//分页查询黑名单
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
        public View getView(final int position, View convertView, ViewGroup parent) {
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
                    holder.tvMode.setText("拦截电话+短信");
                    break;
                }

            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean success = mDao.delete(info.number);
                    if (success) {
                        mList.remove(info);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.show(BlackNumberActivity.this, "删除失败!");
                    }

                    //如果没有正在更新,才能加载,否在暂时不加载下一页,且很容易出错,因为第一次还没加载完,就加载第二次
                    if (!isLoading && mList.size() < 5 && mList.size() < mDao.getTotalCount()) {
                        //加载下一页
                        initData();
                    }
                }
            });

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

    ////右上角image按钮添加监听,添加黑名单进数据库
    @OnClick(R.id.btn_add)
    public void onClick() {
        //添加黑名单
        startActivityForResult(new Intent(this,EditBlackNumberActivity.class),REQUEST_CODE_ADD);
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
