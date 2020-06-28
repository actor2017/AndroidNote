import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ProcessManagerActivity extends AppCompatActivity {

    @BindView(R.id.lv_list)
    StickyListHeadersListView lvList;       //粘性ListView

    private int runningProcessNum;      //正在运行的进程数
    private long availMemory;           //已用内存
    private int totalProcessNum;        //总进程数
    private long totalMemory;           //总内存
    private boolean isShowSystem;       //是否显示系统进程
    private ProcessAdapter mAdapter;    //粘性ListView的适配器
    private ArrayList<ProcessInfoProvider.ProcessInfo> runningProcess;

    private ArrayList<ProcessInfoProvider.ProcessInfo> userProcess;
    private ArrayList<ProcessInfoProvider.ProcessInfo> systemProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_manager);
        ButterKnife.bind(this);

        //运行进程数
        runningProcessNum = ProcessInfoProvider.getRunningProcessNum(this);
        //总进程数
        totalProcessNum = ProcessInfoProvider.getTotalProcessNum(this);
        //可用内存
        availMemory = ProcessInfoProvider.getAvailMemory(this);
        //总内存
        totalMemory = ProcessInfoProvider.getTotalMemory(this);

        //初始化显示信息
        initInfo();
        //初始化数据,给ListView设置适配器
        initData();
        //初始化条目监听
        initItemClick();

        //监听抽屉打开和关闭的事件
        mDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                //ivArrow1
            }
        });
        mDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {

            }
        });
    }


    //初始化数据
    private void initInfo() {

        //获取是否显示系统进程,和初始化是否显示系统进程的开关.
        if (GetSharedPreferencesUtils.getBoolean(this, GlobalConstants.IS_SHOW_SYSTEM_PROGRESS,
                false)) {
            isShowSystem = true;
            sivShowSystem.setToggleState(true);
            GetSharedPreferencesUtils.putBoolean(this, GlobalConstants.IS_SHOW_SYSTEM_PROGRESS,
                    true);
        } else {
            isShowSystem = false;
            sivShowSystem.setToggleState(false);
            GetSharedPreferencesUtils.putBoolean(this, GlobalConstants.IS_SHOW_SYSTEM_PROGRESS,
                    false);
        }

        //获取是否锁屏自动清理进程,和初始化显示开关
        if (GetSharedPreferencesUtils.getBoolean(this, GlobalConstants.IS_AUTP_KILL, false)) {
            sivAutoKill.setToggleState(true);
            GetSharedPreferencesUtils.putBoolean(this, GlobalConstants.IS_AUTP_KILL, true);
        } else {
            sivAutoKill.setToggleState(false);
            GetSharedPreferencesUtils.putBoolean(this, GlobalConstants.IS_AUTP_KILL, false);
        }

        pvProcessNum.setTitle("进程数:");
        pvProcessNum.setLeftText("正在运行:" + runningProcessNum);
        pvProcessNum.setRightText("总进程个数:" + totalProcessNum);
        pvProcessNum.setProgress(runningProcessNum);
        pvProcessNum.setMaxProgress(totalProcessNum);

        pvMemory.setTitle("内    存:");
        pvMemory.setLeftText("已用内存:" + Formatter.formatFileSize(this, totalMemory - availMemory));
        pvMemory.setRightText("总内存:" + Formatter.formatFileSize(this, totalMemory));
        long usedMemory = totalMemory - availMemory;    //已用内存
        pvMemory.setProgress((int) (usedMemory * 100 / totalMemory));
    }

    //初始化数据
    private void initData() {
        llLoading.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //1.获取所有的进程集合
                runningProcess = ProcessInfoProvider.getRunningProcess(ProcessManagerActivity.this);
                //用户进程
                userProcess = new ArrayList<>();
                //系统进程
                systemProcess = new ArrayList<>();

                //遍历集合
                for (ProcessInfoProvider.ProcessInfo processInfo : runningProcess) {

                    //如果是用户进程
                    if (processInfo.isUserProcess) {
                        userProcess.add(processInfo);
                    } else {
                        //获取系统进程
                        systemProcess.add(processInfo);
                    }
                }
                //对runningProcess排序,这儿不要卸载for循环里面,并发修改异常,修改了半天
                runningProcess.removeAll(systemProcess);
                runningProcess.addAll(systemProcess);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //初始化粘性的ListView
                        mAdapter = new ProcessAdapter();
                        //---------------------StickyListHeadersAdapter★
                        lvList.setAdapter(mAdapter);
                        llLoading.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    /**
     * 初始化条目监听,注意view是条目的对象,findViewById()就能找到CheckBox
     */
    private void initItemClick() {
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.点击后切换条目开关的状态
                //2.如果是选中状态,把条目存入"选中集合",否则移出集合
                ProcessInfoProvider.ProcessInfo info = runningProcess.get(position);
                //状态取反
                info.isChecked = !info.isChecked;
                //更新条目的checkBox,注意这种写法
                CheckBox cb_check = (CheckBox) view.findViewById(R.id.cb_check);
                cb_check.setChecked(info.isChecked);
            }
        });
    }

    /**
     * 粘性ListView的适配器
     */
    class ProcessAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        /**
         * 头布局,即{ 标题栏布局 }
         */
        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            TitleViewHolder titleHolder;
            if (convertView == null) {
                convertView = View.inflate(ProcessManagerActivity.this, R.layout.item_title, null);
                titleHolder = new TitleViewHolder(convertView);
                convertView.setTag(titleHolder);
            } else {
                titleHolder = (TitleViewHolder) convertView.getTag();
            }
            ProcessInfoProvider.ProcessInfo info = getItem(position);
            if (info.isUserProcess) {
                titleHolder.tvTitle.setText("用户进程:" + userProcess.size());
            } else {
                titleHolder.tvTitle.setText("系统进程:" + systemProcess.size());
            }
            return convertView;
        }

        /**
         * 返回标题栏布局的id,用户进程0, 系统进程1
         */
        @Override
        public long getHeaderId(int position) {
            ProcessInfoProvider.ProcessInfo info = getItem(position);
            return info.isUserProcess ? 0 : 1;
        }

        /**
         * 如果设置了显示系统进程,返回所有集合的长度,否则返回用户应用的长度
         */
        @Override
        public int getCount() {
            if (isShowSystem) { //抽屉里的开关
                //显示系统进程
                return runningProcess.size();
            } else {
                return userProcess.size();//只返回用户集合的大小
            }
        }

        /**
         * 返回条目的对象
         */
        @Override
        public ProcessInfoProvider.ProcessInfo getItem(int position) {
            return runningProcess.get(position);
        }

        //返回条目的ID
        @Override
        public long getItemId(int position) {
            return position;
        }

        //返回条目布局
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(ProcessManagerActivity.this, R.layout
                        .item_processinfo, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ProcessInfoProvider.ProcessInfo info = getItem(position);
            viewHolder.tvName.setText(info.name);
            viewHolder.tvMemory.setText(Formatter.formatFileSize(ProcessManagerActivity.this,
                    info.memory));
            viewHolder.ivIcon.setImageDrawable(info.icon);
            //手机卫士不显示勾选框
            if (info.packageName.equals(getPackageName())) {
                viewHolder.cbCheck.setVisibility(View.GONE);
            } else {
                //这儿必须重写,因为涉及到要重用
                viewHolder.cbCheck.setVisibility(View.VISIBLE);
            }

            //注意:这儿一定要初始化,因为重用了ListView,checkBox也会重用,所以一定要判断★★★★★★★
            viewHolder.cbCheck.setChecked(info.isChecked);
            return convertView;
        }

    }

    static class TitleViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        TitleViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_memory)
        TextView tvMemory;
        @BindView(R.id.cb_check)
        CheckBox cbCheck;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
