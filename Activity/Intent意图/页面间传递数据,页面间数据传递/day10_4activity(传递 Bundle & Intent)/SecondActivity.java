
public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		//���ܴ��ݹ�����
		Intent intent = getIntent();
		int age = intent.getIntExtra("age", 0);
		String name = intent.getStringExtra("name");
		System.out.println("������"+name+" ���䣺"+age);
	}
}
