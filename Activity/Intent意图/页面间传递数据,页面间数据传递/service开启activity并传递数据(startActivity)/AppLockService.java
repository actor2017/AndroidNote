
public class AppLockService extends AccessibilityService {

    @Override
    public void onCreate() {
        super.onCreate();
        //如果不跳过,跳到输入密码的界面
		Intent intent = new Intent(this, EnterPwdActivity.class);
		intent.putExtra("package", packageName);

		//如果从service中启动activity, 需要加标记,FLAG_ACTIVITY_NEW_TASK, 表示新建一个任务栈
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//异常:Calling startActivity() from outside of an Activity  context requires the
		// FLAG_ACTIVITY_NEW_TASK
		startActivity(intent);
    }
}
