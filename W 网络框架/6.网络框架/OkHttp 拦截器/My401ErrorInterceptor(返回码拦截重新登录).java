package com.ly.changyi.utils.MyOkhttpUtils;

import android.content.Context;
import android.content.Intent;

import com.ly.changyi.constant.Global;
import com.ly.changyi.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description: 当状态为401时，表示帐号在其它地方登录
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/2/15 on 17:48
 */
public class My401ErrorInterceptor implements Interceptor {

    private Context context;

    public My401ErrorInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Response clone = response.newBuilder().build();
        ResponseBody body = clone.body();
        if (body != null) {
            MediaType mediaType = body.contentType();
            if (mediaType != null) {
                if (isText(mediaType)) {
                    try {
                        String resp = body.string();
                        body = ResponseBody.create(mediaType, resp);
                        //直接操作会:Content-Length and stream length disagree, 所以把内容从新塞进去...
                        response = response.newBuilder().body(body).build();
                        if (resp != null) {
                            //{"state":401,"message":"认证失败,该账号可能已在其他地方登录","data":""}
//                            BaseInfo baseInfo = JSONObject.parseObject(resp, BaseInfo.class);
                            if (resp.startsWith("{\"state\":401")){//这样判断速度非常快
                                SPUtils.putString(Global.TOKEN, null);
						        //Intent intent = new Intent(activity, LoginActivity.class);//一样的
                                Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                            }
                        }
//                        return response;
                    } catch (Exception e) {
                        e.printStackTrace();
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
