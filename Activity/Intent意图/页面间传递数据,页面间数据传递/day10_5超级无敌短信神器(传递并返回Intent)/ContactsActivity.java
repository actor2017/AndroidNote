
public class ContactsActivity extends Activity {

	String[] data= new String[]{"110","121","119","114","120"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			//点击listview条目后回到
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(ContactsActivity.this, "pos:"+position, 0).show();
				//2. 设置要返回的数据
				String phoneNum = data[position];
				Intent intent = new Intent();
				intent.putExtra("num", phoneNum);
				setResult(2, intent);
				//3.关闭目标activity
				finish();
			}
		});
	}
}
