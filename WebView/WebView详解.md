# WebView详解 #

## 基本用法 ##

- 清单文件配置WebView

  	    <WebView
	        android:id="@+id/wv_news_detail"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent" />

- WebView加载网页

		//加载网页链接
		mWebView.loadUrl("http://www.itheima.com");
		//加载本地assets目录下的网页
		mWebView.loadUrl("file:///android_asset/demo.html");

- WebView基本设置

		WebSettings settings = mWebView.getSettings();
		settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
		settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
		settings.setJavaScriptEnabled(true);// 支持js功能

- 设置WebViewClient

		mWebView.setWebViewClient(new WebViewClient() {
			// 开始加载网页
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				System.out.println("开始加载网页了");
			}

			// 网页加载结束
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				System.out.println("网页加载结束");
			}

			// 所有链接跳转会走此方法
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				System.out.println("跳转链接:" + url);
				view.loadUrl(url);// 在跳转链接时强制在当前webview中加载

				//此方法还有其他应用场景, 比如写一个超链接<a href="tel:110">联系我们</a>, 当点击该超链接时,可以在此方法中获取链接地址tel:110, 解析该地址,获取电话号码, 然后跳转到本地打电话页面, 而不是加载网页, 从而实现了webView和本地代码的交互
			
				return true;
			}
		});

- 设置WebChromeClient

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				// 进度发生变化
				System.out.println("进度:" + newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				// 网页标题
				System.out.println("网页标题:" + title);
			}
		});

- WebView加载上一页和下一页

		mWebView.goBack();//跳到上个页面
		mWebView.goForward();//跳到下个页面
		mWebView.canGoBack();//是否可以跳到上一页(如果返回false,说明已经是第一页)
		mWebView.canGoForward();//是否可以跳到下一页(如果返回false,说明已经是最后一页)

## WebView高级用法 ##

### 缓存 ###

- 缓存设置

		WebSettings settings = mWebView.getSettings();

		//只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//只加载缓存
		settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
		//根据cache-control决定是否从网络上取数据
		settings.setCacheMode(WebSettings.LOAD_DEFAULT);
		//不加载缓存
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);	

		什么是cache-control?
		cache-control是在请求网页时服务器的响应头,此响应头用于决定网页的缓存策略.
		常见的取值有public(所有内容都将被缓存), private(内容只缓存到私有缓存中),no-cache(所有内容都不会被缓存),max-age=xxx(缓存的内容将在 xxx 秒后失效)等等

	如图所示:

	![Cache_Control](./Cache_Control.png)


- 清理缓存

		最简便的方式:
		mWebView.clearCache(true);

		另外一种方式:
		//删除缓存文件夹
		File file = CacheManager.getCacheFileBaseDir(); 

		   if (file != null && file.exists() && file.isDirectory()) { 
		    for (File item : file.listFiles()) { 
		     item.delete(); 
		    } 
		    file.delete(); 
		   } 
		
		//删除缓存数据库
		context.deleteDatabase("webview.db"); 
		context.deleteDatabase("webviewCache.db");

### Cookie ###

- Cookie设置

		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);

		String cookie = "name=xxx;age=18";
		cookieManager.setCookie(URL, cookie);
		CookieSyncManager.getInstance().sync();

- 获取Cookie

		CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(URL);

- 清除Cookie

		CookieSyncManager.createInstance(context);  
        CookieManager cookieManager = CookieManager.getInstance(); 
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();  

##Android和Js交互##

> 如果Js和Android实现了交互, 那么我们就可以在网页中随意调用本地的Java代码, 也就是实现了WebView和本地代码的交互. 一旦WebView可以操作Android本地代码, 那么WebView的功能就会更加强大,以后我们直接在一个WebView中就几乎可以实现Android的所有功能,Android原生控件就没有了用武之地. 

- Js调用Android

		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);//开启js

		mWebView.loadUrl("file:///android_asset/demo.html");//加载本地网页
		mWebView.setWebChromeClient(new WebChromeClient());//此行代码可以保证js的alert弹窗正常弹出

		//核心方法, 用于处理js被执行后的回调
		mWebView.addJavascriptInterface(new JsCallback() {

			@JavascriptInterface//注意:此处一定要加该注解,否则在4.1+系统上运行失败
			@Override
			public void onJsCallback() {
				System.out.println("js调用Android啦");
			}
		}, "demo");//参1是回调接口的实现;参2是js回调对象的名称

		//定义回调接口
		public interface JsCallback {
			public void onJsCallback();
		}

- Android调用Js

		//直接使用webview加载js就可以了
		mWebView.loadUrl("javascript:wave()");

- 附上demo.html源码
    
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		</head>
	    <script language="javascript">
	        /* This function is invoked by the activity */
	        function wave() {
	            alert("Android调用Js啦");
	        }
	    </script>
	    <body>
	        <!-- Js调用Android代码 -->
	        <a onClick="window.demo.onJsCallback()"><div style="width:80px;
	            margin:0px auto;
	            padding:10px;
	            text-align:center;
	            border:2px solid #202020;" >
	                <img id="droid" src="android_normal.png"/><br>
	                Click me!
	        </div></a>
	    </body>
	</html>

	注意: js回调的方法的书写格式: onClick="window.demo.onJsCallback()
	格式是: window.js回调对象的名称(要和java代码中设置的一致).回调方法名称(要和java代码中设置的一致)

- 注意事项

	 Js调用Android的方式具有版本兼容问题. 经测试, 在2.2, 4.0+ 系统上运行稳定, 可以正常调用, 但是在2.3系统上运行时出现崩溃.原因是底层进行JNI调用时，把一个Java中的String对象当数组来访问了，最终导致虚拟机崩溃. 基本算是一个比较严重的BUG，没办法解决，所以如果说用WebView组件想在js和java之间相互调用的话就没办法适应所有机型.

	参考链接:
	https://code.google.com/p/android/issues/detail?id=12987

	http://www.2cto.com/kf/201108/101375.html
	

> 目前一些比较前卫的app就只使用一个WebView作为整体框架,app中的所有内容全部使用html5进行展示.比如12306手机客户端. 这样做的好处就是可以实现跨平台, 只需要一份h5代码, 就可以在Android和Ios平台上同时运行, 而且更新也更加简便, 只需要更改服务器的h5页面, 本地客户端就马上会同步更新,无需下载apk覆盖安装. 不过劣势也很明显, h5受网速限制,往往加载速度比较慢, 没有原生控件流畅, 用户体验较差. 所以目前完全使用h5搭建app并没有成为主流方式.

## WebView的应用场景 ##

> WebView的应用场景我们无需多讲, 此处我只提一点: 随着html5的普及, 很多app都会内嵌webview来加载html5页面, 而且h5做的和app原生控件极其相似, 那么我们如何辨认某个页面使用h5做的还是用原生控件做的呢?

打开开发者选项, 你会看到这样一个选项:显示布局边界

![setting](./setting.png)

勾选之后,所有原生控件布局的边框都会显示出来	

![bjbj](./bjbj.png)

我们现在在这种状态下打开一个WebView看一眼(这是微信钱包-滴滴出行的页面)

![ddcx](./ddcx.jpg)

发现蛛丝马迹了吗? 如果是WebView的话, 只有在WebView边缘才会显示一个边框, WebView内部是没有边框的;如果是原生控件,怎么可能边框这么少呢? 从而我们可以断定,微信的滴滴出行页面一定是用WebView加载网页实现的, 而不是系统原生控件.

## WebView介绍到此结束, 感谢捧场!!! ##

附件下载地址: <a href="http://pan.baidu.com/s/1mgrPlmK">http://pan.baidu.com/s/1mgrPlmK</a>



