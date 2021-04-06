https://github.com/google/gson
https://www.jianshu.com/p/e740196225a4
https://www.jianshu.com/p/923a9fe78108 ��������


gson�����и�Double-->Integer��bug:https://github.com/google/gson/issues/692

�������:
compile 'com.google.code.gson:gson:2.8.2'


1.�����л�
	int one = gson.fromJson("1", int.class);
	Integer one = gson.fromJson("1", Integer.class);
	Long one = gson.fromJson("1", Long.class);
	Boolean false = gson.fromJson("false", Boolean.class);
	String str = gson.fromJson("\"abc\"", String.class);
	String anotherStr = gson.fromJson("[\"abc\"]", String.class);

	RegisterGson info = GsonUtils.fromJson(json, RegisterGson.class);

2.�����л� List(json��"["��ͷ)
	//TypeToken<List<QueryThemeGson>> typeToken = new TypeToken<List<QueryThemeGson>>() {};
	//Type type = typeToken.getType();
	Type type = GsonUtils.getListType(QueryThemeGson.class);//����������ȡ Type
	List<QueryThemeGson> list = GsonUtils.fromJson(json, type);

3.�����л� Object[]
	//QueryThemeGson[] array = GsonUtils.getArrayType(QueryThemeGson.class);//����������ȡ Type
	QueryThemeGson[] array = GsonUtils.fromJson(json, QueryThemeGson[].class);

4.�����л� Set
	//Type type = new TypeToken<HashSet<Founder>>() {}.getType();
	Type type = GsonUtils.getSetType(Founder.class);//����������ȡ Type
	HashSet<Founder> founderSet = gson.fromJson(json, type); 

5.�����л� Map: jsonʾ��: {"ɽ��":13,"̨��":32,"����":16,"�ӱ�":5,"����":7,"����":4,"����":17,"����":18,"����":21,"����":23}
	//TypeToken<HashMap<QueryThemeGson>> typeToken = new TypeToken<HashMap<QueryThemeGson>>() {};
	//Type type = typeToken.getType();
	Type type = GsonUtils.getMapType(String.class, QueryThemeGson.class);//����������ȡ Type
	Map<QueryThemeGson> map = GsonUtils.fromJson(json, type);

6.������ BaseInfo<T>
	//Type type = new TypeToken<BaseInfo<LoginData>>() {}.getType();
	Type type = GsonUtils.getType(BaseInfo.class, LoginData.class);//����������ȡ Type
	BaseInfo<LoginData> info = GsonUtils.fromJson(json, type);

7.�������󱨴�(��¼�ɹ����ض���, ʧ�ܷ��ؿմ�)
    //�û�������: {"responseCode":"0","responseMsg":"���û������� ","datas":""}
    //��¼�ɹ�  : {"responseCode":"1","responseMsg":"��¼�ɹ� ","datas":{"id":"2c90c1c","token":"eyJ0eXAiOi"}}
	//1.������ص��ǵ� 1 ��json, ���ֽ�����ʽ�ᱨ��(�ַ��������ɶ���)
    BaseInfo<LoginInfo> baseInfo = GsonUtils.fromJson(info, GsonUtils.getType(BaseInfo.class, LoginInfo.class));
	//2.������ص��ǵ� 2 ��json, ���ֽ�����ʽҲ�ᱨ��(����������String,���Ƿ�����һ������... Expected a string but was BEGIN_OBJECT at line 1 column 54 path $.datas)
    BaseInfo<String> baseInfo = GsonUtils.fromJson(info, GsonUtils.getType(BaseInfo.class, String.class));
	//����, �����������, ֱ���жϷ��ص�json��: if (info.contains("\"responseCode\":\"1\"") {}


7.���л�Json
	gson.toJson(1);            ==> prints 1
	gson.toJson("abcd");       ==> prints "abcd"
	gson.toJson(new Long(10)); ==> prints 10
	int[] values = { 1 };
	gson.toJson(values);       ==> prints [1]

	//String json = new Gson().toJson(info);//���Ⱥ������11493?,�о���ȱ��(EDU�Ŀ�, �п����������ַ��Ĺ�)
    String json = GsonUtils.toJson(info);

    �����Ľ������:
    Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    String json = gson.toJson(arrayList);

    EDU����ʧ�ܷ���:�п�����versionName���������ַ�
    �ο���ַ:http://blog.csdn.net/myf0908/article/details/70799781

6.������JsonObject
//{"success":true,"data":0,"message":"����ɹ�","errorMessages":[],"businessStatusCode":"success","enabled":true,"httpStatusCode":200}
JsonObject jsonObject = GsonUtils.fromJson(json, JsonObject.class);
boolean enabled = jsonObject.getAsJsonObject("enabled").getAsBoolean();
String message = jsonObject.getAsJsonObject("message").getAsString();

jsObject.get("errorMessages").getAsString();//����д��, ���򱨴�
//��������дò��Ҳ����, ���errorMessages��value�Ƕ��󻹺�, ���value��������"string", ����õ���ȷ��: ""string""...
String errorMessages = jsObject.get("errorMessages").toString();

int id = jsonObject.get("code").getAsInt();
String id1 = jsonObject.get("id1").getAsString();
String desc = jsonObject.get("hobbyDesc").getAsJsonObject().get("sport_desc").getAsString();//����ȡ


7.JsonParser
JsonObject jsonObject = (JsonObject) new JsonParser().parse(jsonStr);

8.JSONArray
JSONArray jsonArray = new JSONArray(jsonStr);//json��array����: [{},{}]


��ӻ���
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
##��Ҫ����Bean��(����ʾ��:����)
#-keep class com.package.xxx.info.** { *; }
##---------------End: proguard configuration for Gson  ----------

