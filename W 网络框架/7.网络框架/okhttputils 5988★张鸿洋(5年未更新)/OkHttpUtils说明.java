https://github.com/hongyangAndroid/okhttputils
对应okhttp版本3.3.1.

一般的get请求
一般的post请求
基于Http Post的文件上传（类似表单）
文件下载/加载图片
上传下载的进度回调
支持取消某个请求
支持自定义Callback
支持HEAD、DELETE、PATCH、PUT
支持session的保持
支持自签名网站https的访问，提供方法设置下证书就行

1.添加依赖:compile 'com.zhy:okhttputils:2.6.2'

2.配置OkhttpClient
默认情况下，将直接使用okhttp默认的配置生成OkhttpClient，如果你有任何配置，记得在Application中调用initClient方法进行设置。

3.对于Cookie(包含Session)
对于cookie一样，直接通过cookiejar方法配置，参考上面的配置过程。
目前项目中包含：
PersistentCookieStore //持久化cookie
SerializableHttpCookie //持久化cookie
MemoryCookieStore //cookie信息存在内存中
当然也可以自己实现CookieJar接口，编写cookie管理相关代码。
此外，对于持久化cookie还可以使用https://github.com/franmontiel/PersistentCookieJar.
相当于框架中只是提供了几个实现类，你可以自行定制或者选择使用。
CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
OkHttpClient okHttpClient = new OkHttpClient.Builder()
          .cookieJar(cookieJar)
          //其他配置
         .build();
OkHttpUtils.initClient(okHttpClient);

4.对于Log
初始化OkhttpClient时，通过设置拦截器实现，框架中提供了一个LoggerInterceptor，当然你可以自行实现一个Interceptor 。
 OkHttpClient okHttpClient = new OkHttpClient.Builder()
       .addNetworkInterceptor(new MyLoggerInterceptor(null, isDebugMode))
       //.addInterceptor(new LoggerInterceptor("TAG"))
        //其他配置
        .build();
OkHttpUtils.initClient(okHttpClient);

5.添加请求头(可以在拦截器里添加)
OkHttpUtils.get().url(url).addHeader("authorization", SPUtils.getString(Global.TOKEN));//示例

6.对于Https
依然是通过配置即可，框架中提供了一个类HttpsUtils,设置可访问所有的https网站
HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
         //其他配置
         .build();
OkHttpUtils.initClient(okHttpClient);
//设置具体的证书
HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(证书的inputstream, null, null);
OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager))
         //其他配置
         .build();
OkHttpUtils.initClient(okHttpClient);
//双向认证
HttpsUtils.getSslSocketFactory(
	证书的inputstream, 
	本地证书的inputstream, 
	本地证书的密码)
//同样的，框架中只是提供了几个实现类，你可以自行实现SSLSocketFactory，传入sslSocketFactory即可。

7.??
builder.hostnameVerifier(new HostnameVerifier() {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
});


///////////////////////////////////////////////////////////////////////////////////
智慧机关配置:
//忽略ssl
try {
    final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };
    final SSLContext sslContext = SSLContext.getInstance("SSL");
    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
    builder.sslSocketFactory(sslSocketFactory);
    builder.hostnameVerifier((hostname, session) -> true);
} catch (NoSuchAlgorithmException | KeyManagementException e) {
    e.printStackTrace();
}
///////////////////////////////////////////////////////////////////////////////////



7.自定义CallBack
目前内部包含StringCallBack,FileCallBack,BitmapCallback，可以根据自己的需求去自定义Callback，例如希望回调User对象：
public abstract class UserCallback extends Callback<User> {
    @Override
    public User parseNetworkResponse(Response response) throws IOException {//子线程
        String string = response.body().string();
        User user = new Gson().fromJson(string, User.class);
        return user;
    }
}

 OkHttpUtils
    .get()//
    .url(url)//
    .addParams("username", "hyman")//
    .addParams("password", "123")//
    .build()//
    .execute(new UserCallback() {
        @Override
        public void onError(Request request, Exception e) {
            mTv.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(User response) {
            mTv.setText("onResponse:" + response.username);
        }
    });


8.混淆
##---------------Begin: proguard configuration for okhttputils---
#okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}
##---------------End: proguard configuration for okhttputils-----
