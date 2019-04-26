package com.wch.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Description:   
 * @author wangchenghong 
 * @since: 2019年4月19日: 下午12:15:45
 */

public class RabbitUtils {
    
    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    static{
        connectionFactory.setHost("192.168.100.183");
        connectionFactory.setPort(5672);  //15672是RabbitMQ的默认通信端口号
        connectionFactory.setUsername("wang");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/test");
    }
    
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = connectionFactory.newConnection();
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
