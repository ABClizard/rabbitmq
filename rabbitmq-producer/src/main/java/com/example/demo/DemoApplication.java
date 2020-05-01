package com.example.demo;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class
DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	//消息的序列化与反序列化由内部转换器完成，如果我们要采用其他类型的消息转换器，我们可以对其进行设置SimpleMessageListenerContainer。
//	@Bean
//	public SimpleMessageListenerContainer simpleMessageListenerContainer(){
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//		container.setMessageConverter(new Jackson2JsonMessageConverter());
//		// 默认采用下面的这种转换器
//		// container.setMessageConverter(new SimpleMessageConverter());
//		return container;
//	}
}
