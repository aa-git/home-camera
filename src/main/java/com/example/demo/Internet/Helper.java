package com.example.demo.Internet;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.IOException;

public class Helper  {
    
    public static void moveMouse(Robot robot, int x, int y) throws AWTException{
        robot.delay(100);
        System.out.println("mouse move to 0,0");
        robot.mouseMove(0,0);
        robot.delay(100);
        robot.mouseMove(x,y);
        robot.delay(100);
    }

    public static void mouseLeftClick(Robot robot){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(50);
    }

    public static void copyToClipboard(String msg){
        StringSelection stringSelection = new StringSelection(msg);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
    }

    public static String readFromClipboard() throws  UnsupportedFlavorException, IOException{
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();      
        //Camera.fixClipboardUsingPython();
        return (String)clipboard.getData(DataFlavor.stringFlavor);  
    }


    public static void keyPress(Robot robot, int ... keys){
        for(int i: keys){
            robot.keyPress(i);
        }
        for(int i=keys.length-1;i>=0;i--){
            robot.keyRelease(keys[i]);
        }
        robot.delay(50);
    }
}
