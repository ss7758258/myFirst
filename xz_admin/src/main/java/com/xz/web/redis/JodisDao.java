package com.xz.web.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Component
public class JodisDao {

    @Autowired
    private JodisTemplate template;

    // =============================common============================
    /**
     * 指定缓存失效时间
     * 
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public Long expire(String key, int time) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.expire(key, time);
            }
        });
    }

    /**
     * 根据key 获取过期时间
     * 
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {

        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.ttl(key);
            }
        });
    }

    /**
     * 判断key是否存在
     * 
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {

        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(Jedis jedis) {
                return jedis.exists(key);
            }
        });
    }

    /**
     * 删除缓存
     * 
     * @param key 可以传一个值 或多个
     */
    public void del(String key) {

        template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.del(key);
            }
        });
    }

    // ============================String=============================
    /**
     * 普通缓存获取
     * 
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return template.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    /**
     * 普通缓存放入
     * 
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public String set(String key, String value) {
        return template.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) {
                return jedis.set(key, value);
            }
        });
    }

    /**
     * 普通缓存放入并设置时间
     * 
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public String set(String key, String value, int time) {

        return template.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) {
                return jedis.setex(key, time, value);
            }
        });


    }

    // ================================Map=================================
    /**
     * HashGet
     * 
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public String hget(String key, String item) {

        return template.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) {
                return jedis.hget(key, item);
            }
        });


    }


    public Long hdel(String key, String item) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.hdel(key, item);
            }
        });
    }

    /**
     * 获取hash长度
     * 
     * @Description: TODO(方法描述)
     * @param key
     * @return
     * @Author: liulu
     * @Date: 2018年4月3日 下午7:10:09
     */
    public long hgetSize(String key) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.hlen(key);
            }
        });
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * 
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    public Long hset(String key, String item, String value) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.hset(key, item, value);
            }
        });
    }

    // ===============================list=================================
    /**
     * 获取list缓存的内容
     * 
     * @param key 键
     * @param start 开始
     * @param end 结束 0 到 -1代表所有值
     * @return
     */
    public List<String> lGet(String key, long start, long end) {

        return template.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(Jedis jedis) {
                return jedis.lrange(key, start, end);
            }
        });
    }

    /**
     * 获取list缓存的长度
     * 
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {

        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.llen(key);
            }
        });
    }

    /**
     * 通过索引 获取list中的值
     * 
     * @param key 键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public String lGetIndex(String key, long index) {

        return template.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) {
                return jedis.lindex(key, index);
            }
        });
    }

    /**
     * 将list放入缓存 从列头插入元素
     * 
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public Long lSet(String key, String value) {

        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.lpush(key, value);
            }
        });
    }

    /**
     * 将list放入缓存 从列尾插入元素
     * 
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public Long lrSet(String key, String value) {

        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.rpush(key, value);
            }
        });
    }

    /**
     * 根据索引修改list中的某条数据
     * 
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public String lUpdateIndex(String key, long index, String value) {

        return template.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(Jedis jedis) {
                return jedis.lset(key, index, value);
            }
        });
    }

    /**
     * 移除N个值为value
     * 
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, String value) {

        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.lrem(key, count, value);
            }
        });
    }



    // ----------------------------------zset---------------------------------------------//

    /**
     * 取出在这个范围内的数据
     * 
     * @Description: TODO(方法描述)
     * @param key
     * @param startScore
     * @param endScore
     * @return
     * @Author: liulu
     * @Date: 2018年4月3日 下午8:28:13
     */
    public Set<TypedTuple<String>> szRangByScore(String key, Double startScore, Double endScore) {

        Set<Tuple> rawValues = template.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(Jedis jedis) {
                return jedis.zrangeByScoreWithScores(key, startScore, endScore);
            }
        });
        return deserializeTupleValues(rawValues);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    Set<TypedTuple<String>> deserializeTupleValues(Collection<Tuple> rawValues) {
        if (rawValues == null) {
            return null;
        }
        Set<TypedTuple<String>> set = new LinkedHashSet<TypedTuple<String>>(rawValues.size());
        for (Tuple rawValue : rawValues) {
            Object value = rawValue.getElement();
            set.add(new DefaultTypedTuple(value, rawValue.getScore()));
        }
        return set;
    }


    /**
     * 删除
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long szRemove(String key, String v) {

        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.zrem(key, v);
            }
        });
    }

    /**
     * 将数据放入zset缓存
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long szSet(String key, String value, Double score) {

        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.zadd(key, score, value);
            }
        });
    }

    /**
     * 得到分数
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Double szGetScore(String key, String v) {
        return template.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(Jedis jedis) {
                return jedis.zscore(key, v);
            }
        });
    }


    /**
     * 得到排序
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long szGetIndex(String key, String v) {

        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                Long zRank = jedis.zrevrank(key, v);
                return (zRank != null && zRank.longValue() >= 0 ? zRank : null);
            }
        });
    }



    /**
     * 分数按高到低
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Set<String> szReverse(String key, Long start, Long end) {

        return template.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(Jedis jedis) {
                return jedis.zrevrange(key, start, end);

            }
        });
    }

    public Set<TypedTuple<String>> szReverseScores(String key, Long start, Long end) {

        Set<Tuple> rawValues = template.execute(new RedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doInRedis(Jedis jedis) {
                return jedis.zrevrangeWithScores(key, start, end);
            }
        });
        return deserializeTupleValues(rawValues);

    }

    /**
     * 取得大小
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long szGetSise(String key) {

        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(Jedis jedis) {
                return jedis.zcard(key);

            }
        });
    }

    /**
     * 自增
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Double szInc(String key, String value, Double d) {

        return template.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(Jedis jedis) {
                return jedis.zincrby(key, d, value);

            }
        });
    }


}
