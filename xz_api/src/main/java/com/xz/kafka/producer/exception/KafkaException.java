
package com.xz.kafka.producer.exception;

/**
 *
 *  异常类
 *
 */
public class KafkaException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public KafkaException(String message) {
        super(message);
    }

    public KafkaException(String message, Throwable cause) {
        super(message, cause);
    }

    public KafkaException(Throwable cause) {
        super(cause);
    }

}