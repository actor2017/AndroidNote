package com.ly.hihifriend.network;

import android.content.Context;
import android.content.Intent;

import com.ly.hihifriend.application.MyApplication;
import com.ly.hihifriend.utils.Global;
import com.ly.hihifriend.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description: 网络拦截器
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/3/29 on 16:37
 */
public class MyNetWorkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String authorization = SPUtils.getString(Global.authorization);
        if (authorization == null) authorization = "";
        request = request.newBuilder()
                .addHeader(Global.authorization, authorization)//添加验证到请求头, key & value不能为null
                .build();

        Response response = chain.proceed(request);//继续进行
        Response clone = response.newBuilder().build();
        ResponseBody body = clone.body();
        if (body != null) {
            MediaType mediaType = body.contentType();
            if (mediaType != null) {
                if (isText(mediaType)) {
                    String content = body.string();
                    body = ResponseBody.create(mediaType, content);
                    //直接操作会:Content-Length and stream length disagree, 所以把内容从新塞进去...
                    response = response.newBuilder().body(body).build();
                    if (content != null) {//1001认证失败.1002认证过期
                        //{"code":1001,"msg":"认证失败"}
//                        try {
//                            //如果返回网页类型, 会解析报错
//                            BaseInfo baseInfo = JSONObject.parseObject(content, BaseInfo.class);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        if (content.startsWith("{\"code\":1001,") || content.startsWith("{\"code\":1002,")){//这样判断速度非常快
                            SPUtils.putString(Global.authorization, null);
                            Context context = MyApplication.instance;
							//Intent intent = new Intent(activity, LoginActivity.class);//一样的
                            Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                        }
                    }
                }
            }
        }
        return response;
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
}
