package com.znlccy.concurrency.atomic;

import com.znlccy.concurrency.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author: Administrator
 * @Datetime: 2018/11/20-15:51
 * @Version: v1.0.0
 * @Comment: 并发测试
 */

@Slf4j
@ThreadSafe
public class AtomicExampleTwo {

    /* 请求总数 */
    public static int clientTotal = 5000;

    /* 同时并发执行的线程总数 */
    public static int threadTotal = 200;

    /* 计数 */
    public static LongAdder count = new LongAdder();

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int index = 0; index < clientTotal; index ++) {
            exec.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        exec.shutdown();
        log.info("count:{}", count);
    }

    private static void add() {
        count.increment();
        /*count.incrementAndGet();*/
        /*count.getAndIncrement();*/
    }
}
