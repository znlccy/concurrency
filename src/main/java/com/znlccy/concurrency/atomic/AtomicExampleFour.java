package com.znlccy.concurrency.atomic;

import com.znlccy.concurrency.annotation.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author: Administrator
 * @Datetime: 2018/11/20-15:51
 * @Version: v1.0.0
 * @Comment: 并发测试
 */

@Slf4j
@ThreadSafe
public class AtomicExampleFour {

    private static AtomicIntegerFieldUpdater<AtomicExampleFour> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicExampleFour.class, "count");

    @Getter
    public volatile int count = 100;

    private static AtomicExampleFour exampleFour = new AtomicExampleFour();

    public static void main(String[] args) {
        if (updater.compareAndSet(exampleFour, 100, 120)) {
            log.info("update success 1: {}", exampleFour.getCount());
        }

        if (updater.compareAndSet(exampleFour, 100, 120)) {
            log.info("update success 2: {}", exampleFour.getCount());
        } else {
            log.info("update failed: {}", exampleFour.getCount());
        }
    }
}
