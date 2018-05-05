
package com.yeting.kafka.producer.demo;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.security.JaasUtils;

import java.util.Properties;

public class KafkaTopicDemo {

    public static void main(String[] args){
        // zookeeperHosts, connectionTimeoutMs, sessionTimeoutMs, isSecureKafkaCluster
        //zk: 192.168.71.133:2181
        ZkUtils zkUtils = ZkUtils.apply("111.230.224.169:2181", 30000, 30000, JaasUtils.isZkSecurityEnabled());

        // 创建topic
        // zkUtils, topic, partitionSize, replicationCount, topicConfig
        AdminUtils.createTopic(zkUtils, "topicName1", 1, 1, new Properties(), RackAwareMode.Enforced$.MODULE$);
        zkUtils.close();
    }
}