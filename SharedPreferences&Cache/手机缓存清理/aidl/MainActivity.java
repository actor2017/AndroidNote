package cn.itcast.getcachedemo;

import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Method;

/**
 * 不知道和直接获取getCacheDir()的大小有啥区别没有?
 */
public static String getTotalCacheSize(Context context) throws Exception {
	long cacheSize = getFolderSize(context.getCacheDir());
	if (Environment.getExternalStorageState().equals(
			Environment.MEDIA_MOUNTED)) {
		cacheSize += getFolderSize(context.getExternalCacheDir());
	}
	return getFormatSize(cacheSize);
}
// 获取文件
// Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
// 目录，一般放一些长时间保存的数据
// Context.getExternalCacheDir() -->
// SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
public static long getFolderSize(File file) throws Exception {
	long size = 0;
	try {
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			// 如果下面还有文件
			if (fileList[i].isDirectory()) {
				size = size + getFolderSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return size;
}

public class MainActivity extends AppCompatActivity {

    private EditText etPackage;
    private Button btnOk;
    private TextView tvResult;
    private PackageManager mPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPackage = (EditText) findViewById(R.id.et_package);
        btnOk = (Button) findViewById(R.id.btn_ok);
        tvResult = (TextView) findViewById(R.id.tv_result);

        mPm = getPackageManager();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = etPackage.getText().toString().trim();

                //根据包名获取缓存信息
                //通过反射调用已经隐藏的方法
                // <uses-permission android:name="android.permission.GET_PACKAGE_SIZE"/>
                try {
                    Method method = mPm.getClass().getMethod("getPackageSizeInfo", String.class,
                            IPackageStatsObserver.class);
                    method.invoke(mPm, packageName, new MyObserver());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //final IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub()
            }
        });

    }

    class MyObserver extends IPackageStatsObserver.Stub {

        //运行子线程
        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws
                RemoteException {

            final long cacheSize = pStats.cacheSize;//缓存大小
            final long codeSize = pStats.codeSize;//apk大小
            final long dataSize = pStats.dataSize;//数据大小

            System.out.println("cacheSize:" + cacheSize);
            System.out.println("codeSize:" + codeSize);
            System.out.println("dataSize:" + dataSize);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvResult.setText("cacheSize:" + cacheSize + "\ncodeSize:" + codeSize + "\ndataSize:"
                            + dataSize);
                }
            });
        }
    }
}
