
public class FastReceiveActivity extends Activity {

	String[] data = new String[]{"正在开会","圣诞节快乐","元旦快乐","春节快乐","正在跪键盘，勿扰"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fast_receive);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//设置返回的数据
				Intent intent = new Intent();
				intent.putExtra("sms", data[position]);
				setResult(3, intent);
				//关闭当前activity
				finish();
			}
		});
	}
}
