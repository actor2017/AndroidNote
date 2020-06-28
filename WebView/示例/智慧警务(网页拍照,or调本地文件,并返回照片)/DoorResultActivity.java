package com.kuchuan.wisdompolice.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.kuchuan.wisdompolice.R;
import com.kuchuan.wisdompolice.global.Global;
import com.kuchuan.wisdompolice.webViewUtils.ImageUtil;
import com.kuchuan.wisdompolice.webViewUtils.InitVebViewAndSetWebViewClient;
import com.kuchuan.wisdompolice.webViewUtils.MyWebChromeClient;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kuchuan.wisdompolice.R.id.tv_title;
import static com.kuchuan.wisdompolice.application.MyApplication.aCache;

/**
 * 门牌扫描出来后(or 门牌信息管理点击后),跳过来
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
    private AlertDialog.Builder alertDialog;
    private Intent mSourceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_result);
        ButterKnife.bind(this);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.blue_titlebar));
        pbProgressbar.setVisibility(View.GONE);
        intent = getIntent();
        url = intent.getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (aCache.getAsString(Global.INTERNET).contains(Global.INNER_SERVER)) {//内网
            url = Global.INNER_SERVER_WX + url.split("wx")[1];
        }
        alertDialog = new AlertDialog.Builder(this);
        initAlertDialog(alertDialog);

        // TODO: 2017/9/5 1111111111111111111
        url = "http://192.168.1.111:9003/wx/addOwner?type=police";

        //初始化WebView(支持:双击 & 缩放 & javaScript) &setWebViewClient(开始加载 & 跳转链接 & 加载结束)
        wvWebview.setWebViewClient(new InitVebViewAndSetWebViewClient(this,wvWebview,pbProgressbar));
        wvWebview.setWebChromeClient(new MyWebChromeClient(//获取进度,标题,js的alert弹窗...★★这里面很多回调方法★★
                pbProgressbar,tvTitle, new MyWebChromeClient.OpenFileChooserCallBack() {

            @Override
            public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
                mValueCallback=uploadMsg;
//                if(checkRights()) {
                alertDialog.show();
//                }
            }

            @Override
            public void openFileChooserCallBackAndroid5(ValueCallback<Uri[]> uploadMsg, String acceptType) {
                mValueCallbackAndroid5=uploadMsg;
//                if(checkRights()) {
                alertDialog.show();
//                }
            }
        }));
        wvWebview.loadUrl(url);
    }
    ValueCallback<Uri> mValueCallback;
    ValueCallback<Uri[]> mValueCallbackAndroid5;
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;
    private final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE=100;
    private void initAlertDialog(AlertDialog.Builder alertDialog) {
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mValueCallback != null) {
//                    mValueCallback.onReceiveValue(null);
//                    mValueCallback = null;
                }

                if(mValueCallbackAndroid5!=null) {
//                    mValueCallbackAndroid5.onReceiveValue(null);
//                    mValueCallbackAndroid5 = null;
                }
            }
        });
        alertDialog.setTitle("选择图片");
        alertDialog.setItems(new String[]{"本地相册选择","拍照","取消"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {//本地相册选择
                            mSourceIntent = ImageUtil.choosePicture();
                            startActivityForResult(mSourceIntent, REQUEST_CODE_PICK_IMAGE);
                        } else if(which==1) {//拍照
                            mSourceIntent = ImageUtil.takeBigPicture();
                            startActivityForResult(mSourceIntent, REQUEST_CODE_IMAGE_CAPTURE);
                        } else {//取消
                            dialog.dismiss();
                        }
                    }
                }
        );
    }

    //检查读写文件 拍照权限
    private boolean checkRights(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_IMAGE_CAPTURE:
            case REQUEST_CODE_PICK_IMAGE: {
                try {
                    if (mValueCallback == null&&mValueCallbackAndroid5==null) {
                        return;
                    }
                    String sourcePath = ImageUtil.retrievePath(this, mSourceIntent, data);
                    if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {
                        Log.w("WebActivity", "sourcePath empty or not exists.");
                        break;
                    }
                    Uri uri = Uri.fromFile(new File(sourcePath));
                    if(mValueCallback!=null) {
                        mValueCallback.onReceiveValue(uri);
                    }

                    if(mValueCallbackAndroid5!=null) {
                        mValueCallbackAndroid5.onReceiveValue(new Uri[]{uri});
                    }

                    //通知更新图库
                    if(requestCode==REQUEST_CODE_IMAGE_CAPTURE) {
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
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
