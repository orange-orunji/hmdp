//package com.hmdp.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMqConfig {
//
//    private static final String EXCHANGE_NAME = "order_exchange";
//    private static final String QUEUE_NAME = "order_queue";
//    private static final String ROUTING_KEY = "order_routing_key";
//
////    创建队列
//    @Bean
//    public Queue rabbitQueue(){
//        return QueueBuilder.durable(QUEUE_NAME).build();
//    }
//
////    创建交换机
//    @Bean
//    public Exchange rabbitExchange(){
//        return ExchangeBuilder.directExchange(EXCHANGE_NAME).durable(true).build();
//    }
//
////    绑定队列和交换机
//    @Bean
//    public Binding binding(){
//        return BindingBuilder.bind(rabbitQueue()).to(rabbitExchange()).with(ROUTING_KEY).noargs();
//    }
//
////    解析器
//    @Bean
//    public MessageConverter messageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }
//}
