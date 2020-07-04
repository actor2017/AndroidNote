package com.ly.bridgeemergency.utils.MyOkhttpUtils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.actor.myandroidframework.utils.MyOkhttpUtils.MyOkHttpUtils;
import com.actor.myandroidframework.utils.SPUtils;
import com.alibaba.fastjson.JSONObject;
import com.ly.bridgeemergency.info.LoginInfo;
import com.ly.bridgeemergency.utils.Global;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description: 状态=401: 访问的服务需要身份认证，请刷新TOKEN
 * 状态=402: refresh_token不存在或已过期,请重新登录获取
 * 自动刷新token, 可部分参考https://www.jianshu.com/p/8dd4b0a36a75
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/2/15 on 17:48
 */
public class My401$402Error$RefreshTokenInterceptor implements Interceptor {

    private Context context;
    private Map<String, Object> params = new LinkedHashMap<>(20);

    public My401$402Error$RefreshTokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        Request requestClone = request.newBuilder().build();//复制请求
        Response response = chain.proceed(request);
        Response responseClone = response.newBuilder().build();//复制响应
        ResponseBody bodyClone = responseClone.body();//复制响应体

        if (bodyClone == null) return response;

        MediaType mediaType = bodyClone.contentType();
        if (mediaType == null) return response;

        if (!isText(mediaType)) return response;

        String resp = bodyClone.string();
        if (resp == null) return response;
        try {
            bodyClone = ResponseBody.create(mediaType, resp);
            //直接操作会:Content-Length and stream length disagree, 所以把内容从新塞进去...
            response = response.newBuilder().body(bodyClone).build();
            //{"code":402,"message":"refresh_token不存在或已过期,请重新登录获取","data":""}
            if (resp.startsWith("{\"code\":402")) {//被别人挤掉
                go2Login();
                return response;
            }
            //{"code":401,"message":"访问的服务需要身份认证，请刷新TOKEN","data":""}
            //BaseInfo baseInfo = JSONObject.parseObject(resp, BaseInfo.class);
            if (!resp.startsWith("{\"code\":401")) return response;

            Response response1 = refreshToken(SPUtils.getString(Global.refreshToken), request.tag());
            if (response1 == null) {
                go2Login();
                return response;
            }

            if (response1.code() == 200) {
                ResponseBody body = response1.body();
                if (body != null) {
                    String json = body.string();
                    if (json != null) {
                        try {
                            LoginInfo loginInfo = JSONObject.parseObject(json, LoginInfo.class);
                            if (loginInfo != null) {
                                LoginInfo.DataBean data = loginInfo.data;
                                if (data != null) {
                                    String token = "Bearer ".concat(data.access_token);
                                    SPUtils.putString(Global.refreshToken, data.refresh_token);
                                    SPUtils.putString(Global.Authorization, token);
                                    request = request.newBuilder()
                                            .header(Global.Authorization,
                                                    SPUtils.getString(Global.Authorization))
                                            .build();
                                    Log.e("My401ErrorInterceptor", "intercept: 重新请求");
                                    return chain.proceed(request);
                                } else go2Login();
                            } else go2Login();
                        } catch (Exception e) {
                            e.printStackTrace();
                            go2Login();
                        }
                    } else go2Login();
                } else go2Login();
            } else go2Login();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    //刷新token
    private Response refreshToken(String refreshToken, Object tag) {
        params.clear();
        params.put(Global.head, "Basic QW5kcm9pZDpicmlkZ2U=");
        params.put(Global.refreshToken, refreshToken);
        return MyOkHttpUtils.postSync(Global.HOST.concat(Global.LOGIN_REFRESHTOKEN), params, false, tag);
    }

    private void go2Login() {
        SPUtils.putString(Global.Authorization, null);
        //Intent intent = new Intent(activity, LoginActivity.class);//一样的
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
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
