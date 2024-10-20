package com.ithe.huabaiplayer.interaction.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ithe.huabaiplayer.interaction.mq.RabbitMQConsumer.*;

@Component
public class RabbitMQProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    public void sendMessage(String message, String type) {
        if ("barrage".equals(type)) {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, BARRAGE_ROUTING_KEY_PREFIX + "Add", message);
        }
    }

    public void sendMessage(Object message, String type) {
        if ("barrage".equals(type)) {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, BARRAGE_ROUTING_KEY_PREFIX + "Add", message);
        }
    }
}