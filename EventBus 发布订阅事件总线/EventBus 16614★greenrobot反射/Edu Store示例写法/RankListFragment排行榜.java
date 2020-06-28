
public class RankListFragment extends BaseFragment implements IRankListView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//①.所谓的注册，其实就是将当前这个对象放到一个集合中保存起来
    }

    //声明和注释你的订阅方法,选择指定线程模式(onCreate之后写)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusUpdateContacts(UpdateDownloadStatus downloadStatus) {	//②.方法名字随便起,随意
        if (downloadStatus.packageName != null) {
            uploadList();
        }
    }

    //注销文件下载监听
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//③.所谓的注销，其实就是将当前这个对象从集合中移除
        myAppAdapter = null;//在平板上退出app之后再打开,adapter有可能!=null
        myGameAdapter = null;//所以要么在这里=null,要么退出的时候加上System.exit(0);
    }
}
