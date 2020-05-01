package com.example.demo.mq;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.tools.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "TestDirectQueue", durable = "true"),
        exchange = @Exchange(value = "TestDirectExchange", type = ExchangeTypes.DIRECT)))
public class HelloReceiver {

    @RabbitHandler
    public void receiverMessage(@Payload  String payload,
                               @Headers Map<String, Object> headers,
                               Channel channel) throws Exception{
        System.out.println("接受数据" + payload);

    }
}
