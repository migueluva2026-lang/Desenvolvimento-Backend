package com.example.TrabalhoDenis.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configurações de como as coisas do rabbitMQ devem funcionar, é o arquivo geral de config do rabbitMQ

@Configuration
public class RabbitMQConfig {

    // Por exemplo, constantes centralizadas para não repetir strings pelo projeto
    public static final String QUEUE_NAME = "eventos"; // Queue = Fila
    public static final String EXCHANGE_NAME = "eventos-exchange"; // Exchange = distribui entre as filas (sistemas) do RabbitMQ
    public static final String ROUTING_KEY = "evento"; // Chave de roteamento = Diz para qual chave o evento vai (Cada fila tem uma routing key)

    @Bean
    public Queue queue()
    {
        return new Queue(QUEUE_NAME, true); // true para o segundo arg do tipo queue, faz a fila sobreviver ao restart do RabbitMQ
    }

    @Bean
    public DirectExchange exchange() // Exchange é quem distribui 1 evento para diversas routing keys, nesse caso hardcodei só uma mesm
    {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange)
    {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY); // Binda a queue/fila com uma routing key e o exchange passados como arg
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter()
    {
        return new Jackson2JsonMessageConverter(); // Faz o RabbitTemplate serializar/desserializar objetos Java como JSON automaticamente
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    // Configura explicitamente o factory dos listeners para usar Jackson também
    // Sem isso, o RabbitListener não saberia desserializar o JSON de volta para ActionEvent
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory)
    {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
}
