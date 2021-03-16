package com.manastudent.admin.mq;

import com.manastudent.core.config.RabbitMQConfig;
import com.manastudent.core.config.RabbitMQDelayConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQConsumer {

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME_NOTIFY})
    public void watchNotify(String payload, Message message) {
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        System.out.printf("watchNotify --- routingKey: %s   payload: %s\n",receivedRoutingKey,payload);
    }

    @RabbitListener(queues = {RabbitMQDelayConfig.QUEUE_NAME_DELAY})
    public void delayNotify(String payload, Message message) {
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        System.out.printf("delayNotify --- routingKey: %s   payload: %s\n",receivedRoutingKey,payload);
    }
}
