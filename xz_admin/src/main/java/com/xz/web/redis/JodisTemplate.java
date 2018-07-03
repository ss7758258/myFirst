package com.xz.web.redis;

import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;


@Component
public class JodisTemplate {

    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    private String password;
    private String path;
    private String zkHost;
    // redis连接池单例
    private static volatile JedisResourcePool jedisPool = null;

    private JedisResourcePool getJedisPool() {
        if (jedisPool == null) {
            synchronized (JodisTemplate.class) {

                if (jedisPool == null) {

                    jedisPool = RoundRobinJedisPool.create().poolConfig(jedisPoolConfig)
                            .password(password).curatorClient(zkHost, 30000).zkProxyDir(path)
                            .build();
                }

            }

        }
        return jedisPool;
    }

    private Jedis getJedis() {
        return getJedisPool().getResource();
    }

    public <T> T execute(RedisCallback<T> action) {

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return action.doInRedis(jedis);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String getPassword() {
        System.out.println(getJedisPool()+"-----");
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getZkHost() {
        return zkHost;
    }

    public void setZkHost(String zkHost) {
    
        this.zkHost = zkHost;
    }


}
