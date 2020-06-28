package com.yanzhenjie.permission.sample.base;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * AndPermission的使用:
 * 1.必须实现PermissionListener接口
 * 2.写申请权限的逻辑
 * 3.必须重写onRequestPermissionsResult(),且转给AndPermission分析结果
 * 4.如果用户勾选了"不再提示",并且我们叫用户到设置界面去手动给权限,那么可以重写onActivityResult()判断是否从设置界面回来
 */
public class testActivity extends AppCompatActivity implements PermissionListener {

    private static final int REQUEST_CODE_SETTING = 300;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Button btn = new Button(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 申请单个权限。
                AndPermission.with(testActivity.this)
                        .requestCode(100)
                        .permission(Manifest.permission.WRITE_CALENDAR)
                        //rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale(rationaleListener)
                        .send();
            }
        });
    }

    /**
     * 默认的对话框
     */
    private RationaleListener rationaleListener = (requestCode, rationale) ->
            // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
            AndPermission.rationaleDialog(testActivity.this, rationale).show();

    /**
     * Rationale支持，这里自定义对话框。
     */
    /*private RationaleListener rationaleListener = (requestCode, rationale) -> {
        // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：
        // AndPermission.rationaleDialog(Context, Rationale).show();

        // 自定义对话框。
        AlertDialog.build(this)
                .setTitle("友情提示")
                .setMessage("您已拒绝过定位权限，没有定位权限无法为您推荐附近妹子，赶快定位权限给我！")
                .setPositiveButton("好，给你", (dialog, which) -> {
                    dialog.cancel();
                    rationale.resume();
                })
                .setNegativeButton("我拒绝", (dialog, which) -> {
                    dialog.cancel();
                    rationale.cancel();
                }).show();
    };*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * 转给AndPermission分析结果。
         *
         * @param requestCode  请求码。
         * @param permissions  权限数组，一个或者多个。
         * @param grantResults 请求结果。
         * @param listener PermissionListener 对象。
         */
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {//300
                Toast.makeText(this, "用户从设置回来了", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
        Toast.makeText(this, "申请权限成功!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        Toast.makeText(this, "申请权限失败!", Toast.LENGTH_LONG).show();

        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING).show();

            // 第二种：用自定义的提示语。
//             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                     .setTitle("权限申请失败")
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                     .setPositiveButton("好，去设置")
//                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingService = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingService.execute();
//            你的dialog点击了取消调用：
//            settingService.cancel();
        }
    }
}
