package com.example.concurrent.demo;

import java.util.concurrent.Callable;

/**
 * @author:ls
 * @date: 2020/11/3 16:07
 * 实现callable接口的任务类
 **/
public class Task implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        Thread.sleep(2000);
        return 2;
    }
}
