package com.kuchuan.wisdompolice.webViewUtils;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 作用: 自定的实现webView拍照上传的工具类
 * 作者：liuyiyuan
 * 日期：2016/11/10 10:48
 * 邮箱：liuyiyuan@xnihuamm.net
 * weixin: Dkalan
 */

public class MyWebChromeClient extends WebChromeClient {

    //选择图片回调接口
    public interface OpenFileChooserCallBack {
        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);
        void openFileChooserCallBackAndroid5(ValueCallback<Uri[]> uploadMsg, String acceptType);
    }

    private OpenFileChooserCallBack mOpenFileChooserCallBack;//选择图片回调接口
    private ProgressBar pbProgressbar;//进度条,可传null
    private TextView tvTitle;//标题,可传null

    /**
     * @param pbProgressbar 进度条,可传null
     * @param tvTitle 标题,可传null
     * @param mOpenFileChooserCallBack 选择图片回调接口
     */
    public MyWebChromeClient(@Nullable ProgressBar pbProgressbar,@Nullable TextView tvTitle,
                             @NonNull OpenFileChooserCallBack mOpenFileChooserCallBack) {
        this.mOpenFileChooserCallBack = mOpenFileChooserCallBack;
        this.pbProgressbar = pbProgressbar;
        this.tvTitle = tvTitle;
    }

    /**
     * 进度发生变化
     * @param view
     * @param newProgress
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (pbProgressbar != null) {
            pbProgressbar.setProgress(newProgress);
        }
    }

    /**
     * 网页标题
     * @param view
     * @param title
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    /**
     * For Android 3.0+
     * @param uploadMsg
     * @param acceptType
     */
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (mOpenFileChooserCallBack != null) {
            mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
        }
    }


    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }


    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }


    /**
     * For Android > 5.0
     * @param webView
     * @param uploadMsg
     * @param fileChooserParams
     * @return
     */
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg,
                                     FileChooserParams fileChooserParams) {
        if (mOpenFileChooserCallBack != null) {
            mOpenFileChooserCallBack.openFileChooserCallBackAndroid5(uploadMsg, "");
        }
        return true;
    }
}
