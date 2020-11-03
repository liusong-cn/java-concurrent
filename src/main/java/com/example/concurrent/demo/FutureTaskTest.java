package com.example.concurrent.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author:ls
 * @date: 2020/11/3 16:13
 * 利用futuretask作为任务执行类
 * futuretask其父接口继承了runnable和future
 * 因此可以通过future的get方法得到返回值
 **/
public class FutureTaskTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        FutureTask<Integer> task = new FutureTask<>(new Task());
        ExecutorService executorService = Executors.newCachedThreadPool();
        //此处submit的参数类型是runnable而非callable接口
        executorService.submit(task);
        //task自身带有get方法获取返回值
        Integer i = task.get(3, TimeUnit.SECONDS);
        System.out.println(i);
        //高并发情况下相比callable，futuretask能保证只执行一次，需后续研究
    }
}
