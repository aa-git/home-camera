package com.example.demo.ServiceClasses;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    @Bean
    public WebClient webClient() {
        System.out.println("creating webclient -------------<><>");
        return WebClient.builder().build();
    }    
}



