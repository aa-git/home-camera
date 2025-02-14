package com.example.demo.Internet;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Whatsapp {

    private static final Logger logger = Logger.getLogger(Whatsapp.class);

    @Autowired
    private Robot robot;

    @Autowired
    private WebClient webClient;

    public void execute() {
        Thread t = new Thread(new WhatsappThread(robot, webClient));
        t.start();
    }

    private class WhatsappThread implements Runnable {

        private Robot robot;
        private WebClient webClient;

        public WhatsappThread(Robot robot, WebClient webClient) {
            this.robot = robot;
            this.webClient = webClient;
        }

        @Override
        public void run() {
            try {
                String newMsg = "";
                String oldMsg = "";
                while (true) {
                    newMsg = readMessage();
                    if (!oldMsg.equals(newMsg)) {
                        executeCommand(newMsg);
                        sendMessage(newMsg);
                        oldMsg=newMsg;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        private Boolean executeCommand(String msg) {
            return true;
        }

        private String readMessage() throws AWTException, UnsupportedFlavorException, IOException {
            logger.info("moving mouse");
            Helper.moveMouse(robot, 1360, 705);
            logger.info("moving mouse - done");
            for (int i = 0; i < 3; i++) {
                Helper.mouseLeftClick(robot);
            }
            Helper.keyPress(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_C);
            return Helper.readFromClipboard();
        }

        private Boolean sendMessage(String msg) throws AWTException {
            Helper.moveMouse(robot, 1100, 790);
            Helper.mouseLeftClick(robot);
            Helper.copyToClipboard(msg);
            Helper.keyPress(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_V);
            Helper.keyPress(robot, KeyEvent.VK_ENTER);
            return true;
        }
    }

}
