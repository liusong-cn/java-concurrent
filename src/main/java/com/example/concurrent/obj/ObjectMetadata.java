package com.example.concurrent.obj;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author:ls
 * @date: 2020/11/9 11:22
 * 使用jol打印对象头信息
 **/
public class ObjectMetadata {

    public static void main(String[] args) {
        Person person = new Person();
        Person person1 = new Person();
        //由于jdk1.6以后使用了指针压缩，所以看到头信息中表示类对象地址的部分缺少了4个字节 32bit
        //可以看到类相同的两个对象头信息中指向类对象的指针是一样的
        System.out.println(ClassLayout.parseInstance(person).instanceSize());
        System.out.println(ClassLayout.parseInstance(person).toPrintable());
        System.out.println(ClassLayout.parseInstance(person1).instanceSize());
        System.out.println(ClassLayout.parseInstance(person1).toPrintable());
        //测试数组类型的头信息
        //数组类型的会在头信息中描述数组的长度
        Person[] people = new Person[2];
        people[1] = person;
        people[0] = person1;
        System.out.println(ClassLayout.parseInstance(people).instanceSize());
        System.out.println(ClassLayout.parseInstance(people).toPrintable());
    }

    static class Person{
        String name;
        int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
