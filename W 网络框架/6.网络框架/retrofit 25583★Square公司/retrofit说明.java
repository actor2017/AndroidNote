https://github.com/square/retrofit
官网:https://square.github.io/retrofit/	Retrofit.html
使用Retrofit的前提是服务器端代码遵循REST规范 ?

//Retrofit基于OkHttp
//compile 'com.squareup.okhttp3:okhttp:3.11.0'//1.9的Retrofit需要添加这个,2.x不需要
//okhttp官方Log拦截器
compile 'com.squareup.okhttp3:logging-interceptor:3.11.0'
//Retrofit
implementation 'com.squareup.retrofit2:retrofit:2.5.0'
//Rxjava2的Adapter(如果你用到了rxjava2)
compile 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
//Retrofit - Gson数据转换工具
compile 'com.squareup.retrofit2:converter-gson:2.1.0'

//fastjson里面有一个:Retrofit2ConverterFactory, 不用添加依赖


##----------Begin: proguard configuration for Retrofit-----------
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# okhttp
混淆见okhttp文件夹
##----------End: proguard configuration for Retrofit-------------

1.网络请求方式
@DELETE
@GET	//1.必须有baseUrl.
		//2.如果只有baseUrl而@GET()参数内没值,那么baseUrl必须以/结尾 且 @GET 应该写成@GET(".")
		//3.如果@GET 有值,则baseUrl可不以/结尾,@GET(应该以/开头,可不/结尾)
@HEAD
@OPTIONS
@PATCH
@POST	//1.必须有baseUrl.
		//2.如果只有baseUrl而@POST()参数内没值,那么baseUrl必须以/结尾 且 @POST 应该写成@POST(".")
		//3.如果@POST 有值,则baseUrl可不以/结尾,@POST(应该以/开头,可不/结尾)
@PUT
@HTTP		//自定义请求方式

//////////////////////////////////////////////////////////////////////////
//GET测试:
@GET("openapi.do?keyfrom=xxx&ap=1")
Call<TranslationInfo> getCall();

//HTTP示例:
@HTTP(method = "GET", path = "blog/{id}", hasBody = false)//hasBody:是否有请求体
Call<ResponseBody> getCall(@Path("id") int id);


2.标记类
@FormUrlEncoded	//表示发送form-encoded的数据.请求体是一个Form表单(Content-Type:application/x-www-form-urlencoded)
@Multipart		//表示发送form-encoded的数据(适用于"文件上传"的场景).请求体是一个支持文件上传的Form表单
@Streaming		//以流的形式返回数据(下载文件),如果没有使用这个注解,默认把数据都载入内存.
//测试表单:
@POST("/form")
@FormUrlEncoded
Call<ResponseBody> test1(@Field("username") String name, @Field("age") int age);//表单
GetRequestInterface service = retrofit.create(GetRequestInterface.class);
Call<ResponseBody> call1 = service.test1("Carson", 24);

//Body 使用
@POST("/app/land_early_mapping_activity/data_insert")
Observable<BaseBean> addEarlyMapping(@Body RequestBody body);

//测试参数&文件上传:
@POST("/form")
@Multipart
Call<ResponseBody> test2(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);
RequestBody name = RequestBody.create(textType, "Carson");
RequestBody age = RequestBody.create(textType, "24");
MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
Call<ResponseBody> call3 = service.test2(name, age, filePart);

//测试@Streaming(文件下载)
@Streaming//下载大文件必须有这个注解
@GET
Observable<ResponseBody> startDownload(@Url String url);//直接指定url(包括baseUrl[带http/s约束], 也可以不包括baseUrl)


3.网络请求参数
@Body		//放在请求体中,不能和@FormUrlEncoded @Multipart 标签同时使用.可注解 okhttp3.RequestBody, Map & 实体. 可传实体@Body LoginInfo loginInfo
			//RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
			//RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
			
@Field		//表单参数注解
@FieldMap	//同上
@Header		//添加不固定值的Header
@HeaderMap	//同上
@Headers	//添加请求体
@Part		//表单参数注解,用于有文件上传的情况
@PartMap	//同上
@Path		//url路径缺省值
@Query		//用于@GET/@POST方式问号后面的key-value
@QueryMap	//同上
@QueryName	//有key,但是没有value
@Url		//url设置.当有URL注解时,请求方式上传入的URL就可以省略
//测试Body:

//测试@FieldMap:
@POST("/form")
@FormUrlEncoded
Call<ResponseBody> test(@FieldMap Map<String, Object> map);

//测试@Header:
@GET("user")
Call<User> getUser(@Header("Authorization") String authorization);

//测试@Headers:
@Headers("Authorization: authorization")	//和上面效果一致.
@GET("user")
Call<User> getUser();

//测试@PartMap:
@POST("/form")
@Multipart
Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);
Map<String, RequestBody> params = new HashMap<>();
params.put("name", name);
params.put("age", age);
MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
Call<ResponseBody> call4 = service.testFileUpload2(params, filePart); //单独处理文件
ResponseBodyPrinter.printResponseBody(call4);

//测试@Path:
@GET("users/{user}/repos")
Call<ResponseBody>  getBlog(@Path("user") String user);

//测试@Query:
@GET("/")    
Call<String> test(@Query("name") String name, @Query("current") Date now);//可以传Date(传的参数是now.toString())

//测试@Url:
@GET / @POST
Call<ResponseBody> testUrl(@Url String url, @Query("showAll") boolean showAll);


4.Retrofit示例使用
 Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://fanyi.youdao.com/") // 设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .build();
// 创建 网络请求接口 的实例
GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
//对 发送请求 进行封装
Call<ReceptionInfo> call = request.getCall();//getCall:接口里的方法.

//同步请求
Response<Reception> response = call.execute();
response.body().show();//对返回数据进行处理

//异步请求
        call.enqueue(new Callback<ReceptionInfo>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<ReceptionInfo> call, Response<ReceptionInfo> response) {
                //请求处理,输出结果
                response.body().toString();
            }

            //请求失败时候的回调
            @Override
            public void onFailure(Call<ReceptionInfo> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });




淘宝测试json?:http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13512345678
__GetZoneResult_ = {
    mts:'1351234',
    province:'重庆',
    catName:'中国移动',
    telString:'13512345678',
	areaVid:'29404',
	ispVid:'3236139',
	carrier:'重庆移动'
}




以下混淆未测试:
If you are using ProGuard you need to add the following options:

# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement