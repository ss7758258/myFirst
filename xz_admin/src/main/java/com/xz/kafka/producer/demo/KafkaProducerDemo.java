
package com.xz.kafka.producer.demo;

import com.xz.kafka.producer.KafkaProducerInterface;
import com.xz.kafka.producer.exception.KafkaException;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KafkaProducerDemo {
    public static final String TOPIC = "mykafka";

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"kafka_producer.xml"});
        context.start();

        KafkaProducerInterface kafkaProducerImpl1 = (KafkaProducerInterface) context.getBean("kafkaProducerImpl1");

        KafkaProducerInterface kafkaProducerImpl2 = (KafkaProducerInterface) context.getBean("kafkaProducerImpl2");
        try{
            kafkaProducerImpl1.sendAsync("testMessage叶东林1");
            kafkaProducerImpl1.sendAsync(TOPIC,"testMessage叶东林1");
            kafkaProducerImpl1.sendAsync(null,"key1","testMessage叶东林2");
            kafkaProducerImpl1.sendAsync(TOPIC,0,"key2","testMessage叶东林3");
            kafkaProducerImpl1.sendAsync(TOPIC, 0, "key2", "testMessage叶东林4", new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(recordMetadata != null){
                        System.out.println("send success:"+recordMetadata.partition()+",topic:"+recordMetadata.topic()+recordMetadata.toString());
                    }else{
                        System.out.println("send fail:"+e.getMessage());
                    }
                }
            });
//
            kafkaProducerImpl1.sendSync(TOPIC,"testMessage叶东林5");
            kafkaProducerImpl1.sendSync(TOPIC,"key1","testMessage叶东林6");
            kafkaProducerImpl1.sendSync(TOPIC,0,"key2","testMessage叶东林7");

            kafkaProducerImpl2.sendSync("testMessage叶东林5");
            kafkaProducerImpl2.sendSync(null,"testMessage叶东林5");
            kafkaProducerImpl2.sendSync(null,"key1","testMessage叶东林6");
            kafkaProducerImpl2.sendSync(null,0,"key2","testMessage叶东林7");
//
            int count = 100000;
            long start = System.currentTimeMillis();
            while (count<200000){
                count++;
                kafkaProducerImpl2.sendAsync(null,0,"key"+count,"test message"+count);
            }
            long end = System.currentTimeMillis();
            System.out.println("====================================================================================");
            System.out.println(end-start);
            System.in.read();
        }catch (KafkaException e){
            System.out.println("KafkaProducerTest KafkaException "+e.getMessage());
            e.printStackTrace();
        }catch (Exception e){
            System.out.println("KafkaProducerTest Exception "+e.getMessage());
            e.printStackTrace();
        }


    }
}