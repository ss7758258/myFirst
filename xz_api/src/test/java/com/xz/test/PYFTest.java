package com.xz.test;


import com.xz.framework.utils.JsonUtil;
import com.xz.web.dao.redis.RedisDao;
import com.xz.web.mapper.entity.TiUserQianList;
import com.xz.web.service.TcConstellationService;
import com.xz.web.service.ext.EverydayQianService;
import com.xz.web.service.ext.MoreConstellationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
public class PYFTest extends AbstractTransactionalJUnit4SpringContextTests {


    @Autowired
    private RedisDao redisService;
    @Autowired
    private EverydayQianService everydayQianService;

    @Autowired
    private MoreConstellationService moreConstellationService;
    @Autowired
    private TcConstellationService tcConstellationService;

    @Test
    public void test1(){
        List<TiUserQianList> list =  everydayQianService.testSelect();
        System.out.println(JsonUtil.serialize(list));
        TiUserQianList obj = new TiUserQianList();
        obj.setFriendOpenId1("1");
        obj.setFriendOpenId2("2");
        obj.setFriendOpenId3("3");
        obj.setFriendOpenId4("4");
        obj.setFriendOpenId5("5");
        int flag = everydayQianService.save(obj);
        System.out.println(JsonUtil.serialize(obj));
    }

}
