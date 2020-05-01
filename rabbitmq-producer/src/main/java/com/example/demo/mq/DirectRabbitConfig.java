package com.example.demo.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 Queue:消息的载体,每个消息都会被投到一个或多个队列。
 Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 Producer:消息生产者,就是投递消息的程序.
 Consumer:消息消费者,就是接受消息的程序.
 Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 设置参数
     *  x-message-ttl 强制设置队列消息的最大生存时间 （所有消息）
     *  x-expires 队列空闲一段时间删除其对此队列
     *  x-max-length 队列的最大长度，如果队列大于这个值删除最老的消息（可能移到死信队列）
     *  x-dead-letter-exchange  死信交换器
     *  x-dead-letter-routing-key   死信交换器路由的键
     *  x-max-priority  设置队列的最大优先级
     *  x-ha-policy 创建HA队列时，指定模式为nodes
     *  x-ha-nodes HA所在集群的节点是一个数组
     */
    //队列 起名：TestDirectQueue
    @Bean
    public Queue TestDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue("TestDirectQueue",true);
    }
    @Bean
    public Queue queueB() {
        return new Queue("queueB",true);
    }

    //Direct交换机 起名：TestDirectExchange
    //设置交换机类型
    //     DirectExchange:按照routingkey分发到指定队列
    @Bean
    DirectExchange TestDirectExchange() {
        return new DirectExchange("TestDirectExchange",true,false);
    }



    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    //exchange 通过routingkey 绑定queue,
    //
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(queueB()).to(TestDirectExchange()).with("DirectRoutingB");
    }

    //一个交换机可以绑定多个消息队列，也就是消息通过一个交换机，可以分发到不同的队列当中去。
    @Bean
    Binding bindingB() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
    }


    @Bean
    public Queue queueConfirm() {
        return new Queue("queueConfirm",true);
    }
    @Bean
    TopicExchange ConfirmTopicExchange() {
        //声明了一个Topic类型的交换机，durable是持久化（重启rabbitmq这个交换机不会被自动删除）
        return ExchangeBuilder.topicExchange("ConfirmDirectExchange").durable(true).build();
    }
    @Bean
    Binding bindingConfirm() {
        return BindingBuilder.bind(queueConfirm()).to(ConfirmTopicExchange()).with("confirm.save");
    }

}