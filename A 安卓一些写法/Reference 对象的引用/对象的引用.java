图片加载的缓存策略
三级缓存:内存缓存,本地缓存,网络缓存

内存缓存最难处理:加载更快,内存大小是严格控制住的


对象的应用:强引用,软引用.弱引用,虚引用
强引用:Reference<T>可以让程序员通过代码的方式决定某些对象的生命周期,JVM宁愿抛出OutOfMemory错误也不会回收这种对象
软引用:SoftReference<T>内存不足时,垃圾回收器会考虑回收
弱引用:WeakReference<T>内存不足时,垃圾回收器'更'会考虑回收
虚引用:PhantomReference<T>内存不足时,垃圾回收器'最'会考虑回收

但是Android用的DVM,在JVM虚拟机的基础上进行了修改,系统2.3(Lv9)后更倾向于回收软/弱引用,更不可靠,
所以使用LRUCache


LRUCache:最近最少使用的缓存容器(存储Bitmap对象)
保证内存中的Bitmap所占用的内存量在一定的控制范围之内
示例Bitmap中的BitmapUtils,要写成单例

