package com.example.demo.Security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MultiConnectorConfig {

    // @Value("${server.http.port}")
    // private int httpPort;
    
    // @Value("${server.port}")
    // private int httpsPort;

    // @Bean
    // public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
    //     return factory -> {
    //         // Create an additional connector for HTTP (8080)
    //         Connector httpConnector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
    //         httpConnector.setPort(httpPort);
    //         httpConnector.setScheme("http");
    //         httpConnector.setSecure(false);
    //         httpConnector.setRedirectPort(httpsPort);
    //         factory.addAdditionalTomcatConnectors(httpConnector);
    //     };
    // }
}
