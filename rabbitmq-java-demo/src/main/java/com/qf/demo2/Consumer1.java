package com.qf.demo2;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/13 16:48
 */
public class Consumer1 {

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare("myqueue1",false,false,false,null);

        //进行队列和交换机的绑定
        channel.queueBind("myqueue1","myexchange","");
//        channel.exchangeBind();交换机绑定交换机

        //监听队列
        channel.basicConsume("myqueue1",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1收到的消息："+new String(body));
            }
        });

    }
}
