//��ҳ���:html + css + js
//Cookie,�����û���,����,��WebView���.md


webView.loadUrl("www.baidu.com");//������ҳ
//WebView���ر���Html
webView.loadUrl("file:///android_asset/support.html");//src/main/assets/support.html

//loadData:�������治�ܳ���Ӣ���ַ���'#', '%', '\' , '?'
webView.loadData(content, "text/html", "UTF-8");//����
webView.loadData(data.content, "text/html; charset=UTF-8", "UTF-8");//String data, String mimeType, String encoding
//��ʵ������TextView�ķ���:
textView.setText(Html.fromHtml(String source, ImageGetter imageGetter, TagHandler tagHandler));

//ͼƬ���100%
String headStr = "<html><head><meta charset='UTF-8'/><meta name='viewport' content='width=device-width, initial-scale=1.0' /><style>img{width:100%;display:block;}</style></head><body>";
//String headStr = "<html><body>";//���Ҳ����
String footStr = "</body></html>";
String content = data.content;
String data = headStr + content + footStr;
/**
 * ע��:���ͼƬ������ʾ������, �п������ֻ�������...
 * @param baseUrl	
 * @param data		��ҳ����
 * @param mimeType	text/html
 * @param encoding	utf-8
 * @param historyUrl
 */
webView.loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl);

webView.onResume();
webView.onPause();
webView.destroy();//��ص���, ������Fragment���ܻᱨ��https://blog.csdn.net/u012629497/article/details/51280392
webView = null;

//--------------------------------------------------------------------------------------

	//���ؼ�
	@Override
    public void onBackPressed() {
        if(webView.canGoBack()) {//�ж��Ƿ�����һ����ַ
            webView.goBack();//�ص���һ����ַ
            //webView.canGoForward();//�Ƿ����ǰ��
            //webView.goForward();//ǰ��
        } else {
            super.onBackPressed();
        }
    }


Android��js�Ļ���
���ȣ�AndroidҪ��jsʵ�ֻ�����������webview֧��js,��:webView.getSetting().setjavaScriptEnable(true);

//--------------------------------------------------------------------------------------
1. android��js:
webview.loadUrl("javaScript:������('params')");//��������������html��js�����ķ�����һ��,�ɴ�����
//API19���Ͽ������������,�з���ֵ
wvWebView.evaluateJavascript("javascript:changename()", new ValueCallback<String>() {
    @Override
    public void onReceiveValue(String value) {
        //�˴�Ϊ js ���صĽ��
        Log.v("Native",value);
    }
});

//����WebView�е�wave����
//ֱ��ʹ��webview����js�Ϳ�����
webView.loadUrl("javascript:wave()");

//���android���ܵ���ǰ��vue��ܵķ���,��ҪҪ��ǰ�˵ķ�����window:
mounted(){
	window.methodxxxxx = this.methodxxxxx;//https://www.jianshu.com/p/3d9a93c9fea2
}

//--------------------------------------------------------------------------------------
2. js��android:
/**
 * windows:�̶�д��?
 * android:����д,�����д��hehe
 * ������ :js����Android��������Ӧ������
 */
��Ҫ��htmlд"windows.android.������"

/**
 * ��webView�д���һ��js�ӿڣ�����ӿڵ����ƽ���android(���Զ�������)
 * @params object ��һ������:jsҪ���õķ������ĸ���ʵ��(��дthis,��new XxxObject())
 * @params name   �ڶ�������:js�Ķ��󣬱���windows.android.������ �����android����
 */
webView.addJavascriptInterface(this,"android");
		
//ע�����˼�ǣ�����ϵͳ��hehe�����������js�����õģ�û�����ע��Ļ�������ᱨ��
@JavascriptInterface
public void hehe(String str) {
    Toast.makeText(this, "js���õ���android���룬" + str, Toast.LENGTH_SHORT).show();
}

//��ҳ���������ܵ��������hehe(String str);����
<a onClick="window.android.hehe('��ð�')">Click Me</a>

//--------------------------------------------------------------------------------------
2.1.js��android,�ص���ʽ:(�о�����)
//���ķ���, ���ڴ���js��ִ�к�Ļص�
mWebView.addJavascriptInterface(new JsCallback() {

	@JavascriptInterface//ע��:�˴�һ��Ҫ�Ӹ�ע��,������4.1+ϵͳ������ʧ��
	@Override
	public void onJsCallback() {
		System.out.println("js����Android��");
	}
}, "demo");//��1�ǻص��ӿڵ�ʵ��;��2��js�ص����������

//����ص��ӿ�
public interface JsCallback {
	public void onJsCallback();
}

//js
<a onClick="window.demo.onJsCallback()">Click me!</a>

//--------------------------------------------------------------------------------------
Js����Android�ķ�ʽ���а汾��������. ������, ��2.2, 4.0+ ϵͳ�������ȶ�, ������������, 
������2.3ϵͳ������ʱ���ֱ���.ԭ���ǵײ����JNI����ʱ����һ��Java�е�String����������
�����ˣ����յ������������. ��������һ���Ƚ����ص�BUG��û�취������������˵��WebView
�������js��java֮���໥���õĻ���û�취��Ӧ���л���.

//--------------------------------------------------------------------------------------
3.���
http://blog.csdn.net/leejizhou/article/details/50894531/
