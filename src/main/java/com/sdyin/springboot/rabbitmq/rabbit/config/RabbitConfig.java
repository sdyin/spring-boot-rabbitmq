package com.sdyin.springboot.rabbitmq.rabbit.config;

import com.sdyin.springboot.rabbitmq.rabbit.constant.MqConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
  public Queue QueueDemo(){
    //第二个参数: durable: true 消息持久化
    return new Queue(MqConstant.QUEUE_DEMO,true);
  }

  @Bean
  public Queue delayQueue1(){
    //设置参数
    Map<String, Object> map = new HashMap<>();
    //消息过期后，消息要进入的交换机，这里配置的是delay，也就是死信交换机
    map.put("x-dead-letter-exchange", MqConstant.DELAY_EXCHANGE);
    //配置消息过期后，进入死信交换机的routing-key,跟发送消息的routing-key一个道理，根据这个key将消息放入不同的队列
    map.put("x-dead-letter-routing-key", MqConstant.ROUTING_KEY2);
    return new Queue(MqConstant.DELAY_QUEUE1, true, false, false, map);
  }

  @Bean
  public Queue delayQueue2(){
    return new Queue(MqConstant.DELAY_QUEUE2, true);
  }

  @Bean
  public Queue fanoutQueueB() {
    return new Queue(MqConstant.QUEUE_SDYIN_DEMO);
  }

  @Bean
  public Queue fanoutQueueC(){
    return new Queue(MqConstant.QUEUE_RMM_DEMO);
  }

  @Bean
  public Queue topicQueueA(){
    return new Queue(MqConstant.QUEUE_TOPIC_DEMO_A);
  }

  @Bean
  public Queue topicQueueB(){
    return new Queue(MqConstant.QUEUE_TOPIC_DEMO_B);
  }

  @Bean
  public Queue topicQueueC(){
    return new Queue(MqConstant.QUEUE_TOPIC_DEMO_C);
  }
  /**
   * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息
   * 绑定了这个交换机的所有队列都收到这个消息。
   * @return
   */
  @Bean
  FanoutExchange fanoutExchange() {
    return new FanoutExchange("sdyin.fanoutExchange");
  }

  /**
   * 普通直连交换机(此交换机用于延迟队列使用)
   *  FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
   *  HeadersExchange ：通过添加属性key-value匹配
   *  DirectExchange:按照routingkey分发到指定队列
   *  TopicExchange:多关键字匹配
   * @return
   */
  @Bean
  DirectExchange delayDirectExchange(){
    return new DirectExchange(MqConstant.DELAY_EXCHANGE , true, false);
  }

  /**
   * 队列绑定交换机
   * @param fanoutQueueB
   * @param fanoutExchange
   * @return
   */
  @Bean
  Binding bindingExchangeA(Queue fanoutQueueB, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(fanoutQueueB).to(fanoutExchange);
  }

  @Bean
  Binding bindingExchangeB(Queue fanoutQueueC, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(fanoutQueueC).to(fanoutExchange);
  }

  @Bean
  Binding bindingDelayDirectExchangeQueue1(Queue delayQueue1, DirectExchange delayDirectExchange){
    return BindingBuilder.bind(delayQueue1).to(delayDirectExchange).with(MqConstant.ROUTING_KEY1);
  }

  @Bean
  Binding bindingDelayDirectExchangeQueue2(Queue delayQueue2, DirectExchange delayDirectExchange){
    return BindingBuilder.bind(delayQueue2).to(delayDirectExchange).with(MqConstant.ROUTING_KEY2);
  }

  /**
   * topic模式交换机
   * @return
   */
  @Bean
  public TopicExchange topicExchange(){
    return new TopicExchange(MqConstant.EXCHANGE_TOPIC_NAME);
  }

  /**
   * 队列绑定交换机 并关联ROUTINGKEY
   * @param topicQueueA
   * @param topicExchange
   * @return
   */
  @Bean
  Binding bindingTopicExchangeA(Queue topicQueueA, TopicExchange topicExchange) {
    return BindingBuilder.bind(topicQueueA).to(topicExchange).with("topic.#");
  }

  @Bean
  Binding bindingTopicExchangeB(Queue topicQueueB, TopicExchange topicExchange) {
    return BindingBuilder.bind(topicQueueB).to(topicExchange).with("topic.b.#");
  }

  @Bean
  Binding bindingTopicExchangeC(Queue topicQueueC, TopicExchange topicExchange) {
    return BindingBuilder.bind(topicQueueC).to(topicExchange).with("topic.c");
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


  /**
   * 手动新增转换 不然不能发送实体类
   * @param connectionFactory
   * @return
   */
  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(new Jackson2JsonMessageConverter());
    return template;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(new Jackson2JsonMessageConverter());
    return factory;
  }


}
