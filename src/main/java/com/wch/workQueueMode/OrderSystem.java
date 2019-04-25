package com.wch.workQueueMode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wch.constant.RabbitConstant;
import com.wch.util.RabbitUtils;

/**
 * Description:
 * 
 * @author wangchenghong
 * @since: 2019年4月19日: 下午12:04:09
 */

public class OrderSystem {
    public static void main(String[] args) throws Exception {
        Connection conn = RabbitUtils.getConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_ORDER, false, false, false, null);
        
        for(int i=1;i<=50;i++){
            String message = "乘客" + i+ "1390000" + i+ "您的车票已经预定成功!";
            channel.basicPublish("", RabbitConstant.QUEUE_ORDER, null, message.getBytes());
        }
        System.out.println("发送成功!");
        channel.close();
        conn.close();
        
    }
}

