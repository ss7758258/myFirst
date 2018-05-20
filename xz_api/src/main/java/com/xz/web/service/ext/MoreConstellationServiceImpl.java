package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.common.base.BeanCriteria;
import com.xz.framework.utils.DateUtil;
import com.xz.framework.utils.JsonUtil;
import com.xz.web.bo.moreConstellation.X300Bo;
import com.xz.web.dao.redis.RedisDao;
import com.xz.web.mapper.entity.TiLucky;
import com.xz.web.mapper.ext.MoreConstellationMapperExt;
import com.xz.web.service.TiLuckyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoreConstellationServiceImpl implements MoreConstellationService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MoreConstellationMapperExt moreConstellationMapperExt;
    @Autowired
    private TiLuckyService tiLuckyService;

    @Autowired
    private RedisDao redisService;

    @Value("#{constants.redis_key_time}")
    private int redisKeyTime;
    @Value("#{constants.redis_statistics_luckyClickCount}")
    private String statisticsLuckyClickCount;
    @Value("#{constants.redis_statistics_luckyMorePV}")
    private String statisticsLuckyMorePV;
    @Value("#{constants.redis_statistics_luckyMoreUV}")
    private String statisticsLuckyMoreUV;
    @Value("#{constants.redis_statistics_luckyUV_openId}")
    private String statisticsLuckyUVOpenId;

    /**
     * 返回星座运势（更多）
     * @return
     * @param constellationId
     * @param weixin
     */
    @Override
    public XZResponseBody<X300Bo> selectMoreConstellation(Long constellationId, Weixin weixin) throws Exception {
        XZResponseBody<X300Bo> responseBody = new XZResponseBody<X300Bo>();
        X300Bo x300Bo = new X300Bo();
        x300Bo.setConstellationId(constellationId);
        /**
         * 接口返回运势指数；
         * 去做内容（标题及内容）；
         * 别做内容（标题及内容）；
         */

        //更加星座id查询对应的信息, redis key格式为  luckyMore-:constellationId
        TiLucky tiLucky = new TiLucky();
        if (redisService.hasKey("luckyMore-:" + constellationId)){
            String str = redisService.get("luckyMore-:" + constellationId);
            tiLucky =  JsonUtil.deserialize(str, TiLucky.class);
        }else {
            //查询当前openid的运势信息
            BeanCriteria beanCriteria = new BeanCriteria(TiLucky.class);
            BeanCriteria.Criteria criteria = beanCriteria.createCriteria();
            criteria.andEqualTo("constellationId", constellationId);
            criteria.andEqualTo("status", 1);
            criteria.andLike("publishTime", DateUtil.getDate()+"%");
            beanCriteria.setOrderByClause("update_timestamp desc");
            List<TiLucky> tiLuckyList = tiLuckyService.selectByExample(beanCriteria);
            if (!tiLuckyList.isEmpty()) {
                tiLucky = tiLuckyList.get(0);
                String redisJson = JsonUtil.serialize(tiLucky);
                redisService.set("luckyMore-:" + constellationId, redisJson, redisKeyTime);
            }
        }
        if (null != tiLucky) {
            x300Bo.setLuckyScoreMore1(tiLucky.getLuckyScoreMore1() + "");
            x300Bo.setLuckyScoreMore2(tiLucky.getLuckyScoreMore2() + "");
            x300Bo.setLuckyScoreMore3(tiLucky.getLuckyScoreMore3() + "");
            x300Bo.setLuckyScoreMore4(tiLucky.getLuckyScoreMore4() + "");

            x300Bo.setLuckyTypeMore1(tiLucky.getLuckyTypeMore1());
            x300Bo.setLuckyTypeMore2(tiLucky.getLuckyTypeMore2());
            x300Bo.setLuckyTypeMore3(tiLucky.getLuckyTypeMore3());
            x300Bo.setLuckyTypeMore4(tiLucky.getLuckyTypeMore4());

            x300Bo.setLuckyWords1(tiLucky.getLuckyWords1());
            x300Bo.setLuckyWords2(tiLucky.getLuckyWords2());
            x300Bo.setLuckyWords3(tiLucky.getLuckyWords3());
            x300Bo.setLuckyWords4(tiLucky.getLuckyWords4());

            x300Bo.setToDo(tiLucky.getToDo());
            x300Bo.setNotDo(tiLucky.getNotDo());
            x300Bo.setPublishTime(tiLucky.getPublishTime());
            x300Bo.setLuckyDate(tiLucky.getLuckyDate());
            x300Bo.setCreateTime(tiLucky.getCreateTimestamp());
        }

        /*
        //统计
        if (redisService.hasKey(statisticsLuckyClickCount)){
            redisService.incr(statisticsLuckyClickCount, 1L);
        }else {
            redisService.set(statisticsLuckyClickCount, 1L);
        }

        //每日运势页PV
        if (redisService.hasKey(statisticsLuckyMorePV)){
            redisService.incr(statisticsLuckyMorePV, 1L);
        }else {
            redisService.set(statisticsLuckyMorePV, 1L);
        }

        //每日运势页UV
        if (redisService.hasKey(statisticsLuckyUVOpenId + weixin.getOpenId())){
            //
        }else {
            //存活一天
            redisService.set(statisticsLuckyUVOpenId + weixin.getOpenId(), weixin.getOpenId(), 24*60*60L);
            if (redisService.hasKey(statisticsLuckyMoreUV)){
                redisService.set(statisticsLuckyMoreUV, 1L);
            }else {
                redisService.incr(statisticsLuckyMoreUV, 1L);
            }
        }
*/
        responseBody.setStatus(AjaxStatus.SUCCESS);
        responseBody.setData(x300Bo);
        return responseBody;
    }
}
