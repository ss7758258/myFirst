
package com.xz.kafka.producer.impl;

import com.xz.kafka.producer.SendAsyncCallBack;
import com.xz.kafka.producer.KafkaProducerInterface;
import com.xz.kafka.producer.exception.KafkaException;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * kafka producer实现类
 *
 */
public class KafkaProducerImpl implements KafkaProducerInterface, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerImpl.class);

    /**
     * producer参数
     */
    private Properties kafkaProducerProperties;

    /**
     * kafka底层producer
     */
    private KafkaProducer<String, String> producer;

    /**
     * 默认主题
     */
    private String topic;

    /**
     * 生产者ID
     */
    private String clientId;

    /**
     * 初始化方法
     */
    public void init() {
        kafkaProducerProperties.put(ProducerConfig.CLIENT_ID_CONFIG, this.clientId);
        //固定key和value序列化类
        kafkaProducerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(kafkaProducerProperties);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(topic, "topic is null");
        Assert.notNull(clientId, "clientId is null");
        Assert.notNull(kafkaProducerProperties, "kafkaProducerProperties is null");
        Assert.notNull(kafkaProducerProperties.getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG), ProducerConfig.BOOTSTRAP_SERVERS_CONFIG + " is null");
       // Assert.notNull(kafkaProducerProperties.getProperty(ProducerConfig.CLIENT_ID_CONFIG), ProducerConfig.CLIENT_ID_CONFIG + " is null");
    }


    public void setKafkaProducerProperties(Properties kafkaProducerProperties) {
        this.kafkaProducerProperties = kafkaProducerProperties;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Future<RecordMetadata> sendAsync(String value) throws KafkaException {
        return sendAsync(topic,null,null,value);
    }

    @Override
    public Future<RecordMetadata> sendAsync(String topic, String value) throws KafkaException {
        return sendAsync(topic,null,null,value);
    }

    @Override
    public Future<RecordMetadata> sendAsync(String topic, String key, String value) throws KafkaException {
        return sendAsync(topic,null,key,value);
    }

    @Override
    public Future<RecordMetadata> sendAsync(String topic, Integer partition, String key, String value) throws KafkaException {
        Future<RecordMetadata> future = null;
        try {
            if(topic == null){//主题为空使用默认主题
                topic = this.topic;
            }
            future = producer.send(new ProducerRecord<>(topic, partition, key, value),
                    new SendAsyncCallBack(key, value));
            if(future!= null && future.toString().contains("KafkaProducer$FutureFailure")){
                //发送失败
                logger.error("sendAsync msg exception: "+future.toString());
                throw new KafkaException("sendAsync msg exception: " + future.toString());
            }
        } catch (KafkaException e) {
            logger.error("sendAsync msg exception: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("sendAsync msg exception: " + e.getMessage());
            throw new KafkaException("sendAsync msg exception: " + e);
        }
        return future;
    }

    @Override
    public Future<RecordMetadata> sendAsync(String topic, Integer partition, String key, String value, Callback callback) throws KafkaException {
        Future<RecordMetadata> future = null;
        try {
            if(topic == null){//主题为空使用默认主题
                topic = this.topic;
            }
            future = producer.send(new ProducerRecord<>(topic, partition, key, value), callback);
        } catch (Exception e) {
            logger.error("sendAsync msg exception: " + e.getMessage());
            throw new KafkaException("sendAsync msg exception: " + e);
        }
        return future;
    }

    @Override
    public RecordMetadata sendSync(String value) throws KafkaException {
        return sendSync(topic, null, null, value);
    }

    @Override
    public RecordMetadata sendSync(String topic, String value) throws KafkaException {
        return sendSync(topic, null, null, value);
    }

    @Override
    public RecordMetadata sendSync(String topic, String key, String value) throws KafkaException {
        return sendSync(topic, null, key, value);
    }

    @Override
    public RecordMetadata sendSync(String topic, Integer partition, String key, String value) throws KafkaException {
        RecordMetadata result = null;
        try {
            if(topic == null){//主题为空使用默认主题
                topic = this.topic;
            }
            result = producer.send(new ProducerRecord<>(topic, partition, key, value)).get();
        } catch (InterruptedException e) {
            logger.error("sendSync msg exception: " + e.getMessage());
            throw new KafkaException("sendSync msg exception: " + e);
        } catch (ExecutionException e) {
            logger.error("sendSync msg exception: " + e.getMessage());
            throw new KafkaException("sendSync msg exception: " + e);
        }
        return result;
    }
}