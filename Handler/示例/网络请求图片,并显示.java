
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

//只能在主线中初始化控件，并且只能在主线程中修改控件
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
		//主线程创建的imageview
		iv = (ImageView) findViewById(R.id.iv);
	}
	//1. 在主线程中创建Handler对象
	Handler mHandler = new Handler(){
		//3.重写HandlerMessage方法
		public void handleMessage(Message msg) {
			//把位图显示在imageview上
			iv.setImageBitmap((Bitmap)msg.obj);//修改要放到主线程中
		};
	};
	
	/**
	 * 1. 获取用户输入的URL，做非空判断
	 * 2. 发起Http请求
	 * 3. 获取服务器返给客户端的二进制输入流
	 * 4. 把二进制输入流转成位图对象
	 */
	public void look(View v){
		// 获取用户输入的URL
		path = etPath.getText().toString().trim();
		//做非空判断
		if (TextUtils.isEmpty(path) || !path.startsWith("http")) {
			Toast.makeText(this, "亲，请输入正确的网址，例如:http://www.baidu.com", 0).show();
			return;
		}
		requestNetWork();
	}
	
	private void requestNetWork() {//开启线程,发起Http请求
		new Thread(){
			public void run() {
				try {
					// 1. 写一个URL
					URL url = new URL(path);
					// 2. 用这个URL打开连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					// 3. 设置请求参数
					//设置请求方式，默认不写就是get的方式
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(3000);
					// 4. 获取服务器返回的响应状态码
					// * 2xx 成功 3xxx重定向 4xxx客户端错误 5xxx服务器错误
					int code = conn.getResponseCode();
					if (code == 200) {
						// 5. 获取服务器返回的二进制输入流
						InputStream is = conn.getInputStream();
						// 把二进制输入流转成位图对象
						Bitmap bmp = BitmapFactory.decodeStream(is);
						//(2)在子线程中发消息
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
