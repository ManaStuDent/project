package com.manastudent.core.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class RabbitMQDelayConfig {
    Log log = LogFactory.get();

    public static final String EXCHANGE_NAME_DELAY = "ex_delay";
    public static final String QUEUE_NAME_DELAY = "qu_delay";

    @Bean(EXCHANGE_NAME_DELAY)
    public Exchange delayExchange() {
        log.info("\n\nuse rabbitMQ delay message need rabbitmq_delayed_message_exchange plugin.\nplugin download list: https://www.rabbitmq.com/community-plugins.html\n");
        return ExchangeBuilder
                .topicExchange(EXCHANGE_NAME_DELAY)
                .delayed()
                .durable(true)
                .build();
    }

    @Bean(QUEUE_NAME_DELAY)
    public Queue delayQueue() {
        return new Queue(QUEUE_NAME_DELAY);
    }

    @Bean
    public Binding delayBinding(
            @Qualifier(EXCHANGE_NAME_DELAY) Exchange notifyExchange,
            @Qualifier(QUEUE_NAME_DELAY) Queue notifyQueue) {
        return BindingBuilder
                .bind(notifyQueue)
                .to(notifyExchange)
                .with("delay.*")
                .noargs();
    }

}
