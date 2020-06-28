package com.kuchuan.wisdompolice.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.kuchuan.wisdompolice.R;
import com.kuchuan.wisdompolice.utils.MediaUtility;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kuchuan.wisdompolice.R.id.tv_title;

/**
 * 没牌扫描出来后(or 门牌信息管理点击后),跳过来
 */
public class DoorResultActivity extends BaseActivity {

    @BindView(tv_title)
    TextView tvTitle;
    @BindView(R.id.wv_webview)
    WebView wvWebview;
    @BindView(R.id.pb_progressbar)
    ProgressBar pbProgressbar;
    private Intent intent;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_result);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.blue_titlebar));
        pbProgressbar.setVisibility(View.GONE);
        intent = getIntent();
        url = intent.getStringExtra("url");
        initWebView(wvWebview);//支持:双击 & 缩放 & javaScript
        SetWebViewClient(wvWebview);//开始加载 & 跳转链接 & 加载结束
        SetWebChromeClient(wvWebview);//获取进度,标题,js的alert弹窗...★★这里面很多回调方法★★
        wvWebview.loadUrl(url);
    }

    //初始化WebView
    private void initWebView(WebView webView) {
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setHorizontalScrollbarOverlay(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.requestFocus();

        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);  // 显示缩放按钮(wap网页不支持)
        settings.setUseWideViewPort(true);      // 支持双击缩放(wap网页不支持)
        settings.setJavaScriptEnabled(true);    // 支持js功能
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);      //设置缓存

        settings.setDomStorageEnabled(true);            // 开启 DOM storage API 功能
        settings.setDatabaseEnabled(true);            //开启 database storage API 功能
        settings.setAppCacheEnabled(true);            //开启 Application Caches 功能
        settings.setAllowFileAccess(true);
        settings.setSavePassword(true);
        settings.setSupportZoom(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    }

    //设置WebViewClient
    private void SetWebViewClient(WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            // 开始加载网页
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbProgressbar.setVisibility(View.VISIBLE);
                pbProgressbar.setProgress(0);
            }

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbProgressbar.setVisibility(View.GONE);
            }

            // 所有链接跳转会走此方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);// 在跳转链接时强制在当前webview中加载
                return true;
            }

            // 加载网页失败时处理
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                pbProgressbar.setVisibility(View.GONE);
            }
        });
    }

    //设置WebChromeClient,★★这里面有很多回调方法★★
    private MyFileWebChromeClient myFileWebChromeClient;
    private void SetWebChromeClient(WebView webView) {
        myFileWebChromeClient = new MyFileWebChromeClient();
        webView.setWebChromeClient(myFileWebChromeClient);
    }

    private static final int REQUEST_FILE_PICKER = 1;
    private class MyFileWebChromeClient extends WebChromeClient {
        ValueCallback<Uri> mFilePathCallback;
        ValueCallback<Uri[]> mFilePathCallbacks;

        // 进度发生变化
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            pbProgressbar.setProgress(newProgress);
        }

        // 网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            tvTitle.setText(title);
        }

        // Android < 3.0 调用这个方法
        public void openFileChooser(ValueCallback<Uri> filePathCallback) {
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_PICKER);
        }
        // 3.0 + 调用这个方法
        public void openFileChooser(ValueCallback filePathCallback, String acceptType) {
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_PICKER);
        }
        //  / js上传文件的<input type="file" name="fileField" id="fileField" />事件捕获
        // Android > 4.1.1 调用这个方法
        public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_PICKER);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            mFilePathCallbacks = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "File Chooser"), REQUEST_FILE_PICKER);
            return true;
        }
    }

    //接收选择文件返回的图片地址
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_FILE_PICKER) {
            if (myFileWebChromeClient.mFilePathCallback != null) {
                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
                if (result != null) {
                    String path = MediaUtility.getPath(getApplicationContext(), result);
                    Uri uri = Uri.fromFile(new File(path));
                    myFileWebChromeClient.mFilePathCallback.onReceiveValue(uri);
                } else {
                    myFileWebChromeClient.mFilePathCallback.onReceiveValue(null);
                }
            }
            if (myFileWebChromeClient.mFilePathCallbacks != null) {
                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
                if (result != null) {
                    String path = MediaUtility.getPath(getApplicationContext(), result);
                    Uri uri = Uri.fromFile(new File(path));
                    myFileWebChromeClient.mFilePathCallbacks.onReceiveValue(new Uri[] { uri });
                } else {
                    myFileWebChromeClient.mFilePathCallbacks.onReceiveValue(null);
                }
            }
            myFileWebChromeClient.mFilePathCallback = null;
            myFileWebChromeClient.mFilePathCallbacks = null;
        }
    }

    //点击事件
    @OnClick(R.id.tv_back)
    public void onClick() {
        finish();
    }

    //返回键
    @Override
    public void onBackPressed() {
        if (wvWebview.canGoBack()) {
            wvWebview.goBack();
            return;
        }
        super.onBackPressed();
    }
}
