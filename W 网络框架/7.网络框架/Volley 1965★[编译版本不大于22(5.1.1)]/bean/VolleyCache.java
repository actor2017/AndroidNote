package cn.itcast.volley12;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Kevin.
 * 自定义图片缓存
 */

public class VolleyCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> mCache;

    public VolleyCache() {						//貌似这儿只写了内存缓存??
        mCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 8)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();//返回一个图片的大小
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }
}
