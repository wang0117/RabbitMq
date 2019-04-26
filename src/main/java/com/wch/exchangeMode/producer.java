package com.wch.exchangeMode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wch.constant.RabbitConstant;
import com.wch.util.RabbitUtils;

/**
 * Description:
 * 
 * @author wangchenghong
 * @since: 2019年4月25日: 下午2:33:21
 */

public class producer {

    public static void main(String[] args) throws Exception {
        Connection conn = RabbitUtils.getConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(RabbitConstant.FANOUT, "fanout");
        String message = "测试交换机";
        channel.basicPublish(RabbitConstant.FANOUT, "", null, message.getBytes());
        System.out.println("发布完成");
        channel.close();
        conn.close();
    }

}
