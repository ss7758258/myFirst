
package com.xz.kafka.producer.demo;

import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerThread extends ShutdownableThread implements InitializingBean {

    /**
     * Consumer properties
     */
    private Properties kafkaConsumerProperties;

    private KafkaConsumer<Integer, String> consumer;

    /**
     * 主题
     */
    private String topic;

    /**
     * 组号
     */
    private String groupId;


    public KafkaConsumerThread() {
        super("KafkaConsumerExample", false);
    }

    /**
     * 初始化方法
     */
    public void init() {
        kafkaConsumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,this.groupId);
        consumer = new KafkaConsumer<>(kafkaConsumerProperties);
    }

    public void setKafkaConsumerProperties(Properties kafkaConsumerProperties) {
        this.kafkaConsumerProperties = kafkaConsumerProperties;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(topic, "topic is null");
        Assert.notNull(groupId, "groupId is null");
        Assert.notNull(kafkaConsumerProperties, "kafkaConsumerProperties is null");
        Assert.notNull(kafkaConsumerProperties.getProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG), ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG + " is null");
       // Assert.notNull(kafkaConsumerProperties.getProperty(ConsumerConfig.GROUP_ID_CONFIG), ConsumerConfig.GROUP_ID_CONFIG + " is null");
        Assert.notNull(kafkaConsumerProperties.getProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG), ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG + " is null");
        Assert.notNull(kafkaConsumerProperties.getProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG), ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG + " is null");
    }

    @Override
    public void doWork() {
        consumer.subscribe(Collections.singletonList(this.topic));
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        for (ConsumerRecord<Integer, String> record : records) {
            System.out.println(this.groupId+" Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
           // reccount.incrementAndGet();
          //  System.out.print("Received count: "+reccount.incrementAndGet());
//
//            //处理完之后进行提交
//            consumer.commitAsync();
        }
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public boolean isInterruptible() {
        return false;
    }
}
