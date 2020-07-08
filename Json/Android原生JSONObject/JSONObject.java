Android原生JSONObject
<dependency>
	<groupId>org.json</groupId>
	<artifactId>json</artifactId>
	<version>20160810</version>
</dependency>

一般情况下，我会在解析json数据的时候使用Gson来进行解析，因为它的使用非常的方便.
但是，在下述的情况下，我会使用JsonObject来进行解析
1、当json数据相当复杂的情况之下，使用JsonObject来进行解析，因为它的效率高
2、当json数据相当简单的情况之下，使用JsonObject来进行解析，{"sucess":"true"}


1.JSONObject -> json
JSONObject jsonObj = new JSONObject(/*json*/);
//JSONObject jsonObj = new JSONObject(map);//这种也一样的
//JSONObject jsonObj = new JSONObject(userInfo);

jsonObj.put("female", true);
jsonObj.put("hobbies", Arrays.asList(new String[] { "yoga", "swimming" }));
jsonObj.put("discount", 9.5);
jsonObj.put("age", "26");
jsonObj.put("features", new HashMap<String, Integer>() {
	private static final long serialVersionUID = 1L;
	{
		put("height", 175);
		put("weight", 70);
	}
});
System.out.println(jsonObj);
{
	"features": {
		"weight": 70,
		"height": 175
	},
	"hobbies": ["yoga", "swimming"],
	"discount": 9.5,
	"female": true,
	"age": 26
}

2.get方法
jsonObj.getBoolean("female")
jsonObj.getDouble("discount")
jsonObj.getLong("age")
String s = jsonObj.getString("versionName");
jsonObj.getJSONObject("features")

String[] names = JSONObject.getNames(jsonObj);//names

JSONArray hobbies = jsonObj.getJSONArray("hobbies");

https://blog.csdn.net/wildstark/article/details/79306950


//安全卫士示例:

//{"versionName":"2.0","versionCode":2, "description":"最新版手机卫士,
//非常牛逼,快来下载哦!!!","downloadUrl":"http://10.0.3.2:8080/mobilesafe2.0.apk"}
//解析json
try {
	JSONObject jsonObject = new JSONObject(s);
	String versionName = jsonObject.getString("versionName");
	versionCode = jsonObject.getInt("versionCode");
	description = jsonObject.getString("description");
	downloadUrl = jsonObject.getString("downloadUrl");
} catch (JSONException e) {
	e.printStackTrace();
	Toast.makeText(HomeActivity.this, "json解析错误", Toast.LENGTH_SHORT).show();
}


//-----------------------json解析成数组-----------------------
try {
	JSONArray ja = new JSONArray(json);
	dataList = new ArrayList<>();
	for (int i = 0; i < ja.length(); i++) {
		AppInfo appInfo = new AppInfo();
		JSONObject joItem = ja.getJSONObject(i);
		appInfo.des = joItem.getString("des");
		appInfo.downloadUrl = joItem.getString("downloadUrl");
		appInfo.iconUrl = joItem.getString("iconUrl");
		appInfo.id = joItem.getString("id");
		appInfo.name = joItem.getString("name");
		appInfo.packageName = joItem.getString("packageName");
		appInfo.size = joItem.getString("size");
		appInfo.stars = joItem.getString("stars");
		dataList.add(appInfo);
	}
	return dataList;
} catch (JSONException e) {
	e.printStackTrace();
}
