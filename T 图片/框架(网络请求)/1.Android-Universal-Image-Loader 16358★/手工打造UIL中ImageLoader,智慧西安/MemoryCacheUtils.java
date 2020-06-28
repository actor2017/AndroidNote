package cn.itcast.zhxa12.utils.image;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import cn.itcast.zhxa12.pager.impl.menu.TabDetailPager;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by zhengping on 2017/2/28,10:28.
 * 内存缓存
 * 注意:但是Android用的DVM,在JVM虚拟机的基础上进行了修改,系统2.3(Lv9)后更倾向于回收软/弱引用,更不可靠,
 * 所以使用LRUCache
 */

public class MemoryCacheUtils {

    //private HashMap<String, Bitmap> mSavedBitmap = new HashMap<>();//强引用,Bitmap多了会OOM
    //private HashMap<String, SoftReference<Bitmap>> mSavedBitmapReference = new HashMap<>();
    private LruCache<String, Bitmap> lruCache;

    public MemoryCacheUtils() {
        //使用LRUCache：最近最少使用的算法  A B C D A B
        //LRUCache其实就是一个容器，这个容器就是装对象使用
        //LRUCache能够保证内部的图片所占用的内存量不会超出最大值
        long maxMemory = Runtime.getRuntime().maxMemory();//应用程序最大的内存
        //LRUCache的使用方法
        //1、创建这个对象，传入最大值,保证内部图片所占内存量不会超过maxMemory/8
        //2、重写一个方法，sizeOf  定义规则：放入对象所占用内存的计算规则
        //3、像是用Hashmap一样使用Lrucache即可
        lruCache = new LruCache<String,Bitmap>((int) (maxMemory/8)){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();//获取bitmap所占用的内存数量
            }
        };
    }



    public void saveBitmap2Memory(Bitmap bitmap, String url) {
        //mSavedBitmap.put(url, bitmap);

        //Reference<T>
        //SoftReference<T>:软引用：在系统内存不足的情况之下，垃圾回收器会考虑回收软引用的对象  jvm    dvm
        //WeakReference<T>：弱引用：在系统内存不足的情况之下，垃圾回收器更会考虑回收弱引用的对象
        //PhantomReference<T>:虚引用：在系统内存不足的情况之下，垃圾回收器最会考虑回收虚引用的对象
       // SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
        //用软引用将bitmap这个对象进行包装
       // mSavedBitmapReference.put(url, softReference);//把软引用所包装的对象bitmap存在HashMap中



        lruCache.put(url, bitmap);

    }

    public Bitmap getBitmapFromMemory(String url) {
        //Bitmap bitmap = mSavedBitmap.get(url);
        /*SoftReference<Bitmap> softReference = mSavedBitmapReference.get(url);
        if(softReference != null) {
            Bitmap bitmap = softReference.get();
            return bitmap;
        }*/
        Bitmap bitmap = lruCache.get(url);
        return bitmap;
    }
}
