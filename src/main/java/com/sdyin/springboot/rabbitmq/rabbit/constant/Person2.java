package com.sdyin.springboot.rabbitmq.rabbit.constant;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuye
 * @Date: 2018/9/8 19:45
 * @Description
 */
@Data
public class Person2 implements Serializable{

  private static final long serialVersionUID = -2853845864566122338L;

  private int id;

  private String name;

  @Override
  public String toString() {
    return "Person2{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }
}
