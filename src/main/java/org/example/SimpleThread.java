package org.example;

public class SimpleThread extends Thread{
    public SimpleThread(String str){
        super(str);
    }
    @Override
    public void run(){
        for (int i=0;i<10;i++){
            System.out.println(i + " "+getName());
            try{
                Thread.sleep(200);
            } catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("===DONE=== "+getName());
    }
}
