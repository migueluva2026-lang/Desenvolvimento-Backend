package com.example.TrabalhoDenis.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.*;

// Configura o Websocket para funcionar no Spring
// Websocket é bom para ter uma conexão constante com o frontend, evita polling ou ficar dando f5

@Configuration
@EnableWebSocketMessageBroker // Ativa o Websocket no Spring
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Configura o prefixo pros tópicos que o navegador pode se inscrever
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config)
    {
        // Prefixo dos tópicos que o servidor posta para os clientes conectados (Serve para os consumers se inscreverem com subscribe)
        config.enableSimpleBroker("/topic");

        // Prefixo para mensagens do cliente pro servidor (não vai ser usado já que o cliente SÓ recebe mensagem, mas aparentemente é obrigatório declarar)
        config.setApplicationDestinationPrefixes("/app");
    }

    // Cria o endpoint onde o navegador inicia a conexão WebSocket
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("/eventos").setAllowedOriginPatterns("*");
    }
}