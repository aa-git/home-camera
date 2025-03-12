package com.example.demo.Internet;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Whatsapp {

    private static final Logger logger = Logger.getLogger(Whatsapp.class);

    @Autowired
    private Robot robot;

    @Value("${server.port}")
    private String myPort;

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

            String newMsg = "";
            String oldMsg = "";
            while (true) {
                try {
                    robot.delay(1000);
                    newMsg = readMessage();
                    if (!oldMsg.equals(newMsg)) {
                        executeCommand(newMsg);
                        sendMessage(newMsg);
                        oldMsg = newMsg;
                    }
                } catch (Exception e) {
                    for (StackTraceElement x : e.getStackTrace()) {
                        logger.error("--whatsappp thread-->" + x.toString());
                    }
                    logger.info(e);
                }
            }
        }

        private Boolean executeCommand(String msg) {
            if (msg.toLowerCase().equals("get screenshots")) {
                String ip = "";
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (Exception e) {
                    logger.info(e);
                    return false;
                }

                Set<String> locations = webClient
                        .get().uri("http://" + ip + ":" + myPort + "/getAllLocations")
                        .retrieve().bodyToMono(Set.class).block();

                for (String loc : locations) {
                    byte[] bytes = null;
                    try {
                        bytes = webClient
                                .get().uri("http://" + ip + ":" + myPort + "/camera/" + loc)
                                .retrieve().bodyToMono(Resource.class).block().getContentAsByteArray();
                    } catch (Exception e) {
                        logger.info(e);
                        return false;
                    }

                    BufferedImage image = null;
                    try {
                        image = ImageIO.read(new ByteArrayInputStream(bytes));
                    } catch (IOException e) {
                        logger.info(e);
                        return false;
                    }

                    Toolkit
                            .getDefaultToolkit()
                            .getSystemClipboard()
                            .setContents(new Helper.TransferableImage(image), null);
                    try {
                        sendMessageOfClipboard();
                    } catch (AWTException e) {
                        logger.info(e);
                        return false;
                    }
                }

            }
            return true;
        }

        private String readMessage() throws AWTException, UnsupportedFlavorException, IOException {
            logger.info("moving mouse");
            Helper.moveMouse(robot, 1360, 705);
            logger.info("moving mouse - done");
            for (int i = 0; i < 3; i++) {
                Helper.mouseLeftClick(robot, true);
            }
            Helper.keyPress(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_C);
            return Helper.readFromClipboard();
        }

        private Boolean sendMessage(String msg) throws AWTException {
            Helper.moveMouse(robot, 1100, 790);
            Helper.mouseLeftClick(robot, false);
            Helper.copyToClipboard(msg);
            Helper.keyPress(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_V);
            Helper.keyPress(robot, KeyEvent.VK_ENTER);
            return true;
        }

        private Boolean sendMessageOfClipboard() throws AWTException {
            Helper.moveMouse(robot, 1100, 790);
            Helper.mouseLeftClick(robot, false);
            Helper.keyPress(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_V);
            Helper.keyPress(robot, KeyEvent.VK_ENTER);
            return true;
        }
    }

}
