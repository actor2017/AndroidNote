package cn.itcast.googleplay12.manager;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by zhengping on 2017/4/10,10:22.
 * //downloadUrl id fileName packageName totalSize
 //downloadedSize  downloadState  savePath
 */

public class DownloadInfo {

    public String fileName;
    public String downloadUrl;
    public String savePath;

    //    public String id;
    //    public String packageName;

    public long downloadedSize = 0;
    public Long totalSize;//下载前中赋值,有可能=null
    public int downloadState;

    /**
     * @param fileName
     * @param downloadUrl
     * @param totalSize 感觉不好
     */
    public DownloadInfo(String fileName, String downloadUrl, Long totalSize) {
        this.downloadUrl = downloadUrl;
        // TODO: 2018/10/24 还需判断fileName=null
        if (!TextUtils.isEmpty(fileName)) this.fileName = fileName;
        savePath = getFilePath(fileName);
        downloadState = MyDownloadManager.STATE_NONE;
        // TODO: 2018/10/24 totalSize需要传入,不科学
        this.totalSize = totalSize;
    }



//    public static DownloadInfo createDownloadInfoFromAppInfo(AppInfo appInfo) {
//        DownloadInfo downloadInfo = new DownloadInfo();
//        downloadInfo.downloadUrl = appInfo.downloadUrl;
//        downloadInfo.id = appInfo.id;
//        downloadInfo.fileName = appInfo.fileName;
//        downloadInfo.packageName = appInfo.packageName;
//        downloadInfo.totalSize = appInfo.size;
//        downloadInfo.downloadedSize = 0;
//        downloadInfo.downloadState = MyDownloadManager.STATE_NONE;
//        downloadInfo.savePath = getFilePath(appInfo.name);//      /sdcard/Gooleplay12/xxx.apk
//        return downloadInfo;
//    }

    public static String getFilePath(String name) {
        File sdcardRoot = Environment.getExternalStorageDirectory();
        File appDir = new File(sdcardRoot, "GooglePlay12");
        if(!appDir.exists() || appDir.isFile()) {
            appDir.mkdirs();//创建文件夹
        }

        File apkFile = new File(appDir,name + ".apk");
        return apkFile.getAbsolutePath();
    }
}
