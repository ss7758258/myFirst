package com.yeting.boot;

import com.yeting.kafka.consumer.KafkaConsumerThread;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class KafkaBoot {

	private final static Logger log = Logger.getLogger(KafkaBoot.class);
	
	public static void main(String[] args) {
		try {
			log.info("=========start begint==========");
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[]{"classpath:spring/spring-context.xml", "classpath:spring/spring-mvc.xml", "classpath:kafka_consumer.xml"});
			context.start();

			KafkaConsumerThread commentTopicThread = (KafkaConsumerThread) context.getBean("commentTopicThread");
			commentTopicThread.start();

			KafkaConsumerThread weixinTopicThread = (KafkaConsumerThread) context.getBean("weixinTopicThread");
			weixinTopicThread.start();

			KafkaConsumerThread artShareCountTopicThread = (KafkaConsumerThread) context.getBean("artShareCountTopicThread");
			artShareCountTopicThread.start();

			KafkaConsumerThread collectTopicThread = (KafkaConsumerThread) context.getBean("collectTopicThread");
			collectTopicThread.start();

			KafkaConsumerThread artCollectCountTopicThread = (KafkaConsumerThread) context.getBean("artCollectCountTopicThread");
			artCollectCountTopicThread.start();

			KafkaConsumerThread artLikeCountTopicThread = (KafkaConsumerThread) context.getBean("artLikeCountTopicThread");
			artLikeCountTopicThread.start();

			KafkaConsumerThread userArtLikeTopicThread = (KafkaConsumerThread) context.getBean("userArtLikeTopicThread");
			userArtLikeTopicThread.start();

			KafkaConsumerThread commentLikeCountTopicThread = (KafkaConsumerThread) context.getBean("commentLikeCountTopicThread");
			commentLikeCountTopicThread.start();

			KafkaConsumerThread userCommentLikeTopicThread = (KafkaConsumerThread) context.getBean("userCommentLikeTopicThread");
			userCommentLikeTopicThread.start();

			KafkaConsumerThread feedbackTopicThread = (KafkaConsumerThread) context.getBean("feedbackTopicThread");
			feedbackTopicThread.start();

			KafkaConsumerThread artUnLikeCountTopicThread = (KafkaConsumerThread) context.getBean("artUnLikeCountTopicThread");
			artUnLikeCountTopicThread.start();

			KafkaConsumerThread userArtUnLikeTopicThread = (KafkaConsumerThread) context.getBean("userArtUnLikeTopicThread");
			userArtUnLikeTopicThread.start();

			KafkaConsumerThread commentUnLikeCountTopicThread = (KafkaConsumerThread) context.getBean("commentUnLikeCountTopicThread");
			commentUnLikeCountTopicThread.start();

			KafkaConsumerThread userCommentUnLikeTopicThread = (KafkaConsumerThread) context.getBean("userCommentUnLikeTopicThread");
			userCommentUnLikeTopicThread.start();

			KafkaConsumerThread artUnCollectCountTopicThread = (KafkaConsumerThread) context.getBean("artUnCollectCountTopicThread");
			artUnCollectCountTopicThread.start();

			KafkaConsumerThread uncollectTopicThread = (KafkaConsumerThread) context.getBean("uncollectTopicThread");
			uncollectTopicThread.start();


			KafkaConsumerThread deleteCollectTopicThread = (KafkaConsumerThread) context.getBean("deleteCollectTopicThread");
			deleteCollectTopicThread.start();

			KafkaConsumerThread setCommentTopicThread = (KafkaConsumerThread) context.getBean("setCommentTopicThread");
			setCommentTopicThread.start();

			KafkaConsumerThread deleteCommentTopicThread = (KafkaConsumerThread) context.getBean("deleteCommentTopicThread");
			deleteCommentTopicThread.start();

			KafkaConsumerThread updateMainReadCountTopicThread = (KafkaConsumerThread) context.getBean("updateMainReadCountTopicThread");
			updateMainReadCountTopicThread.start();

			/**
			 * 更新redis
			 */
			KafkaConsumerThread redisMainTopicThread = (KafkaConsumerThread) context.getBean("redisMainTopicThread");
			redisMainTopicThread.start();

			KafkaConsumerThread redisDetailTopicThread = (KafkaConsumerThread) context.getBean("redisDetailTopicThread");
			redisDetailTopicThread.start();

			KafkaConsumerThread redisMainReadTopicThread = (KafkaConsumerThread) context.getBean("redisMainReadTopicThread");
			redisMainReadTopicThread.start();

			KafkaConsumerThread redisCommentTopicThread = (KafkaConsumerThread) context.getBean("redisCommentTopicThread");
			redisCommentTopicThread.start();

			KafkaConsumerThread redisSaveCommentTopicThread = (KafkaConsumerThread) context.getBean("redisSaveCommentTopicThread");
			redisSaveCommentTopicThread.start();

			KafkaConsumerThread redisArtShareTopicThread = (KafkaConsumerThread) context.getBean("redisArtShareTopicThread");
			redisArtShareTopicThread.start();

			KafkaConsumerThread redisArtCollectTopicThread = (KafkaConsumerThread) context.getBean("redisArtCollectTopicThread");
			redisArtCollectTopicThread.start();

			KafkaConsumerThread redisArtLikeTopicThread = (KafkaConsumerThread) context.getBean("redisArtLikeTopicThread");
			redisArtLikeTopicThread.start();

			KafkaConsumerThread redisMainLikeTopicThread = (KafkaConsumerThread) context.getBean("redisMainLikeTopicThread");
			redisMainLikeTopicThread.start();

			KafkaConsumerThread redisCommentLikeTopicThread = (KafkaConsumerThread) context.getBean("redisCommentLikeTopicThread");
			redisCommentLikeTopicThread.start();

			KafkaConsumerThread redisArtUnLikeTopicThread = (KafkaConsumerThread) context.getBean("redisArtUnLikeTopicThread");
			redisArtUnLikeTopicThread.start();

			KafkaConsumerThread redisMainUnLikeTopicThread = (KafkaConsumerThread) context.getBean("redisMainUnLikeTopicThread");
			redisMainUnLikeTopicThread.start();

			KafkaConsumerThread redisCommentUnLikeTopicThread = (KafkaConsumerThread) context.getBean("redisCommentUnLikeTopicThread");
			redisCommentUnLikeTopicThread.start();

			KafkaConsumerThread redisArtUnCollectTopicThread = (KafkaConsumerThread) context.getBean("redisArtUnCollectTopicThread");
			redisArtUnCollectTopicThread.start();

			KafkaConsumerThread redisCommentDeleteTopicThread = (KafkaConsumerThread) context.getBean("redisCommentDeleteTopicThread");
			redisCommentDeleteTopicThread.start();

			log.info("=========start end=============");
		} catch (Exception e) {
			log.error("server error:", e);
			e.printStackTrace();
		}

	}

}
