package com.example.TrabalhoDenis.config;

import org.springframework.context.annotation.Configuration;

// Configurações de como as coisas do rabbitMQ devem funcionar, é o arquivo geral de config do rabbitMQ
//

@Configuration
public class RabbitMQConfig {

    // Por exemplo constantes centralizadas para não repetir strings pelo projeto
    public static final String QUEUE_NAME = "eventos"; // Queue = Fila
    public static final String EXCHANGE_NAME = "eventos-exchange"; // Exchange = Tipo/algorítmo? do rabbitMQ
    public static final String ROUTING_KEY = "evento"; // chave de roteamento, ainda não sei pra que serve


}
