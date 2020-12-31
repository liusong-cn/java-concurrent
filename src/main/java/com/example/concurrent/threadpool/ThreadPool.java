package com.example.concurrent.threadpool;

/**
 * @author: Administrator
 * @date: 2020/12/30 21:32
 *
 * 简单线程池接口
 * 线程不安全切设计有弊病
 */
public interface ThreadPool {

    void addWorker();

    void execute(Runnable job);

    int count();

    void shutdown();
}
