package cn.itcast.googleplay12.manager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhengping on 2017/4/10,9:21.
 * 线程池的管理类  单例
 * 用法:
 * MyThreadPoolManager.getInstance().execute(downloadTask);
 * MyThreadPoolManager.getInstance().cancel(downloadTask);
 *
 * class MyRunnable implements Runnable {
 */

public class MyThreadPoolManager {

    private static MyThreadPoolManager instance;
    //只需要往这个池子里面丢一个又一个的任务,对于线程池来说，线程的创建其实是由线程池自身来完成的
    private ThreadPoolExecutor threadPoolExecutor;

    private MyThreadPoolManager() {
        throw new RuntimeException("MyThreadPoolManager can't be new!");
    }

    public synchronized static MyThreadPoolManager getInstance() {
        if (instance == null) {
            instance = new MyThreadPoolManager();
        }
        return instance;
    }

    /**
     * 用线程池来管理我们这个应用程序中的线程数量（并不是这个样子的）
     * 使用场景：用线程池管理某一个模块中线程的数量
     * @param r
     */
    public void execute(Runnable r) {
        if (r == null) return;

        /**
         * int corePoolSize,核心线程数,线程池中拥有线程的数量,除非threadPoolExecutor.allowCoreThreadTimeOut(true);否则它闲着也不会死
         * int maximumPoolSize,最大线程数,当workQueue都放不下时,启动新线程执行任务,最多再启动max-core个
         * long keepAliveTime,超时时长,作用于非核心线程(allowCoreThreadTimeOut被设置为true时也会同时作用于核心线程),闲置超时便被回收
         * TimeUnit unit,空闲时间的单位,有TimeUnit.MILLISECONDS（ms）、TimeUnit. SECONDS（s）等
         * BlockingQueue<Runnable> workQueue,等待区域,缓冲任务队列
         * ThreadFactory threadFactory,线程工厂接口,只有一个new Thread(Runnable r)方法,可为线程池创建新线程
         * RejectedExecutionHandler handler 异常处理机制:
         *                                      ThreadPoolExecutor.AbortPolicy();废弃新来的任务
         *                                      ThreadPoolExecutor.CallerRunsPolicy();用调用者的线程执行任务
         *                                      ThreadPoolExecutor.DiscardOldestPolicy();废弃(等待时间)最长的任务
         *                                      ThreadPoolExecutor.DiscardPolicy();抛弃当前任务
         *
         * （1）当currentSize<corePoolSize时，没什么好说的，直接启动一个核心线程并执行任务。
         * （2）当currentSize>=corePoolSize、并且workQueue未满时，添加进来的任务会被安排到workQueue中等待执行。
         * （3）当workQueue已满，但是currentSize<maximumPoolSize时，会立即开启一个非核心线程来执行任务。
         * （4）当currentSize>=maximumPoolSize + workQueue，调用handler默认抛出RejectExecutionExpection异常。
         */
        if (threadPoolExecutor == null) {
            int cpuCounts = Runtime.getRuntime().availableProcessors();//CPU数量
            int corePoolSize = cpuCounts*2 + 1;
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,//3
                    corePoolSize*2, 0,//5
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(666),//20
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());
        }
        threadPoolExecutor.execute(r);//将任务丢到了线程池中
    }

    public void cancel(Runnable r) {
        if(threadPoolExecutor != null && r != null) {
            threadPoolExecutor.getQueue().remove(r);//将等待区域中的任务移除掉
        }
    }
}
