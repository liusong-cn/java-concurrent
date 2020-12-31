package com.example.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2020/12/30 21:34
 */
public class MyThreadPool implements ThreadPool {

    private List<Worker> workers;

    private List<Runnable> jobs;

    private int workerCount;

    public MyThreadPool() {
        initThreadPool();
    }

    private void initThreadPool(){
        workers = new ArrayList<>();
        jobs = new ArrayList<>();
    }

    @Override
    public void addWorker() {
        workers.add(new Worker());
        workerCount++;
    }

    @Override
    public void execute(Runnable job) {
        jobs.add(job);
    }

    @Override
    public int count() {
        return workerCount;
    }

    public void start(){
        for (Worker worker : workers) {
            worker.run();
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    class Worker implements Runnable{

        private int state;

        @Override
        public void run() {
            while (state >= 0){
                if(jobs.size() > 0){
                    for (Runnable job : jobs) {
                        job.run();
                    }
                }
            }
        }

        public void shutdown(){
            state = -1;
        }
    }
}
