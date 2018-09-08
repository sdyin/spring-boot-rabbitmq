package com.sdyin.springboot.rabbitmq.rabbit.simple;

import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import com.sdyin.springboot.rabbitmq.rabbit.constant.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: liuye
 * @Date: 2018/8/29 20:08
 * @Description
 */
@Slf4j
@Component
public class Send implements RabbitTemplate.ReturnCallback,RabbitTemplate.ConfirmCallback{

  @Autowired
  private AmqpTemplate amqpTemplate;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  /**
   * amqpTemplate
   */
  public void send(){
    String context = "简单消息发送";
    log.info("简单消息发送时间："+new Date());
    amqpTemplate.convertAndSend(MqConstant.QUEUE_NAME, context);
    System.out.println("发送完成");
  }

  public void sendTest(){
    Person person = new Person();
    person.setId(1);
    person.setName("lisa");
    System.out.println("测试发送实体类MQ");
    amqpTemplate.convertAndSend(MqConstant.QUEUE_DEMO,person);
    //rabbitTemplate.convertAndSend(MqConstant.QUEUE_DEMO,person);
  }

  /**
   * rabbitTemplate
   * @return
   */
  public String send2(){
    String context = "简单消息发送(确认机制)";
    log.info("简单消息(确认机制)发送时间："+ new Date());
    rabbitTemplate.setReturnCallback(this);

    rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
      if (!ack) {
        System.out.println("发送方确认:消息发送失败" + cause + correlationData.toString());
      } else {
        System.out.println("发送方确认:消息发送成功 ");
      }
    });
    rabbitTemplate.convertAndSend(MqConstant.QUEUE_SDYIN_DEMO, context);
    //Object obj = rabbitTemplate.convertSendAndReceive(MqConstant.QUEUE_SDYIN_DEMO, context);
    return "";
  }

  /**
   * 消息返回
   * @param message
   * @param replyCode
   * @param replyText
   * @param exchange
   * @param routingKey
   */
  @Override
  public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
    System.out.println("消息主体 message : "+message);
    System.out.println("消息主体 message : "+replyCode);
    System.out.println("描述："+replyText);
    System.out.println("消息使用的交换器 exchange : "+exchange);
    System.out.println("消息使用的路由键 routing : "+routingKey);
  }


  /**
   * 消息发送确认
   * @param correlationData
   * @param ack
   * @param cause
   */
  @Override
  public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    System.out.println("消息唯一标识："+correlationData);
    System.out.println("确认结果："+ack);
    System.out.println("失败原因："+cause);
  }
}
