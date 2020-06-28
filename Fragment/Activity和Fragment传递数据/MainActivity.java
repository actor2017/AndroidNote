public class MainActivity extends Activity implements MainFragment.FragmentResultListener {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		//setArguments����������fragment�����Ժ���Ӹ�Activityǰ��ɡ�
		//ǧ��Ҫ�����ȵ�����add��Ȼ������arguments��
		Bundle bundle = new Bundle();
        bundle.putString("argument", "argument");
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
    }
	
	//ʵ�ֽӿڣ���ȡFragment�ص�����
	@Override
    public void process(String str) {
        if (str != null) textView.setText(str);
    }
}