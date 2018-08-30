package com.sdyin.springboot.rabbitmq.rabbit.simple;

import com.rabbitmq.client.Channel;
import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author: liuye
 * @Date: 2018/8/29 20:08
 * @Description
 */
@Slf4j
@Component
public class Receive {

  @RabbitListener(queues = MqConstant.QUEUE_NAME)
  public void receive(String message){
    System.out.println("接收mq消息:"+ message);
  }

  /**
   * 消息确认
   * @param channel
   * @param message
   * @throws IOException
   */
  @RabbitHandler
  @RabbitListener(queues = MqConstant.QUEUE_SDYIN_DEMO)
  public void receiveConfirm(String message,Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
    try {
      channel.basicAck(tag,false);
      //确认消息
      System.out.println("接收方: 接收mq消息:"+ message);
    } catch (IOException e) {
      //丢弃消息
      //channel.basicNack(tag,false,false);
      System.out.println("接收方: receiver fail");
    }

  }

}
