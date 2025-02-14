package com.example.demo.ServiceClasses;

import java.awt.AWTException;
import java.awt.Robot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RobotClass {
    @Bean
    public Robot robot() throws AWTException {
        System.setProperty("java.awt.headless", "false");
        System.out.println("creating robot -------------<><>");
        try {
            Robot r = new Robot();
            return r;
        } catch (Exception e) {
            System.out.println("creating robot -------------<><>");
            System.out.println(e);
            return null;
        }
    }
}
