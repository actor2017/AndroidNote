package com.kuchuanyun.wisdompolicehy4sd.application;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    public boolean isDebugMode = false;
    //使用字符串替代服务器.cer证书
    private String CER_SERVER = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDWzCCAkOgAwIBAgIEU7j1ZTANBgkqhkiG9w0BAQsFADBdMQswCQYDVQQGEwJjbjEPMA0GA1UE\n" +
            "CBMGc2hhbnhpMQ0wCwYDVQQHEwR4aWFuMQ4wDAYDVQQKEwV6aGFuZzEOMAwGA1UECxMFemhhbmcx\n" +
            "DjAMBgNVBAMTBXpoYW5nMCAXDTE4MTEwMTA2NTAxOVoYDzMwMTcwMzA0MDY1MDE5WjBdMQswCQYD\n" +
            "VQQGEwJjbjEPMA0GA1UECBMGc2hhbnhpMQ0wCwYDVQQHEwR4aWFuMQ4wDAYDVQQKEwV6aGFuZzEO\n" +
            "MAwGA1UECxMFemhhbmcxDjAMBgNVBAMTBXpoYW5nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB\n" +
            "CgKCAQEAnXHDbJBAK9pFR0cuOriWLHhM1DjnAc5sC8rkgyOsbSncDJJ0ZXwofUnBZf2iWaMXjFGW\n" +
            "zg1BjG/70uScDUUXzQkD9e6EVwDPFsOAm8IcJh53lrQgTZa774m3Y6BKLCFyvEvbqqXMqL6hDa7E\n" +
            "JoL0khz2rkq6rfVUHygNIIyJmV63IR95qbDZ7KLNLWk49oZGZy0Qo8bfJaGIDjF6FkyhDm87enSa\n" +
            "xPI9rOP3YkdxYRHwSyq73mTKZqt4RqVFK+FE46dGQQ6KRPELk0XquoI7+iO3U25tEOGDSPawJ7+9\n" +
            "DLQcPJKRX9LxlTpNbzYaEHJ0l6elGRFC7YrJLh3HwhvXZwIDAQABoyEwHzAdBgNVHQ4EFgQU11aV\n" +
            "b5kPGXETeH8HT/vBjqpLGRgwDQYJKoZIhvcNAQELBQADggEBAB++qyekOgLZQFplNZgv9+9nfR8/\n" +
            "JSwL6W0BWgEE1efTxbR1hnqCClj0alTDsz9pr3RpEoKIHZm3/RKlZTOlPWGXbMTfAaY8AkUHCk+C\n" +
            "wKu+1N9nXVqDMSpMEdHpn1noPay7WDd47lH0i1nZGNFMiY6Wek72z4J/1YlxiGbRPZy1wduW62+S\n" +
            "r62wnAw6JlFtH1hczHEYVkQSPH6+5fWdHQrZVOPKdWn24Wp/fGujRa//p0CKNFh708gKhYoXp2f0\n" +
            "5pDfpw+Y5FBbzhCLAd4Ab8UKydr5BvzgPjk+MlKge7M/QKn3/HNCa1D6mVka8ht4MGKqfcgljSUO\n" +
            "9e1txbZlG4o=\n" +
            "-----END CERTIFICATE-----";

    @Override
    public void onCreate() {
        super.onCreate();
		
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                .writeTimeout(1000L, TimeUnit.MILLISECONDS)
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(this)))
                .cache(new Cache(getFilesDir(), 1024*1024*10))//10Mb
				.addInterceptor(new MyNetWorkInterceptor());//请求头添加token拦截器
				.addInterceptor(new My401ErrorInterceptor());//错误拦截等
                //其他配置
				
		if (isDebugMode) {
			//打印Log, okhttp3自带的拦截器
            builder.addInterceptor(new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(HttpLoggingInterceptor.Level.BODY));
        } else {
            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Global.IP, Global.PORT)));
        }
        OkHttpClient okHttpClient = builder.build();
        OkHttpUtils.initClient(okHttpClient);
		
		/**
		 * 完整配置
		 */
		//持久化Cookie & Session框架:PersistentCookieJar
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
        /**
         * InputStream[] certificates:  证书,可多个证书(公钥)
         * InputStream bksFile:         .bks文件,本地证书(双向认证时本地私钥,银行,金融)
         * String password:             本地证书密码
         * 都为null:                 设置可访问所有的https网站
         * 证书的InputStream不为null: 设置具体的证书
         * 都不为null:               双向认证
         */
        InputStream certificate = new Buffer().writeUtf8(CER_SERVER).inputStream();//okio.Buffer
        InputStream bksFile = AssetsUtils.openFile(this, "zhy_client.bks");//使用zhy_client.jks要报错?
        InputStream[] certificates = {certificate};
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(certificates, bksFile, "123456");
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(new MyLoggerInterceptor(getPackageName() + ",NetworkInterceptor:", isDebugMode))
                .addInterceptor(new My401ErrorInterceptor())
//                .authenticator(new Authenticator() {//认证x
//                    @Override
//                    public Request authenticate(Route route, Response response) throws IOException {
//                        return null;
//                    }
//                })
                .cache(new Cache(getFilesDir(), 1024*1024*10))//10Mb

//                .certificatePinner(CertificatePinner.DEFAULT)//x
//                .connectionPool(new ConnectionPool())
//                .connectionSpecs(new ArrayList<ConnectionSpec>())
//                .connectTimeout(10_000L, TimeUnit.MILLISECONDS)//连接超时,默认10秒

//                new CookieJarImpl(new MemoryCookieStore());//cookie信息存在内存中
//                new CookieJarImpl(new PersistentCookieStore(this)); //持久化cookie
//                new CookieJarImpl(new SerializableHttpCookie()); //持久化cookie
                .cookieJar(cookieJar)//Cookie & Session

//                .dispatcher(new Dispatcher())
//                .dns(Dns.SYSTEM)
//                .followRedirects(false)
//                .followSslRedirects(false)
                //对服务端返回的一些信息进行相关校验,用于客户端判断所连接的服务端是否可信,默认返回true
                .hostnameVerifier(new HostnameVerifier() {//很多子类
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        try {
                            //获取证书链中的所有证书
                            Certificate[] peerCertificates = session.getPeerCertificates();
                            for (Certificate c : peerCertificates) {//打印所有证书内容
                                LogUtils.println("verify: "+c.toString(), true);
                            }
                        } catch (SSLPeerUnverifiedException e) {
                            e.printStackTrace();
                        }
//                        OkHostnameVerifier.INSTANCE.verifyIpAddress(hostname, session);
//                        return Util.verifyAsIpAddress(hostname) ?
//                                Util.verifyIpAddress(hostname, session)
//                                : Util.verifyHostname(hostname, session);
                                                return true;
                    }
                })
//                .interceptors()//List<Interceptor>
//                .networkInterceptors()//List<Interceptor>
//                .protocols(new ArrayList<Protocol>())
//				   1.如果设置https代理:https://github.com/square/okhttp/issues/3787
//				   2.每个线程分配代理ip(不停换ip):https://blog.csdn.net/wang704987562/article/details/82869394
//				   3.Charles经验证有效,Wireshark(无效...)
//				   4.Postman & 其它工具没有验证是否能抓包.https://www.baidu.com/s?from=563j&word=app防抓包&ua=baidu_ua_value
//				   设置代理:
//						会把请求发送到代理ip(不用手机wifi设置手动代理),防止抓包,和System.setProperty效果一致
//						System.setProperty("http.proxyHost", Global.IP);,System.setProperty("http.proxyPort", String.valueOf(Global.PORT));
//                .proxy(Proxy.NO_PROXY)//设置不使用代理,或者这样也行:new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Global.IP, Global.PORT))
//                .proxyAuthenticator(null)
//                .proxySelector(null)//代理选择器
//                .readTimeout(10_000L, TimeUnit.MILLISECONDS)//读超时,默认10秒
                .retryOnConnectionFailure(true)//默认true
//                .socketFactory(null)
//                .sslSocketFactory(null)//过时
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);//设置具体的证书
//                .writeTimeout(10_000L, TimeUnit.MILLISECONDS)//写超时,默认10秒

				//builder.addInterceptor(new LoggerInterceptor(getPackageName() + ",Interceptor:", isDebugMode))//打印Log 过时, 用okhttp3自带的拦截器
				if (isDebugMode) builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
                OkHttpClient okHttpClient = builder.build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
