
/**
 * 程序锁页面
 */
public class AppLockActivity extends AppCompatActivity {

    @BindView(R.id.tv_lock_num)
    TextView tvLockNum;         //2个页面切换的时候,ListView上的灰色标题显示
    @BindView(R.id.lv_unlock)
    ListView lvUnlock;          //未加锁的ListView
    @BindView(R.id.lv_lock)
    ListView lvLock;            //已加锁的ListView
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;     //加载的自定义旋转进度条
    private AppLockDao mDao;

    private ArrayList<AppInfo> mUnlockList;
    private ArrayList<AppInfo> mLockList;

    private AppLockAdapter mUnlockAdapter;
    private AppLockAdapter mLockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock);
        ButterKnife.bind(this);

        mDao = AppLockDao.getInstance(this);
        inidData();
    }

    //初始化数据
    private void inidData() {
        //AsyncTask:异步任务  =  Thread  + Handler
        //new AppLockTask().execute();//启动异步任务
        new AppLockTask().execute("http://www.baidu.com", 100);//传递参数给下面的Object,这儿是测试
    }

    /**
     * 三个泛型(了解):
     * //public final AsyncTask<Params, Progress, Result> execute(Params... params) {
     * //params:The parameters of the task.任务的参数
     * 1. Params传参的类型, 和doInBackground的参数类型一致
     * 2. Progress更新进度数据的类型, 和onProgressUpdate类型一致
     * 3. Result返回值类型, 和doInBackground返回值类型一致, 和 onPostExecute 参数类型一致
     */
    //把<void, void, void>改成<Object, Integer, String>
    class AppLockTask extends AsyncTask<Object, Integer, String> {

        //在主线程,预加载, 加载之前的准备工作-->热身
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llLoading.setVisibility(View.VISIBLE);

            System.out.println("onPreExecute");
        }

        //在子线程,后台加载-->开跑
        @Override
        protected String doInBackground(Object... params) {
            //获取传递进来的参数
            String url = (String) params[0];    //"http://www.baidu.com"
            Integer i = (Integer) params[1];    //100

            System.out.println("doInBackground:" + url + ";;" + i);

            //1. 获取所有已安装的app
            //2. 遍历这个集合, 判断是否已加锁
            ArrayList<AppInfo> installedApps = AppInfoProvider.getInstalledApps
                    (getApplicationContext());

            mUnlockList = new ArrayList<AppInfo>();
            mLockList = new ArrayList<AppInfo>();

            int progress = 0;

            for (AppInfo installedApp : installedApps) {
                if (mDao.find(installedApp.packageName)) {
                    //已加锁
                    mLockList.add(installedApp);
                } else {
                    //未加锁
                    mUnlockList.add(installedApp);
                }

                progress++;

                //通知进度更新, 此方法会将参数传递到onProgressUpdate中
                publishProgress(progress);
            }

            return "success";//此结果会返回到 onPostExecute 中
        }

        //在主线程,更新进度-->跨栏
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Integer progress = values[0];

            System.out.println("当前进度:" + progress);
        }

        //在主线程,加载结束之后,更新界面的回调-->冲刺
        @Override
        protected void onPostExecute(String result) {
            System.out.println("onPostExecute:" + result);  //onPostExecute:success

            super.onPostExecute(result);
            //设置未加锁数据
            mUnlockAdapter = new AppLockAdapter(false);
            lvUnlock.setAdapter(mUnlockAdapter);

            //设置已加锁数据
            mLockAdapter = new AppLockAdapter(true);
            lvLock.setAdapter(mLockAdapter);

            llLoading.setVisibility(View.GONE);
        }
    }
}
