package com.example.concurrent.demo;

import java.util.Date;

/**
 * @author:ls
 * @date: 2020/11/3 19:34
 * 将线程设为demon线程，并观察其生命周期
 **/
public class DaemonThreadTest {

    public static void main(String[] args) throws InterruptedException {
        Thread daemon = new Thread(()->{
            //线程中保证在执行时一直运行，由于没有设置退出条件，那么就可以验证当主线程退出后daemon自动结束
            while(true){
                System.out.println(String.format("当前时间为：%s",new Date().getSeconds()));
            }
        });
        //设置为守护线程后当所有主线程都退出后才会结束
        daemon.setDaemon(true);
        daemon.start();
        //留出1s时间让daemon线程一直运行
        Thread.sleep(1000);
        System.out.println("现在结束了");
    }
}
