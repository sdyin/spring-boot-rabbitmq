package com.sdyin.springboot.rabbitmq.rabbit.simple;

import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
}
