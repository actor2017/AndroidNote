
public class ThirdActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		
		//获取意图
		Intent intent = getIntent();
		//用意图获取数据捆
		Bundle bundle = intent.getBundleExtra("b");
		//在数据捆中获取姓名
		String name = bundle.getString("name");
		//获取你对象的姓名(用来算命)
		String objName = bundle.getString("objname");
		
		String text = name + objName;
		byte[] b = text.getBytes();
		int total = -100;
		for (byte c : b) {
			total += c;
		}
		//取绝对值，避免负数，模101，最大值就是100
		total = Math.abs(total) % 101;
		
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText(name + "和" + objName + "的姻缘值为：" + total + ",实乃天作之合,天赐良缘、天生一对");
	}
}
