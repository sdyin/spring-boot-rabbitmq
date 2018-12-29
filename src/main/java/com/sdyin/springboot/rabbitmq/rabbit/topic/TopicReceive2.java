package com.sdyin.springboot.rabbitmq.rabbit.topic;

import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: liuye
 * @Date: 2018/9/5 17:49
 * @Description
 */
@Component
@RabbitListener(queues = MqConstant.QUEUE_TOPIC_DEMO_B)
public class TopicReceive2 {

  @RabbitHandler
  public void process(String message) {
    System.out.println("Topic Receiver2  : " + message);
  }
}
