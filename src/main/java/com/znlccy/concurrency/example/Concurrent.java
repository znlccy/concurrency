package com.znlccy.concurrency.example;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: Administrator
 * @Datetime: 2018/11/20-15:51
 * @Version: v1.0.0
 * @Comment: 并发测试
 */

@Slf4j
public class Concurrent {

    /* 请求总数 */
    public static int clientTotal = 5000;

    /* 同时并发执行的线程总数 */
    public static int threadTotal = 50;

    /* 计数 */
    public static int count = 0;

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
        System.out.println("count:{}" + count);
    }

    private static void add() {
        count++;
    }
}
