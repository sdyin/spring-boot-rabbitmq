package com.sdyin.springboot.rabbitmq.rabbit.config;

import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: liuye
 * @Date: 2018/8/29 19:36
 * @Description
 */
@Configuration
public class RabbitConfig {

  /**
   * 创建消息队列
   * @return
   */
  @Bean
  public Queue Queue() {
    return new Queue(MqConstant.QUEUE_NAME);
  }

  @Bean
  public Queue QueueB() {
    return new Queue(MqConstant.QUEUE_SDYIN_DEMO);
  }

  @Bean
  public Queue QueueC(){
    return new Queue(MqConstant.QUEUE_RMM_DEMO);
  }

  /**
   * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息
   * 绑定了这个交换机的所有队列都收到这个消息。
   * @return
   */
  @Bean
  FanoutExchange fanoutExchange() {
    return new FanoutExchange("sdyinExchange");
  }

  /**
   * 队列绑定交换机
   * @param QueueB
   * @param fanoutExchange
   * @return
   */
  @Bean
  Binding bindingExchangeA(Queue QueueB, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(QueueB).to(fanoutExchange);
  }

  @Bean
  Binding bindingExchangeB(Queue QueueC, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(QueueC).to(fanoutExchange);
  }

  //配置文件配置开启ack 或者 代码注入配置
  /*@Bean
  public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(new Jackson2JsonMessageConverter());
    //开启手动 ack
    factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
    return factory;
  }*/



}
