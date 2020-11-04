package com.example.concurrent.demo;

/**
 * @author:ls
 * @date: 2020/11/3 19:55
 * 线程组异常处理
 * 在线程组内定义线程抛出异常时的处理方法
 * 在线程内手动抛出异常
 **/
public class ThreadGroupExceptionTest {

    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("group1"){

            //这种写法比较少见，常规写法是通过继承ThreadGroup，并重写方法
            //而这个方式直接在初始化父类时直接重写了方法
            //想到了使用匿名类来初始化抽象类时的操作
            @Override
            public void uncaughtException(Thread t, Throwable e) {
//                super.uncaughtException(t, e);
                System.out.println(String.format("抛出异常的线程名称：%s，异常信息为：%s",
                        t.getName(),e.getMessage()));
            }

        };

        Thread thread = new Thread(threadGroup,()->{
            throw new RuntimeException("主动抛出异常");
        });
        //切记不要忘记启动线程
        thread.start();
    }
}
