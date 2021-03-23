https://github.com/FasterXML/jackson
https://github.com/FasterXML/jackson-docs
https://github.com/FasterXML/jackson-docs/wiki/JacksonOnAndroid

Jackson 的核心模块由三部分组成。
jackson-core，核心包，提供基于"流模式"解析的相关 API，它包括 JsonPaser 和 JsonGenerator。 Jackson 内部实现正是通过高性能的流模式 API 的 JsonGenerator 和 JsonParser 来生成和解析 json。
jackson-annotations，注解包，提供标准注解功能；
jackson-databind ，数据绑定包， 提供基于"对象绑定" 解析的相关 API （ ObjectMapper ） 和"树模型" 解析的相关 API （JsonNode）；基于"对象绑定" 解析的 API 和"树模型"解析的 API 依赖基于"流模式"解析的 API。

<!-- https://github.com/FasterXML/jackson-databind -->
<properties>
  ...
  <!-- Use the latest version whenever possible. -->
  <jackson.version>2.12.0</jackson.version>
  ...
</properties>
<dependencies>
<!-- Package also depends on jackson-core and jackson-annotations packages -->
  <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>${jackson.version}</version>
  </dependency>
</dependencies>


1.创建 ObjectMapper 实例
ObjectMapper mapper = new ObjectMapper();//create once, reuse(创建一次, 重用)



2.反序列化
2.1.从File读取json
MyValue value = mapper.readValue(new File("data.json"), MyValue.class);

2.2.从URL
MyValue value = mapper.readValue(new URL("http://some.com/api/entry.json"), MyValue.class);

2.3.从String
MyValue value = mapper.readValue("{\"name\":\"Bob\", \"age\":13}", MyValue.class);

2.4.其它类型
Map<String, Integer> scoreByName = mapper.readValue(jsonString, Map.class);
//如果Map类型不是简单类型, 需要指定TypeReference
Map<String, ResultValue> results = mapper.readValue(jsonSource, new TypeReference<Map<String, ResultValue>>() { } );
//List即使不是简单类型, 也可以不用指定
List<String> names = mapper.readValue(jsonString, List.class);
   


3.序列化
3.1.将对象写入到File: result.json
mapper.writeValue(new File("result.json"), value);
// or:
byte[] jsonBytes = mapper.writeValueAsBytes(value);
// or:
String jsonString = mapper.writeValueAsString(value);

3.2.其它类型
mapper.writeValue(new File("names.json"), names);//List<String> names



4.树模型
While dealing with Maps, Lists and other "simple" Object types (Strings, Numbers, Booleans) can be simple, Object traversal can be cumbersome. This is where Jackson's Tree model can come in handy:
虽然处理映射、列表和其他“简单”对象类型(字符串、数字、布尔值)可能很简单，但对象遍历可能很麻烦。这就是Jackson的树模型可以派上用场的地方:
ObjectNode root = mapper.readTree("stuff.json");
String name = root.get("name").asText();
int age = root.get("age").asInt();

// can modify as well: this adds child Object as property 'other', set property 'type'
// 也可以修改:将子对象添加为属性“other”，设置属性“type”
root.with("other").put("type", "student");
String json = mapper.writeValueAsString(root);
// {
//   "name" : "Bob", "age" : 13,
//   "other" : {
//      "type" : "student"
//   }
// }
//树模型可能比数据绑定更方便，特别是在结构高度动态或不能很好地映射到Java类的情况下。



5.Streaming parser, generator
JsonFactory f = mapper.getFactory(); // may alternatively construct directly too

// First: write simple JSON output
File jsonFile = new File("test.json");
JsonGenerator g = f.createGenerator(jsonFile);
// write JSON: { "message" : "Hello world!" }
g.writeStartObject();
g.writeStringField("message", "Hello world!");
g.writeEndObject();
g.close();

// Second: read file back
JsonParser p = f.createParser(jsonFile);

JsonToken t = p.nextToken(); // Should be JsonToken.START_OBJECT
t = p.nextToken(); // JsonToken.FIELD_NAME
if ((t != JsonToken.FIELD_NAME) || !"message".equals(p.getCurrentName())) {
   // handle error
}
t = p.nextToken();
if (t != JsonToken.VALUE_STRING) {
   // similarly
}
String msg = p.getText();
System.out.printf("My message to you is: %s!\n", msg);
p.close();



6.配置(2种入门配置: Features and Annotations)
6.1.数据绑定配置, Full set of features are explained on Jackson Features page.完整功能配置: https://github.com/FasterXML/jackson-databind/wiki/JacksonFeatures
// 配置序列化特征
// to enable standard indentation ("pretty-printing"): 启用标准缩进(“漂亮打印”)
mapper.enable(SerializationFeature.INDENT_OUTPUT);

// to allow serialization of "empty" POJOs (no properties to serialize) 允许对“空”pojo进行序列化(没有要序列化的属性)
// (without this setting, an exception is thrown in those cases) (如果没有这个设置，在这些情况下会抛出异常)
mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

// to write java.util.Date, Calendar as number (timestamp): 将Date, Calendar 写成number(时间戳)
mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

// 配置反序列化特征
// to prevent exception when encountering unknown property: 防止遇到未知属性时出现异常
mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

// to allow coercion of JSON empty String ("") to null Object value: 允许JSON空字符串("")强制为空对象值
mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);


//解析设置的配置特性: JsonParser。
// to allow C/C++ style comments in JSON (non-standard, disabled by default) 允许在JSON中使用C/ c++风格的注释(非标准，默认禁用)
// (note: with Jackson 2.5, there is also `mapper.enable(feature)` / `mapper.disable(feature)`) (注意:在Jackson 2.5中，还有' mapper.enable(feature) ' / ' mapper.disable(feature) ')
mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);

// to allow (non-standard) unquoted field names in JSON: 在JSON中允许(非标准的)不带引号的字段名
mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

// to allow use of apostrophes (single quotes), non standard 允许使用撇号(单引号)，不标准
mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

//用于配置低级JSON生成的特性: JsonGenerator.Feature for configuring low-level JSON generation: JsonGenerator。
// to force escaping of non-ASCII characters: 强制转义非ascii字符
mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);



//配置private 的属性也能序列化, 否则如果实体连 1个public字段/set方法 都没有的话, 会报错
mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);


6.2.注解配置
// changing property names 改变属性名
// The simplest annotation-based approach is to use @JsonProperty annotation like so: 最简单的基于注释的方法是像这样使用@JsonProperty注释

public class MyBean {
   private String _name;

   // without annotation, we'd get "theName", but we want "name": 如果没有annotation，我们会得到"theName", 但我们想要的是"name"
   @JsonProperty("name")
   public String getTheName() { return _name; }

   public void setTheName(String n) { _name = n; }
}
7.还有很多注释属性... ...
https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=jackson%20%E4%BD%BF%E7%94%A8&oq=jakeson%2520%25E4%25BD%25BF%25E7%2594%25A8&rsv_pq=cbc8c6bf0002270f&rsv_t=92ec5%2FAK58A7TswXWQ1v6JdiPMVo5MF5o6DW%2ByQLdhnWMvYxRBXI%2BIRL%2F3s&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_sug3=3&rsv_sug1=2&rsv_sug7=100&bs=jakeson%20%E4%BD%BF%E7%94%A8
https://www.cnblogs.com/guanbin-529/p/11488869.html





//Ignore Null Fields on the Class/Field
//@JsonInclude(JsonInclude.Include.NON_NULL)
//mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);//全局配置



        //写到Response
//        mapper.writeValue(response.getWriter(), testUser);
