package org.brocode.orderservice.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String INVENTORY_REQUEST_QUEUE = "inventory.request.queue";
    public static final String INVENTORY_REPLY_QUEUE = "inventory.reply.queue";

    @Bean
    public Queue requestQueue() {
        return new Queue(INVENTORY_REQUEST_QUEUE);
    }

    @Bean
    public Queue replyQueue() {
        return new Queue(INVENTORY_REPLY_QUEUE);
    }

}
