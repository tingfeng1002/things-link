package com.thingslink.executors;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ThingsLink ForkJoinWorkerThreadFactory
 * @author wang xiao
 * date 2023/1/2
 */
public class ThingsLinkForkJoinWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {

    private final String namePrefix;

    private final AtomicLong threadNumber = new AtomicLong(1);

    public ThingsLinkForkJoinWorkerThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        ForkJoinWorkerThread newThread = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
        newThread.setContextClassLoader(this.getClass().getClassLoader());
        newThread.setName(namePrefix+"-"+newThread.getPoolIndex()+"-"+threadNumber.getAndIncrement());
        return newThread;
    }
}
