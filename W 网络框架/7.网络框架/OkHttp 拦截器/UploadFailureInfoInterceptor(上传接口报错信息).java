package com.cmcc.yyn.http;

import android.text.TextUtils;

import com.actor.myandroidframework.utils.okhttputils.MyOkHttpUtils;
import com.blankj.utilcode.util.AppUtils;
import com.cmcc.yyn.common.Global;
import com.cmcc.yyn.utils.SPUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import kotlin.Pair;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * description: 网络拦截器, 上传错误信息
 *              因为有些请求没有回调, 所以用拦截器来做错误上传
 * @author    : 李大发
 * date       : 2020/8/21 on 14:21
 * @version 1.0
 */
public class UploadFailureInfoInterceptor implements Interceptor {

    public static final String APP_NAME = AppUtils.getAppName();

    private static final Map<String, Object> HEADERS = new LinkedHashMap<>();
    private static final Map<String, Object> PARAMS = new LinkedHashMap<>();

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        //如果请求失败, code < 200 或 code > 299
        if (/*true || */!response.isSuccessful()) {
            //错误日志token
            String errorLogToken = SPUtils.getString(Global.ERROR_LOG_TOKEN);
            //如果未获取到token, 就再去服务器获取
            if (TextUtils.isEmpty(errorLogToken)) {
                Global.getErrorLogToken();
                return response;
            }
            Request request1 = response.request();
            String url = request1.url().toString();
            //如果这个报错的请求就是 "上传错误信息" 的请求, 就不再上传. 否则会一直调用.
            if (!url.contains(Global.ERROR_LOG_UPLOAD)) {
                String method = request1.method();
                int code = response.code();//200
                String message = response.message();//OK
                Headers headers = request1.headers();//headers
                RequestBody params = request1.body();//Post请求的参数

                ResponseBody body = response.body();
                String returnString = null;//返回的json/html
                if (body != null) {
                    MediaType mediaType = body.contentType();//application/json;charset=UTF-8
                    if (mediaType != null) {
                        String mediaTypeString = mediaType.toString();//application/json;charset=UTF-8
                        String subtype = mediaType.subtype();//json/xml/png
                        if (mediaTypeString.startsWith("application/json") ||   //json
                                mediaTypeString.startsWith("text/html") ||      //HTML格式
                                mediaTypeString.startsWith("application/xml") ||//xml
                                mediaTypeString.startsWith("text/plain")) {     //纯文本格式
                            returnString = body.string();
                            //重新构建body&response
                            body = ResponseBody.create(mediaType, returnString);
                            response = response.newBuilder().body(body).build();
                        }
                    }
                }
                uploadFailureInfo(method, url, code, message, headers, params, returnString);
            }
        }
        return response;
    }

    /**
     * 上传错误信息
     * @param method GET/POST
     * @param url https://www.xxx.com/
     * @param code 403...
     * @param msg OK/...
     * @param headers 请求头
     * @param body POST的请求参数(String/json/...)
     * @param returnString 服务器返回的错误信息
     * @throws IOException
     */
    public static synchronized void uploadFailureInfo(String method, String url, int code, String msg, Headers headers, RequestBody body, String returnString) throws IOException {
        for (Pair<? extends String, ? extends String> header : headers) {
            String first = header.getFirst();   //header的key
            String second = header.getSecond(); //header的value
        }
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        //获取post方法的"所有参数"(b=1&a=2) 或 发送的(String/json), get不知是否能获取, 没试
        String params = buffer.readUtf8();

        //错误日志token
        String errorLogToken = SPUtils.getString(Global.ERROR_LOG_TOKEN);
        //开始上传错误信息
        HEADERS.clear();
        HEADERS.put("token", errorLogToken);
        PARAMS.clear();
        PARAMS.put("clientType", "ANDROID");
        PARAMS.put("code", code);
        PARAMS.put("errorType", "INTERFACE");//PAGE, INTERFACE, LINK(页面, 接口, 外链)
        PARAMS.put("info", returnString);
        PARAMS.put("parameter", params);
        PARAMS.put("projectName", APP_NAME);
        PARAMS.put("url", url);
        MyOkHttpUtils.post(Global.ERROR_LOG_UPLOAD, HEADERS, PARAMS, null   //上传不成功就没办法了
//                new NoLoadingDialogCallBack<ErrorLogUploadInfo>(null) {
//                    @Override
//                    public void onOk(@NonNull ErrorLogUploadInfo info, int id) {
//                    }
//                }
        );
    }
}
