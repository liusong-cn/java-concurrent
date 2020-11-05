package com.example.concurrent.demo;

/**
 * @author:ls
 * @date: 2020/11/4 17:36
 * 通过volatile实现一种信号量，进而在多个线程之间进行通信
 * 本例描述了使用volatile在两个线程之间交替打印一个递增的数字
 **/
public class ThreadWithVolatileTest {

    private static volatile int signal = 0;

    public static void main(String[] args) {
        //新建一个对象用于对象锁
        Object o = new Object();
        new Thread(() ->{
            while (signal < 5){
                if(signal%2 == 0){
                    System.out.println(String.format("0当前的数字：%d",signal));
                    //由于自增不是原子操作，因此需要利用synchronized加锁保证同步
                    //由于使用了volatile,保证了signal修改后同步到主存中，因此其他线程可以看见更新后的signal
                    synchronized (o){
                        signal++;
                    }
                }
            }
        },"thread1").start();

        new Thread(()->{
            while (signal < 5){
                if(signal%2 !=0){
                    System.out.println(String.format("1当前的数字：%d",signal));
                    synchronized (o){
                        signal = signal + 1;
                    }
                }
            }
        },"thread2").start();
    }
}
