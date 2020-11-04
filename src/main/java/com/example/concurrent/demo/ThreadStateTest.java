package com.example.concurrent.demo;

/**
 * @author:ls
 * @date: 2020/11/4 15:01
 * 测试java中线程的状态
 * 线程的状态在Thread.State这个枚举中，包括new runnable blocked waiting timed_waiting terminated
 **/
public class ThreadStateTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadStateTest t = new ThreadStateTest();
        Thread a = new Thread(()->{
            t.test();
        },"a");
        Thread b = new Thread(()->{
            t.test();
        },"b");
        a.start();
        //当不使用sleep时，由于a和b可以抢占cpu执行时间，因此在打印时a和b的状态不能确定
        //当使用sleep时，同样不能确定，因为start执行进入runnable也需要时间，有可能在sleep 1000ms期间
        //a已经执行完毕，也有可能a正在执行，因此b的状态也不能确定
//        Thread.sleep(1000);
        //当使用join时，由于join使得当前线程main等待a执行完毕，因此a的状态是固定的terminated，而b可能处于runnable也
        //可能处于timed_waited
        a.join();
        b.start();
        System.out.println(String.format("a线程状态:%s",a.getState()));
        System.out.println(String.format("b线程状态：%s",b.getState()));

    }

    public void test(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
