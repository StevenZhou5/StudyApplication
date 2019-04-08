package com.example.lib;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTestClass {
    public static void main(String[] args) {
        test();
    }

    // 总结：volatile关键字可以解决可见性问题：即只要被其修饰的变量在任意线程中被修改时，其线程是在这之后在取这个值的时候（如果正在被修改就会等待）一定取得是新的值
    // volatile 关键字可以保证其前面和后面代码的有序性（指令重拍会导致无序）：既其前面的代码一定先执行完，后面的代码才会执行
    // volatile 关键字并不能解决原子性的问题，如果要将非原子操作变成原子操作可以视情况使用synchronized关键字 或者使用AtomicXXX来解决
    // 在可以使用AtomicXXX解决的问题不要使用synchronized，因为AtomicXXX的性能比synchronized高效很多
    private static volatile int intTest;
    static AtomicInteger init = new AtomicInteger();

    private static synchronized void test() {
        intTest = 0;
        final long initTime = System.currentTimeMillis();
        System.out.println("初始时间" + initTime);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000000; i++) {
                        doSomeThing();

                    }
                    System.out.println("jieguo = " + init + "; 差值时间" + (System.currentTimeMillis() - initTime));
                }
            }).start();
        }
    }

    private static void doSomeThing() {
//        intTest++;
        init.incrementAndGet();
    }
}
