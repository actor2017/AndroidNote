package cn.itcast.mobilesafe12.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.itcast.mobilesafe12.R;
import cn.itcast.mobilesafe12.global.GlobalConstants;
import cn.itcast.mobilesafe12.utils.ToastUtils;

/**
 * 程序锁输入密码页面
 *
 * 1. 解决任务栈页面跳转bug,在清单文件中手动注册页面,启动模式设置成单例设计模式
 *
 <activity
 android:name=".activity.EnterPwdActivity"
 android:launchMode="singleInstance">
 </activity>

  2. 不让输入密码页面进入系统最近任务列表中

 android:excludeFromRecents="true"

 */
public class EnterPwdActivity extends AppCompatActivity {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    private String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pwd);
        ButterKnife.bind(this);

        //获取AppLockService.java发过来的包名
        packageName = getIntent().getStringExtra("package");

        PackageManager pm = getPackageManager();
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);

            String name = applicationInfo.loadLabel(pm).toString();
            Drawable icon = applicationInfo.loadIcon(pm);

            tvName.setText(name);
            ivIcon.setImageDrawable(icon);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_ok)
    public void onClick() {
        String pwd = etPwd.getText().toString().trim();
        if (!TextUtils.isEmpty(pwd)) {
            if (pwd.equals("123")) {
                //通知程序锁服务, 跳过当前app的验证
                //广播
                //当程序有很多页面或组件时, 某个页面发生变化之后,需要通知给其他页面或组件, 此时可以通过发广播来进行通知和数据传递
                //EventBus: 事件总线, 专用于组件之间的通信
                Intent intent = new Intent();
                intent.setAction(GlobalConstants.ACTION_APPLOCK_SKIP);
                intent.putExtra("package", packageName);

                sendBroadcast(intent);

                finish();//销毁页面, 让底下的应用页面展示出来
            } else {
                ToastUtils.show(this, "密码错误!");
            }

        } else {
            ToastUtils.show(this, "密码不能为空!");
        }
    }

    //拦截手机物理返回键
    @Override
    public void onBackPressed() {
        //跳到桌面
        //隐式意图
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);

        //销毁当前页面
        finish();
    }
}
