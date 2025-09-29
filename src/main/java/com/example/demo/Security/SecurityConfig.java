package com.example.demo.Security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig{
    
    
    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeHttpRequests(auth -> auth.requestMatchers("/actuator/health").permitAll().anyRequest().authenticated())
    //         .requiresChannel(channel -> channel.anyRequest().requiresSecure());
            
    //     return http.build();
    // }

    // @Bean
    // @Order(1)
    // public SecurityFilterChain healthChain(HttpSecurity http) throws Exception {
    //     http
    //         .securityMatcher("/actuator/health")
    //         .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
    //         .csrf(csrf -> csrf.disable()); // not needed for GET

    //     return http.build();
    // }

    // // 2️⃣ Chain for everything else (HTTPS required)
    // @Bean
    // @Order(2)
    // public SecurityFilterChain appChain(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
    //         .requiresChannel(channel -> channel.anyRequest().requiresSecure());

    //     return http.build();
    // }
}
