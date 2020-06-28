package cn.itcast.googleplay12.manager;

import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.itcast.googleplay12.http.HttpHelper;
import cn.itcast.googleplay12.utils.IOUtils;

//import cn.itcast.googleplay12.bean.DownloadInfo;

/**
 * Created by zhengping on 2017/4/10,9:21.
 * 下载的管理类  单例
 */

public class MyDownloadManager {
    public static final int STATE_NONE = 0;//未下载
    public static final int STATE_WAITING = 1;//等待下载(核心线程都在运行,所以正在排队waiting)
    public static final int STATE_DOWNLOADING = 2;//正在下载
    public static final int STATE_PAUSE = 3;//下载暂停
    public static final int STATE_SUCCESS = 4;//下载成功
    public static final int STATE_ERROR = 5;//下载失败
    private static MyDownloadManager instance;
    private HashMap<String, DownloadInfo> savedDownloadInfo = new HashMap<>();
    private HashMap<String, MyRunnable> savedDownloadTask = new HashMap<>();

    private MyDownloadManager() {
        throw new RuntimeException("MyDownloadManager can't be new!");
    }

    public static synchronized MyDownloadManager getInstance() {
        if (instance == null) {
            instance = new MyDownloadManager();
        }
        return instance;
    }

    /**
     * @param downloadUrl 下载地址
     * @return 返回下载信息
     */
    public DownloadInfo getDownloadInfo(String downloadUrl) {
        return savedDownloadInfo.get(downloadUrl);
    }

    //downloadUrl id fileName packageName totalSize
    //downloadedSize  downloadState  savePath
    /**
     * 开始下载
     * @param fileName
     * @param downloadUrl
     */
    public void startDownload(String fileName, String downloadUrl, Long totalSize) {
        //我们此时其实需要的是DownloadInfo这个对象
        DownloadInfo downloadInfo = savedDownloadInfo.get(downloadUrl);
        if (downloadInfo == null) {
            downloadInfo = new DownloadInfo(fileName, downloadUrl, totalSize);
            savedDownloadInfo.put(downloadUrl, downloadInfo);
        }
        downloadInfo.downloadState = STATE_WAITING;
        MyRunnable myRunnable = savedDownloadTask.get(downloadUrl);
        if (myRunnable == null) {
            myRunnable = new MyRunnable(downloadInfo);
            savedDownloadTask.put(downloadUrl, myRunnable);
        }
//        MyRunnable task = new MyRunnable(downloadInfo);
//        savedDownloadTask.put(downloadUrl, task);

        notifyStateChanged(downloadInfo);
        MyThreadPoolManager.getInstance().execute(myRunnable);
    }

    public void pauseDownload(String downloadUrl) {
        //暂停下载就只需要把currentState变为STATE_PAUSE的状态，这样一来就会在任务的while循环中跳出
        DownloadInfo downloadInfo = savedDownloadInfo.get(downloadUrl);
        downloadInfo.downloadState = STATE_PAUSE;
        notifyStateChanged(downloadInfo);

        //如果这个下载的任务还没有线程来执行，此时要把这个任务从线程池的等待区域中移除
        MyRunnable task = savedDownloadTask.get(downloadUrl);
        MyThreadPoolManager.getInstance().cancel(task);
    }

    class MyRunnable implements Runnable {

        private DownloadInfo downloadInfo;

        public MyRunnable(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            FileOutputStream fos = null;
            try {
                //此时才代表正在的开始下载，因为run方法一旦执行，就代表这个任务有线程执行它了
                downloadInfo.downloadState = STATE_DOWNLOADING;
                notifyStateChanged(downloadInfo);

                //需要判断一下是否是第一次下载
                File apkFile = new File(downloadInfo.savePath);
                String url = "";
                if (!apkFile.exists() || downloadInfo.downloadedSize == 0 && downloadInfo
                        .downloadedSize != apkFile.length()) {
                    //第一次下载
                    apkFile.delete();
                    downloadInfo.downloadedSize = 0;
                    url = HttpHelper.URL + "download?fileName=" + downloadInfo.downloadUrl;
                } else {
                    //断点下载
                    url = HttpHelper.URL + "download?fileName=" + downloadInfo.downloadUrl +
                            "&range=" + downloadInfo.downloadedSize;
                }


                HttpHelper.HttpResult httpResult = HttpHelper.download(url);
                if (httpResult != null) {
                    //获取到服务器的反馈
                    InputStream inputStream = httpResult.getInputStream();//获取输入流
                    if (inputStream != null) {
                        fos = new FileOutputStream(apkFile,true);//true的含义代表是否从原有的文件后头进行追加
                        byte[] buffer = new byte[1024];//apk的大小如果不是1024的整数倍     30
                        int length = 0;
                        while ((length = inputStream.read(buffer)) != -1 && downloadInfo
                                .downloadState == STATE_DOWNLOADING) {
                            fos.write(buffer, 0, length);
                            downloadInfo.downloadedSize = downloadInfo.downloadedSize + length;
                            notifyStateChanged(downloadInfo);
                            fos.flush();
                        }

                        //下载结束
                        //需要判断一下是否下载成功
                        long serverFileSize = downloadInfo.totalSize;
                        long localFileSize = downloadInfo.downloadedSize;
                        if (serverFileSize == localFileSize) {
                            //下载成功
                            downloadInfo.downloadState = STATE_SUCCESS;
                            notifyStateChanged(downloadInfo);
                        } else {
                            //下载失败
                            //下载暂停
                            if (downloadInfo.downloadState == STATE_PAUSE) {
                                //下载暂停
                                downloadInfo.downloadState = STATE_PAUSE;
                                notifyStateChanged(downloadInfo);
                            } else {
                                //下载失败
                                downloadInfo.downloadState = STATE_ERROR;
                                notifyStateChanged(downloadInfo);
                            }
                        }

                    } else {
                        //此时代表，已经访问到服务器的servlet，但是服务器找不到它所需要的文件
                        //代表下载失败
                        downloadInfo.downloadState = STATE_ERROR;
                        notifyStateChanged(downloadInfo);
                    }
                } else {
                    //获取数据失败
                    downloadInfo.downloadState = STATE_ERROR;
                    notifyStateChanged(downloadInfo);
                }
            } catch (Exception e) {
                downloadInfo.downloadState = STATE_ERROR;
                notifyStateChanged(downloadInfo);
            } finally {
                IOUtils.close(fos);
            }

        }
    }

    private List<OnDownloadListener> downloadListeners = new ArrayList<>();

    /**
     * 下载状态改变
     */
    private void notifyStateChanged(DownloadInfo downloadInfo) {
        switch (downloadInfo.downloadState) {
            case STATE_WAITING:
                for (int i = 0; i < downloadListeners.size(); i++) {
                    downloadListeners.get(i).onWaiting();
                }
                break;
            case STATE_DOWNLOADING:
                for (int i = 0; i < downloadListeners.size(); i++) {
                    long current = downloadInfo.downloadedSize;
                    long total = 0;
                    try {
                        total = downloadInfo.totalSize;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Integer ratio = null;
                    if (total > 0) ratio = (int) (current * 100 / total);//0~1的值
                    downloadListeners.get(i).onDownloading(current, total, ratio);
                }
                break;
            case STATE_PAUSE:
                for (int i = 0; i < downloadListeners.size(); i++) {
                    downloadListeners.get(i).onPause();
                }
                break;
            case STATE_SUCCESS:
                for (int i = 0; i < downloadListeners.size(); i++) {
                    downloadListeners.get(i).onSuccess();
                }
                break;
            case STATE_ERROR:
                for (int i = 0; i < downloadListeners.size(); i++) {
                    downloadListeners.get(i).onError();
                }
                break;
        }
    }

    /**
     * 添加下载监听
     */
    public void addOnDownloadListener(OnDownloadListener listener) {
        if (listener != null && !downloadListeners.contains(listener)) {
            downloadListeners.add(listener);
        }
    }

    public interface OnDownloadListener {

        void onWaiting();

        /**
         * @param downloadedSize
         * @param totalSize
         * @param percent 如果百分比没有算出来,percent = null
         */
        void onDownloading(long downloadedSize, long totalSize, @Nullable Integer percent);
        void onPause();
        void onSuccess();
        void onError();
    }
}
