package com.qf.demo2;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/13 16:43
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare("myexchange","fanout");

        //发送消息
        String content = "hello,rabbitmq";
        channel.basicPublish("myexchange","",null,content.getBytes());

        connection.close();
    }
}
