https://github.com/square/retrofit
����:https://square.github.io/retrofit/	Retrofit.html
ʹ��Retrofit��ǰ���Ƿ������˴�����ѭREST�淶 ?

//Retrofit����OkHttp
//compile 'com.squareup.okhttp3:okhttp:3.11.0'//1.9��Retrofit��Ҫ������,2.x����Ҫ
//okhttp�ٷ�Log������
compile 'com.squareup.okhttp3:logging-interceptor:3.11.0'
//Retrofit
implementation 'com.squareup.retrofit2:retrofit:2.5.0'
//Rxjava2��Adapter(������õ���rxjava2)
compile 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
//Retrofit - Gson����ת������
compile 'com.squareup.retrofit2:converter-gson:2.1.0'

//fastjson������һ��:Retrofit2ConverterFactory, �����������


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
������okhttp�ļ���
##----------End: proguard configuration for Retrofit-------------

1.��������ʽ
@DELETE
@GET	//1.������baseUrl.
		//2.���ֻ��baseUrl��@GET()������ûֵ,��ôbaseUrl������/��β �� @GET Ӧ��д��@GET(".")
		//3.���@GET ��ֵ,��baseUrl�ɲ���/��β,@GET(Ӧ����/��ͷ,�ɲ�/��β)
@HEAD
@OPTIONS
@PATCH
@POST	//1.������baseUrl.
		//2.���ֻ��baseUrl��@POST()������ûֵ,��ôbaseUrl������/��β �� @POST Ӧ��д��@POST(".")
		//3.���@POST ��ֵ,��baseUrl�ɲ���/��β,@POST(Ӧ����/��ͷ,�ɲ�/��β)
@PUT
@HTTP		//�Զ�������ʽ

//////////////////////////////////////////////////////////////////////////
//GET����:
@GET("openapi.do?keyfrom=xxx&ap=1")
Call<TranslationInfo> getCall();

//HTTPʾ��:
@HTTP(method = "GET", path = "blog/{id}", hasBody = false)//hasBody:�Ƿ���������
Call<ResponseBody> getCall(@Path("id") int id);


2.�����
@FormUrlEncoded	//��ʾ����form-encoded������.��������һ��Form��(Content-Type:application/x-www-form-urlencoded)
@Multipart		//��ʾ����form-encoded������(������"�ļ��ϴ�"�ĳ���).��������һ��֧���ļ��ϴ���Form��
@Streaming		//��������ʽ��������(�����ļ�),���û��ʹ�����ע��,Ĭ�ϰ����ݶ������ڴ�.
//���Ա�:
@POST("/form")
@FormUrlEncoded
Call<ResponseBody> test1(@Field("username") String name, @Field("age") int age);//��
GetRequestInterface service = retrofit.create(GetRequestInterface.class);
Call<ResponseBody> call1 = service.test1("Carson", 24);

//Body ʹ��
@POST("/app/land_early_mapping_activity/data_insert")
Observable<BaseBean> addEarlyMapping(@Body RequestBody body);

//���Բ���&�ļ��ϴ�:
@POST("/form")
@Multipart
Call<ResponseBody> test2(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);
RequestBody name = RequestBody.create(textType, "Carson");
RequestBody age = RequestBody.create(textType, "24");
MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
Call<ResponseBody> call3 = service.test2(name, age, filePart);

//����@Streaming(�ļ�����)
@Streaming//���ش��ļ����������ע��
@GET
Observable<ResponseBody> startDownload(@Url String url);//ֱ��ָ��url(����baseUrl[��http/sԼ��], Ҳ���Բ�����baseUrl)


3.�����������
@Body		//������������,���ܺ�@FormUrlEncoded @Multipart ��ǩͬʱʹ��.��ע�� okhttp3.RequestBody, Map & ʵ��. �ɴ�ʵ��@Body LoginInfo loginInfo
			//RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
			//RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
			
@Field		//������ע��
@FieldMap	//ͬ��
@Header		//��Ӳ��̶�ֵ��Header
@HeaderMap	//ͬ��
@Headers	//���������
@Part		//������ע��,�������ļ��ϴ������
@PartMap	//ͬ��
@Path		//url·��ȱʡֵ
@Query		//����@GET/@POST��ʽ�ʺź����key-value
@QueryMap	//ͬ��
@QueryName	//��key,����û��value
@Url		//url����.����URLע��ʱ,����ʽ�ϴ����URL�Ϳ���ʡ��
//����Body:

//����@FieldMap:
@POST("/form")
@FormUrlEncoded
Call<ResponseBody> test(@FieldMap Map<String, Object> map);

//����@Header:
@GET("user")
Call<User> getUser(@Header("Authorization") String authorization);

//����@Headers:
@Headers("Authorization: authorization")	//������Ч��һ��.
@GET("user")
Call<User> getUser();

//����@PartMap:
@POST("/form")
@Multipart
Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);
Map<String, RequestBody> params = new HashMap<>();
params.put("name", name);
params.put("age", age);
MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
Call<ResponseBody> call4 = service.testFileUpload2(params, filePart); //���������ļ�
ResponseBodyPrinter.printResponseBody(call4);

//����@Path:
@GET("users/{user}/repos")
Call<ResponseBody>  getBlog(@Path("user") String user);

//����@Query:
@GET("/")    
Call<String> test(@Query("name") String name, @Query("current") Date now);//���Դ�Date(���Ĳ�����now.toString())

//����@Url:
@GET / @POST
Call<ResponseBody> testUrl(@Url String url, @Query("showAll") boolean showAll);


4.Retrofitʾ��ʹ��
 Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://fanyi.youdao.com/") // �������������Url��ַ
                .addConverterFactory(GsonConverterFactory.create()) // �������ݽ�����
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // ֧��RxJavaƽ̨
                .build();
// ���� ��������ӿ� ��ʵ��
GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
//�� �������� ���з�װ
Call<ReceptionInfo> call = request.getCall();//getCall:�ӿ���ķ���.

//ͬ������
Response<Reception> response = call.execute();
response.body().show();//�Է������ݽ��д���

//�첽����
        call.enqueue(new Callback<ReceptionInfo>() {
            //����ɹ�ʱ�ص�
            @Override
            public void onResponse(Call<ReceptionInfo> call, Response<ReceptionInfo> response) {
                //������,������
                response.body().toString();
            }

            //����ʧ��ʱ��Ļص�
            @Override
            public void onFailure(Call<ReceptionInfo> call, Throwable throwable) {
                System.out.println("����ʧ��");
            }
        });




�Ա�����json?:http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13512345678
__GetZoneResult_ = {
    mts:'1351234',
    province:'����',
    catName:'�й��ƶ�',
    telString:'13512345678',
	areaVid:'29404',
	ispVid:'3236139',
	carrier:'�����ƶ�'
}




���»���δ����:
If you are using ProGuard you need to add the following options:

# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement