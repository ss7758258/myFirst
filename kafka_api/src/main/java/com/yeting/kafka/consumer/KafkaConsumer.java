
package com.yeting.kafka.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KafkaConsumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"kafka_consumer.xml"});
        context.start();
        KafkaConsumerThread consumerThread = (KafkaConsumerThread) context.getBean("kafkaConsumerThread");
        consumerThread.start();
    }
}