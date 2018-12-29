package com.sdyin.springboot.rabbitmq.rabbit.topic;

import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * topic模式消息生产者
 * @author: liuye
 * @Date: 2018/9/5 17:41
 * @Description
 */
@Component
public class TopicSender {
  @Autowired
  private AmqpTemplate rabbitTemplate;

  public void send() {
    String context = "hi, i am message all";
    System.out.println("Sender : " + context);
    this.rabbitTemplate.convertAndSend(MqConstant.EXCHANGE_TOPIC_NAME, "topic.1", context);
  }

  public void send1() {
    String context = "hi, i am message 1";
    System.out.println("Sender : " + context);
    this.rabbitTemplate.convertAndSend(MqConstant.EXCHANGE_TOPIC_NAME, "topic.message", context);
  }

  public void send2() {
    String context = "hi, i am messages 2";
    System.out.println("Sender : " + context);
    this.rabbitTemplate.convertAndSend(MqConstant.EXCHANGE_TOPIC_NAME, "topic.messages", context);
  }

}

