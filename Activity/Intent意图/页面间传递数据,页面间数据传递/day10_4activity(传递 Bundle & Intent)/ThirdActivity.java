
public class ThirdActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		
		//��ȡ��ͼ
		Intent intent = getIntent();
		//����ͼ��ȡ������
		Bundle bundle = intent.getBundleExtra("b");
		//���������л�ȡ����
		String name = bundle.getString("name");
		//��ȡ����������(��������)
		String objName = bundle.getString("objname");
		
		String text = name + objName;
		byte[] b = text.getBytes();
		int total = -100;
		for (byte c : b) {
			total += c;
		}
		//ȡ����ֵ�����⸺����ģ101�����ֵ����100
		total = Math.abs(total) % 101;
		
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText(name + "��" + objName + "����ԵֵΪ��" + total + ",ʵ������֮��,�����Ե������һ��");
	}
}
