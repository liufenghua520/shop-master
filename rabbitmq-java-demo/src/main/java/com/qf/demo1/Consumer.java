package com.qf.demo1;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/13 15:58
 */
public class Consumer {

    private static ExecutorService executorService = Executors.newFixedThreadPool(20);

    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            Channel channel = connection.createChannel();

            channel.queueDeclare("myqueue",false,false,false,null);

            channel.basicConsume("myqueue",true,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                    executorService.submit(() -> {
                        try {
                            System.out.println("消费者接受到的消息："+new String(body,"utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });


                    /*executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("消费者接受到的消息："+new String(body,"utf-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });*/
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
