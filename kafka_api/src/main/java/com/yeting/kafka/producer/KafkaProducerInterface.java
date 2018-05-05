package com.yeting.kafka.producer;

import com.yeting.kafka.producer.exception.KafkaException;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

/**
 *
 *  kafka producer接口
 *  KEY和VALUE均为String
 *  建议用异步接口，系统吞吐量更大，发送速度更快
 */
public interface KafkaProducerInterface {

    /**
     * 异步发送消息
     * 其他参数将使用默认值(默认主题）
     * @param value value 不能为空
     * @return
     */
    public Future<RecordMetadata> sendAsync(String value) throws KafkaException;

    /**
     * 异步发送消息
     * 其他参数将使用默认值
     * @param topic 主题 如果为空则使用默认主题
     * @param value value 不能为空
     * @return
     */
    public Future<RecordMetadata> sendAsync(String topic, String value) throws KafkaException;

    /**
     * 异步发送消息
     * 其他参数将使用默认值
     * @param topic 主题 如果为空则使用默认主题
     * @param key key
     * @param value value 不能为空
     * @return
     */
    public Future<RecordMetadata> sendAsync(String topic, String key, String value) throws KafkaException;

    /**
     * 异步发送消息
     * 其他参数将使用默认值
     * @param topic 主题 如果为空则使用默认主题
     * @param partition 分区
     * @param key key
     * @param value value 不能为空
     * @return
     */
    public Future<RecordMetadata> sendAsync(String topic, Integer partition, String key, String value) throws KafkaException;

    /**
     * 异步发送消息
     * @param topic 主题 如果为空则使用默认主题
     * @param partition 分区
     * @param key key
     * @param value value 不能为空
     * @param callback 回调函数
     * @return
     */
    public Future<RecordMetadata> sendAsync(String topic, Integer partition, String key, String value, Callback callback) throws KafkaException;



    /**
     * 同步发送消息
     * 其他参数将使用默认值，使用默认主题
     * @param value value 不能为空
     * @return
     */
    public RecordMetadata sendSync(String value) throws KafkaException;

    /**
     * 同步发送消息
     * 其他参数将使用默认值
     * @param topic 主题 如果为空则使用默认主题
     * @param value value 不能为空
     * @return
     */
    public RecordMetadata sendSync(String topic, String value) throws KafkaException;

    /**
     * 同步发送消息
     * 其他参数将使用默认值
     * @param topic 主题 如果为空则使用默认主题
     * @param key key
     * @param value value 不能为空
     * @return
     */
    public RecordMetadata sendSync(String topic, String key, String value) throws KafkaException;

    /**
     * 同步发送消息
     * 其他参数将使用默认值
     * @param topic 主题 如果为空则使用默认主题
     * @param partition 分区
     * @param key key
     * @param value value 不能为空
     * @return
     */
    public RecordMetadata sendSync(String topic, Integer partition, String key, String value) throws KafkaException;

}