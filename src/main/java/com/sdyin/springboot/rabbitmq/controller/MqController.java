package com.sdyin.springboot.rabbitmq.controller;

import com.sdyin.springboot.rabbitmq.rabbit.simple.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liuye
 * @Date: 2018/8/29 20:49
 * @Description
 */
@RestController
@RequestMapping("/mq")
public class MqController {

  @Autowired
  private Send send;

  @GetMapping("/sendMq")
  public void sendMq(){
    send.send();
    System.out.println("发送完成");
  }
}
