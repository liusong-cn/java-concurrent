package com.example.concurrent.lock.excluded;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author: ls
 * @date: 2021/1/15 14:23
 * 自定义独占锁
 **/
public class MainLock implements Lock {

    private static final class Sync extends AbstractQueuedSynchronizer{

        @Override
        protected boolean tryAcquire(int arg) {
           if(compareAndSetState(0,1)){
               setExclusiveOwnerThread(Thread.currentThread());
               return true;
           }
           return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
           if(getState() == 0)
               throw new IllegalStateException("state must = 1");
           setExclusiveOwnerThread(null);
           setState(0);
           return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
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
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
