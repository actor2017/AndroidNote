package com.ly.changyi.utils.MyOkhttpUtils;

import android.text.TextUtils;
import android.util.Log;

import com.ly.changyi.constant.Global;
import com.ly.changyi.utils.SPUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Description: 拦截器, 参考: com.zhy.http.okhttp.log.LoggerInterceptor;
 *                            builder.addInterceptor(new LoggerInterceptor(getPackageName() + ",Interceptor:", isAppDebug()))//tag, showResponse
 *
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/1/26 on 13:03
 * @deprecated : 过时, 用okhttp3自带的拦截器
 */
 @Deprecated
public class MyLoggerInterceptor implements Interceptor {
    public static final String  TAG = "OkHttpUtils";
    private             String  tag;
    private             boolean isDebugMode;

    public MyLoggerInterceptor(String tag, boolean isDebugMode) {
        if (TextUtils.isEmpty(tag)) tag = TAG;
        this.isDebugMode = isDebugMode;
        this.tag = tag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String token = SPUtils.getString(Global.TOKEN);
        if (token == null) token = "";
        request = request.newBuilder()
                .addHeader("authorization", token)//添加验证到请求头, key & value不能为null
                .build();
        if (isDebugMode) logForRequest(request);
        Response response = chain.proceed(request);//继续进行
        if (!isDebugMode) return response;//如果是release, 直接return
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        Log.e(tag, "========================response'log========================");
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        Log.e(tag, "url : " + clone.request().url());
        Log.e(tag, "code : " + clone.code());
        Log.e(tag, "protocol : " + clone.protocol());
        Log.e(tag, "message : " + clone.message());
        ResponseBody body = clone.body();
        if (body != null) {
            MediaType mediaType = body.contentType();
            if (mediaType != null) {
                Log.e(tag, "responseBody's contentType : " + mediaType.toString());
                if (isText(mediaType)) {
                    try {
                        String resp = body.string();
                        Log.e(tag, "responseBody's content : " + resp);
                        body = ResponseBody.create(mediaType, resp);
                        response = response.newBuilder().body(body).build();
//                        return response;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(tag, "responseBody's content : " + " maybe [file part] , too " +
                            "large too print , ignored!");
                }
            }
        }
        Log.e(tag, "========================response'log========================end");
        Log.e(tag, " ");
        Log.e(tag, " ");
        return response;
    }

    private void logForRequest(Request request) {
        try {
            HttpUrl httpUrl = request.url();
            String url = httpUrl.toString();
            Headers headers = request.headers();

            if (!"POST".equals(request.method()))//如果post就不打印这句
                Log.e(tag, "========================request'log========================");
            Log.e(tag, "method : " + request.method());
            Log.e(tag, "url : " + url);
//            if ("GET".equals(request.method())) {//打印参数
//                Log.e(tag, "params: ");
//                Set<String> keys = httpUrl.queryParameterNames();
//                for (String key : keys) {
//                    Log.e(tag, key + " : " + httpUrl.queryParameter(key));
//                }
//            }
//            if ("POST".equals(request.method())) {
//                Log.e(tag, "params:");
//                RequestBody requestBody = request.body();
//                //在OkHttoUtils中:com.zhy.http.okhttp.request.CountingRequestBody
//                Log.e(TAG, "RequestBody类型: " + requestBody.getClass().getName());
//                if (requestBody instanceof FormBody) {//false
//                    for (int i = 0; i < ((FormBody) requestBody).size(); i++) {
//                        String key = ((FormBody) requestBody).encodedName(i);
//                        String value = ((FormBody) requestBody).encodedValue(i);
//                        Log.e(tag, key + "=" + value);
//                    }
//                } else {//https://blog.csdn.net/wuyinlei/article/details/57087872
//                    //buffer流
//                    Buffer buffer = new Buffer();
//                    requestBody.writeTo(buffer);
//                    String oldParamsJson = buffer.readUtf8();
//                    rootMap = mGson.fromJson(oldParamsJson, HashMap.class);  //原始参数
//                    rootMap.put("publicParams", commomParamsMap);  //重新组装
//                    String newJsonParams = mGson.toJson(rootMap);  //装换成json字符串
//
//                    request = request.newBuilder().post(RequestBody.create(JSON, newJsonParams)).build();
//                }
//            }
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.e(tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.e(tag, "requestBody's content : " + bodyToString(request));
                    } else {
                        Log.e(tag, "requestBody's content : " + " maybe [file part] , too large " +
                                "too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(tag, "========================request'log========================end");
        Log.e(tag, " ");
        Log.e(tag, " ");
    }

    private boolean isText(MediaType mediaType) {
        if ("text".equals(mediaType.type())) return true;
        String subtype = mediaType.subtype();
        if (subtype != null) {
            if (subtype.equals("json") || subtype.equals("xml") || subtype.equals("html") ||
                    subtype.equals("webviewhtml"))
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            e.printStackTrace();
            return "something error when show requestBody.";
        }
    }
}
