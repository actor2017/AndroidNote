https://github.com/google/gson
gson�����и�Double-->Integer��bug:https://github.com/google/gson/issues/692

1.��gson��jar������libsĿ¼,��ˢ��.�����������:compile 'com.google.code.gson:gson:2.8.2'

    ��.��һ�ֽ�������.
        Gson gson = new Gson();
        RegisterGson registerGson = gson.fromJson(response, RegisterGson.class);

    ��.json����û������,����"["��ͷ,��һ��List,��ôдjavaBean?
	Gson gson = new Gson();
	TypeToken<List<QueryThemeGson>> typeToken = new TypeToken<List<QueryThemeGson>>(){};
	List<QueryThemeGson> list = gson.fromJson(response, typeToken.getType());

    ��.дһ��BaseInfo<T>,�����ǻ۾����¼ʾ��,��ϸ�������ļ���
	Gson gson = new Gson();
	BaseAPIResult<LoginData> loginResult = gson.fromJson(response, new TypeToken<BaseAPIResult<LoginData>>() {}.getType());

2.Gson����Json
    Gson gson = new Gson();
    String json = gson.toJson(userApps);//���Ⱥ������11493?,�о���ȱ��(EDU�Ŀ�,Ӧ���������ַ��Ĺ�)

    �����Ľ������:
    Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    String json = gson.toJson(arrayList);

    EDU����ʧ�ܷ���:�п�����versionName���������ַ�
    �ο���ַ:http://blog.csdn.net/myf0908/article/details/70799781


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

