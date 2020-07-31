https://github.com/hongyangAndroid/okhttputils
��Ӧokhttp�汾3.3.1.

һ���get����
һ���post����
����Http Post���ļ��ϴ������Ʊ���
�ļ�����/����ͼƬ
�ϴ����صĽ��Ȼص�
֧��ȡ��ĳ������
֧���Զ���Callback
֧��HEAD��DELETE��PATCH��PUT
֧��session�ı���
֧����ǩ����վhttps�ķ��ʣ��ṩ����������֤�����

1.�������:compile 'com.zhy:okhttputils:2.6.2'

2.����OkhttpClient
Ĭ������£���ֱ��ʹ��okhttpĬ�ϵ���������OkhttpClient����������κ����ã��ǵ���Application�е���initClient�����������á�

3.����Cookie(����Session)
����cookieһ����ֱ��ͨ��cookiejar�������ã��ο���������ù��̡�
Ŀǰ��Ŀ�а�����
PersistentCookieStore //�־û�cookie
SerializableHttpCookie //�־û�cookie
MemoryCookieStore //cookie��Ϣ�����ڴ���
��ȻҲ�����Լ�ʵ��CookieJar�ӿڣ���дcookie������ش��롣
���⣬���ڳ־û�cookie������ʹ��https://github.com/franmontiel/PersistentCookieJar.
�൱�ڿ����ֻ���ṩ�˼���ʵ���࣬��������ж��ƻ���ѡ��ʹ�á�
CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
OkHttpClient okHttpClient = new OkHttpClient.Builder()
          .cookieJar(cookieJar)
          //��������
         .build();
OkHttpUtils.initClient(okHttpClient);

4.����Log
��ʼ��OkhttpClientʱ��ͨ������������ʵ�֣�������ṩ��һ��LoggerInterceptor����Ȼ���������ʵ��һ��Interceptor ��
 OkHttpClient okHttpClient = new OkHttpClient.Builder()
       .addNetworkInterceptor(new MyLoggerInterceptor(null, isDebugMode))
       //.addInterceptor(new LoggerInterceptor("TAG"))
        //��������
        .build();
OkHttpUtils.initClient(okHttpClient);

5.�������ͷ(�����������������)
OkHttpUtils.get().url(url).addHeader("authorization", SPUtils.getString(Global.TOKEN));//ʾ��

6.����Https
��Ȼ��ͨ�����ü��ɣ�������ṩ��һ����HttpsUtils,���ÿɷ������е�https��վ
HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
         //��������
         .build();
OkHttpUtils.initClient(okHttpClient);
//���þ����֤��
HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(֤���inputstream, null, null);
OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager))
         //��������
         .build();
OkHttpUtils.initClient(okHttpClient);
//˫����֤
HttpsUtils.getSslSocketFactory(
	֤���inputstream, 
	����֤���inputstream, 
	����֤�������)
//ͬ���ģ������ֻ���ṩ�˼���ʵ���࣬���������ʵ��SSLSocketFactory������sslSocketFactory���ɡ�

7.??
builder.hostnameVerifier(new HostnameVerifier() {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
});


///////////////////////////////////////////////////////////////////////////////////
�ǻۻ�������:
//����ssl
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



7.�Զ���CallBack
Ŀǰ�ڲ�����StringCallBack,FileCallBack,BitmapCallback�����Ը����Լ�������ȥ�Զ���Callback������ϣ���ص�User����
public abstract class UserCallback extends Callback<User> {
    @Override
    public User parseNetworkResponse(Response response) throws IOException {//���߳�
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


8.����
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
