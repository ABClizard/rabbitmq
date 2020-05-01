package com.example.demo.mq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "queueB", durable = "true"),
        exchange = @Exchange(value = "TestDirectExchange")))
public class QueueBReceiver {

    @RabbitHandler
    public void process(String content) {
        log.info("接收处理队列B当中的消息： " + content);
    }
}
