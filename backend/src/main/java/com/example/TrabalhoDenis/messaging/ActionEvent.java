package com.example.TrabalhoDenis.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// Representa um evento que aconteceu no sistema
// Vai ser serializado para JSON, enviado ao RabbitMQ, e depois repassado pro WebSocket
// É basicamente um model, isso vai dizer o tipo do evento, se é um create, delete, etc

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionEvent {
    private String action; // Se é um Create, delete, etc
    private String entity; // Qual entidade ele mexe (product, user, category)
    private Long entityId;
    private String message; // A mensagem em sí, varia conforme o action (diferente para criar e deletar, duhhh)
    private LocalDateTime timestamp;
}
