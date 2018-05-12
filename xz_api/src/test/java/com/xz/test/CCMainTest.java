package com.xz.test;


import com.xz.web.service.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:spring/spring-*.xml", "classpath*:kafka_producer.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
public class CCMainTest extends AbstractTransactionalJUnit4SpringContextTests {


    @Autowired
    private RedisService redisService;

    @Test
    public void testRedis() {
        String key = "test_123";

       /* Long sise = redisService.szGetSise(key);
        Set<Object> set = redisService.szReverse(key, 0L, sise-2);
        redisService.szInc(key, "11", -1.0);*/

        String value1 = "11";
        String value2 = "22";
        String value3 = "33";
        redisService.szSet(key, value1, 1.0);
        redisService.szSet(key, value2, 3.0);
        redisService.szSet(key, value3, 2.0);
        System.out.println(123);
    }

}
