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

    // Configura a rota principal das mensagens
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config)
    {
        // Prefixo dos tópicos que o servidor publica pros clientes conectados (Pega dentro de um subscribe, tipo um addEventListener)
        config.enableSimpleBroker("/topic");

        // Prefixo para mensagens do cliente pro servidor no endpoint (não vai ser usado já que o cliente SÓ recebe mensagem, mas aparentemente é obrigatório declarar)
        config.setApplicationDestinationPrefixes("/app");
    }

    // Define o endpoint para o frontend acessar as mensagens
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("/eventos").withSockJS();
    }
}