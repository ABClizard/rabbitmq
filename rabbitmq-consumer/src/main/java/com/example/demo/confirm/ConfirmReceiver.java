package com.example.demo.confirm;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "queueConfirm", durable = "true"),
                exchange = @Exchange(value = "ConfirmDirectExchange", type = "topic",
                        durable = "true", ignoreDeclarationExceptions = "false"),
                key = "ConfirmRouting"
        )
)
@Slf4j
@Component
public class ConfirmReceiver {

    //
    @RabbitHandler
    public void receiverMsg( byte[] message, Channel channel) throws IOException {
        try {
            Thread.sleep(1);
            log.info("message receive##############这里用###################################:{}");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        long deliveryTag=111L;
        log.info("message receiver:{}", message.toString());

        //手工ack
        channel.basicAck(deliveryTag,true );
        log.info("deliveryTag:{}", deliveryTag);
    }
}
