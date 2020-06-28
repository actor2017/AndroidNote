package com.ly.changyi.utils.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可暂停的线程池
 * Created by Administrator on 2018/4/11.
 */

public class PausableThreadPoolExecutor extends ThreadPoolExecutor {
    //CPU核数
    public static int CORE_NUM = Runtime.getRuntime().availableProcessors();
    //核心线程数
    public static int CORE_THREAD_NUM = CORE_NUM + 1;
    //最大线程数
    public static int MAX_THREAD_NUM = CORE_NUM * 2 + 1;
    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unPaused = pauseLock.newCondition();

    public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(CORE_THREAD_NUM, MAX_THREAD_NUM, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(command);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) unPaused.await();
        } catch (InterruptedException ie) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    public BlockingQueue<Runnable> getCurrentQueue() {
        BlockingQueue<Runnable> a = this.getQueue();
        if (!a.isEmpty()) {
        }
        return a;
    }

    //暂停
    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    //重新开始
    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unPaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }
}
