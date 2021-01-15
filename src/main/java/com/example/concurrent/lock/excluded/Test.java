package com.example.concurrent.lock.excluded;

import java.util.concurrent.TimeUnit;

/**
 * @author: ls
 * @date: 2021/1/15 14:46
 **/
public class Test {

    public static void main(String[] args) {
        MainLock lock = new MainLock();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                lock.lock();
                try {
                    while (true){
                        System.out.println(String.format("线程%s开始工作", finalI));
                        TimeUnit.SECONDS.sleep(3);
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println("线程"+ Thread.currentThread().getName() + "释放锁");
                }
            }, String.valueOf(i));
            thread.start();
        }
    }
}
