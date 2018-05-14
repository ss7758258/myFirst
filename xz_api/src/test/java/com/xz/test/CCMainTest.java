package com.xz.test;


import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.web.bo.moreConstellation.X300Bo;
import com.xz.web.dao.redis.RedisDao;
import com.xz.web.mapper.entity.TcConstellation;
import com.xz.web.service.TcConstellationService;
import com.xz.web.service.ext.MoreConstellationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
public class CCMainTest extends AbstractTransactionalJUnit4SpringContextTests {


    @Autowired
    private RedisDao redisService;
    @Autowired
    private MoreConstellationService moreConstellationService;
    @Autowired
    private TcConstellationService tcConstellationService;

    @Test
    public void test1(){
        Long constellationId = 1L;

        //TcConstellation tcConstellation = tcConstellationService.selectByKey(constellationId);
        System.out.println(1);
        try {
            XZResponseBody<X300Bo> responseBody = moreConstellationService.selectMoreConstellation(constellationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRedis() {
        String key1 = "userId";
        redisService.set(key1, 123);
        System.out.println(redisService.get(key1));
        System.out.println(Long.valueOf(redisService.get(key1)));

     /*   String key = "test_123";

       *//* Long sise = redisService.szGetSise(key);
        Set<Object> set = redisService.szReverse(key, 0L, sise-2);
        redisService.szInc(key, "11", -1.0);*//*

        String value1 = "11";
        String value2 = "22";
        String value3 = "33";
        redisService.szSet(key, value1, 1.0);
        redisService.szSet(key, value2, 3.0);
        redisService.szSet(key, value3, 2.0);
        System.out.println(123);*/
    }

}
