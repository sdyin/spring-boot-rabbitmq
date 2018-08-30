package com.sdyin.springboot.rabbitmq.rabbit.simple;

import com.rabbitmq.client.Channel;
import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


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
  public void receiveConfirm(String message,Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
    try {
      //手动确认消息: ack
      channel.basicAck(tag,false);
      //确认消息
      System.out.println("接收方: 接收mq消息:"+ message);
    } catch (IOException e) {
      System.out.println("接收方: receiver fail");
    }

  }

  /**
   *
   * @param message
   * @param channel
   * @param map
   */
  @RabbitListener(queues = MqConstant.QUEUE_RMM_DEMO)
  public void processMessage2(String message, Channel channel,@Headers Map<String,Object> map) {
    System.out.println(message);
    if (map.get("error")!= null){
      System.out.println("错误的消息");
      try {
        //否认消息 重试
        //channel.basicNack((Long)map.get(AmqpHeaders.DELIVERY_TAG),false,true);
        //丢弃消息
        channel.basicNack((Long)map.get(AmqpHeaders.DELIVERY_TAG),false,false);
        return;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    try {
      //确认消息
      channel.basicAck((Long)map.get(AmqpHeaders.DELIVERY_TAG),false);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



}
