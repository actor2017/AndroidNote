
public class FastReceiveActivity extends Activity {

	String[] data = new String[]{"���ڿ���","ʥ���ڿ���","Ԫ������","���ڿ���","���ڹ���̣�����"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fast_receive);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//���÷��ص�����
				Intent intent = new Intent();
				intent.putExtra("sms", data[position]);
				setResult(3, intent);
				//�رյ�ǰactivity
				finish();
			}
		});
	}
}
