package com.example.concurrent.threadpool;

/**
 * @author: Administrator
 * @date: 2020/12/30 21:47
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        MyThreadPool threadPool = new MyThreadPool();
        threadPool.addWorker();
        System.out.println(threadPool.count());
        threadPool.execute(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("job执行:" + i);
            }
        });
        threadPool.start();
    }
}
