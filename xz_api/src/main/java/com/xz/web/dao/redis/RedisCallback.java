package com.xz.web.dao.redis;

import redis.clients.jedis.Jedis;

public interface RedisCallback<T> {

	/**
	 * @param jedis active jedis client
	 * @return a result object or {@code null} if none
	 *
	 */
	T doInRedis(Jedis jedis);
}