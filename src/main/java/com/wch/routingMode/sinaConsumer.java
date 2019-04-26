package com.wch.routingMode;


import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.wch.constant.RabbitConstant;
import com.wch.util.RabbitUtils;

/**
 * Description:   
 * @author wangchenghong 
 * @since: 2019年4月26日: 下午3:10:20
 */

public class sinaConsumer {
    
    public static void main(String[] args) throws Exception {
        Connection conn = RabbitUtils.getConnection();
        final Channel channel = conn.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_SINA, false, false, false, null);
        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.ROUTING, "com.sina");
        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.ROUTING, "com.sina.weather");
        channel.basicQos(1);
        channel.basicConsume(RabbitConstant.QUEUE_SINA, false, new DefaultConsumer(channel){
             
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                    throws IOException {
                System.out.println("新浪收到信息："+new String(body, "UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
