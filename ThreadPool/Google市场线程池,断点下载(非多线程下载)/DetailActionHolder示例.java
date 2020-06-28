package cn.itcast.googleplay12.holder;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;

import cn.itcast.googleplay12.R;
import cn.itcast.googleplay12.bean.AppInfo;
import cn.itcast.googleplay12.manager.DownloadInfo;
import cn.itcast.googleplay12.manager.MyDownloadManager;
import cn.itcast.googleplay12.utils.UiUtils;

/**
 * Created by zhengping on 2017/4/9,11:03.
 */

public class DetailActionHolder extends BaseHolder<AppInfo> implements View.OnClickListener {

    private Button fav;
    private Button share;
    private Button download_bt;
    private FrameLayout download_fl;
    private DownloadInfo downloadInfo;

    @Override
    public void refreshView() {
        downloadInfo = MyDownloadManager.getInstance().getDownloadInfo(data.downloadUrl);
        if(downloadInfo == null) {
            downloadInfo = new DownloadInfo(data.name, data.downloadUrl, data.size);
        }
        updateUi(downloadInfo);

    }

    @Override
    public View initView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.layout_detail_download, null);
        fav = (Button) view.findViewById(R.id.fav);
        share = (Button) view.findViewById(R.id.share);
        download_bt = (Button) view.findViewById(R.id.download_bt);
        download_fl = (FrameLayout) view.findViewById(R.id.download_fl);
        fav.setOnClickListener(this);
        share.setOnClickListener(this);
        download_bt.setOnClickListener(this);

        MyDownloadManager.getInstance().addOnDownloadListener(new MyDownloadManager.OnDownloadListener() {

            @Override
            public void onWaiting() {
                updateUi(downloadInfo);//简单处理
            }

            @Override
            public void onDownloading(long downloadedSize, long totalSize, @Nullable Integer
                    percent) {
                updateUi(downloadInfo);//简单处理
            }

            @Override
            public void onPause() {
                updateUi(downloadInfo);//简单处理
            }

            @Override
            public void onSuccess() {
                updateUi(downloadInfo);//简单处理
            }

            @Override
            public void onError() {
                updateUi(downloadInfo);//简单处理
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fav:
                break;
            case R.id.share:
                break;
            case R.id.download_bt:
                //CPU处理任务的能力是有限，主线程子线程的切换其实就是CPU时间片的分配，子线程太多的话，
                //会造成CPU分配给主线程的时间片太少。主线程来不及处理ui的操作
                int currentState = downloadInfo.downloadState;
                switch (currentState) {
                    case MyDownloadManager.STATE_NONE://开始下载
                        MyDownloadManager.getInstance().startDownload(data.name, data.downloadUrl, data.size);
                        break;
                    case MyDownloadManager.STATE_DOWNLOADING://暂停下载
                        MyDownloadManager.getInstance().pauseDownload(data.downloadUrl);
                        break;
                    case MyDownloadManager.STATE_SUCCESS://安装APK文件
                        installApk(data);
                        break;
                    case MyDownloadManager.STATE_ERROR://重新下载apk文件
                        MyDownloadManager.getInstance().startDownload(data.name, data.downloadUrl, data.size);
                        break;
                    case MyDownloadManager.STATE_WAITING://将等待的任务停止掉
                        MyDownloadManager.getInstance().pauseDownload(data.downloadUrl);
                        break;
                    case MyDownloadManager.STATE_PAUSE://继续下载
                        MyDownloadManager.getInstance().startDownload(data.name, data.downloadUrl, data.size);
                        break;
                }

                break;
        }
    }

    public void installApk(AppInfo appInfo) {
//        MyDownloadManager.DownloadInfo downloadInfo = savedDownloadInfo.get(appInfo.downloadUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(
                Uri.fromFile(new File(DownloadInfo.getFilePath(appInfo.name))),//downloadInfo.savePath
                "application/vnd.android.package-archive");
        UiUtils.getContext().startActivity(intent);
    }

    private void updateUi(DownloadInfo downloadInfo) {

        if(downloadInfo.downloadUrl.equals(data.downloadUrl)){
            this.downloadInfo = downloadInfo;
            //更新UI
            int currentState = downloadInfo.downloadState;
            long currentPosition = downloadInfo.downloadedSize;


            float ratio = currentPosition * 1.0f / downloadInfo.totalSize;//0~1的值
            //0.63  --> 63%
            String strPercent = (int)(ratio * 100) + "%";

            download_bt.setText(currentState + "," + strPercent);
        }
    }
}
