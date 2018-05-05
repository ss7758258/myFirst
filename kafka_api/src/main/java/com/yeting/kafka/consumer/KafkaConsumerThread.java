
package com.yeting.kafka.consumer;

import com.yeting.framework.utils.DateUtil;
import com.yeting.framework.utils.StringUtil;
import com.yeting.web.service.consumerkafka.ConsumerKafka;
import com.yeting.web.service.redis.RedisService;
import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerThread extends ShutdownableThread implements InitializingBean {

    @Autowired
    private ConsumerKafka consumerKafka;
    @Autowired
    private RedisService redisService;

    /**
     * Consumer properties
     */
    private Properties kafkaConsumerProperties;

    private KafkaConsumer<String, String> consumer;

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
        kafkaConsumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
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
        ConsumerRecords<String, String> records = consumer.poll(1000);
        for (ConsumerRecord<String, String> record : records) {
            System.out.println(this.groupId + " Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
            this.cumsumerSwitch(this.topic, record.value());
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


    /**
     * kafka消费
     *
     * @param topicStr 主题
     *                 操作符目前不传 operationStr  操作符，insert、update、delete
     * @param valueStr 操作语句
     */
    public void cumsumerSwitch(String topicStr, String valueStr) {
        if (StringUtil.isEmpty(valueStr)) {
            return;
        }
        System.out.println(DateUtil.getDatetime()+"-->TOPIC:"+topicStr+",VALUE:"+valueStr);
        try {
            switch (topicStr) {
                case "sentCommentTopic": {
                    consumerKafka.commentTopic(valueStr);
                    break;
                }
                case "weixinTopic": {
                    consumerKafka.saveWeixinTopic(valueStr);
                    break;
                }
                case "artShareCountTopic": {
                    consumerKafka.artShareCountTopic(valueStr);
                    break;
                }
                case "artCollectCountTopic": {
                    consumerKafka.artCollectCountTopic(valueStr);
                    break;
                }
                case "collectTopic": {
                    consumerKafka.collectTopic(valueStr);
                    break;
                }
                case "artLikeCountTopic": {
                    consumerKafka.artLikeCountTopic(valueStr);
                    break;
                }
                case "userArtLikeTopic": {
                    consumerKafka.userArtLikeTopic(valueStr);
                    break;
                }
                case "commentLikeCountTopic": {
                    consumerKafka.commentLikeCountTopic(valueStr);
                    break;
                }
                case "userCommentLikeTopic": {
                    consumerKafka.userCommentLikeTopic(valueStr);
                    break;
                }
                case "artUnLikeCountTopic": {
                    consumerKafka.artUnLikeCountTopic(valueStr);
                    break;
                }
                case "userArtUnLikeTopic": {
                    consumerKafka.userArtUnLikeTopic(valueStr);
                    break;
                }
                case "commentUnLikeCountTopic": {
                    consumerKafka.commentUnLikeCountTopic(valueStr);
                    break;
                }
                case "userCommentUnLikeTopic": {
                    consumerKafka.userCommentUnLikeTopic(valueStr);
                    break;
                }
                case "artUnCollectCountTopic": {
                    consumerKafka.artUnCollectCountTopic(valueStr);
                    break;
                }
                case "uncollectTopic": {
                    consumerKafka.uncollectTopic(valueStr);
                    break;
                }
                case "feedbackTopic": {
                    consumerKafka.feedbackTopic(valueStr);
                    break;
                }
                case "deleteCollectTopic": {
                    consumerKafka.deleteCollectTopic(valueStr);
                    break;
                }
                case "setCommentTopic": {
                    consumerKafka.updateSetCommentTopic(valueStr);
                    break;
                }
                case "deleteCommentTopic": {
                    consumerKafka.deleteCommentTopic(valueStr);
                    break;
                }
                case "redisMainTopic": {
                    consumerKafka.updateRedisMainTopic(valueStr);
                    break;
                }
                case "redisDetailTopic": {
                    consumerKafka.updateRedisDetailTopic(valueStr);
                    break;
                }
                case "redisMainReadTopic": {
                    consumerKafka.updateRedisMainReadTopic(valueStr);
                    break;
                }
                case "redisCommentTopic": {
                    consumerKafka.updateRedisCommentTopic(valueStr);
                    break;
                }
                case "updateMainReadCountTopic": {
                    consumerKafka.updateMainReadCountTopic(valueStr);
                    break;
                }
                case "redisSaveCommentTopic": {
                    consumerKafka.updateRedisSaveCommentTopic(valueStr);
                    break;
                }
                case "redisArtShareTopic": {
                    consumerKafka.updateRedisArtShareTopic(valueStr);
                    break;
                }
                case "redisArtCollectTopic": {
                    consumerKafka.updateRedisArtCollectTopic(valueStr);
                    break;
                }
                case "redisArtLikeTopic": {
                    consumerKafka.updateRedisArtLikeTopic(valueStr);
                    break;
                }
                case "redisMainLikeTopic": {
                    consumerKafka.updateRedisMainLikeTopic(valueStr);
                    break;
                }
                case "redisCommentLikeTopic": {
                    consumerKafka.updateRedisCommentLikeTopic(valueStr);
                    break;
                }
                case "redisArtUnLikeTopic": {
                    consumerKafka.updateRedisArtUnLikeTopic(valueStr);
                    break;
                }
                case "redisMainUnLikeTopic": {
                    consumerKafka.updateRedisMainUnLikeTopic(valueStr);
                    break;
                }
                case "redisCommentUnLikeTopic": {
                    consumerKafka.updateRedisCommentUnLikeTopic(valueStr);
                    break;
                }
                case "redisArtUnCollectTopic": {
                    consumerKafka.updateRedisArtUnCollectTopic(valueStr);
                    break;
                }
                case "redisCommentDeleteTopic": {
                    consumerKafka.updateRedisCommentDeleteTopic(valueStr);
                    break;
                }
                default: {
                    break;
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
