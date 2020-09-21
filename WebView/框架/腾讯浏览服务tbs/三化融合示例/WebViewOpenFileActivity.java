package com.ly.changyi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.futils.annotation.view.ContentView;
import com.futils.annotation.view.ViewID;
import com.ly.changyi.R;
import com.ly.changyi.activity.BaseActivity;
import com.ly.changyi.utils.MyOkhttpUtils.MyOkHttpUtils;
import com.ly.changyi.utils.MyOkhttpUtils.OnGetFileListener;
import com.ly.changyi.view.titlebar.CommonTitleBar;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

/**
 * 能打开文件,比如.doc .xls .txt .pdf ...
 *
 * 使用:
 * Intent intent = new Intent(activity, WebViewOpenFileActivity.class);
 * intent.putExtra(WebViewOpenFileActivity.filePath, file);
 * activity.startActivity(intent);
 */
@ContentView(R.layout.activity_web_view_open_file)
public class WebViewOpenFileActivity extends BaseActivity {

    @ViewID(R.id.ctb_webview)
    CommonTitleBar titleBar;

    public static final String filePath = "filePath";//文件路径
    private TbsReaderView mTbsReaderView;
    private File file;

    @Override
    protected void onViewCreated(Bundle bundle) {

        titleBar.getCenterTextView().setEllipsize(TextUtils.TruncateAt.MARQUEE);//跑马灯

        mTbsReaderView = new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {
                //一直没打印出来, 不知道什么用
                println(integer);
                println(String.valueOf(o));
                println(String.valueOf(o1));
            }
        });
        RelativeLayout rootRl = findViewById(R.id.rl_root);
        rootRl.addView(mTbsReaderView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        Intent intent = getIntent();
        String path = intent.getStringExtra(filePath);
        if (path == null) return;
        if (path.startsWith("http://") || path.startsWith("https://")) {//需要先下载文件
            MyOkHttpUtils.getFile(path, null, null, new OnGetFileListener() {
                @Override
                public void onOk(File file1, int id) {
                    file = file1;
                    if (file != null && file.exists()) titleBar.getCenterTextView().setText(file.getName());
                    startView(file);
                }
            });
        } else {
            file = new File(path);
            if (file != null && file.exists()) titleBar.getCenterTextView().setText(file.getName());
            startView(file);
        }
    }

    private void startView(File file) {
        if (file.exists() && file.isFile()) {
            Bundle bundle1 = new Bundle();
            String path = file.getAbsolutePath();
            bundle1.putString(filePath, path);
            bundle1.putString("tempPath", getCacheDir().getAbsolutePath());
            String fileType = path.substring(path.lastIndexOf(".") + 1);
            boolean result = mTbsReaderView.preOpen(fileType, false);
            if (result) mTbsReaderView.openFile(bundle1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
    }

    @Override
    public void things(View view) {

    }
}
