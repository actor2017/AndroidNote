https://square.github.io/okhttp/3.x/okhttp/okhttp3/Response.html
Request request = chain.request();
Response response = chain.proceed(request);
Response.Builder builder = response.newBuilder();
Response responseClone = response/*builder.build()*/;

//如果此响应传递给Callback.onResponse(okhttp3.Call, okhttp3.Response)或从返回，则返回非null值Call.execute()。
ResponseBody bodyClone = responseClone.body();
CacheControl cacheControl = responseClone.cacheControl();//返回此响应的缓存控制指令。
Response response1 = responseClone.cacheResponse();//返回从缓存接收的原始响应。
List<Challenge> challenges = responseClone.challenges();//返回适用于此响应代码的RFC 7235授权质询。
responseClone.close();//关闭响应主体。
int code = responseClone.code();//返回HTTP状态代码。
Handshake handshake = responseClone.handshake();//返回承载此响应的连接的TLS握手，如果收到的响应没有TLS，则返回null。
String date = responseClone.header("Date");
String contentType = responseClone.header("Content-Type", "application/json;charset=UTF-8");
Headers headers = responseClone.headers();
List<String> allowCredentials = responseClone.headers("Access-Control-Allow-Credentials");
boolean redirect = responseClone.isRedirect();//如果此响应重定向到另一个资源，则返回true。
boolean successful = responseClone.isSuccessful();
String message = responseClone.message();//返回HTTP状态消息。
Response networkResponse = responseClone.networkResponse();//返回从网络收到的原始响应。
Response.Builder builder = responseClone.newBuilder();//克隆一份
ResponseBody responseBody = responseClone.peekBody(1L);//byteCount从响应主体中查看最多字节，并将其作为新的响应主体返回。
Response priorResponse = responseClone.priorResponse();//返回触发该响应的HTTP重定向或授权质询的响应；如果此响应不是由自动重试触发的，则返回null。
Protocol protocol = responseClone.protocol();//返回HTTP协议，例如Protocol.HTTP_1_1或Protocol.HTTP_1_0。
long ms = responseClone.receivedResponseAtMillis();//返回在OkHttp从网络接收到此响应的标头后立即采取的时间戳。
Request request1 = responseClone.request();//发起此HTTP响应的线路级别请求。
long l = responseClone.sentRequestAtMillis();//返回在OkHttp通过网络传输发起请求之前紧接的时间戳。
String string = responseClone.toString();
//Headers hes = responseClone.trailers();//在HTTP响应之后返回预告片，该预告片可以为空。(现在没这个方法啊?)
