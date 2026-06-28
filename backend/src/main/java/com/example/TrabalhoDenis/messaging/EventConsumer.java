package com.example.TrabalhoDenis.messaging;

import com.example.TrabalhoDenis.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

// Diz o que deve acontecer ao receber uma mensagem
// Fiz com que serializasse o objeto recebido pelo RabbitMQ e publicasse o evento (passando para JSON) no tópico WebSocket

@Component
@RequiredArgsConstructor
public class EventConsumer {
    // SimpMessagingTemplate é a ferramenta do Spring WebSocket para enviar mensagens a todos os clients conectados num determinado tópico
    private final SimpMessagingTemplate messagingTemplate;

    // @RabbitListener faz o Spring ouvir a fila continuamente durante a conexão
    // Todas as vezes que chegar uma mensagem, essa função é chamado automaticamente.
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receive(ActionEvent event) // note que é um ActionEvent e não bytes, o spring automáticamente transformou bytes -> json -> actionEvent
    {
        // Repassa o evento para todos os navegadores conectados em "/topic/eventos"
        messagingTemplate.convertAndSend("/topic/eventos", event); // converte o ActionEvent para o formato da mensagem STOMP (JSON) e envia
    }
}