package com.leipaiyun.lpsj.utils;

import com.actor.myandroidframework.utils.SPUtils;
import com.runman.baselibrary.control.Global;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description: 网络拦截器, 添加参数Params
 * @author    : 李大发
 * date       : 2020/8/8 on 12:06
 * @version 1.0
 */
public class AddParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String token = SPUtils.getString(Global.token);
        if (token == null) token = "";
        HttpUrl.Builder builder = request.url().newBuilder();

        //得到新的Url（已经追加好了参数）
        HttpUrl newUrl = builder
                .addQueryParameter(Global.token, token)
                .build();

        //利用新的Url 构建新的Request ，并发送给服务器
        Request newRequest = request.newBuilder()
                .url(newUrl)						//★★★1.添加在url后面★★★
                .build();
        return chain.proceed(newRequest);//继续进行





        //post方法的所有参数(b=1&a=2), get不知是否能获取, 没试
        RequestBody body = request.body();
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        String params = buffer.readString(Charset.forName("UTF-8"));





        //2.添加到Post的Body, GET没试
        if (body instanceof FormBody) {
            FormBody.Builder builder = new FormBody.Builder();
            int size = ((FormBody) body).size();
            for (int i = 0; i < size; i++) {
                builder.add(((FormBody) body).encodedName(i), ((FormBody) body).encodedValue(i));
            }
            request = request.newBuilder().method(request.method(), body);
        }


    }
}
