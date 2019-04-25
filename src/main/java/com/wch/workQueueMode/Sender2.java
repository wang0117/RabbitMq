package com.wch.workQueueMode;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.wch.constant.RabbitConstant;
import com.wch.util.RabbitUtils;

/**
 * Description:   
 * @author wangchenghong 
 * @since: 2019年4月19日: 下午3:18:35
 */

public class Sender2 {
    public static void main(String[] args) throws IOException {
        Connection conn = RabbitUtils.getConnection();
        final Channel channel = conn.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_ORDER, false, false, false, null);
        //如果不写basicQos(1)，则MQ自动会将所有请求平均发送给所有消费者
        //basicQos,MQ不再对消费者一次发送多个请求，而是消费者处理完一个消息后（确认后），在从队列中获取一个新的消息
        channel.basicQos(1);//处理完一个消息取一个消息 ，如果设置成10，则处理完10个，再取10个
        channel.basicConsume(RabbitConstant.QUEUE_ORDER, false, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                    throws IOException {
//              super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println("SMSSender2-短信发送成功：" + message);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
            
        });
    }
}
