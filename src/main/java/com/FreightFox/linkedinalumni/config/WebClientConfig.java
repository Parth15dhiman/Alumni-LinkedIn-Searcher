package com.freightfox.linkedinalumni.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient phantomBusterWebClient(PhantomBusterProperties props){
        System.out.println("Creating PhantomBuster WebClient with baseUrl = " + props.baseUrl());
        return WebClient.builder()
                .baseUrl(props.baseUrl())
                .defaultHeader("X-Phantombuster-Key", props.apiKey())
                .build();
    }
}
