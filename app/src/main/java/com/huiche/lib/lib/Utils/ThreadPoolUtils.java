package com.huiche.lib.lib.Utils;


import com.huiche.lib.lib.ThreadPool.ThreadPoolFactory;

/**
 * 线程池
 */
public final class ThreadPoolUtils {

    private ThreadPoolUtils() {
    }

    /**
     * 在非UI线程中执行
     */
    public static void runTaskInThread(Runnable task) {
        //线程池
        ThreadPoolFactory.getCommonThreadPool().execute(task);
    }
}
