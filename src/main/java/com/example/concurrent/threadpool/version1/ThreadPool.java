package com.example.concurrent.threadpool.version1;

/**
 * @author: ls
 * @date: 2020/12/31 15:32
 **/
public interface ThreadPool {

    void addWorker(int num);

    void execute(Runnable job);

    void shutdown();

    int getTaskCount();


}
