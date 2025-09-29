package com.example.demo;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    private static String getArgVal(String x) {
        return x.split("=")[1];
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
        /* 
        System.setProperty("java.awt.headless", "false");
        // String[] args_ = {"--server.port=8080", "--myLoc=nnanan dd"};
        // args = args_;
        if(args.length==0){
            System.out.println("read README.txt file or");
            System.out.println("run with command(as master node):java -jar <jar name> --server.port=1234 --myLoc=\"some location\" ");
            System.out.println("or run with command(as slave node):java -jar <jar name> --masterIP=1.1.1.1 --masterPort=9901 --server.port=1234 --myLoc=\"some location\"");
            System.exit(0);
        }

        for (int i = 0; i < args.length; i++) {
            logger.info("arg " + i + " : " + getArgVal(args[i]));
        }

        Boolean runningAsMaster = args.length == 2;
        String myIP = "";

        try {
            myIP = InetAddress.getLocalHost().getHostAddress();
            logger.info("my ip address = " + myIP);
        } catch (Exception e) {
            logger.info(e);
            System.exit(1);
        }

        if (runningAsMaster) {//master node
            String myPort = getArgVal(args[0]);
            String loc = getArgVal(args[1]);
            logger.info("running as master node, ip:port=" + myIP + ":" + myPort + " and location = " + loc);
        } else {//slave node
            String masterIP = getArgVal(args[0]), masterPort = getArgVal(args[1]), myPort = getArgVal(args[2]), loc = getArgVal(args[3]);

            logger.info("running as slave node with IP:port of master = " + masterIP + ":" + masterPort);
            logger.info("slave node ip:port=" + myIP + ":" + myPort + " and location = " + loc);

            //register with master, then start your own application
            StringMsg response = new StringMsg("init", false);
            while (!(response != null && response.getSuccess())) {
                logger.info("registering " + myIP + " with master");
                response = WebClient.builder().build().get().uri("http://" + masterIP + ":" + masterPort + "/register/" + myIP + "/" + myPort + "/" + loc).retrieve().bodyToMono(StringMsg.class).block();
                // try {
                //     response = WebClient.builder().build().get().uri("http://" + masterIP + ":" + port + "/register/" + myIP + "/" + myPort + "/" + loc).retrieve().bodyToMono(StringMsg.class).block();
                // }catch(Exception e){
				// 	response = null;
				// }
            }
            logger.info("registering " + myIP + " with master: success");
        }
        
        ApplicationContext context = SpringApplication.run(Main.class, args);
        
        if(runningAsMaster) {
            //new Thread(new KeepActive()).start();
            //new Thread(new Terminate()).start();
            Whatsapp whatsapp = context.getBean(Whatsapp.class);
            whatsapp.execute();
        }
        */
    }
}
