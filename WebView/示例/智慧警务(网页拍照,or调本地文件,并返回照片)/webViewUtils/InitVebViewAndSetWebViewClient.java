package com.kuchuan.wisdompolice.webViewUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Description: 初始化WebView & 设置WebViewClient(开始加载 & 跳转链接 & 加载结束)
 * Copyright  : Copyright (c) 2017
 * Company    : 酷川科技 www.kuchuanyun.com
 * Author     : 李小松
 * Date       : 2017/9/5 on 16:10.
 *
 * 示例调用:
 * //初始化WebView(支持:双击 & 缩放 & javaScript) &setWebViewClient(开始加载 & 跳转链接 & 加载结束)
 * wvWebview.setWebViewClient(new InitVebViewAndSetWebViewClient(this,wvWebview,pbProgressbar));
 */

public class InitVebViewAndSetWebViewClient extends WebViewClient {
    private ProgressBar pbProgressbar;

    /**
     * @param context
     * @param webView
     * @param pbProgressbar 最上方显示的加载url进度条,可以传null
     */
    public InitVebViewAndSetWebViewClient(@NonNull Context context, @NonNull WebView webView, @Nullable ProgressBar pbProgressbar){
        this.pbProgressbar = pbProgressbar;

        //初始化WebView,(支持:双击 & 缩放 & javaScript)
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setHorizontalScrollbarOverlay(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.requestFocus();

        //HTML5支持的配置
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);  // 显示缩放按钮(wap网页不支持)
        settings.setUseWideViewPort(true);      // 支持双击缩放,将图片调整到适合webview的大小(wap网页不支持)
        settings.setJavaScriptEnabled(true);    // 支持js功能
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存
        settings.setAppCacheEnabled(true);            //开启 Application Caches 功能
        settings.setAllowFileAccess(true);            //设置可以访问文件
        settings.setSavePassword(true);
        settings.setSupportZoom(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        settings.setLoadWithOverviewMode(true);
        settings.supportMultipleWindows();//支持多窗口模式
        settings.setSaveFormData(false);//html的支持
        settings.setDatabaseEnabled(true);//开启 database storage API 功能,启用数据库
        String dir = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(dir);//设置数据库路径
        settings.setDomStorageEnabled(true);//开启 DOM storage API 功能,使用localStorage则必须打开
        settings.setGeolocationEnabled(true);//启用地理定位
        settings.setGeolocationDatabasePath(dir);// 设置定位的数据库路径
    }

    // 开始加载网页
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (pbProgressbar != null) {
            pbProgressbar.setVisibility(View.VISIBLE);
            pbProgressbar.setProgress(0);
        }
    }

    // 网页加载结束
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (pbProgressbar != null) {
            pbProgressbar.setVisibility(View.GONE);
        }
    }

    // 所有链接跳转会走此方法
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);// 在跳转链接时强制在当前webview中加载
        return true;
    }

    // 加载网页失败时处理
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (pbProgressbar != null) {
            pbProgressbar.setVisibility(View.GONE);
        }
    }
}
