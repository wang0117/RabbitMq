package com.wch.topicMode;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wch.constant.RabbitConstant;
import com.wch.util.RabbitUtils;

/**
 * Description:   
 * @author wangchenghong 
 * @since: 2019年4月25日: 下午2:33:21
 */

public class producer {
    
    public static void main(String[] args) throws Exception {
        Map<String,String> infoMap = new LinkedHashMap<String, String>();
        infoMap.put("com.baidu", "百度发布");
        infoMap.put("com.baidu.info", "百度发布信息");
        infoMap.put("com.baidu.weather", "百度发布天气信息");
        infoMap.put("com.sina", "新浪发布");
        infoMap.put("com.sina.info", "新浪发布信息");
        infoMap.put("com.sina.weather", "新浪发布天气信息");
        Connection conn = RabbitUtils.getConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(RabbitConstant.TOPIC, "topic", true, false, false, null);
        Iterator<Map.Entry<String, String>> it = infoMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> info = (Entry<String, String>) it.next();
            channel.basicPublish(RabbitConstant.TOPIC, info.getKey(), null, info.getValue().getBytes());
        }
        System.out.println("发布完成");
        channel.close();
        conn.close();
    }

}
