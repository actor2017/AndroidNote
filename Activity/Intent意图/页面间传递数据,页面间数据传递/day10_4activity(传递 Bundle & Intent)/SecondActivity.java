
public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		//接受传递过来的
		Intent intent = getIntent();
		int age = intent.getIntExtra("age", 0);
		String name = intent.getStringExtra("name");
		System.out.println("姓名："+name+" 年龄："+age);
	}
}
