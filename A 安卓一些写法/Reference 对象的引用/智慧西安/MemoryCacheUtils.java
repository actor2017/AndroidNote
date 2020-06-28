package cn.itcast.zhxa12.utils.image;

import android.graphics.Bitmap;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by zhengping on 2017/2/28,10:28.
 * 内存缓存
 * 注意:但是Android用的DVM,在JVM虚拟机的基础上进行了修改,系统2.3(Lv9)后更倾向于回收软/弱引用,更不可靠,
 * 所以使用LRUCache
 */

public class MemoryCacheUtils {

    //private HashMap<String, Bitmap> mSavedBitmap = new HashMap<>();//强引用,Bitmap多了会OOM
    private HashMap<String, SoftReference<Bitmap>> mSavedBitmapReference = new HashMap<>();

    public void saveBitmap2Memory(Bitmap bitmap, String url) {
        //mSavedBitmap.put(url, bitmap);

        //Reference<T>:强引用
        //SoftReference<T>:软引用：在系统内存不足的情况之下，垃圾回收器会考虑回收软引用的对象  jvm    dvm
        //WeakReference<T>：弱引用：在系统内存不足的情况之下，垃圾回收器更会考虑回收弱引用的对象
        //PhantomReference<T>:虚引用：在系统内存不足的情况之下，垃圾回收器最会考虑回收虚引用的对象
        SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
        //用软引用将bitmap这个对象进行包装
        mSavedBitmapReference.put(url, softReference);//把软引用所包装的对象bitmap存在HashMap中
    }

    public Bitmap getBitmapFromMemory(String url) {
        //Bitmap bitmap = mSavedBitmap.get(url);
        SoftReference<Bitmap> softReference = mSavedBitmapReference.get(url);
        return softReference.get();
    }
}
