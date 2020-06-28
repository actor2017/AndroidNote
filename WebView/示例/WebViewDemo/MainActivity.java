package com.example.webviewdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mWebView = (WebView) findViewById(R.id.webview);

		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);//打开js
		mWebView.setWebChromeClient(new WebChromeClient());//此行代码可以保证js的alert弹窗正常弹出

		mWebView.loadUrl("file:///android_asset/demo.html");

		mWebView.addJavascriptInterface(new JsCallback() {

			@JavascriptInterface
			@Override
			public void onJsCallback() {
				System.out.println("js调用Android啦");
				Toast.makeText(getApplicationContext(), "js调用android啦",
						Toast.LENGTH_SHORT).show();
			}
		}, "demo");
	}

	public interface JsCallback {
		public void onJsCallback();
	}

	// android调用js
	public void androidCallJs(View view) {
		mWebView.loadUrl("javascript:wave()");
	}

}
