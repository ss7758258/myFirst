package com.xz.test;


import com.xz.framework.bean.ajax.YTResponseBody;
import com.xz.framework.utils.BeanUtil;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.f10.F10Bo;
import com.xz.web.bo.f20.F13Bo;
import com.xz.web.bo.f20.F70Bo;
import com.xz.web.bo.f30.F20Bo;
import com.xz.web.mapper.entity.Article;
import com.xz.web.mapper.ext.ArticleExtMapper;
import com.xz.web.mapper.ext.MyCollectionViewExtMapper;
import com.xz.web.service.ArticleService;
import com.xz.web.service.ext.F20Service;
import com.xz.web.service.redis.RedisService;
import com.xz.web.utils.StrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ContextConfiguration(locations = {"classpath:spring/spring-*.xml", "classpath*:kafka_producer.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
public class CCMainTest extends AbstractTransactionalJUnit4SpringContextTests {


    @Autowired
    private RedisService redisService;

    @Autowired
    private MyCollectionViewExtMapper myCollectionViewExtMapper;
    @Autowired
    private ArticleExtMapper articleExtMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private F20Service f20Service;


    @Test
    public void test2(){
        String json = "{\"commentId\":947,\"createTime\":\"2018-03-23 11:33:20\",\"content\":\"55S355S35aWz5aWz5L2g\",\"userName\":\"5Y6f5p2l5aaC5q2k\",\"userHead\":\"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIcHOOqDpy7glLiaSWxKSyicszQfOy9YGwY3icMtLictvfTOPX1yGYloTtwtkYO78PduYp1x3RHhBLTYw/0\",\"likeCount\":322,\"like\":null}";
        F13Bo f13Bo = JsonUtil.deserialize(json, F13Bo.class);
        System.out.println(1);

        /*List<Long> ids = new ArrayList<>();
        ids.add(945L);
        for (int i = 0; i < ids.size(); i++) {
            //更加评论id找到文章id
            Long artId = 246L;
            String commentKey = "art_list" + "_" + artId;
            String hotComment = "art_zset" + "_" + artId;
            if (redisService.hasKey(commentKey)) {
                List<String> list = new ArrayList<>();
                Long size = redisService.lGetListSize(commentKey);
                list = (List) redisService.lrange(commentKey, 0, size);
                for (int j = 0; j < list.size(); j++) {
                    F70Bo f70Bo = new F70Bo();
                    f70Bo = JsonUtil.deserialize(list.get(j), F70Bo.class);
                    if (f70Bo.getCommentId().equals(ids.get(i))) {
                        redisService.lRemove(commentKey, 1, list.get(j));
                        break;
                    }
                }
            }
            if (redisService.hasKey(hotComment)) {
                List<String> list = new ArrayList<>();
                Long size = redisService.szRemove(hotComment, ids.get(i));
            }
        }*/

/*        String openId = "oaaxM5RKCZ6hGchEn5Z5kAje-PwQ";
        YTResponseBody<Boolean> ll = f20Service.updateCommentUnLike("220", "862", "5Y6f5p2l5aaC5q2k");
        System.out.println(1);*/
    }

    @Test
    public void test() {
        List<Long> ids = new ArrayList<>();
        ids.add(731L);
        for (int i = 0; i < ids.size(); i++) {
            String commentKey = "art_list" + "_" + 215;
            if (redisService.hasKey(commentKey)) {
                List<String> list = new ArrayList<>();
                Long size = redisService.lGetListSize(commentKey);
                list = (List) redisService.lrange(commentKey, 0, size);
                for (int j = 0; j < list.size(); j++) {
                    F70Bo f70Bo = new F70Bo();
                    f70Bo = JsonUtil.deserialize(list.get(j), F70Bo.class);
                    if (f70Bo.getCommentId().equals(ids.get(i))) {
                        redisService.lRemove(commentKey, 1, list.get(j));
                        break;
                    }
                }
            }
        }
        System.out.println(1);
}

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
