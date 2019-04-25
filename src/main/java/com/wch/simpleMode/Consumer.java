package com.wch.simpleMode;
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
 * @since: 2019年4月19日: 上午11:19:10
 */

public class Consumer {
    public static void main(String[] args) throws Exception {
        //TCP 物理连接
        Connection conn = RabbitUtils.getConnection();
        //创建通信“通道”，相当于TCP中的虚拟连接
        Channel channel = conn.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD,false, false,false,null);
        //创建一个消息消费者
        //第一个参数：队列名称
        //第二个参数：代表是否自动确认收到消息，false代表手动编程来确认消息，这是MQ的推荐做法
        //第三个参数：要传入DefaultConsumer的实现类对象
        channel.basicConsume(RabbitConstant.QUEUE_HELLOWORLD, false, new Reciver(channel));
        //不关闭通道与连接 ，，持续监听。。
//      channel.close();
//      conn.close();
    }
}


class Reciver extends DefaultConsumer {
    Channel channel = null;
    //重写构造函数，Channel 通道对象需要从外层传入，在handleDelivery中要用到
    public Reciver(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
//      super.handleDelivery(consumerTag, envelope, properties, body);
        String messagebody= new String(body);
        System.out.println("消费者接收到：" + messagebody);
        //签收消息，确认消息
        //第一个参数：envelope.getDeliveryTag() 获取这个消息的TagId
        //第二个单数：false只确认签收当前的消息，设置为true的时候，则代表签收该消费者所有未签收的消息
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}