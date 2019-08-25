package com.qf.shopgoodsservice;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/13 17:11
 */
@Configuration
public class RabbitMqComfig {
    @Bean(name = "squeue")
    public Queue getQueue1(){
        return new Queue("search_queue");
    }

    @Bean(name = "iqueue")
    public Queue getQueue2(){
        return new Queue("item_queue");
    }

    @Bean(name = "gexchange")
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("goods_exchange");
    }
    /**
     * 进行队列与交换机的绑定
     */
    @Bean
    public Binding getBinding1(Queue squeue,FanoutExchange gexchange){
        return BindingBuilder.bind(squeue).to(gexchange);
    }
    @Bean
    public Binding getBinding2(Queue iqueue,FanoutExchange gexchange){
        return BindingBuilder.bind(iqueue).to(gexchange);
    }
}
