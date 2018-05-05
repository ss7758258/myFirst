
package com.yeting.kafka.producer;

import com.yeting.kafka.producer.exception.KafkaException;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *  异步发送消息回调类
 *
 */
public class SendAsyncCallBack  implements Callback {

    private static final Logger logger = LoggerFactory.getLogger(SendAsyncCallBack.class);

    private final String key;
    private final String value;

    /**
     * 构造方法
     * @param key
     * @param value
     */
    public SendAsyncCallBack(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * A callback method the user can implement to provide asynchronous handling of request completion. This method will
     * be called when the record sent to the server has been acknowledged. Exactly one of the arguments will be
     * non-null.
     *
     * @param metadata  The metadata for the record that was sent (i.e. the partition and offset). Null if an error
     *                  occurred.
     * @param exception The exception thrown during processing of this record. Null if no error occurred.
     */
    public void onCompletion(RecordMetadata metadata, Exception exception) throws KafkaException {
        if (metadata != null) {
//            System.out.println("message(" + key + ", " + value + ") sent to partition("
//                    + metadata.partition() + "), " +"offset(" + metadata.offset() + ")");
        } else {
         //   exception.printStackTrace();
            logger.error("sendSync msg exception: " + exception.getMessage());
            throw new KafkaException("sendSync msg exception: " + exception);
        }
    }
}