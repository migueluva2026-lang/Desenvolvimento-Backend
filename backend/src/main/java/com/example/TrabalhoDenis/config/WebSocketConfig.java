package com.example.TrabalhoDenis.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.*;

// Configurações do Websocket, é mais para dar override em alguns métodos e melhorar eles
//

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

    }
}
