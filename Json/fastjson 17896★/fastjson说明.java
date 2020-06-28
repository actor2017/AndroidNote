https://github.com/alibaba/fastjson
https://github.com/alibaba/fastjson/wiki/Quick-Start-CN
Android�汾:https://github.com/alibaba/fastjson/wiki/Android%E7%89%88%E6%9C%AC
��������:https://github.com/alibaba/fastjson/wiki/Samples-DataBind
Google JSON ���ָ��:https://github.com/darcyliu/google-styleguide/blob/master/JSONStyleGuide.md(���˽���θ��õ�ʹ��JSON)

//-----------------ʹ��˵��----------------------------------
1.�������
compile 'com.alibaba:fastjson:1.2.9'//û��Retrofit2ConverterFactory(��copy��retrofit�ļ���)

VERSION_CODE��ȡ:
http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.alibaba%22%20AND%20a%3A%22fastjson%22 (�������ܴ�)
http://repo1.maven.org/maven2/com/alibaba/fastjson/ (�����)
https://bintray.com/bintray/jcenter/com.alibaba%3Afastjson#files (�򿪽���)


//##################################################################################################################
2.JSONObject extends JSON implements Map<String, Object>	//��һ��Map����.
JSON.parseObject(String str)��JSONObject.parseObject(String str)������
JSON��һ�������࣬JSON����һ����̬����parseObject(String text)����text����Ϊһ��JSONObject���󲢷���;
JSONObject��һ���̳���JSON���࣬������JSONObject.parseObject(result)ʱ����ֱ�ӵ��ø����parseObject(String text)��
��������ûʲô����һ�����ø���ȥ���ø����Լ��ľ�̬��parseObject(String text)��һ����������ȥ���ø���ľ�̬parseObject(String text)�����ߵ�����ͬһ��������


//##################################################################################################################
3.����json�ַ���(��������)
import com.alibaba.fastjson.JSON;
String jsonString = JSON.toJSONString(bean);

//BeanToArray: [[15,1469003271063,"John Doe"],[20,1469003271063,"Janette Doe"]]
String json = JSONObject.toJSONString(list);//List���Bean�������ڲ���
String jsonOutput= JSON.toJSONString(list, SerializerFeature.BeanToArray);//List<Person>,List���Bean�������ڲ���

//BeanToArray: [{15,1469003271063,"John Doe"},{20,1469003271063,"Janette Doe"}]
JSONArray jsonArray = new JSONArray();
jsonArray.add(jsonObject);
String jsonOutput = jsonArray.toJSONString();

//ʹ�� ContextValueFilter ���� JSON ת��
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

//ʹ�� NameFilter �� SerializeConfig
NameFilter formatName = new NameFilter() {//���л�ʱ�޸� Key
	public String process(Object object, String name, Object value) {
		return name.toLowerCase().replace(" ", "_");
	}
};
 
SerializeConfig.getGlobalInstance().addFilter(Person.class,  formatName);//�ڲ��Ǹ�map������Ҫ���������ò���¼ÿ��Java���Ͷ�Ӧ�����л���
String jsonOutput = JSON.toJSONStringWithDateFormat(listOfPersons, "yyyy-MM-dd");
//ʵ�������������� formatName ������ʹ�� NameFilter �������������ֶ����ơ� �´����Ĺ������� Person ���������Ȼ����ӵ�ȫ��ʵ�������� SerializeConfig ���еľ�̬���ԡ�
//ע������ʹ�õ��� toJSONStringWithDateFormat() ������ toJSONString() �������Ը����ٵĸ�ʽ������
//���json: [{"full_name":"John Doe","date_of_birth":"2016-07-21"},{"full_name":"Janette Doe","date_of_birth":"2016-07-21"}]

SerializeConfig mapping = new SerializeConfig(); 
mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd")); 
String x2 = JSON.toJSONString(new Bean(), mapping); 


//##################################################################################################################
4. JSON����json(��������)
String jsonString = {"id":0,"name":"admin","users":[{"id":2,"name":"guest"},{"id":3,"name":"root"}]}
Group group = JSON.parseObject(jsonString, Group.class);

4.1 JSON/JSONObjectת����Bean��
User user = JSON.toJavaObject(json/jsonObject,User.class);
User user = JSONObject.toJavaObject(json/jsonObject,User.class);//����JSON�ķ���

4.2 JSONObject �ֶ����� json
		if (!TextUtils.isEmpty(message)) {//�ǻ۳ǹ�
			JSONObject msgObj = JSON.parseObject(message);
			if (msgObj.containsKey("type") && msgObj.getString("type").equals("video")) {
				String admin = msgObj.getString("admin");
				String url = msgObj.getString("url");
				System.out.println(admin+"�������㷢����Ƶ"+url);
				//TO-DO
				setNotification(MyApplication.instance,message);
			}
		}


//##################################################################################################################
5.��������([��ͷ��json),ֱ�ӽ�����JsonArray.(�ǻ۳ǹ�ʾ��)
List<ChooseAreaInfo> chooseAreaInfos = JSON.parseArray(response, ChooseAreaInfo.class);

JSONObject jsonObject = JSONObject.parseObject(response);
JSONArray jsonArray = jsonObject.getJSONArray("data");
List<Class> clazzs = jsonArray.toJavaList(Class.class);


//##################################################################################################################
6.������Map
// String���ͣ�
Map<String, String> tempMap = JSON.parseObject(jsonString, new TypeReference<Map<String, String>>() {});

// JavaBean���ͣ�
Map<String, UserInfo> tempMap = JSON.parseObject(jsonString, new TypeReference<Map<String, UserInfo>>() {});

// List<JavaBean>���ͣ�
Map<String, List<UserInfo>> tempMap = JSON.parseObject(jsonString, new TypeReference<Map<String, List<UserInfo>>>() {});



//##################################################################################################################
7.ע��˵��
@JSONCreator


@JSONType(orders = {"age","name"})		//ע��������,TYPE    ����orders������˳��, ������json
//JSONType ����һЩ����...
public class User {
	
	@JSONField(ordinal = 1, name = "z_name")//ʾ��: {"name":"����","age":"23"} ==> {"z_name":"����","age":"23"}
    public String name;
	
	@JSONField(ordinal = 0)				//ʾ��: {"name":"����","age":"23"} ==> {"age":"23","name":"����"}
	public int age;
	
	@JSONField(format = "yyyy-MM-dd")
    public Date birtyday;
}


@JSONField ����˵��: //ע���� METHOD(get/set����), FIELD(�ֶ�), PARAMETER
    int ordinal() default 0;			//�������� ���json�ַ�����key���Ե��Ⱥ�˳��,������ @JSONType ��orders={}��������.
    String name() default "";			//���ֶ������,�ɰ����ո�, ������ "���� & ���" key������.
    String format() default "";			//�������ڸ�ʽ��, �� String���� ת��Ϊ Date
    boolean serialize() default true;	//ָ���ֶ��Ƿ����л�(����json�ַ���), ���Ϊfalse, ������ֶβ������л���json��
    boolean deserialize() default true;	//ָ���ֶ��Ƿ���Ҫ���з����л�(jsonתBean), ���Ϊfalse, �������Bean����ֶ�Ϊ��
	
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




7.��ӻ���: ��ַ: http ?????
##---------------Begin: proguard configuration for fastjson------
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.*{*;}
##������Fastjson��Bean��(�����������·��Ҫ�Լ��ġ���)
#-keep class com.package.xxx.info.** { *; }
##---------------End: proguard configuration for fastjson--------


//-----------------�����Ǿ���˵��----------------------------
Fastjson�ṩAndroid�汾���ͱ�׼�汾��ȣ�Android�汾ȥ��һЩAndroid�����dalvik��֧�ֵĹ��ܣ�ʹ��jar��С��ͬʱ���dalvik���˺ܶ������Ż����������ٷ������õȡ�parseΪJSONObject/JSONArrayʱ��ԭ��org.json�ٶȿ죬���л������л�JavaBean���ܱ�jackson/gson���ܸ��á�

Android�汾��ȥ���Ĺ���

1. ASM

Dalvik��������ֽ����ʽ��Java SE��һ����Ŀǰ��ASM��̬�Ż��޷���Android��ʵ�֡�

2. AWT

Android�ϵ�Dalvik�������֧��awt������ȥ����׼�汾��awt��������л��ͷ����л���֧�֡�

3. AtomicXXX

AtomicXXX��Ӧ����ΪPOJO�����ԣ������ã�������Android�汾��ȥ���ˡ�

4. ��֧��Clob�������л���

5. JSONPObject

�����������web jsonp�������ɣ���������android�ͻ��ˣ�����ȥ����

6. ��֧��Reference�ֶ��������л��ͷ����л�������WeakReference/SoftReference/AtomicReference����Щ���Ͳ���������POJO�����ԣ�����ȥ���ˡ�

7. ���·��������ã���֧��

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

// JSONSerializerMap�ѷ�������֧��
com.alibaba.fastjson.serializer.JSONSerializer.JSONSerializer(JSONSerializerMap)
8. һЩ�������಻֧��

com.alibaba.fastjson.parser.JavaBeanMapping ʹ��com.alibaba.fastjson.parser.ParserConfig����
com.alibaba.fastjson.serializer.JSONSerializerMap  ʹ��com.alibaba.fastjson.serializer.SerializeConfig����
com.alibaba.fastjson.parser.DefaultExtJSONParser ʹ��com.alibaba.fastjson.parser.DefaultJSONParser����
9. һЩ����������֧��

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