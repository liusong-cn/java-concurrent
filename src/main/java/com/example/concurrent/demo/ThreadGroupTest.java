package com.example.concurrent.demo;

/**
 * @author:ls
 * @date: 2020/11/3 16:25
 * 测试线程组管理的线程
 * 每个线程都有其线程组，所有线程组为一棵树
 **/
public class ThreadGroupTest {

    public static void main(String[] args) {
        //创建线程时如果没指定所在线程组那么会自动继承其父线程所在线程组
        Thread thread = new Thread(()->{
            System.out.println(String.format("当前线程名称：%s",Thread.currentThread().getName()));
            System.out.println(String.format("当前线程组的线程名称：%s",Thread.currentThread().getThreadGroup().getName()));
        });
        //start方法不能忘
        thread.start();
        System.out.println(String.format("主线程的线程名称：%s,其进程组：%s",Thread.currentThread().getName(),
                Thread.currentThread().getThreadGroup().getName()));
    }
}
