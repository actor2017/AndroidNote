//startActivity, ���� Bundle & Intent
public class MainActivity extends Activity {

	/**
	 * ����������
	 * @param v
	 */
	public void send(View v){
		//������ʾ��ͼ
		Intent intent = new Intent(this, ThirdActivity.class);
		//����������������
		Bundle bundle = new Bundle();
		bundle.putString("name", name);
		bundle.putString("objname", objName);
		//�����������ø���ͼ
		intent.putExtra("b", bundle);
		
		startActivity(intent);
	}
	/**
	 * һ��һ�����ݵش���
	 * @param v
	 */
	public void pass(View v){
		Intent intent = new Intent(this, SecondActivity.class);
		//����Ҫ���ݵ�����
		intent.putExtra("name", name);
		intent.putExtra("age", age);
		startActivity(intent);
	}
}
