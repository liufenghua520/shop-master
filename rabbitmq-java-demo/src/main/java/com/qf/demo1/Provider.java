package com.qf.demo1;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/13 15:32
 */
public class Provider {

    public static void main(String[] args) throws IOException {

        //1.连接RabbitMQ
        Connection connection = ConnectionUtil.getConnection();

        //2.获取Channel对象
        Channel channel = connection.createChannel();

        //3.创建队列(声明队列)
        channel.queueDeclare("myqueue",false,false,false,null);

        //4.发送消息到指定队列
        String content = "hello,rabbitmq!";

        for (int i = 0; i < 10; i++) {
            String c = content+(i+1);
            channel.basicPublish("","myqueue",null,c.getBytes());
        }

        //5.断开连接
        connection.close();
    }
}
