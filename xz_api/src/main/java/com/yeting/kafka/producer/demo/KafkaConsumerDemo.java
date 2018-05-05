
package com.yeting.kafka.producer.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KafkaConsumerDemo {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"kafka_consumer.xml"});
        context.start();

        KafkaConsumerThread consumerThread1 = (KafkaConsumerThread) context.getBean("kafkaConsumerThread1");
        consumerThread1.start();

        KafkaConsumerThread consumerThread2 = (KafkaConsumerThread) context.getBean("kafkaConsumerThread2");
        consumerThread2.start();
    }
}