package com.example.concurrent.demo;



/**
 * @author:ls
 * @date: 2020/11/5 19:56
 * threadlocal用来对变量保持线程隔离
 * 使得变量与线程隔离，常用来保持数据库连接,对于简单的变量可以使用private变量来保证线程私有
 * 同时测试了不使用threadlocal时，普通共享变量在多线程下的效果
 *
 **/
public class ThreadLocalTest {

    static class Thread1 implements Runnable{
        private ThreadLocal<String> threadLocal;

        public Thread1(ThreadLocal<String> threadLocal) {
            this.threadLocal = threadLocal;
        }

        @Override
        public void run() {
            threadLocal.set("1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("1，get：%s",
                    threadLocal.get()));
        }
    }

    static class Thread2 implements Runnable{

        private ThreadLocal<String> threadLocal;

        public Thread2(ThreadLocal<String> threadLocal) {
            this.threadLocal = threadLocal;
        }

        @Override
        public void run() {
            threadLocal.set("2");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("2，get：%s",
                    threadLocal.get()));
        }

    }

    static class Thread3 implements Runnable{

        private P p;


        public Thread3(P p) {
            this.p = p;
        }

        @Override
        public void run() {
            p.setName("3");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("3，get：%s",
                    p.getName()));
        }

    }
    static class Thread4 implements Runnable{

        private P p;


        public Thread4(P p) {
            this.p = p;
        }

        @Override
        public void run() {
            p.setName("4");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(String.format("4，get：%s",
                    p.getName()));
        }

    }



    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        //使用threadlocal来隔离变量时，保证同一个变量在线程内具有隔离的副本，不会影响到其他线程
        new Thread(new Thread1(threadLocal)).start();
        new Thread(new Thread2(threadLocal)).start();

        P p = new P();
        //当没有使用threadlocal来保证变量线程隔离时，由于引用了同一个变量，就可能导致变量在线程之间共享导致出现线程bug
        new Thread(new Thread3(p)).start();
        new Thread(new Thread4(p)).start();
    }

    public static class P{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
