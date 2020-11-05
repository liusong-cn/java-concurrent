package com.example.concurrent.demo;

/**
 * @author:ls
 * @date: 2020/11/4 17:13
 * java线程通知机制
 * 1.基于锁的方式 常见方式 synchronized
 * 2.基于obj的wait和notify机制(等待/通知机制)
 * 本例描述第二种方式，通过等待/通知机制实现线程之间交替打印
 **/
public class ThreadToggleTest {

    private static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ThreadA()).start();
        //通过延时来保证第一个线程先拿到锁
        Thread.sleep(10);
        new Thread(new ThreadB()).start();
    }

    static class ThreadA implements Runnable{

        @Override
        public void run() {
            synchronized (object){
                for (int i = 0; i < 5; i++) {
                    System.out.println(String.format("当前线程名为：%s，打印数字：%s",Thread.currentThread().getName(),
                            i));
                    //打印之后尝试唤醒其他线程
                    object.notify();
                    try {
                        //其他线程若得到锁，需当前线程执行完毕或者当前线程主动让出锁，让其他线程得以有机会执行
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                object.notify();
            }
        }
    }
    static class ThreadB implements Runnable{

        @Override
        public void run() {
            synchronized (object){
                for (int i = 0; i < 5; i++) {
                    System.out.println(String.format("当前线程名为：%s，打印数字：%s",Thread.currentThread().getName(),
                            i));
                    //打印之后尝试唤醒其他线程
                    object.notify();
                    try {
                        //其他线程若得到锁，需当前线程执行完毕或者当前线程主动让出锁，让其他线程得以有机会执行
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                object.notify();
            }
        }
    }

}

