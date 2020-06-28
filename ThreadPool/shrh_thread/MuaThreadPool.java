package com.ly.changyi.utils.thread;

import com.ly.changyi.view.pickers.util.LogUtils;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * Created by Administrator on 2018/4/11.
 *
 * 示例使用:
 * MuaThreadPool.getInstance().addTask2WorkQueue(new PriorityRunnable(0) {
 *     @Override
 *     public void run() {
 *     }
 * });
 */
public class MuaThreadPool<T> {
    //CPU核数
    public static int CORE_NUM = Runtime.getRuntime().availableProcessors();
    //核心线程数
    public static int CORE_THREAD_NUM = CORE_NUM + 1;
    //最大线程数
    public static int MAX_THREAD_NUM = CORE_NUM * 2 + 1;
    //单例线程
    public static MuaThreadPool muaThreadPool;
    //带有暂停的优先队列线程池
    public PausableThreadPoolExecutor pausableThreadPoolExecutor;

    public MuaThreadPool() {
        getPrioriThreadPool();
    }

    public static MuaThreadPool getInstance() {
        if (muaThreadPool == null) {
            muaThreadPool = new MuaThreadPool();
        }
        return muaThreadPool;
    }

    //获取可暂停的优先队列线程池
    public PausableThreadPoolExecutor getPrioriThreadPool() {
        if (pausableThreadPoolExecutor == null) {
            pausableThreadPoolExecutor = new PausableThreadPoolExecutor(CORE_THREAD_NUM, MAX_THREAD_NUM, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
            LogUtils.error("线程池新建");
        }
        return pausableThreadPoolExecutor;
    }


    public void addTask2WorkQueue(Runnable runnable) {
        pausableThreadPoolExecutor.execute(runnable);
    }

    public void getQu() {
        pausableThreadPoolExecutor.getCurrentQueue();
    }

    public interface CallBack {
        void callBack(String id, String msg);
    }
}
