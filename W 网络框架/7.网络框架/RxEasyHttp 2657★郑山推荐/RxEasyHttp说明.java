https://github.com/zhou-you/RxEasyHttp
本库是一款基于RxJava2+Retrofit2实现简单易用的网络请求框架，结合android平台特性的网络封装库,采用api链式调用一点到底,集成cookie管理,多种缓存模式,极简https配置,上传下载进度显示,请求错误自动重试,请求携带token、时间戳、签名sign动态配置,自动登录成功后请求重发功能,3种层次的参数设置默认全局局部,默认标准ApiResult同时可以支持自定义的数据结构，已经能满足现在的大部分网络请求。

槽点:
1.参数是Map<String, String>
2.不能自定义解析的Gson
3.有个感觉没啥用的 ProgressDialogCallBack
4.解析泛型不能传<List<Object>>, 必须调用: .execute(new TypeToken<List<SectionItem>>() {}.getType())//Type类型
5.由于写死了 ApiResult<T>, 当调用如下接口时报错, 自定义ApiResult写法感觉麻烦.(我想解析成什么就解析成什么, 需要你写死 ApiResult???)
    BaseSubscriber.onError(L:69): -->http is onError
    BaseSubscriber.onError(L:71): --> e instanceof ApiException err:com.zhouyou.http.exception.ApiException: NullPointerException
EasyHttp.get("https://api.github.com").execute(new SimpleCallBack<GithubInfo>() {
    @Override
    public void onError(ApiException e) {
        e.printStackTrace();
        toast(e.getMessage());
    }
    @Override
    public void onSuccess(GithubInfo githubInfo) {
        toast(githubInfo.getHub_url());
    }
});
public class GithubInfo {
	public String hub_url;//即使写成 private & get/set方法, 也会报错
}
6.minSdkVersion 19
7.所以,不推荐这个库


1.特点
比Retrofit使用更简单、更易用。
采用链式调用一点到底
加入基础ApiService，减少Api冗余
支持动态配置和自定义底层框架Okhttpclient、Retrofit.
支持多种方式访问网络GET、POST、PUT、DELETE等请求协议
支持网络缓存,八种缓存策略可选,涵盖大多数业务场景
支持固定添加header和动态添加header
支持添加全局参数和动态添加局部参数
支持文件下载、多文件上传和表单提交数据
支持文件请求、上传、下载的进度回调、错误回调，也可以自定义回调
支持默认、全局、局部三个层次的配置功能
支持任意数据结构的自动解析
支持添加动态参数例如timeStamp时间戳、token、签名sign
支持自定义的扩展API
支持多个请求合并
支持Cookie管理
支持异步、同步请求
支持Https、自签名网站Https的访问、双向验证
支持失败重试机制，可以指定重试次数、重试间隔时间
支持根据ky删除网络缓存和清空网络缓存
提供默认的标准ApiResult解析和回调，并且可自定义ApiResult
支持取消数据请求，取消订阅，带有对话框的请求不需要手动取消请求，对话框消失会自动取消请求
支持请求数据结果采用回调和订阅两种方式
api设计上结合http协议和android平台特点来实现,loading对话框,实时进度条显示
返回结果和异常统一处理
结合RxJava2，线程智能控制

2.RxEasyHttp网络库与Rxjava2结合常见使用场景介绍
http://blog.csdn.net/zhouy478319399/article/details/78550248

3.更新日志
https://github.com/zhou-you/RxEasyHttp/blob/master/update.md

4.想查看所有版本
https://jcenter.bintray.com/com/zhouyou/rxeasyhttp/

5.添加依赖, minSdkVersion 19//4.4
dependencies {
    implementation 'com.zhouyou:rxeasyhttp:2.1.7'
}
5.1.已经添加的第三方依赖库 https://github.com/zhou-you/RxEasyHttp/blob/master/rxeasyhttp/build.gradle
    //第三方依赖库
    compile 'com.squareup.okhttp3:logging-interceptor:3.12.2'
    compile 'com.squareup.okhttp3:okhttp:3.12.2'
    compile 'com.jakewharton:disklrucache:2.0.2'
    compile 'io.reactivex.rxjava2:rxjava:2.2.10'
    compile 'io.reactivex.rxjava2:rxandroid:2.1.1'
    compile 'com.squareup.retrofit2:retrofit:2.5.0'
    compile 'com.squareup.retrofit2:converter-gson:2.5.0'
    compile 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'


6.默认初始化
//RxEasyHttp 默认初始化,必须调用
EasyHttp.init(this);

7.高级初始化
	//全局设置请求头
	HttpHeaders headers = new HttpHeaders();
	headers.put("User-Agent", SystemInfoUtils.getUserAgent(this, AppConstant.APPID));
	//全局设置请求参数
	HttpParams params = new HttpParams();
	params.put("appId", AppConstant.APPID);

	//以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
	EasyHttp.getInstance()
	
			//可以全局统一设置全局URL
			.setBaseUrl(Url)//设置全局URL  url只能是域名 或者域名+端口号 

			// 打开该调试开关并设置TAG,不需要就不要加入该行
			// 最后的true表示是否打印内部异常，一般打开方便调试错误
			.debug("EasyHttp", true)
			
			//如果使用默认的60秒,以下三行也不需要设置
			.setReadTimeOut(60 * 1000)
			.setWriteTimeOut(60 * 100)
			.setConnectTimeout(60 * 100)
			
			//可以全局统一设置超时重连次数,默认为3次,那么最差的情况会请求4次(一次原始请求,三次重连请求),
			//不需要可以设置为0
			.setRetryCount(3)//网络不好自动重试3次
			//可以全局统一设置超时重试间隔时间,默认为500ms,不需要可以设置为0
			.setRetryDelay(500)//每次延时500ms重试
			//可以全局统一设置超时重试间隔叠加时间,默认为0ms不叠加
			.setRetryIncreaseDelay(500)//每次延时叠加500ms
			
			//可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体请看CacheMode
			.setCacheMode(CacheMode.NO_CACHE)
			//可以全局统一设置缓存时间,默认永不过期
			.setCacheTime(-1)//-1表示永久缓存,单位:秒 ，Okhttp和自定义RxCache缓存都起作用
			//全局设置自定义缓存保存转换器，主要针对自定义RxCache缓存
			.setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
			//全局设置自定义缓存大小，默认50M
			.setCacheMaxSize(100 * 1024 * 1024)//设置缓存大小为100M
			//设置缓存版本，如果缓存有变化，修改版本后，缓存就不会被加载。特别是用于版本重大升级时缓存不能使用的情况
			.setCacheVersion(1)//缓存版本为1
			//.setHttpCache(new Cache())//设置Okhttp缓存，在缓存模式为DEFAULT才起作用
			
			//可以设置https的证书,以下几种方案根据需要自己设置
			.setCertificates()                                  //方法一：信任所有证书,不安全有风险
			//.setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
			//配置https的域名匹配规则，不需要就不要加入，使用不当会导致https握手失败
			//.setHostnameVerifier(new SafeHostnameVerifier())
			//.addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
			.addCommonHeaders(headers)//设置全局公共头
			.addCommonParams(params)//设置全局公共参数
			//.addNetworkInterceptor(new NoCacheInterceptor())//设置网络拦截器
			//.setCallFactory()//局设置Retrofit对象Factory
			
														//如果不想让本库管理cookie,以下不需要
			//.setCookieStore(new CookieManger(this)) //cookie持久化存储，如果cookie不过期，则一直有效
			//.setOkproxy()//设置全局代理
			//.setOkconnectionPool()//设置请求连接池
			//.setCallbackExecutor()//全局设置Retrofit callbackExecutor
			//可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
			//.addInterceptor(new GzipRequestInterceptor())//开启post数据进行gzip后发送给服务器
			.addInterceptor(new CustomSignInterceptor());//添加参数签名拦截器





