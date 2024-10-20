package com.ithe.huabaiplayer.interaction.mq;

import com.ithe.huabaiplayer.interaction.model.entity.Barrage;
import com.ithe.huabaiplayer.interaction.service.BarrageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author L
 */
@Component
@Slf4j
public class RabbitMQConsumer {
    private BarrageService barrageService;

    @Autowired
    public void setBarrageService(BarrageService barrageService) {
        this.barrageService = barrageService;
    }
    public static final String EXCHANGE_NAME = "interactionTopicExchange";
    public static final String QUEUE_NAME_LIKE = "interactionLikeQueue";
    public static final String QUEUE_NAME_BARRAGE = "interactionBarrageQueue";
    public static final String LIKE_ROUTING_KEY_PREFIX = "interactionLike.";
    public static final String BARRAGE_ROUTING_KEY_PREFIX = "interactionBarrage.";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_NAME_LIKE, durable = "true"),
            exchange = @Exchange(value = EXCHANGE_NAME, type = "topic"),
            key = LIKE_ROUTING_KEY_PREFIX + "*"
    ))
    public void receiveLikeMessage(Object message) {
        System.out.println("Received like message: " + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_NAME_BARRAGE, durable = "true"),
            exchange = @Exchange(value = EXCHANGE_NAME, type = "topic"),
            key = BARRAGE_ROUTING_KEY_PREFIX + "*"
    ))
    public void receiveBarrageMessage(Barrage message) {
        log.info("成功接收到新增弹幕的消息！~{}", message);
        barrageService.saveByTime(message);
    }
}