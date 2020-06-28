package com.kuchuan.education.utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 静默安装
 * 注意事项:在子线程中调用
 * new Thread(new Runnable() {
 *     @Override
 *     public void run() {
 *         SilentInstall.install(apkPath);
 *     }
 * }).start();
 */
public class SilentInstall {

    public static boolean install(@NonNull String packageName, @NonNull String apkPath) {
        if (TextUtils.isEmpty(apkPath)) {
            return false;
        }
        if (!apkPath.toLowerCase().endsWith(".apk")) {
            return false;
        }
        boolean resault = false;
        Process p;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// 判断版本大于等于7.0
                p = Runtime.getRuntime().exec("pm install -i " + packageName +" --user 0 " + apkPath);
            } else {
                p = Runtime.getRuntime().exec("pm install -r "+apkPath);
            }
            int status = p.waitFor();
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                buffer.append(line);
                System.out.println("Return ============" + buffer.toString());
                if (status == 0) {
                    resault = true;
                } else {
                    resault = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return resault;
        }
        return resault;
    }
}