package com.example.concurrent.lock.shared;

import com.example.concurrent.lock.shared.TwinsLock;

import java.util.concurrent.TimeUnit;

/**
 * @author: ls
 * @date: 2021/1/15 14:03
 **/
public class Test {

    public static void main(String[] args) {
        TwinsLock lock = new TwinsLock();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(()->{
                lock.lock();
                try {
                    while (true){
                        System.out.println("线程"+ Thread.currentThread().getName() + "正在执行");
                        TimeUnit.SECONDS.sleep(5);
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("线程" + Thread.currentThread().getName() + "释放");
                    lock.unlock();
                }

            }, String.valueOf(i));
            //由于均设置为daemon时导致没有非daemon线程运行，因此程序退出
//            thread.setDaemon(true);
            thread.start();
        }



    }
}
