https://github.com/franmontiel/PersistentCookieJar

PersistentCookieJar for OkHttp 3
A persistent CookieJar implementation for OkHttp 3 based on SharedPreferences.

//------------------------------------------------------------------------------
//OKHttp管理Cookie关键代码:
private Request networkRequest(Request request) throws IOException {
    Request.Builder result = request.newBuilder();

    //例行省略....
    List<Cookie> cookies = client.cookieJar().loadForRequest(request.url());
    if (!cookies.isEmpty()) {//如果不保存,就没有,所以要报错Cookie&Session
      result.header("Cookie", cookieHeader(cookies));//帮我们将session和cookie放到了请求头里面
    }
    //例行省略....
    return result.build();
}
private String cookieHeader(List<Cookie> cookies) {
    StringBuilder cookieHeader = new StringBuilder();
    for (int i = 0, size = cookies.size(); i < size; i++) {
      if (i > 0) {
        cookieHeader.append("; ");
      }
      Cookie cookie = cookies.get(i);
      cookieHeader.append(cookie.name()).append('=').append(cookie.value());
    }
    return cookieHeader.toString();
}
public void receiveHeaders(Headers headers) throws IOException {
    if (client.cookieJar() == CookieJar.NO_COOKIES) return;
    List<Cookie> cookies = Cookie.parseAll(userRequest.url(), headers);
    if (cookies.isEmpty()) return;
    client.cookieJar().saveFromResponse(userRequest.url(), cookies);
}
//------------------------------------------------------------------------------

If you're looking for a OkHttp 2/HTTPUrlConnection persistent CookieStore it can be found at this Gist.

Download
Step 1. Add the JitPack repository in your root build.gradle at the end of repositories:

allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
Step 2. Add the dependency

dependencies {
    compile 'com.github.franmontiel:PersistentCookieJar:v1.0.1'
}

使用:
Create an instance of PersistentCookieJar passing a CookieCache and a CookiePersistor:

ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
Then just add the CookieJar when building your OkHttp client:

OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

混淆:The following configuration is only needed for version 0.9.2 and below:

-dontwarn com.franmontiel.persistentcookiejar.**
-keep class com.franmontiel.persistentcookiejar.**

-keepclassmembers class * implements java.io.Serializable {  
    static final long serialVersionUID;  
    private static final java.io.ObjectStreamField[] serialPersistentFields;  
    !static !transient <fields>;  
    private void writeObject(java.io.ObjectOutputStream);  
    private void readObject(java.io.ObjectInputStream);  
    java.lang.Object writeReplace();  
    java.lang.Object readResolve();  
}
