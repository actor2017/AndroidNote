
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mobilesafe_work.R;
import com.mobilesafe_work.receiver.AdminReceiver;
import com.mobilesafe_work.utils.ToastUtils;

public class Setup4Activity extends BaseGestureDetector {

    private ComponentName mComponent;//初始化组件,需要设备管理员组件AdminReceiver.java
    private DevicePolicyManager mDPM;//设备管理器
    private ImageView iv_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        iv_admin = (ImageView) findViewById(R.id.iv_admin);

        //初始化组件
        mComponent = new ComponentName(this, AdminReceiver.class);
        //设备管理器
        mDPM = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        findViewById(R.id.rl_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断有没有激活
                if (mDPM.isAdminActive(mComponent)) {
                    //取消激活
                    mDPM.removeActiveAdmin(mComponent);
                    iv_admin.setImageResource(R.drawable.admin_inactivated);
                } else {
                    //跳到设备管理器页面,激活管理员
                    //隐式意图
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    //额外的数据的名称,包前缀。     组件
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponent);
                    //说明
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            "来自Setup4Activity.java,显示在激活界面");
                    startActivity(intent);
                    //不能在这儿设置图片,因为有可能点击"取消"
                    //iv_admin.setImageResource(R.drawable.admin_activated);
                }
            }
        });
    }

    //初始化组件
    //oncreate->onstart->onresume->onpause->onstop->ondestroy
    //onstart: 1. 第一次创建activity时会进来, 进行数据回显;
    // 2. activity退到后台,重新进入时会走此方法, 进行图片更新
    @Override
    protected void onStart() {
        super.onStart();
        iv_admin.setImageResource(mDPM.isAdminActive(mComponent) ? R.drawable
                .admin_activated : R.drawable.admin_inactivated);
    }

    @Override
    public void goToPre() {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_pre_enter, R.anim.anim_pre_exit);
    }

    @Override
    public void goToNext() {
        if (!mDPM.isAdminActive(mComponent)) {
            ToastUtils.show(this,"设置了管理员才能下一波哦!");
            return;
        }
        startActivity(new Intent(this, Setup5Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_next_enter, R.anim.anim_next_exit);
    }
}
