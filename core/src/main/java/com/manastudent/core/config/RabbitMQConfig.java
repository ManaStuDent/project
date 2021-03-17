package com.manastudent.core.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    Log log = LogFactory.get();

    public static final String EXCHANGE_NAME_NOTIFY = "ex_notify";
    public static final String QUEUE_NAME_NOTIFY = "qu_notify";

    @Bean(EXCHANGE_NAME_NOTIFY)
    public Exchange notifyExchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_NAME_NOTIFY)
                .durable(true)
                .build();
    }

    @Bean(QUEUE_NAME_NOTIFY)
    public Queue notifyQueue() {
        return new Queue(QUEUE_NAME_NOTIFY);
    }

    @Bean
    public Binding notifyBinding(
            @Qualifier(EXCHANGE_NAME_NOTIFY) Exchange notifyExchange,
            @Qualifier(QUEUE_NAME_NOTIFY) Queue notifyQueue) {
        return BindingBuilder
                .bind(notifyQueue)
                .to(notifyExchange)
                .with("notify.*")
                .noargs();
    }

    /**
     * 定制化amqp模版
     *
     * ConfirmCallback接口用于ack回调   即消息发送到exchange  ack
     * ReturnCallback接口用于消息发送失败回调  即消息发送不到任何一个队列中  ack
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // 消息返回, 需要配置 publisher-returns: true
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });

        // 消息确认, 需要配置 spring.rabbitmq.publisher-confirm-type=correlated
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送到exchange成功");
            } else {
                log.info("消息发送到exchange失败,原因: {}", cause);
            }
        });
        return rabbitTemplate;
    }

}
