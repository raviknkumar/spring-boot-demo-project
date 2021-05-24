package com.example.messageapp.dbevent.handlers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryHandlerConfiguration {

    @Bean
    public MessageEventHandler messageEventHandler() {
        return new MessageEventHandler();
    }
}
