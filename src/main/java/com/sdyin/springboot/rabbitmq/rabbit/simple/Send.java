package com.sdyin.springboot.rabbitmq.rabbit.simple;

import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
public class Send implements RabbitTemplate.ReturnCallback{

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
        System.out.println("HelloSender消息发送失败" + cause + correlationData.toString());
      } else {
        System.out.println("HelloSender 消息发送成功 ");
      }
    });

    rabbitTemplate.convertAndSend(MqConstant.QUEUE_SDYIN_DEMO, context);
    //Object obj = rabbitTemplate.convertSendAndReceive(MqConstant.QUEUE_SDYIN_DEMO, context);
    return "";
  }

  @Override
  public void returnedMessage(Message message, int i, String s, String s1, String s2) {
    System.out.println("sender return success" + message.toString()+"==="+i+"==="+s1+"==="+s2);

  }
}
