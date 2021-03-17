package com.manastudent.admin.mq;

import com.manastudent.core.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MQConsumer {

    private void ack(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME_NOTIFY})
    public void watchNotify(String payload, Message message, Channel channel) throws IOException {
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        System.out.printf("watchNotify --- routingKey: %s   payload: %s\n",receivedRoutingKey,payload);

        ack(message, channel);
    }


}
