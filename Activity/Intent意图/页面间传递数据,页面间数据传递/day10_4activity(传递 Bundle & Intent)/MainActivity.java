//startActivity, 传递 Bundle & Intent
public class MainActivity extends Activity {

	/**
	 * 传递数据捆
	 * @param v
	 */
	public void send(View v){
		//创建显示意图
		Intent intent = new Intent(this, ThirdActivity.class);
		//用数据捆传递数据
		Bundle bundle = new Bundle();
		bundle.putString("name", name);
		bundle.putString("objname", objName);
		//把数据捆设置改意图
		intent.putExtra("b", bundle);
		
		startActivity(intent);
	}
	/**
	 * 一个一个数据地传递
	 * @param v
	 */
	public void pass(View v){
		Intent intent = new Intent(this, SecondActivity.class);
		//设置要传递的数据
		intent.putExtra("name", name);
		intent.putExtra("age", age);
		startActivity(intent);
	}
}
