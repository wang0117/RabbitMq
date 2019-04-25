package com.wch.simpleMode;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wch.constant.RabbitConstant;
import com.wch.util.RabbitUtils;

/**
 * Description:   
 * @author wangchenghong 
 * @since: 2019年4月19日: 上午11:09:43
 */

public class producer {
    public static void main(String[] args) throws Exception {
        //TCP 物理连接
        Connection conn = RabbitUtils.getConnection();
        //创建通信“通道”，相当于TCP中的虚拟连接
        Channel channel = ((com.rabbitmq.client.Connection) conn).createChannel();
        //创建队列,声明并创建一个队列，如果队列已存在，则使用这个队列
        //第一个参数：队列名称
        //第二个参数：是否持久化，false对应不持久化数据，MQ停掉数据就会丢失
        //第三个参数：是否队列私有化，false则代表所有消费者都可以访问，true代表只有第一次拥有它的消费者才能一直使用，其他消费者不让访问
        //第四个参数：是否自动删除，false代表连接停掉后，不自动删除这个队列
        //其他额外的参数，null
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD,false, false,false,null);
        String message = "hello world";
        //四个参数：
        //第一个参数：exchange 交换机，暂时用不到，在后面进行发布订阅时，才会用到
        //第二个参数：routingkey 队列名称
        //第三个参数：props 额外的设置属性
        //第四个参数：body 要传递的消息字节数组
        channel.basicPublish("", RabbitConstant.QUEUE_HELLOWORLD, null, message.getBytes());
        channel.close();
        conn.close();
        System.out.println("消息发送成功!");
        
    }
}
