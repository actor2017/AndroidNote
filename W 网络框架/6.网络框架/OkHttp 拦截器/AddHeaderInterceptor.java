package com.ly.bridgeemergency.utils.MyOkhttpUtils;

import com.ly.bridgeemergency.utils.Global;
import com.ly.bridgeemergency.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description: 网络拦截器, 添加Header验证
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/3/29 on 16:37
 */
public class AddHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String authorization = SPUtils.getString(Global.Authorization);
        if (authorization == null) authorization = "";
        request = request.newBuilder()
                .addHeader(Global.Authorization, authorization)//添加验证到请求头, key & value不能为null
                .build();
        return chain.proceed(request);//继续进行
    }
}
