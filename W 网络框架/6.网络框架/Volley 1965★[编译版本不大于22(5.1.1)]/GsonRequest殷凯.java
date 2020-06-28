package cn.itcast.volley12;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by Kevin.
 * <p>
 * 自定义Gson请求
 */

public class GsonRequest<T> extends Request<T> {

    //请求成功的回调
    private final Response.Listener<T> mListener;

    //要解析的类型
    private Class<T> clazz;

    public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        this.clazz = clazz;
    }

    //解析网络请求结果
    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            //根据从响应头中解析出的编码来生成字符串, 默认"ISO-8859-1"
            parsed = new String(response.data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }

        //使用gson解析json
        Gson gson = new Gson();
        T t = gson.fromJson(parsed, clazz);

        //将结果回传
        return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
    }

    //分发请求的结果
    //通知前端调用者请求结果
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}
