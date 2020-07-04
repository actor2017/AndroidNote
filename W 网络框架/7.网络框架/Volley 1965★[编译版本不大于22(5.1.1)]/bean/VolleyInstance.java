package cn.itcast.volley12;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Kevin.可能需要修改!!!!!!!!!!!!!!!!!!!!!!!!
 */

public class VolleyInstance {

    private static VolleyInstance sInstance;
    private final RequestQueue queue;
    private final ImageLoader loader;

    private VolleyInstance(Context ctx) {
        //初始化请求队列
        queue = Volley.newRequestQueue(ctx);

        //初始化图片加载器
        loader = new ImageLoader(queue, new VolleyCache());
    }

    public static VolleyInstance getInstance(Context ctx) {
        if (sInstance == null) {
            synchronized (VolleyInstance.class) {
                if (sInstance == null) {
                    sInstance = new VolleyInstance(ctx);
                }
            }
        }

        return sInstance;
    }

    public RequestQueue getQueue() {
        return queue;
    }

    public ImageLoader getImageLoader() {
        return loader;
    }
}
