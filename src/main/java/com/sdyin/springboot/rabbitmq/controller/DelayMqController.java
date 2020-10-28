package com.sdyin.springboot.rabbitmq.controller;

import com.rabbitmq.client.ConnectionFactory;
import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @Description 延迟队列示例
 * @Author liuye
 * @Date 2020/10/28 14:26
 **/
@RestController
@RequestMapping("/delay")
public class DelayMqController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送延迟消息
     */
    @GetMapping("/sendDelayMq")
    public void sendMq() {
        String msg = "test delay mq";
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("6000");
        messageProperties.setCorrelationId(UUID.randomUUID().toString());
        Message message = new Message(msg.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("delay","delay", message);
        System.out.println("发送完成,当前时间: " + LocalDateTime.now());
    }


    @RabbitListener(queues = MqConstant.DELAY_QUEUE2)
    public void receive(Message message) {
        System.out.println("delay_queue2 收到消息 : " + new String(message.getBody()));
        System.out.println("[当前时间] time: "+ LocalDateTime.now());
    }




}
