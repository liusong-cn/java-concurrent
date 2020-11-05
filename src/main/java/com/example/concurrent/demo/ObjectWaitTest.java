package com.example.concurrent.demo;

/**
 * @author:ls
 * @date: 2020/11/4 16:29
 * 测试object.wait方法的作用
 * 从源码注释上来看object.wait会使得执行这行代码所在的线程放弃抢夺cpu执行时间，直到其他线程唤醒该线程notify notifyall
 **/
public class ObjectWaitTest {

    public static void main(String[] args) throws InterruptedException {
        Person p = new ObjectWaitTest().new Person("liusong");
        Thread t1 = new Thread(()->{
            //在同步块内的代码是原子性的，除非主动调用wait，否则会直接执行完毕，而不会出现多个线程争抢cpu导致分开执行
            //使用wait时需要注意obj.wait需要当前线程对obj占有锁
            synchronized (p){
                try {
                    System.out.println("执行wait前");
                    p.wait();
                    System.out.println("执行wait后");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1");
        t1.start();

        Thread t2 = new Thread(()->{
            synchronized (p){
               p.notifyAll();
            }
        },"t2");
        t2.start();
    }

    public class Person{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Person(String name) {
            this.name = name;
        }
    }
}
