package com.kuchuanyun.cpptest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String permissionInternet = "android.permission.INTERNET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		//1.ContextCompat.checkSelfPermission(this, permission);//检查权限
		//2.PermissionChecker.PERMISSION_GRANTED;//常量,是否有权限
		//3.ActivityCompat.requestPermissions(this, String[] permissions, requestCode);//申请权限,弹一个对话框
		//4.ActivityCompat.shouldShowRequestPermissionRationale(this, permission);//第一次请求就返回false 拒绝过返回true 或者 用户选择不再提示返回false
		//5.onRequestPermissionsResult();//请求权限后回调
		
		//checkSelfPermission("");//直接调用要23+
        int i = ContextCompat.checkSelfPermission(this, permissionInternet);//检查权限
        switch (i) {
            case PermissionChecker.PERMISSION_GRANTED://0同意
                break;
            case PermissionChecker.PERMISSION_DENIED://-1没有权限(targetSdkVersion>=23)
                //第一次请求就返回false 拒绝过返回true 或者 用户选择不再提示返回false
                boolean answer=  ActivityCompat.shouldShowRequestPermissionRationale(this, permissionInternet);
                //申请权限,弹一个对话框
                ActivityCompat.requestPermissions(this, new String[]{permissionInternet}, 1);
                break;
            case PermissionChecker.PERMISSION_DENIED_APP_OP://-2没有权限(targetSDKVersion<23)
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionInternet.equals(permissions[0]) && grantResults[0] != PermissionChecker.PERMISSION_GRANTED) {
            Toast.makeText(this, "未获取到上网权限", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
