
/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/6/11 15 05
 */
public class SlidingPaneLayoutActi extends Activity {
 
    SlidingPaneLayout mSlidingPaneLayout;
    View mSmallLeft;
    View mFullLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.slding_pane_layout);
 
        mSlidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.slidingpanelayout);
        mSmallLeft = findViewById(R.id.small_left);
        mFullLeft = findViewById(R.id.full_left);
 
        mFullLeft.setAlpha(0);//默认 full侧栏隐藏 显示最小-预览式的侧栏
 
        mWebView = (WebView) findViewById(R.id.webview);
 
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        WebViewClient client = new WebViewClient();
        mWebView.setWebViewClient(client);
 
        mSlidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //slideOffset: close left->open left    from 0-1
                System.out.println("slide" + slideOffset);
                //view.setalpha(0~1)
                //full完全显示时small就应完全不可见
                mSmallLeft.setAlpha(1 - slideOffset);
                mFullLeft.setAlpha(slideOffset);
                mSmallLeft.setVisibility(mSlidingPaneLayout.isOpen() ? View.GONE : View.VISIBLE);
            }
 
            @Override
            public void onPanelOpened(View panel) {
                System.out.println("opened");
            }
 
            @Override
            public void onPanelClosed(View panel) {
                System.out.println("closed");
            }
        });
    }
 
    WebView mWebView;
 
    public void baidu(View view) {
        mWebView.loadUrl("http://www.baidu.com");
    }
 
    public void qq(View view) {
        mWebView.loadUrl("http://www.qq.com");
    }
 
    public void wangyi(View view) {
        mWebView.loadUrl("http://www.163.com");
    }
 
    public void sina(View view) {
        mWebView.loadUrl("http://www.sina.com");
    }
}
--------------------- 
作者：匆忙拥挤repeat 
来源：CSDN 
原文：https://blog.csdn.net/jjwwmlp456/article/details/46468603 
版权声明：本文为博主原创文章，转载请附上博文链接！