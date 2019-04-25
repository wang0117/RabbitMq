package com.wch.publishMode;


import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wch.constant.RabbitConstant;
import com.wch.util.RabbitUtils;

/**
 * Description:
 * 
 * @author wangchenghong
 * @since: 2019年4月19日: 下午4:38:22
 */

public class producer {
    public static void main(String[] args) throws Exception {
        Connection conn = RabbitUtils.getConnection();
        Channel channel = conn.createChannel();
        String message = new Scanner(System.in).next();
        channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER, "", null, message.getBytes());
        System.out.println("发布成功");
        channel.close();
        conn.close();
    }
}
