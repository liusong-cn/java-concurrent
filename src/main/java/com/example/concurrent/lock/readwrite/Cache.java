package com.example.concurrent.lock.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: Administrator
 * @date: 2021/2/19 0:05
 * 读写锁使用示例
 */
public class Cache {

    private static Map<String, String> params = new HashMap<>();

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static Lock r = readWriteLock.readLock();

    private static Lock w = readWriteLock.writeLock();

    //锁结合线程不安全的map实现线程安全
    public static String get(String key){
        r.lock();
        String value = null;
        try {
            value = params.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            r.unlock();
        }
        return value;
    }

    public static void put(String key, String value){
        w.lock();
        try {
            params.put(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            w.unlock();
        }
    }
}
