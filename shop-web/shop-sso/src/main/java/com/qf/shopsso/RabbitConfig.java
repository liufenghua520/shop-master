package com.qf.shopsso;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/18 22:00
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue(){
        return new Queue("email_queue");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("email_exchange");
    }

    @Bean
    public Binding getBinding(Queue queue,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

}
