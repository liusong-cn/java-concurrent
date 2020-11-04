package com.example.concurrent.demo;

import java.util.Arrays;

/**
 * @author:ls
 * @date: 2020/11/3 19:47
 * 线程组复制
 **/
public class ThreadGroupCpTest {

    public static void main(String[] args) {
        //获取当前线程组
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        Thread[] target = new Thread[group.activeCount()];
        //该方法的副作用为将线程组复制到target中
        group.enumerate(target);
        //通过打印信息可知有两个线程 main monitor
        Arrays.asList(target).forEach((i)->{
            System.out.println(i.getName());
        });
    }
}
