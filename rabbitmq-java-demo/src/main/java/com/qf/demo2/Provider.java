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
        //1.建立连接
        Connection connection = ConnectionUtil.getConnection();
        //2.创建通道
        Channel channel = connection.createChannel();
        //3.声明交换机
        channel.exchangeDeclare("myexchange","fanout");
        //4.发送消息
        String content = "hello,rabbitmq";
        channel.basicPublish("myexchange","",null,content.getBytes());
        //5.关闭连接
        connection.close();
    }
}
