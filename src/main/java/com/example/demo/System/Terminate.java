package com.example.demo.System;

import java.util.Scanner;

import org.apache.log4j.Logger;
public class Terminate implements Runnable {
    
    private static final Logger logger = Logger.getLogger(Terminate.class);

    //keep on reading keyboard/mouse for termination signal
    //termination code 19
    public void run() {
        
        boolean flag1 = false, flag2 = false;
        Scanner scan = new Scanner(System.in);
        while (!flag1 || !flag2) {

            if (!flag1 && !flag2 && scan.nextInt() == 1) {
                flag1=true;
                continue;
            } 
            if(flag1 && !flag2 && scan.nextInt() == 9){
                flag2=true;
                continue;
            }
        }
        logger.info("terminate code received 19");
        System.exit(0);
        
    }
}
