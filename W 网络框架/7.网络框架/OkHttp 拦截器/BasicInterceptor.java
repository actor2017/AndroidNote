package com.actor.myandroidframework.utils.okhttputils;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * description: 拦截器工具类, 借鉴自: https://www.jianshu.com/p/3d8fa2c60b6b
 * 使用:
 * Interceptor interceptor = new BasicInterceptor.Builder()
 *     .addQueryParam("key1", "v1")
 *     .addQueryParam("key2", "v2")
 *     .build();
 *
 * @author : 李大发
 * date       : 2020/10/12 on 15
 * @version 1.0
 */
public class BasicInterceptor implements Interceptor {

    // 添加到 URL 末尾，Get Post 方法都适用
    protected static final Map<String, String> QUERY_PARAMS_MAP  = new LinkedHashMap<>();
    // 添加到公共参数到消息体，适用 Post 请求
    protected static final Map<String, String> PARAMS_MAP        = new LinkedHashMap<>();
    // 公共 Headers 添加
    protected static final Map<String, String> HEADER_PARAMS_MAP = new LinkedHashMap<>();
    // 消息头 集合形式，一次添加一行
    protected static final List<String>        HEADER_LINES_LIST = new ArrayList<>();

    // 私有构造器
    protected BasicInterceptor() {}

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        //添加请求头

        if (!HEADER_PARAMS_MAP.isEmpty() || HEADER_LINES_LIST.isEmpty()) {
            Headers.Builder headerBuilder = request.headers().newBuilder();
            // 以 Entry 添加消息头
            if (!HEADER_PARAMS_MAP.isEmpty()) {
                for (Map.Entry<String, String> entry : HEADER_PARAMS_MAP.entrySet()) {
                    headerBuilder.add(entry.getKey(), entry.getValue());
                }
            }
            // 以 String 形式添加消息头
            if (!HEADER_LINES_LIST.isEmpty()) {
                for (String line: HEADER_LINES_LIST) {
                    headerBuilder.add(line);
                }
                requestBuilder.headers(headerBuilder.build());
            }
        }


        // 添加到 URL 末尾，Get Post 方法都适用
        if (!QUERY_PARAMS_MAP.isEmpty()) {
            HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
            for (Map.Entry<String, String> entry : QUERY_PARAMS_MAP.entrySet()) {
                httpUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            requestBuilder.url(httpUrlBuilder.build());
            request = requestBuilder.build();
        }

        // 添加到公共参数到消息体，适用 Post 请求
        if (!PARAMS_MAP.isEmpty()) {
            if (canInjectIntoBody(request)) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                for(Map.Entry<String, String> entry : PARAMS_MAP.entrySet()) {
                    formBodyBuilder.add(entry.getKey(), entry.getValue());
                }

                RequestBody formBody = formBodyBuilder.build();
                RequestBody body = request.body();
                String postBodyString = bodyToString(body);
                postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  bodyToString(formBody);
                MediaType mediaType = body.contentType();
                requestBuilder.post(RequestBody.create(mediaType/*MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8")*/, postBodyString));
            }
        }
        request = requestBuilder.build();
        return chain.proceed(request);
    }

    /**
     *  确认是否是 post 请求
     * @param request 发出的请求
     * @return true 需要注入公共参数
     */
    protected boolean canInjectIntoBody(Request request) {
        if (request == null) {
            return false;
        }
        if (!TextUtils.equals(request.method(), "POST")) {
            return false;
        }
        RequestBody body = request.body();
        if (body == null) {
            return false;
        }
        MediaType mediaType = body.contentType();
        if (mediaType == null) {
            return false;
        }
        //multipart/form-data
        //multipart/x-www-form-urlencoded
        //...
        return TextUtils.equals(mediaType.subtype(), "x-www-form-urlencoded");
    }

    protected String bodyToString(final RequestBody request){
        try {
            final Buffer buffer = new Buffer();
            if(request != null) {
                request.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            e.printStackTrace();
            return "did not work";
        }
    }

    public static class Builder {

        BasicInterceptor interceptor;

        public Builder() {
            interceptor = new BasicInterceptor();
            QUERY_PARAMS_MAP.clear();
            PARAMS_MAP.clear();
            HEADER_PARAMS_MAP.clear();
            HEADER_LINES_LIST.clear();
        }

        // 添加公共参数到 post 消息体
        public Builder addParam(String key, String value) {
            PARAMS_MAP.put(key, value);
            return this;
        }

        // 添加公共参数到 post 消息体
        public Builder addParamsMap(Map<String, String> paramsMap) {
            PARAMS_MAP.putAll(paramsMap);
            return this;
        }

        // 添加公共参数到消息头
        public Builder addHeaderParam(String key, String value) {
            HEADER_PARAMS_MAP.put(key, value);
            return this;
        }

        // 添加公共参数到消息头
        public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
            HEADER_PARAMS_MAP.putAll(headerParamsMap);
            return this;
        }

        // 添加公共参数到消息头
        public Builder addHeaderLine(String headerLine) {
            int index = headerLine.indexOf(":");
            if (index == -1) {
                throw new IllegalArgumentException("Unexpected header: " + headerLine);
            }
            HEADER_LINES_LIST.add(headerLine);
            return this;
        }

        // 添加公共参数到消息头
        public Builder addHeaderLinesList(List<String> headerLinesList) {
            for (String headerLine: headerLinesList) {
                int index = headerLine.indexOf(":");
                if (index == -1) {
                    throw new IllegalArgumentException("Unexpected header: " + headerLine);
                }
                HEADER_LINES_LIST.add(headerLine);
            }
            return this;
        }

        // 添加公共参数到 URL
        public Builder addQueryParam(String key, String value) {
            QUERY_PARAMS_MAP.put(key, value);
            return this;
        }

        // 添加公共参数到 URL
        public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {
            QUERY_PARAMS_MAP.putAll(queryParamsMap);
            return this;
        }

        public BasicInterceptor build() {
            return interceptor;
        }
    }
}
