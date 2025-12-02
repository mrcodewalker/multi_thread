package org.example;

public class Main {
    public static void main(String[] args) {

//        firstExample();
//        secondExample();
        thirdExample();
    }
    public static void firstExample(){
        FirstThread firstThread = new FirstThread();
        SecondThread secondThread = new SecondThread();

        Thread thread1 = new Thread(firstThread);
        thread1.start();

        Thread thread2 = new Thread(secondThread);
        thread2.start();
    }
    public static void secondExample(){
        SimpleThread simpleThread1 = new SimpleThread("HANOI");
        SimpleThread simpleThread2 = new SimpleThread("TP.HCM");
        simpleThread1.start();
        simpleThread2.start();
    }
    public static void thirdExample(){
        Thread t1 = new Thread(new Multi3());
        Thread t2  = new Thread(new Multi3());

        t1.start();
        t2.start();
    }
}