https://square.github.io/okhttp/3.x/okhttp/okhttp3/Request.html

RequestBody body = request1.body();
CacheControl cacheControl = request1.cacheControl();//返回此响应的缓存控制指令。
String token = request1.header("Authorization");//自己添加的token
Headers headers = request1.headers();
List<String> tokens = request1.headers("Authorization");
boolean https = request1.isHttps();//是否是 https
String method = request1.method();//GET, POST, ...
Request.Builder builder = request1.newBuilder();
Object tag = request1.tag();//返回附带有标签Object.class的键作为键；如果没有附加任何键的键，则返回null。
LoginActivity tag1 = request1.tag(LoginActivity.class);//返回附带有标签type的键作为键；如果没有附加任何键的键，则返回null。
String string = request1.toString();//method,url,tags
HttpUrl url1 = request1.url();
