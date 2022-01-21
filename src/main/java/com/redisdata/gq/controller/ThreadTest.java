package com.redisdata.gq.controller;

public class ThreadTest extends Thread{
    boolean waiting = true;
    boolean ready = false;

    public void run() {
        Thread t = Thread.currentThread();
        String thrdName = t.getName();
        while (waiting) System.out.println("waiting 等待中:" + waiting);
        System.out.println("waiting... 不用等待");
        startWait();
        try {
            Thread.sleep(1000);
            System.out.println("休息一秒钟");
        } catch (Exception exc) {
            System.out.println(thrdName + " interrupted. 打断 ");
        }

        System.out.println(thrdName + " terminating. 终止");
    }

    synchronized void startWait() {
        try {
            while (!ready) wait();
        } catch (InterruptedException exc) {
            System.out.println("wait() interrupted");
        }
    }

    synchronized void notice() {
        ready = true;
        notify();
    }

}

class ThreadTest3 {
    public static void main(String[] args) throws Exception {
        ThreadTest thrd = new ThreadTest();
        thrd.setName("nnnnnn-1   ");
        showThreadStatus(thrd);
        thrd.start();
//        Thread.sleep(5);
        thrd.waiting = false;
        Thread.sleep(5);
//        showThreadStatus(thrd);
        thrd.notice();
//        Thread.sleep(5);
        showThreadStatus(thrd);
//        while (thrd.isAlive()) System.out.println("alive");
        showThreadStatus(thrd);
    }

    static void showThreadStatus(Thread thrd) {
        System.out.println(thrd.getName() + "Alive:=" + thrd.isAlive() + " State:=" + thrd.getState());
    }
}
