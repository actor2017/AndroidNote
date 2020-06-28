
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//ֻ���������г�ʼ���ؼ�������ֻ�������߳����޸Ŀؼ�
public class MainActivity extends Activity {

	private EditText	etPath;
	private ImageView	iv;
	private String	path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etPath = (EditText) findViewById(R.id.et_path);	
		etPath.setText("http://192.168.13.41:8080/nice/cool.jpg");
		//���̴߳�����imageview
		iv = (ImageView) findViewById(R.id.iv);
	}
	//1. �����߳��д���Handler����
	Handler mHandler = new Handler(){
		//3.��дHandlerMessage����
		public void handleMessage(Message msg) {
			//��λͼ��ʾ��imageview��
			iv.setImageBitmap((Bitmap)msg.obj);//�޸�Ҫ�ŵ����߳���
		};
	};
	
	/**
	 * 1. ��ȡ�û������URL�����ǿ��ж�
	 * 2. ����Http����
	 * 3. ��ȡ�����������ͻ��˵Ķ�����������
	 * 4. �Ѷ�����������ת��λͼ����
	 */
	public void look(View v){
		// ��ȡ�û������URL
		path = etPath.getText().toString().trim();
		//���ǿ��ж�
		if (TextUtils.isEmpty(path) || !path.startsWith("http")) {
			Toast.makeText(this, "�ף���������ȷ����ַ������:http://www.baidu.com", 0).show();
			return;
		}
		requestNetWork();
	}
	
	private void requestNetWork() {//�����߳�,����Http����
		new Thread(){
			public void run() {
				try {
					// 1. дһ��URL
					URL url = new URL(path);
					// 2. �����URL������
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					// 3. �����������
					//��������ʽ��Ĭ�ϲ�д����get�ķ�ʽ
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(3000);
					// 4. ��ȡ���������ص���Ӧ״̬��
					// * 2xx �ɹ� 3xxx�ض��� 4xxx�ͻ��˴��� 5xxx����������
					int code = conn.getResponseCode();
					if (code == 200) {
						// 5. ��ȡ���������صĶ�����������
						InputStream is = conn.getInputStream();
						// �Ѷ�����������ת��λͼ����
						Bitmap bmp = BitmapFactory.decodeStream(is);
						//(2)�����߳��з���Ϣ
						Message msg = new Message();
						msg.obj = bmp;
						mHandler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}
