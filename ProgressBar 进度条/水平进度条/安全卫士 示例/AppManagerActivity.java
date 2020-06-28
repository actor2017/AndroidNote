package cn.itcast.mobilesafe12.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.itcast.mobilesafe12.R;
import cn.itcast.mobilesafe12.view.ProgressView;

/**
 * 软件管理
 */
public class AppManagerActivity extends AppCompatActivity {

    @BindView(R.id.pv_rom)
    ProgressView pvRom;
    @BindView(R.id.pv_sdcard)
    ProgressView pvSdcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        ButterKnife.bind(this);

        pvRom.setTitle("内部存储:");
        pvSdcard.setTitle("外部存储:");

        initSpaceInfo();
    }

    //初始化空间信息
    private void initSpaceInfo() {
        //内部存储文件夹: /data
        File dataDirectory = Environment.getDataDirectory();
        long totalSpace = dataDirectory.getTotalSpace();//总空间
        long freeSpace = dataDirectory.getFreeSpace();//剩余空间
        long usedSpace = totalSpace - freeSpace;//已用空间

        //Formatter.formatFileSize: 可以将字节转为带单位的字符串
        pvRom.setLeftText(Formatter.formatFileSize(this, usedSpace) + "已用");
        pvRom.setRightText(Formatter.formatFileSize(this, freeSpace) + "可用");

        int progress = (int) (usedSpace * 100 / totalSpace);
        pvRom.setProgress(progress);

        //外部存储文件夹
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        long sdcardTotalSpace = externalStorageDirectory.getTotalSpace();//总空间
        long sdcardFreeSpace = externalStorageDirectory.getFreeSpace();//剩余空间
        long sdcardUsedSpace = sdcardTotalSpace - sdcardFreeSpace;//已用空间

        pvSdcard.setLeftText(Formatter.formatFileSize(this, sdcardUsedSpace) + "已用");
        pvSdcard.setRightText(Formatter.formatFileSize(this, sdcardFreeSpace) + "可用");

        int sdcardProgress = (int) (sdcardUsedSpace * 100 / sdcardTotalSpace);
        pvSdcard.setProgress(sdcardProgress);
    }
}
