package com.sdyin.springboot.rabbitmq.rabbit.constant;

import lombok.Data;

/**
 * @author: liuye
 * @Date: 2018/9/8 19:45
 * @Description
 */
@Data
public class Person {

  private int id;

  private String name;

  private String nikeName;

  @Override
  public String toString() {
    return "Person{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", nikeName='" + nikeName + '\'' +
            '}';
  }
}
