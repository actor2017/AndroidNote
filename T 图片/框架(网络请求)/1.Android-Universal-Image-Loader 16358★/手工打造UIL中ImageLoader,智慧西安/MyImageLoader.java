package cn.itcast.zhxa12.utils.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import static android.R.attr.bitmap;

/**
 * Created by zhengping on 2017/2/28,8:54.
 */

public class MyImageLoader {

    private  NetCacheUtils netCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;

    private MyImageLoader() {
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = new LocalCacheUtils();
        netCacheUtils = new NetCacheUtils(localCacheUtils,memoryCacheUtils);
    }
    private static MyImageLoader instance;
    public synchronized static MyImageLoader getInstance() {
        if(instance == null) {
            instance = new MyImageLoader();
        }
        return instance;
    }

    public void displayImage(String url,ImageView imageView){
        //1、先加载内存缓存
        Bitmap bitmap = memoryCacheUtils.getBitmapFromMemory(url);
        if(bitmap != null) {
            //内存有缓存
            System.out.println("加载内存缓存");
            imageView.setImageBitmap(bitmap);
            return;
        }
        //2、其次加载本地缓存
        bitmap = localCacheUtils.getBitmapFromLocal(url);
        if(bitmap != null) {
            //本地有缓存
            System.out.println("加载本地缓存");
            imageView.setImageBitmap(bitmap);
            //在设置给ImageView  bitmap之后，顺便把bitmap对象存在内存中
            memoryCacheUtils.saveBitmap2Memory(bitmap,url);
            return;
        }
        //3、最后加载网络缓存
        System.out.println("加载网络缓存");
        netCacheUtils.getImageFromServer(url,imageView);
    }

}
