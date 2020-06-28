package cn.itcast.mobilesafe12.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.itcast.mobilesafe12.R;
import cn.itcast.mobilesafe12.utils.ToastUtils;

/**
 * 清理缓存
 */
public class CleanCacheActivity extends AppCompatActivity {

    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.ll_progress)
    LinearLayout llProgress;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.ll_result)
    LinearLayout llResult;
    @BindView(R.id.btn_clear)
    Button btnClear;

    private PackageManager mPm;

    private ArrayList<CacheInfo> mList;
    private ArrayList<CacheInfo> mCacheList;

    private CacheAdapter mAdapter;
    private CacheTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_cache);
        ButterKnife.bind(this);

        mPm = getPackageManager();

        //startScan();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startScan();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTask.stop();
    }

    //开始扫描
    private void startScan() {
        mTask = new CacheTask();
        mTask.execute();
    }

    @OnClick({R.id.btn_scan, R.id.btn_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                //快速扫描
                startScan();
                break;
            case R.id.btn_clear:
                //立即清理
                //LRU: least recenttly used: 最近最少使用算法
                //a, b, c, b, d, e---> a
                //freeStorageAndNotify: 释放存储空间, Integer.Max_Value==1.9G
                //Long.MAX_Value
                //原理: 向系统索要足够大的空间, 那么系统就会清理全部缓存来凑空间, 从而达到清理缓存的目的
                //明修栈道暗度陈仓
                //<uses-permission android:name="android.permission.CLEAR_APP_CACHE"/>
                try {
                    Method method = mPm.getClass().getMethod
                            ("freeStorageAndNotify", long.class,
                                    IPackageDataObserver.class);
                    method.invoke(mPm, Long.MAX_VALUE, new IPackageDataObserver.Stub() {

                        @Override
                        public void onRemoveCompleted(String packageName, boolean succeeded)
                                throws RemoteException {
                            //succeeded:是否请求空间成功!
                            System.out.println("清理缓存完成!" + succeeded);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.show(getApplicationContext(), "清理成功!");
                                    //重新扫描, 注意: 要在主线程启动AsyncTask
                                    startScan();
                                }
                            });

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    class CacheTask extends AsyncTask<Void, CacheInfo, Void> {

        private int totalCount;
        private int progress;

        private boolean isStop;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mList = new ArrayList<>();
            mCacheList = new ArrayList<>();

            //设置列表数据
            rcvList.setLayoutManager(new LinearLayoutManager(CleanCacheActivity.this));
            mAdapter = new CacheAdapter();
            rcvList.setAdapter(mAdapter);

            //启动扫描动画
            //移动动画: 注意, x方向不变, y方向相对父控件(RELATIVE_TO_PARENT)从0到1
            TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation
                    .RELATIVE_TO_PARENT, 1);
            anim.setDuration(1000);
            anim.setRepeatCount(Animation.INFINITE);
            anim.setRepeatMode(Animation.REVERSE);

            ivLine.startAnimation(anim);

            //显示进度布局, 隐藏结果布局
            llProgress.setVisibility(View.VISIBLE);
            llResult.setVisibility(View.GONE);

            //禁用立即清理按钮
            btnClear.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            //遍历所有已安装app, 获取每个app的缓存大小
            List<PackageInfo> installedPackages = mPm.getInstalledPackages(0);

            totalCount = installedPackages.size();
            progress = 0;
            for (PackageInfo installedPackage : installedPackages) {
//                CacheInfo info = new CacheInfo();
                String packageName = installedPackage.packageName;
                //根据包名获取缓存大小
                //根据包名获取缓存信息
                //通过反射调用已经隐藏的方法
                // <uses-permission android:name="android.permission.GET_PACKAGE_SIZE"/>
                try {
                    Method method = mPm.getClass().getMethod("getPackageSizeInfo", String.class,
                            IPackageStatsObserver.class);
                    method.invoke(mPm, packageName, new MyObserver());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progress++;

                if (isStop) {
                    break;
                }

                SystemClock.sleep(200);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(CacheInfo... values) {
            super.onProgressUpdate(values);

            if (isStop) {
                return;
            }

            mAdapter.notifyDataSetChanged();
            rcvList.smoothScrollToPosition(mList.size());

            CacheInfo info = values[0];

            //1. 更新图标
            ivIcon.setImageDrawable(info.icon);
            //2. 更新进度
            int percent = progress * 100 / totalCount;
            pbProgress.setProgress(percent);
            //3. 更新应用名称
            tvName.setText(info.name);
            //4. 更新缓存大小
            tvCache.setText("缓存大小:" + Formatter.formatFileSize(getApplicationContext(), info
                    .cacheSize));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (isStop) {
                return;
            }

            // System.out.println("cache list:" + mList);
            rcvList.smoothScrollToPosition(0);

            //停止扫描动画
            ivLine.clearAnimation();

            //显示结果布局
            llProgress.setVisibility(View.GONE);
            llResult.setVisibility(View.VISIBLE);

            //计算缓存大小
            long totalCache = 0;
            for (CacheInfo info : mCacheList) {
                totalCache += info.cacheSize;
            }

            tvResult.setText(String.format("发现%d处缓存,共%s", mCacheList.size(), Formatter
                    .formatFileSize(getApplicationContext(), totalCache)));

            //启用立即清理按钮
            btnClear.setEnabled(true);
        }

        //装饰者设计模式
        //由于外部的类无法直接访问publishProgress(protected), 所以可以对此方法进行包装, 提高访问权限
        public void updateProgress(CacheInfo info) {
            publishProgress(info);
        }

        public void stop() {
            isStop = true;
        }
    }

    class MyObserver extends IPackageStatsObserver.Stub {

        //运行子线程
        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws
                RemoteException {
            CacheInfo info = new CacheInfo();

            String packageName = pStats.packageName;
            info.packageName = packageName;

            long cacheSize = pStats.cacheSize;//缓存大小
            info.cacheSize = cacheSize;

            try {
                ApplicationInfo applicationInfo = mPm.getApplicationInfo(packageName, 0);
                info.name = applicationInfo.loadLabel(mPm).toString();
                info.icon = applicationInfo.loadIcon(mPm);

                //某些高版本系统至少给每个app预留12KB缓存无法清理
                //此处操作可以让用户认为我们已经清理啦
                info.cacheSize -= 12 * 1024;
                if (info.cacheSize < 0) {
                    info.cacheSize = 0;
                }

                if (info.cacheSize > 0) {
                    //有缓存
                    mList.add(0, info);
                    mCacheList.add(info);
                } else {
                    mList.add(info);
                }

                //刷新列表
                mTask.updateProgress(info);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    class CacheInfo {
        public String packageName;
        public String name;
        public Drawable icon;
        public long cacheSize;

        @Override
        public String toString() {
            return "CacheInfo{" +
                    "name='" + name + '\'' +
                    ", cacheSize=" + cacheSize +
                    '}';
        }
    }

    class CacheAdapter extends RecyclerView.Adapter<CacheHolder> {

        @Override
        public CacheHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(CleanCacheActivity.this).inflate(R.layout
                    .item_cache, parent, false);
            return new CacheHolder(view);
        }

        @Override
        public void onBindViewHolder(CacheHolder holder, int position) {
            final CacheInfo info = mList.get(position);
            holder.ivIcon.setImageDrawable(info.icon);
            holder.tvName.setText(info.name);
            holder.tvCache.setText("缓存大小:" + Formatter.formatFileSize(getApplicationContext(),
                    info.cacheSize));

            if (info.cacheSize > 0) {
                holder.ivClear.setVisibility(View.VISIBLE);
                holder.ivClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //清理单个app的缓存
                        Intent infoIntent = new Intent();
                        infoIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", info.packageName, null);
                        infoIntent.setData(uri);
                        startActivity(infoIntent);
                    }
                });

            } else {
                holder.ivClear.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

    }

    static class CacheHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_cache)
        TextView tvCache;
        @BindView(R.id.iv_clear)
        ImageView ivClear;

        public CacheHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
