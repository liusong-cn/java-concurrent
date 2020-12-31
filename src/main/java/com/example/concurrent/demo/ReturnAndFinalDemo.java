package com.example.concurrent.demo;

/**
 * @author: ls
 * @date: 2020/11/20 14:40
 * 测试return和final先后
 **/
public class ReturnAndFinalDemo {

    public static void main(String[] args) {
        System.out.println(test());
    }

    static int test(){
        try {
            //多个return会被finally中return覆盖
            return 1;
        }finally {
            return 2;
        }
    }
}
