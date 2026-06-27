package com.example.TrabalhoDenis.messaging;

import com.example.TrabalhoDenis.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


// Recebe os dados do evento e publica no Exchange do RabbitMQ.
// O RabbitTemplate serializa o ActionEvent para JSON automaticamente (pelo MessageConverter configurado)

@Service
@RequiredArgsConstructor
public class EventPublisher {

    // De acordo com docs.spring.io: "Helper class that simplifies synchronous RabbitMQ access (sending and receiving messages)."
    // Eu diria que é a classe "principal" que faz o rabbitMQ funcionar
    private final RabbitTemplate rabbitTemplate;

    // Perceba: Os params são os mesmos do ActionEvent.Java (O model que define o que é um evento)
    // O que faz: Cria um evento do tipo do Model e envia ele para a EXCHANGE (O distribuidor para diferentes queues) e especifica a fila (routing_key)
    public void publish(String action, String entity, Long entityId, String message)
    {
        ActionEvent event = new ActionEvent(action, entity, entityId, message, LocalDateTime.now()); // cria o evento
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, event);
    }
}
