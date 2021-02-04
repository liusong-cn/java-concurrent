package com.example.concurrent.demo.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * @author: ls
 * @date: 2021/2/4 15:40
 **/
public class CountDownLatchDemo {


    public static void main(String[] args) throws InterruptedException {

        CountDownLatch count = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                    count.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        System.out.println("主线程等待执行");
        //主线程等待计数器归零，主线程等待子线程也可以通过join，但是join只能一个，否则只能嵌套join
        count.await();
        System.out.println("主线程已执行");
    }
}
