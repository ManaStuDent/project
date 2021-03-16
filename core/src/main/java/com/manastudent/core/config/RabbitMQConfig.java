package com.manastudent.core.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

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

}
