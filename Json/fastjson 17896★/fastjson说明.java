https://github.com/alibaba/fastjson
https://github.com/alibaba/fastjson/wiki/Quick-Start-CN
Android版本:https://github.com/alibaba/fastjson/wiki/Android%E7%89%88%E6%9C%AC
快速入门:https://github.com/alibaba/fastjson/wiki/Samples-DataBind
Google JSON 风格指南:https://github.com/darcyliu/google-styleguide/blob/master/JSONStyleGuide.md(可了解如何更好的使用JSON)

//-----------------使用说明----------------------------------
1.添加依赖
compile 'com.alibaba:fastjson:1.2.9'//没有Retrofit2ConverterFactory(已copy到retrofit文件夹)

VERSION_CODE获取:
http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.alibaba%22%20AND%20a%3A%22fastjson%22 (基本不能打开)
http://repo1.maven.org/maven2/com/alibaba/fastjson/ (打开最快)
https://bintray.com/bintray/jcenter/com.alibaba%3Afastjson#files (打开较慢)


//##################################################################################################################
2.JSONObject extends JSON implements Map<String, Object>	//是一个Map集合.
JSON.parseObject(String str)与JSONObject.parseObject(String str)的区别
JSON是一个抽象类，JSON中有一个静态方法parseObject(String text)，将text解析为一个JSONObject对象并返回;
JSONObject是一个继承自JSON的类，当调用JSONObject.parseObject(result)时，会直接调用父类的parseObject(String text)。
所以两者没什么区别，一个是用父类去调用父类自己的静态的parseObject(String text)，一个是用子类去调用父类的静态parseObject(String text)，两者调的是同一个方法。


//##################################################################################################################
3.生成json字符串(快速入门)
import com.alibaba.fastjson.JSON;
String jsonString = JSON.toJSONString(bean);

//BeanToArray: [[15,1469003271063,"John Doe"],[20,1469003271063,"Janette Doe"]]
String json = JSONObject.toJSONString(list);//List里的Bean不能是内部类
String jsonOutput= JSON.toJSONString(list, SerializerFeature.BeanToArray);//List<Person>,List里的Bean不能是内部类

//BeanToArray: [{15,1469003271063,"John Doe"},{20,1469003271063,"Janette Doe"}]
JSONArray jsonArray = new JSONArray();
jsonArray.add(jsonObject);
String jsonOutput = jsonArray.toJSONString();

//使用 ContextValueFilter 配置 JSON 转换
ContextValueFilter valueFilter = new ContextValueFilter () {
	public Object process(
	  BeanContext context, Object object, String name, Object value) {
		if (name.equals("DATE OF BIRTH")) {
			return "NOT TO DISCLOSE";
		}
		if (value.equals("John")) {
			return ((String) value).toUpperCase();
		} else {
			return null;
		}
	}
};
String jsonOutput = JSON.toJSONString(listOfPersons, valueFilter);

//使用 NameFilter 和 SerializeConfig
NameFilter formatName = new NameFilter() {//序列化时修改 Key
	public String process(Object object, String name, Object value) {
		return name.toLowerCase().replace(" ", "_");
	}
};
 
SerializeConfig.getGlobalInstance().addFilter(Person.class,  formatName);//内部是个map容器主要功能是配置并记录每种Java类型对应的序列化类
String jsonOutput = JSON.toJSONStringWithDateFormat(listOfPersons, "yyyy-MM-dd");
//实例中我们声明了 formatName 过滤器使用 NameFilter 匿名类来处理字段名称。 新创建的过滤器与 Person 类相关联，然后添加到全局实例，它是 SerializeConfig 类中的静态属性。
//注意我们使用的是 toJSONStringWithDateFormat() 而不是 toJSONString() ，它可以更快速的格式化日期
//输出json: [{"full_name":"John Doe","date_of_birth":"2016-07-21"},{"full_name":"Janette Doe","date_of_birth":"2016-07-21"}]

SerializeConfig mapping = new SerializeConfig(); 
mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd")); 
String x2 = JSON.toJSONString(new Bean(), mapping); 


//##################################################################################################################
4. JSON解析json(快速入门)
String jsonString = {"id":0,"name":"admin","users":[{"id":2,"name":"guest"},{"id":3,"name":"root"}]}
Group group = JSON.parseObject(jsonString, Group.class);

4.1 JSON/JSONObject转换成Bean类
User user = JSON.toJavaObject(json/jsonObject,User.class);
User user = JSONObject.toJavaObject(json/jsonObject,User.class);//这是JSON的方法

4.2 JSONObject 手动解析 json
		if (!TextUtils.isEmpty(message)) {//智慧城管
			JSONObject msgObj = JSON.parseObject(message);
			if (msgObj.containsKey("type") && msgObj.getString("type").equals("video")) {
				String admin = msgObj.getString("admin");
				String url = msgObj.getString("url");
				System.out.println(admin+"正在向你发起视频"+url);
				//TO-DO
				setNotification(MyApplication.instance,message);
			}
		}


//##################################################################################################################
5.解析数组([开头的json),直接解析成JsonArray.(智慧城管示例)
List<ChooseAreaInfo> chooseAreaInfos = JSON.parseArray(response, ChooseAreaInfo.class);

JSONObject jsonObject = JSONObject.parseObject(response);
JSONArray jsonArray = jsonObject.getJSONArray("data");
List<Class> clazzs = jsonArray.toJavaList(Class.class);


//##################################################################################################################
6.解析成Map
// String类型：
Map<String, String> tempMap = JSON.parseObject(jsonString, new TypeReference<Map<String, String>>() {});

// JavaBean类型：
Map<String, UserInfo> tempMap = JSON.parseObject(jsonString, new TypeReference<Map<String, UserInfo>>() {});

// List<JavaBean>类型：
Map<String, List<UserInfo>> tempMap = JSON.parseObject(jsonString, new TypeReference<Map<String, List<UserInfo>>>() {});



//##################################################################################################################
7.注解说明
@JSONCreator


@JSONType(orders = {"age","name"})		//注解在类上,TYPE    按照orders数组中顺序, 解析成json
//JSONType 还有一些属性...
public class User {
	
	@JSONField(ordinal = 1, name = "z_name")//示例: {"name":"张三","age":"23"} ==> {"z_name":"张三","age":"23"}
    public String name;
	
	@JSONField(ordinal = 0)				//示例: {"name":"张三","age":"23"} ==> {"age":"23","name":"张三"}
	public int age;
	
	@JSONField(format = "yyyy-MM-dd")
    public Date birtyday;
}


@JSONField 属性说明: //注解在 METHOD(get/set方法), FIELD(字段), PARAMETER
    int ordinal() default 0;			//用来排序 输出json字符串中key属性的先后顺序,可以用 @JSONType （orders={}）来代替.
    String name() default "";			//给字段起别名,可包含空格, 定义了 "输入 & 输出" key的名称.
    String format() default "";			//用来日期格式化, 将 String日期 转换为 Date
    boolean serialize() default true;	//指定字段是否序列化(生成json字符串), 如果为false, 则这个字段不会序列化到json中
    boolean deserialize() default true;	//指定字段是否需要进行反序列化(json转Bean), 如果为false, 则解析的Bean这个字段为空
	
	SerializerFeature[] serialzeFeatures() default {};
	Feature[] parseFeatures() default {};
	String label() default "";
	boolean jsonDirect() default false;
	Class<?> serializeUsing() default Void.class;
	Class<?> serializeUsing() default Void.class;
	Class<?> deserializeUsing() default Void.class;
	String[] alternateNames() default {};
    boolean unwrapped() default false;


@JSONPOJOBuilder




7.添加混淆: 地址: http ?????
##---------------Begin: proguard configuration for fastjson------
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.*{*;}
##不混淆Fastjson的Bean类(★★★下面这个路径要自己改★★★)
#-keep class com.package.xxx.info.** { *; }
##---------------End: proguard configuration for fastjson--------


//-----------------下面是具体说明----------------------------
Fastjson提供Android版本，和标准版本相比，Android版本去掉一些Android虚拟机dalvik不支持的功能，使得jar更小，同时针对dalvik做了很多性能优化，包括减少方法调用等。parse为JSONObject/JSONArray时比原生org.json速度快，序列化反序列化JavaBean性能比jackson/gson性能更好。

Android版本中去掉的功能

1. ASM

Dalvik虚拟机的字节码格式和Java SE不一样，目前的ASM动态优化无法在Android上实现。

2. AWT

Android上的Dalvik虚拟机不支持awt，所以去掉标准版本的awt相关类序列化和反序列化的支持。

3. AtomicXXX

AtomicXXX不应该作为POJO的属性，不常用，所以在Android版本中去掉了。

4. 不支持Clob对象序列化。

5. JSONPObject

这个功能用于web jsonp数据生成，不常用于android客户端，所以去掉。

6. 不支持Reference字段类型序列化和反序列化，比如WeakReference/SoftReference/AtomicReference，这些类型不常用用作POJO的属性，所以去掉了。

7. 以下方法不常用，不支持

public abstract class JSON {
    public static parseObject(byte[] input, int off, int len, CharsetDecoder charsetDecoder, Type clazz,
                                      Feature... features) { ... }

    public static Object parse(byte[] input, int off, int len, CharsetDecoder charsetDecoder, 
                                      int features) {}

    public static Object parse(byte[] input, int off, int len, CharsetDecoder charsetDecoder, 
                                      Feature...features) {}

    public static Object parse(byte[] input, int off, int len, CharsetDecoder charsetDecoder, 
                                      int features) {}
}

// JSONSerializerMap已废弃，不支持
com.alibaba.fastjson.serializer.JSONSerializer.JSONSerializer(JSONSerializerMap)
8. 一些废弃的类不支持

com.alibaba.fastjson.parser.JavaBeanMapping 使用com.alibaba.fastjson.parser.ParserConfig代替
com.alibaba.fastjson.serializer.JSONSerializerMap  使用com.alibaba.fastjson.serializer.SerializeConfig代替
com.alibaba.fastjson.parser.DefaultExtJSONParser 使用com.alibaba.fastjson.parser.DefaultJSONParser代替
9. 一些废弃方法不支持

class com.alibaba.fastjson.JSONWriter {
    @Deprecated
    public void writeStartObject();
    
    @Deprecated
    public void writeEndObject();

    @Deprecated
    public void writeStartArray();
    
    @Deprecated
    public void writeEndArray();
}