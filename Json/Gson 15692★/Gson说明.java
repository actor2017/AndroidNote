https://github.com/google/gson
https://www.jianshu.com/p/e740196225a4
https://www.jianshu.com/p/923a9fe78108 官网翻译


gson好像有个Double-->Integer的bug:https://github.com/google/gson/issues/692

添加依赖:
compile 'com.google.code.gson:gson:2.8.2'


1.反序列化
	int one = gson.fromJson("1", int.class);
	Integer one = gson.fromJson("1", Integer.class);
	Long one = gson.fromJson("1", Long.class);
	Boolean false = gson.fromJson("false", Boolean.class);
	String str = gson.fromJson("\"abc\"", String.class);
	String anotherStr = gson.fromJson("[\"abc\"]", String.class);

	RegisterGson info = GsonUtils.fromJson(json, RegisterGson.class);

2.反序列化 List(json以"["开头)
	//TypeToken<List<QueryThemeGson>> typeToken = new TypeToken<List<QueryThemeGson>>() {};
	//Type type = typeToken.getType();
	Type type = GsonUtils.getListType(QueryThemeGson.class);//或者这样获取 Type
	List<QueryThemeGson> list = GsonUtils.fromJson(json, type);

3.反序列化 Object[]
	//QueryThemeGson[] array = GsonUtils.getArrayType(QueryThemeGson.class);//或者这样获取 Type
	QueryThemeGson[] array = GsonUtils.fromJson(json, QueryThemeGson[].class);

4.反序列化 Set
	//Type type = new TypeToken<HashSet<Founder>>() {}.getType();
	Type type = GsonUtils.getSetType(Founder.class);//或者这样获取 Type
	HashSet<Founder> founderSet = gson.fromJson(json, type); 

5.反序列化 Map: json示例: {"山东":13,"台湾":32,"福建":16,"河北":5,"河南":7,"重庆":4,"湖北":17,"湖南":18,"江西":21,"海南":23}
	//TypeToken<HashMap<QueryThemeGson>> typeToken = new TypeToken<HashMap<QueryThemeGson>>() {};
	//Type type = typeToken.getType();
	Type type = GsonUtils.getMapType(String.class, QueryThemeGson.class);//或者这样获取 Type
	Map<QueryThemeGson> map = GsonUtils.fromJson(json, type);

6.解析成 BaseInfo<T>
	//Type type = new TypeToken<BaseInfo<LoginData>>() {}.getType();
	Type type = GsonUtils.getType(BaseInfo.class, LoginData.class);//或者这样获取 Type
	BaseInfo<LoginData> info = GsonUtils.fromJson(json, type);

7.解析对象报错(登录成功返回对象, 失败返回空串)
    //用户不存在: {"responseCode":"0","responseMsg":"该用户不存在 ","datas":""}
    //登录成功  : {"responseCode":"1","responseMsg":"登录成功 ","datas":{"id":"2c90c1c","token":"eyJ0eXAiOi"}}
	//1.如果返回的是第 1 个json, 这种解析方式会报错(字符串解析成对象)
    BaseInfo<LoginInfo> baseInfo = GsonUtils.fromJson(info, GsonUtils.getType(BaseInfo.class, LoginInfo.class));
	//2.如果返回的是第 2 个json, 这种解析方式也会报错(期望解析成String,但是返回是一个对象... Expected a string but was BEGIN_OBJECT at line 1 column 54 path $.datas)
    BaseInfo<String> baseInfo = GsonUtils.fromJson(info, GsonUtils.getType(BaseInfo.class, String.class));
	//所以, 遇到这种情况, 直接判断返回的json吧: if (info.contains("\"responseCode\":\"1\"") {}


7.序列化Json
	gson.toJson(1);            ==> prints 1
	gson.toJson("abcd");       ==> prints "abcd"
	gson.toJson(new Long(10)); ==> prints 10
	int[] values = { 1 };
	gson.toJson(values);       ==> prints [1]

	//String json = new Gson().toJson(info);//长度好像最多11493?,感觉有缺陷(EDU的坑, 有可能是特殊字符的锅)
    String json = GsonUtils.toJson(info);

    后来的解决方法:
    Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    String json = gson.toJson(arrayList);

    EDU生成失败分析:有可能是versionName里有特殊字符
    参考地址:http://blog.csdn.net/myf0908/article/details/70799781

6.解析成JsonObject
//{"success":true,"data":0,"message":"处理成功","errorMessages":[],"businessStatusCode":"success","enabled":true,"httpStatusCode":200}
JsonObject jsonObject = GsonUtils.fromJson(json, JsonObject.class);
boolean enabled = jsonObject.getAsJsonObject("enabled").getAsBoolean();
String message = jsonObject.getAsJsonObject("message").getAsString();

jsObject.get("errorMessages").getAsString();//不能写成, 否则报错
//但是这样写貌似也不对, 如果errorMessages的value是对象还好, 如果value本来就是"string", 结果得到的确是: ""string""...
String errorMessages = jsObject.get("errorMessages").toString();

int id = jsonObject.get("code").getAsInt();
String id1 = jsonObject.get("id1").getAsString();
String desc = jsonObject.get("hobbyDesc").getAsJsonObject().get("sport_desc").getAsString();//多层获取


7.JsonParser
JsonObject jsonObject = (JsonObject) new JsonParser().parse(jsonStr);

8.JSONArray
JSONArray jsonArray = new JSONArray(jsonStr);//json是array类型: [{},{}]


添加混淆
https://github.com/google/gson/tree/master/examples/android-proguard-example
##---------------Begin: proguard configuration for Gson  ------------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Application classes that will be serialized/deserialized over Gson
##不要混淆Bean类(★★★示例:★★★)
#-keep class com.package.xxx.info.** { *; }
##---------------End: proguard configuration for Gson  ----------

