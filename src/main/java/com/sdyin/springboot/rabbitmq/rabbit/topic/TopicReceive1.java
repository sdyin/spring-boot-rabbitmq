package com.sdyin.springboot.rabbitmq.rabbit.topic;

import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * topic 模式消费者
 * @author: liuye
 * @Date: 2018/9/5 17:45
 * @Description
 */
@Component
@RabbitListener(queues = MqConstant.QUEUE_TOPIC_DEMO_A)
public class TopicReceive1 {

  @RabbitHandler
  public void process(String message) {
    System.out.println("Topic Receiver1  : " + message);
  }
}
