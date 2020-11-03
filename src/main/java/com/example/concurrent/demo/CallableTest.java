package com.example.concurrent.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author:ls
 * @date: 2020/11/3 15:52
 * 测试jcallable
 * 这是jdk异步编程模型，相比runnable和thread，可以获取执行结果
 **/
public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //executorservice为jdk提供的线程池工具
        //可以创建cache fixed single working类的线程池
        ExecutorService service = Executors.newCachedThreadPool();
        Task task = new Task();
        //future接口用来表示线程执行的结果
        Future<Integer> r = service.submit(task);
        //实际过程中，get会阻塞线程，因此常调用带有超时设置的get
//        System.out.println(r.get());
        System.out.println(r.get(3000, TimeUnit.MILLISECONDS));
    }


}
