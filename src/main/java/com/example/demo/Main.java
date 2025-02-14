package com.example.demo;

import java.net.InetAddress;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.Internet.Whatsapp;
import com.example.demo.RepresentationClasses.StringMsg;

@SpringBootApplication
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

	private static String getArgVal(String x){
		return x.split("=")[1];
	}
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
		/*for(Thread t:Thread.getAllStackTraces().keySet())
			System.out.println(t.getName());
		try{Thread.sleep(1000000);}catch(Exception e){}*/

		// Integer i_1 = new Integer(45);
        // Integer i_2 = new Integer(45);

		// HashMap<Integer, Integer> hm = new HashMap<>();
		// hm.put(i_1, 1);
		// hm.put(i_2, 2);
		// System.out.println(hm.get(45));
		// System.out.println(hm.get(i_1));
		// System.out.println(hm.get(i_2));

		// IdentityHashMap<Integer, Integer> i_hm = new IdentityHashMap<>();
		// i_hm.put(i_1, 1);
		// i_hm.put(i_2, 2);
		// System.out.println(i_hm.get(45));
		// System.out.println(i_hm.get(i_1));
		// System.out.println(i_hm.get(i_2));
		
		//try{Thread.sleep(1000000);}catch(Exception e){}
		
		for(int i=0;i<args.length;i++){
			logger.info("arg "+i+" : "+getArgVal(args[i]));
		}
        String myIP = "", myPort = "";
        try {
            myIP = InetAddress.getLocalHost().getHostAddress();
            logger.info("my ip address = " + myIP);
            myPort = "8080";
        } catch (Exception e) {
            logger.info(e.getMessage());
            System.exit(0);
        }

        if (args.length == 3) {//slave node
            logger.info("running as slave node with IP:port of master = " + getArgVal(args[0]) + ":" + getArgVal(args[1]) + "\nat location = " + getArgVal(args[2]));
            String masterIP = getArgVal(args[0]), port = getArgVal(args[1]), loc = getArgVal(args[2]);

            //register with master, then start your own application
            StringMsg response = new StringMsg("init", false);
            while (!(response != null && response.getSuccess())) {
                logger.info("registering " + myIP + " with master");
				response = WebClient.builder().build().get().uri("http://" + masterIP + ":" + port + "/register/" + myIP + "/" + myPort + "/" + loc).retrieve().bodyToMono(StringMsg.class).block();
                /*try {
                    response = WebClient.builder().build().get().uri("http://" + masterIP + ":" + port + "/register/" + myIP + "/" + myPort + "/" + loc).retrieve().bodyToMono(StringMsg.class).block();
                }catch(Exception e){
					response = null;
				}*/
            }
            logger.info("registering " + myIP + " with master: success");
        }
        if (args.length == 1) {//master node
            System.out.println("running as master node with location at = " + getArgVal(args[0]));
        }
        ApplicationContext context = SpringApplication.run(Main.class, args);
        //new Thread(new KeepActive()).start();
        //new Thread(new Terminate()).start();
        
        Whatsapp whatsapp = context.getBean(Whatsapp.class);
        whatsapp.execute();
    }
}
