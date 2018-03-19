package com.wrist_trainer.bivsa.wristtrainer.Utils;

/**
 * Created by bivsa on 3/17/2018.
 */

public class Training implements Runnable {
    @Override
    public void run() {
        while(true){
            System.out.println("HELLO");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){
                System.out.println(e.getMessage());
            }

        }
    }
}
