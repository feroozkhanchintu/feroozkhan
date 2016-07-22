package com.codenation.findbugs.Tests;

/**
 * Created by Ferooz on 22/07/16.
 */
public class Test {
    public static void main(String args[])
    {

        Thread th1 = new Thread();
        th1.run();

        MyThread th = new MyThread();
        th.run();

        MyThread1 th2 = new MyThread1();
        th2.run();

//        Runnable myRunnable = new Runnable(){@Override public void run() {}};
//        myRunnable.run();

        MyRun myRun = new MyRun();
        myRun.run();
    }
}

class MyRun implements Runnable{

    @Override
    public void run() {

    }
}

class MyThread extends MyThread1{

}

class MyThread1 extends Thread{

}