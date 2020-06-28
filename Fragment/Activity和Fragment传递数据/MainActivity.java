public class MainActivity extends Activity implements MainFragment.FragmentResultListener {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		//setArguments方法必须在fragment创建以后，添加给Activity前完成。
		//千万不要，首先调用了add，然后设置arguments。
		Bundle bundle = new Bundle();
        bundle.putString("argument", "argument");
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
    }
	
	//实现接口，获取Fragment回调数据
	@Override
    public void process(String str) {
        if (str != null) textView.setText(str);
    }
}