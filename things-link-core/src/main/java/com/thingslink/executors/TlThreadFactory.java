package com.thingslink.executors;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * thread factory for thingsLink
 * @author wang xiao
 * date 2023/1/2
 */
public final class TlThreadFactory implements ThreadFactory {


    private static final AtomicInteger POOL_SIZE = new AtomicInteger(1);

    private final String namePrefix;

    private final ThreadGroup threadGroup;

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private TlThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix + "-"+POOL_SIZE.getAndIncrement()+"-thread-";
        this.threadGroup = Thread.currentThread().getThreadGroup();
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(threadGroup,r,namePrefix+threadNumber.getAndIncrement(),0);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != Thread.NORM_PRIORITY){
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
