package com.example.concurrent.threadpool.version1;

/**
 * @author: ls
 * @date: 2020/12/31 16:14
 **/
public class ThreadPoolTest {

    public static void main(String[] args) {

        ThreadPool pool = new DefaultThreadPool();

        Runnable job = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("第一个任务" + i);
                }
            }
        };

        pool.execute(job);

        try {
            Thread.sleep(2000);
            pool.execute(() -> {
                for (int i = 0; i < 10; i++) {
                    System.out.println("第三个任务" + i);
                }
            });
            Thread.sleep(3000);
            pool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
