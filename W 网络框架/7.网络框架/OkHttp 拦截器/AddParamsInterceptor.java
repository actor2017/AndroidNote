package com.leipaiyun.lpsj.utils;

import com.actor.myandroidframework.utils.SPUtils;
import com.runman.baselibrary.control.Global;

import org.jetbrains.annotations.NotNull;

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

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String token = SPUtils.getStringNoNull(Global.token);
        HttpUrl.Builder builder = request.url().newBuilder();

        //得到新的Url（已经追加好了参数）
        HttpUrl newUrl = builder
                .addQueryParameter(Global.token, token)//即使在post也会把参数追加到"url后面", http://a.com?token=12
                .build();

        //利用新的Url 构建新的Request ，并发送给服务器
        Request newRequest = request.newBuilder()
                .url(newUrl)//★★★1.添加在url后面★★★
                .build();



        //获取post方法的"所有参数"(b=1&a=2), get不知是否能获取, 没试
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
            request = request.newBuilder().method(request.method(), builder.build()).build();
        }


        /**
         * 3.下方反射获取张鸿洋的okhttputils(post方法已测试可行)
         */
        RequestBody body = request.body();
        if (body instanceof CountingRequestBody) {
            ReflectUtils reflect = ReflectUtils.reflect(body);
            RequestBody body1 = reflect.field("delegate").get();
            if (body1 instanceof FormBody) {
                FormBody.Builder builder = new FormBody.Builder();
                int size = ((FormBody) body1).size();
                for (int i = 0; i < size; i++) {
                    String encodedName = ((FormBody) body1).encodedName(i);
                    String encodedValue = ((FormBody) body1).encodedValue(i);
                    builder.add(URLDecoder.decode(encodedName), URLDecoder.decode(encodedValue));
                }
                builder.add(Global.token, token);//添加token
                builder.add("dataProvider", "");
                FormBody newBody = builder.build();
                //重新给FormBody赋值
                reflect.field("delegate", newBody);
                //还是使用原来的body, 因为原来body里面有个进度Listener
                request = request.newBuilder().method(request.method(), body).build();
            }
        }


        //最后return
        return chain.proceed(newRequest);//继续进行
    }
}
