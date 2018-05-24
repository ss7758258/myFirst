package com.xz.test;


import com.xz.framework.common.base.BeanCriteria;
import com.xz.framework.utils.DateUtil;
import com.xz.framework.utils.JsonUtil;
import com.xz.web.dao.redis.RedisDao;
import com.xz.web.mapper.entity.TiLucky;
import com.xz.web.mapper.entity.TiUserQianList;
import com.xz.web.service.TcConstellationService;
import com.xz.web.service.TiLuckyService;
import com.xz.web.service.ext.EverydayQianService;
import com.xz.web.service.ext.MoreConstellationService;
import com.xz.web.utils.AccessToken;
import com.xz.web.utils.QRCodeUtil;
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
public class CCMainTest extends AbstractTransactionalJUnit4SpringContextTests {


    @Autowired
    private RedisDao redisService;
    @Autowired
    private EverydayQianService everydayQianService;

    @Autowired
    private MoreConstellationService moreConstellationService;
    @Autowired
    private TcConstellationService tcConstellationService;
    @Autowired
    private TiLuckyService tiLuckyService;


    @Test
    public void test2(){
        BeanCriteria beanCriteria = new BeanCriteria(TiLucky.class);
        BeanCriteria.Criteria criteria = beanCriteria.createCriteria();
        String datatime = DateUtil.getDate();
        String beginTime = datatime + " 00:00:00";
        String endTime = datatime + " 23:00:00";
        criteria.andBetween("createTimestamp", beginTime, endTime);
        List<TiLucky> luckyList = tiLuckyService.selectByExample(beanCriteria);
        System.out.println(1);
    }

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
