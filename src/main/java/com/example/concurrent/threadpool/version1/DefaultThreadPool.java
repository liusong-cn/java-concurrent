package com.example.concurrent.threadpool.version1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: ls
 * @date: 2020/12/31 15:35
 * 简单线程池接口实现
 **/
public class DefaultThreadPool implements ThreadPool {

    private List<Worker> workers = new ArrayList<>();

    private LinkedList<Runnable> jobs = new LinkedList<>();

    //默认worker数量
    private int defaultSize = 2;

    private int taskCount;

    private AtomicInteger threadNum = new AtomicInteger();

    public DefaultThreadPool() {
        for (int i = 0; i < defaultSize; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            new Thread(worker, "thread-" + threadNum.getAndIncrement()).start();
        }
    }

    @Override
    public void addWorker(int num) {
        synchronized (workers){
            for (int i = 0; i < num; i++) {
                Worker worker = new Worker();
                workers.add(worker);
            }
            //唤醒等待在workers上的一个线程
            workers.notify();
        }
    }

    @Override
    public void execute(Runnable job) {
        synchronized (jobs){
            jobs.push(job);
            taskCount++;
            jobs.notify();
        }
    }

    @Override
    public void shutdown() {
        workers.forEach(worker -> worker.shutdown());
    }

    @Override
    public int getTaskCount() {
        return taskCount;
    }

    class Worker implements Runnable, Serializable{

        private static final long SerialVersionUID = 23L;

        private int state;

        //此方法非线程安全,同一个job可能被多个worker消费
        @Override
        public void run() {//
            while (state == 0){
                Runnable job = jobs.poll();
                if(job != null){
                    job.run();
                }
            }
        }

        public void shutdown(){
            state = -1;
        }
    }
}
