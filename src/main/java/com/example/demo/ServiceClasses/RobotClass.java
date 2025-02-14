package com.example.demo.ServiceClasses;

import java.awt.AWTException;
import java.awt.Robot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RobotClass {

    @Bean
    public Robot robot() throws AWTException {
        return new Robot();
    }
}
