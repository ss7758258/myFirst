package com.xz.web.dao.redis;

import com.xz.framework.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Component
public class RedisDao {


    // private final Logger logger = LoggerFactory.getLogger(RedisDao.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // =============================common============================
    /**
     * 指定缓存失效时间
     * 
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        if (time > 0) {
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } else {
            return false;
        }

    }

    /**
     * 根据key 获取过期时间
     * 
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);

    }

    /**
     * 判断key是否存在
     * 
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     * 
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    // ============================String=============================
    /**
     * 普通缓存获取
     * 
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * 
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        String v = null;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = JsonUtil.serialize(value);
        }
        redisTemplate.opsForValue().set(key, v);
        return true;

    }

    /**
     * 普通缓存放入并设置时间
     * 
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        String v = null;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = JsonUtil.serialize(value);
        }
        if (time > 0) {
            redisTemplate.opsForValue().set(key, v, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
        return true;
    }

    /**
     * 递增
     * 
     * @param key 键
     * @param by 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * 
     * @param key 键
     * @param by 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ================================Map=================================
    /**
     * HashGet
     * 
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }


    public Object hdel(String key, String item) {
        return redisTemplate.opsForHash().delete(key, item);
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
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取hashKey对应的所有键值
     * 
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * 
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        String v = null;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = JsonUtil.serialize(value);
        }
        redisTemplate.opsForHash().put(key, item, v);
        return true;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * 
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        String v = null;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = JsonUtil.serialize(value);
        }
        redisTemplate.opsForHash().put(key, item, v);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    /**
     * hash 自增
     * 
     * @param key 键
     * @param value 值
     * @param d 增长步长
     * @return 成功个数
     */
    public long hInc(String key, Object value, long d) {
        String v = null;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = JsonUtil.serialize(value);
        }
        return redisTemplate.opsForHash().increment(key, v, d);
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
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     * 
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     * 
     * @param key 键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public String lGetIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存 从列头插入元素
     * 
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value) {
        String v = null;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = JsonUtil.serialize(value);
        }
        redisTemplate.opsForList().leftPush(key, v);
        return true;
    }

    /**
     * 将list放入缓存 从列尾插入元素
     * 
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean lrSet(String key, Object value) {
        String v = null;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = JsonUtil.serialize(value);
        }
        redisTemplate.opsForList().rightPush(key, v);
        return true;
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
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /*    *//**
             * 将list放入缓存
             * 
             * @param key 键
             * @param value 值
             * @param time 时间(秒)
             * @return
             *//*
                * public boolean lSet(String key, Object value, long time) { try {
                * //redisTemplate.opsForList().rightPush(key, value);
                * redisTemplate.opsForList().leftPush(key, value); if (time > 0) expire(key, time);
                * return true; } catch (Exception e) { e.printStackTrace(); return false; } }
                */

    /*    *//**
             * 将list放入缓存
             * 
             * @param key 键
             * @param value 值
             * @param time 时间(秒)
             * @return
             */
    /*
     * public boolean lSet(String key, List<String> value) { try {
     * //redisTemplate.opsForList().rightPushAll(key, value);
     * redisTemplate.opsForList().leftPushAll(key, value); return true; } catch (Exception e) {
     * e.printStackTrace(); return false; } }
     */
    /*    *//**
             * 将list放入缓存
             * 
             * @param key 键
             * @param value 值
             * @param time 时间(秒)
             * @return
             *//*
                * public boolean lSet(String key, List<String> value, long time) { try { //
                * redisTemplate.opsForList().rightPushAll(key, value);
                * redisTemplate.opsForList().leftPushAll(key, value); if (time > 0) expire(key,
                * time); return true; } catch (Exception e) { e.printStackTrace(); return false; } }
                */

    /**
     * 根据索引修改list中的某条数据
     * 
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        String v = null;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = JsonUtil.serialize(value);
        }
        redisTemplate.opsForList().set(key, index, v);
        return true;
    }

    /**
     * 移除N个值为value
     * 
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        Long remove = redisTemplate.opsForList().remove(key, count, value);
        return remove;
    }

    /*   *//**
            * 移除并获取列表最后一个元素，当列表不存在或者为空时，返回Null
            * 
            * @param key
            * @return String
            *//*
               * public String rpop(String key) { try { String remove = (String)
               * redisTemplate.opsForList().rightPop(key); return remove; } catch (Exception e) {
               * e.printStackTrace(); return ""; } }
               */

    /**
     * 删除
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long szRemove(String key, String v) {
        return redisTemplate.opsForZSet().remove(key, v);
    }

    /**
     * 获取List列表
     * 
     * @param key
     * @param start long，开始索引
     * @param end long， 结束索引
     * @return List<String>
     */
    public List<String> lrange(String key, long start, long end) {
        List<String> list = redisTemplate.opsForList().range(key, start, end - 1);
        return list;
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
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, startScore, endScore);

    }


    /**
     * 将数据放入zset缓存
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Boolean szSet(String key, Object value, Double score) {
        String v = null;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = JsonUtil.serialize(value);
        }
        return redisTemplate.opsForZSet().add(key, v, score);
    }

    /**
     * 得到分数
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Double szGetScore(String key, String v) {
        return redisTemplate.opsForZSet().score(key, v);
    }


    /**
     * 得到排序
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long szGetIndex(String key, String v) {
        return redisTemplate.opsForZSet().reverseRank(key, v);
    }



    /**
     * 按索引取数据，分数从高到低
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Set<String> szReverse(String key, Long l1, Long l2) {

        return redisTemplate.opsForZSet().reverseRange(key, l1, l2);
    }

    public Set<TypedTuple<String>> szReverseScores(String key, Long l1, Long l2) {

        return redisTemplate.opsForZSet().reverseRangeWithScores(key, l1, l2);
    }

    /**
     * 取得大小
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long szGetSise(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 自增
     * 
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Double szInc(String key, Object value, Double d) {
        String v = null;
        if (value instanceof String) {
            v = (String) value;
        } else {
            v = JsonUtil.serialize(value);
        }
        return redisTemplate.opsForZSet().incrementScore(key, v, d);
    }

    /**
     * 
    * @Description: 按索引取数据，分数从低到高
    * @param key
    * @param start 开始索引
    * @param end 结束索引（-1代表所有值）
    * @return
    * @Author: DJ
    * @Date: 2018年4月20日 上午11:34:04
     */
    public Set<String> szRang(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }
    
    /**
     * 
    * @Description: 获取set数据,带分数
    * @param key
    * @param start 开始索引
    * @param end 结束索引（-1代表所有值）
    * @return
    * @Author: DJ
    * @Date: 2018年4月20日 上午11:34:04
     */
    public Set<TypedTuple<String>> szRangWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

}
