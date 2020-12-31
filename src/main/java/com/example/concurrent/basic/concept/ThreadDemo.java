package com.example.concurrent.basic.concept;

/**
 * @author: ls
 * @date: 2020/11/28 10:13
 * 测试多线程和单线程串行执行速度问题
 **/
public class ThreadDemo {

    private final int count = 10000;

    //串行执行
    public void serial(){
        long start = System.currentTimeMillis();
        int a = 0;
        for (int i = 0; i < count; i++) {
            a+= 5;
        }
        int b = 0;
        for (int i = count; i > 0; i--) {
            b--;
            //本意是测串行和并发执行的总时长，但由于串行执行过程简单，不知jvm和缓存优化等如何，导致
            //简单累加执行不需要执行时间，故此加上打印增加时间
            System.out.println(b);
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("总共耗时：%s,a为：%s，b为：%s",end-start,a,b));

    }

    //并发执行
    public void concurrent() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t = new Thread(()->{
            int a = 0;
            for (int i = 0; i < count; i++) {
                a += 5;
            }
        });
        t.start();
        int b = 0;
        for (int i = count; i > 0; i--) {
            b--;
        };
        //等待t线程完成再执行
        t.join();
        long end = System.currentTimeMillis();
        System.out.println(String.format("并发总共耗时：%s,b为：%s",end-start,b));
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadDemo d = new ThreadDemo();
        d.concurrent();
        d.serial();
    }
}
