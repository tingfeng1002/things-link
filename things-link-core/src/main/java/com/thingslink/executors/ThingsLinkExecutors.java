package com.thingslink.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

/**
 * ThingsLinkExecutors
 * @author wang xiao
 * date 2023/1/2
 */
public class ThingsLinkExecutors {

    public static ExecutorService newWorkStealingPool(int parallelism, String namePrefix) {
        return new ForkJoinPool(parallelism,
                new ThingsLinkForkJoinWorkerThreadFactory(namePrefix),
                null, true);
    }

}
