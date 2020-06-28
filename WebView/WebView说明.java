//网页组成:html + css + js
//Cookie,缓存用户名,密码,见WebView详解.md


webView.loadUrl("www.baidu.com");//加载网页
//WebView加载本地Html
webView.loadUrl("file:///android_asset/support.html");//src/main/assets/support.html

//loadData:数据里面不能出现英文字符：'#', '%', '\' , '?'
webView.loadData(content, "text/html", "UTF-8");//乱码
webView.loadData(data.content, "text/html; charset=UTF-8", "UTF-8");//String data, String mimeType, String encoding
//其实可以用TextView的方法:
textView.setText(Html.fromHtml(String source, ImageGetter imageGetter, TagHandler tagHandler));

//图片宽度100%
String headStr = "<html><head><meta charset='UTF-8'/><meta name='viewport' content='width=device-width, initial-scale=1.0' /><style>img{width:100%;display:block;}</style></head><body>";
//String headStr = "<html><body>";//这个也可以
String footStr = "</body></html>";
String content = data.content;
String data = headStr + content + footStr;
/**
 * 注意:如果图片老是显示不出来, 有可能是手机的问题...
 * @param baseUrl	
 * @param data		网页内容
 * @param mimeType	text/html
 * @param encoding	utf-8
 * @param historyUrl
 */
webView.loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl);

webView.onResume();
webView.onPause();
webView.destroy();//务必调用, 否则在Fragment可能会报错https://blog.csdn.net/u012629497/article/details/51280392
webView = null;

//--------------------------------------------------------------------------------------

	//返回键
	@Override
    public void onBackPressed() {
        if(webView.canGoBack()) {//判断是否有上一个网址
            webView.goBack();//回到上一个网址
            //webView.canGoForward();//是否可以前进
            //webView.goForward();//前进
        } else {
            super.onBackPressed();
        }
    }


Android和js的互调
首先，Android要和js实现互调，必须让webview支持js,即:webView.getSetting().setjavaScriptEnable(true);

//--------------------------------------------------------------------------------------
1. android调js:
webview.loadUrl("javaScript:方法名('params')");//这个方法名必须和html的js声明的方法名一致,可传参数
//API19以上可以用这个方法,有返回值
wvWebView.evaluateJavascript("javascript:changename()", new ValueCallback<String>() {
    @Override
    public void onReceiveValue(String value) {
        //此处为 js 返回的结果
        Log.v("Native",value);
    }
});

//调用WebView中的wave方法
//直接使用webview加载js就可以了
webView.loadUrl("javascript:wave()");

//如果android不能调用前端vue框架的方法,需要要把前端的方法给window:
mounted(){
	window.methodxxxxx = this.methodxxxxx;//https://www.jianshu.com/p/3d9a93c9fea2
}

//--------------------------------------------------------------------------------------
2. js调android:
/**
 * windows:固定写法?
 * android:随意写,比如可写成hehe
 * 方法名 :js调用Android方法所对应方法名
 */
需要在html写"windows.android.方法名"

/**
 * 往webView中存了一个js接口，这个接口的名称叫做android(是自定义名称)
 * @params object 第一个参数:js要调用的方法由哪个类实现(可写this,或new XxxObject())
 * @params name   第二个参数:js的对象，比如windows.android.方法名 的这个android对象。
 */
webView.addJavascriptInterface(this,"android");
		
//注解的意思是，告诉系统，hehe这个方法是由js来调用的，没有这个注解的话，上面会报错
@JavascriptInterface
public void hehe(String str) {
    Toast.makeText(this, "js调用到了android代码，" + str, Toast.LENGTH_SHORT).show();
}

//网页中这样才能调到上面的hehe(String str);方法
<a onClick="window.android.hehe('你好啊')">Click Me</a>

//--------------------------------------------------------------------------------------
2.1.js调android,回调方式:(感觉不好)
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

//js
<a onClick="window.demo.onJsCallback()">Click me!</a>

//--------------------------------------------------------------------------------------
Js调用Android的方式具有版本兼容问题. 经测试, 在2.2, 4.0+ 系统上运行稳定, 可以正常调用, 
但是在2.3系统上运行时出现崩溃.原因是底层进行JNI调用时，把一个Java中的String对象当数组来
访问了，最终导致虚拟机崩溃. 基本算是一个比较严重的BUG，没办法解决，所以如果说用WebView
组件想在js和java之间相互调用的话就没办法适应所有机型.

//--------------------------------------------------------------------------------------
3.详解
http://blog.csdn.net/leejizhou/article/details/50894531/
