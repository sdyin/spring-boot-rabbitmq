package com.sdyin.springboot.rabbitmq.rabbit.constant;

/**
 * @author: liuye
 * @Date: 2018/8/29 20:13
 * @Description
 */
public class MqConstant {

  public static final String QUEUE_NAME = "message";

  public static final String QUEUE_DEMO = "demo.queue";

  public static final String QUEUE_SDYIN_DEMO = "com.sdyin.demo";

  public static final String QUEUE_RMM_DEMO = "com.rmm.demo";

  public static final String QUEUE_TOPIC_DEMO_A = "com.sdyin.demo.a.topic.queue";

  public static final String QUEUE_TOPIC_DEMO_B = "com.sdyin.demo.b.topic.queue";

  public static final String QUEUE_TOPIC_DEMO_C = "com.sdyin.demo.c.topic.queue";


  public static final String EXCHANGE_TOPIC_NAME = "sdyin.topicExchange";

  //延迟队列1
  public static final String DELAY_QUEUE1 = "delay_queue1";

  //延迟队列2
  public static final String DELAY_QUEUE2 = "delay_queue2";

  //延迟队列交换机(普通交换机，这里当作死信交换机使用)
  public static final String DELAY_EXCHANGE = "delay";

  /**
   * 路由key
   */
  public static final String ROUTING_KEY1 = "delay";

  public static final String ROUTING_KEY2 = "delay_key";

}
