package com.example.demo.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


@Slf4j
@RestController
public class RirectMessageController {

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @GetMapping(value = "/sendDirectMessage")
    public String sendDirectMessage() {
        log.info("发送消息了 send message ######################################");
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("correlationData.Id" + Math.random());
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map.toString(), correlationData);
        return "ok";
    }

    @GetMapping(value = "/sendQueueB")
    public void sendMsg() {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        log.info("--------------------------------sendMsg to queueB--------------------------------------------");
        rabbitTemplate.convertAndSend("TestDirectExchange", "DirectRoutingB", "HELLO KITTY：" + System.currentTimeMillis(), correlationId);
    }

}