package com.example.concurrent.threadpool.version2;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: ls
 * @date: 2021/1/15 9:18
 **/
public class Test {

    public static void main(String[] args) {
        MyConnectionPool connectionPool = new MyConnectionPool(10);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(()->{
                Connection connection = null;
                try {
                    connection = connectionPool.fetchConnection(500);
                    try {
                        if(connection != null){
                            connection.createStatement();
                            connection.commit();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    connectionPool.releaseConnection(connection);
                }
            },String.valueOf(i));
            thread.start();
        }
    }
}
