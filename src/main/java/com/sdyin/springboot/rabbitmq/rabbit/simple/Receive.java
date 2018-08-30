package com.sdyin.springboot.rabbitmq.rabbit.simple;

import com.rabbitmq.client.Channel;
import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
  @RabbitListener(queues = MqConstant.QUEUE_SDYIN_DEMO)
  public void receiveConfirm(Channel channel, Message message) throws IOException {
    try {
      channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
      System.out.println("接收mq消息:"+ message);
    } catch (IOException e) {
      //丢弃消息
      //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
      System.out.println("receiver fail");
    }

  }
}
