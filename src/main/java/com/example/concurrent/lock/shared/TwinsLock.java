package com.example.concurrent.lock.shared;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author: ls
 * @date: 2021/1/15 13:30
 * 自定义共享锁，允许同时两个线程访问
 **/
public class TwinsLock implements Lock {

    private Sync sync = new Sync(2);

    private static final class Sync extends AbstractQueuedSynchronizer{

        public Sync(int count){
            if(count <= 0)
                throw new IllegalArgumentException("count must > 0");
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for (;;){
                int currentCount = getState();
                int newCount = currentCount - reduceCount;
                //newCount < 0表示无资源可用应立即返回
                //除此之外，那么认为有资源可用，因此通过cas设置
                if(newCount <0 || compareAndSetState(currentCount, newCount))
                    return newCount;
            }
        }

        @Override
        protected boolean tryReleaseShared(int increaseCount) {
            for (;;){
                int currentCount = getState();
                int newCount = currentCount + increaseCount;
                if(compareAndSetState(currentCount, newCount))
                    return true;
            }
        }
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
