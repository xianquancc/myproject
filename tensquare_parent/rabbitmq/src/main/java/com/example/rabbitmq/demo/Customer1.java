package com.example.rabbitmq.demo;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "xianquan")
public class Customer1 {
    @RabbitHandler
    public void showMessage(String message) {
        System.out.println("xianquan接收到消息：" + message);
    }
}