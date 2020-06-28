https://github.com/google/gson
gson好像有个Double-->Integer的bug:https://github.com/google/gson/issues/692

1.把gson的jar包放入libs目录,并刷新.或者添加依赖:compile 'com.google.code.gson:gson:2.8.2'

    ①.第一种解析方法.
        Gson gson = new Gson();
        RegisterGson registerGson = gson.fromJson(response, RegisterGson.class);

    ②.json数据没有名字,是以"["开头,是一个List,怎么写javaBean?
	Gson gson = new Gson();
	TypeToken<List<QueryThemeGson>> typeToken = new TypeToken<List<QueryThemeGson>>(){};
	List<QueryThemeGson> list = gson.fromJson(response, typeToken.getType());

    ③.写一个BaseInfo<T>,比如智慧警务登录示例,详细见外面文件夹
	Gson gson = new Gson();
	BaseAPIResult<LoginData> loginResult = gson.fromJson(response, new TypeToken<BaseAPIResult<LoginData>>() {}.getType());

2.Gson生成Json
    Gson gson = new Gson();
    String json = gson.toJson(userApps);//长度好像最多11493?,感觉有缺陷(EDU的坑,应该是特殊字符的锅)

    后来的解决方法:
    Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    String json = gson.toJson(arrayList);

    EDU生成失败分析:有可能是versionName里有特殊字符
    参考地址:http://blog.csdn.net/myf0908/article/details/70799781


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

