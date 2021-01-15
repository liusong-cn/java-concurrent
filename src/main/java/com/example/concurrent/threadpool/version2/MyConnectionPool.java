package com.example.concurrent.threadpool.version2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @author: ls
 * @date: 2021/1/14 17:16
 * 自定义连接池
 **/
public class MyConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    private int size;

    public MyConnectionPool(int size) {
        if(size > 0){
            for (int i = 0; i < size; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
            this.size = size;
        }
    }

    public void releaseConnection(Connection connection){
        if(connection != null){
            synchronized (pool){
                pool.addFirst(connection);
                pool.notifyAll();
            }
        }
    }

    //超时等待，通过设定一个超时时间，如果未超时并且从wait()返回后没有取得想要的结果，比如此例中的pool.removeFirst(),那么继续等待
    //若超时了则直接退出，不再等待
    public Connection fetchConnection(long time) throws InterruptedException {
        synchronized (pool){
            //完全超时
            if(time <= 0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else {
                long future = time + System.currentTimeMillis();
                long remaining = time;
                while (remaining > 0 && pool.isEmpty()){
                    pool.wait();
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }

    static class ConnectionDriver{

        static class ConnectionHandler implements InvocationHandler{
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(method.getName().equalsIgnoreCase("commit"))
                    TimeUnit.MILLISECONDS.sleep(500);
                return null;
            }
        }

        static Connection createConnection(){
            return (Connection) Proxy.newProxyInstance(MyConnectionPool.class.getClassLoader(),new Class[]{Connection.class},new ConnectionHandler());
        }
    }
}
