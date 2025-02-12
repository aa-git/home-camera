package com.example.demo.System;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import org.apache.log4j.Logger;

public class KeepActive implements Runnable {

    private static final Logger logger = Logger.getLogger(KeepActive.class);
    //use locks to synchronize the mouse movement here with robot class, or acquire lock on robot class
    //use some design pattern [ singleton] for robot class, for its only one instance and have a lock on it.
    @Override
    public void run() {
        System.setProperty("java.awt.headless", "false");
        Robot robot=null;
        try {
            robot = new Robot();
        } catch (Exception e) {
            logger.info(e);
            logger.info("Keep active thread didnt work, run this program again");
            System.exit(1);
        }

        try {
            while (true) {
                Thread.sleep(5000);//every 2 min
                Point p = MouseInfo.getPointerInfo().getLocation();
                int x = p.x;
                int y = p.y;

                if (x == 0 && y == 0) {
                    robot.mouseMove(100, 100);
                    Thread.sleep(1000);//1 SECONDs
                    robot.mouseMove(x, y);
                    Thread.sleep(1000);//1 SECONDs
                } else {
                    robot.mouseMove(0, 0);
                    Thread.sleep(2000);//1 SECONDs
                    robot.mouseMove(x, y);
                    Thread.sleep(2000);//1 SECONDs
                }
            }
        }catch(Exception e){
            logger.info(e);
            System.exit(1);
        }
    }
}
