Android原生JSONObject
<dependency>
	<groupId>org.json</groupId>
	<artifactId>json</artifactId>
	<version>20160810</version>
</dependency>

1.JSONObject -> json
JSONObject jsonObj = new JSONObject();
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
jsonObj.getJSONObject("features")

String[] names = JSONObject.getNames(jsonObj);//names

JSONArray hobbies = jsonObj.getJSONArray("hobbies");

https://blog.csdn.net/wildstark/article/details/79306950
