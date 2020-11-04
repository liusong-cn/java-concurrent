package com.example.concurrent.demo;

import java.util.stream.IntStream;

/**
 * @author:ls
 * @date: 2020/11/3 19:19
 * 线程优先级测试
 * 通过给每个线程设置优先级，发现并不能最终决定线程执行顺序，因为涉及到cpu调度策略，抢占式调度策略
 **/
public class ThreadPriorityTest {

    public static void main(String[] args) {
        IntStream.range(1,11).forEach((i)->{
            Thread thread = new Thread(() ->{
                System.out.println(String.format("当前线程名称：%s，当前线程优先级：%d",Thread.currentThread().getName(),
                        Thread.currentThread().getPriority()));
            });
            thread.setPriority(i);
            //线程一定要启动
            thread.start();
        });
        //通过打印结果可以看出线程的优先级和打印顺序并不能完全匹配，甚至完全不能匹配
    }
}
