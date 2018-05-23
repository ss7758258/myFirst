package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.web.dao.redis.RedisDao;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StatisticsConstellationServiceImpl implements StatisticsConstellationService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisDao redisService;

    @Value("#{constants.redis_statistics_luckySaveCount}")
    private String statisticsLuckySaveCount;
    @Value("#{constants.redis_statistics_luckyQRCount}")
    private String statisticsLuckyQRCount;
    @Value("#{constants.redis_statistics_qianShareCount}")
    private String statisticsQianShareCount;
    @Value("#{constants.redis_statistics_qianBringCount}")
    private String statisticsQianBringCount;
    @Value("#{constants.redis_statistics_qianSaveCount}")
    private String statisticsQianSaveCount;
    @Value("#{constants.redis_statistics_qianQRCount}")
    private String statisticsQianQRCount;
    @Value("#{constants.redis_statistics_yanSaveCount}")
    private String statisticsYanSaveCount;
    @Value("#{constants.redis_statistics_yanBringCount}")
    private String statisticsYanBringCount;
    @Value("#{constants.redis_statistics_qianAvgCount}")
    private String statisticsQianAvgCount;
    @Value("#{constants.redis_statistics_yanAvgCount}")
    private String statisticsYanAvgCount;
    @Value("#{constants.redis_has_formid_user_zset}")
    private String hasFormidUserZset;
    @Value("#{constants.redis_user_formid_zset_openid}")
    private String userFormidZsetOpenid;

    @Override
    public XZResponseBody<String> x600() {
        return this.saveRedis(statisticsLuckySaveCount);
    }

    @Override
    public XZResponseBody<String> x601() {
        return this.saveRedis(statisticsLuckyQRCount);
    }

    @Override
    public XZResponseBody<String> x602() {
        return this.saveRedis(statisticsQianShareCount);
    }

    @Override
    public XZResponseBody<String> x603() {
        return this.saveRedis(statisticsQianBringCount);
    }

    @Override
    public XZResponseBody<String> x604() {
        return this.saveRedis(statisticsQianSaveCount);
    }

    @Override
    public XZResponseBody<String> x605() {
        return this.saveRedis(statisticsQianQRCount);
    }

    @Override
    public XZResponseBody<String> x606() {
        return this.saveRedis(statisticsYanSaveCount);
    }

    @Override
    public XZResponseBody<String> x607() {
        return this.saveRedis(statisticsYanBringCount);
    }

    private XZResponseBody<String> saveRedis(String string){
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        //统计
        if (redisService.hasKey(string)){
            redisService.incr(string, 1L);
        }else {
            redisService.set(string, 1L);
        }
        if ("statisticsQianShareCount".equals(string) || "statisticsQianSaveCount".equals(string)){
            Integer userCount = Integer.valueOf(redisService.get("userCount"));
            if (userCount > 0){
                Integer avg = Integer.valueOf(redisService.get(string)) / userCount;
                redisService.set(statisticsQianAvgCount, avg);
            }
        }
        if ("statisticsYanSaveCount".equals(string)){
            Integer userCount = Integer.valueOf(redisService.get("userCount"));
            if (userCount > 0){
                Integer avg = Integer.valueOf(redisService.get(string)) / userCount;
                redisService.set(statisticsYanAvgCount, avg);
            }
        }

        responseBody.setStatus(AjaxStatus.SUCCESS);
        responseBody.setData(null);
        return responseBody;
    }

    @Override
    public XZResponseBody<String> x610(Weixin weixin, String formid) {
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        Long time = System.currentTimeMillis();
        Long time1 = time + 7*24*60*60*1000;
        redisService.szSet(hasFormidUserZset, weixin.getOpenId(), (double)time1);

        Long time2 = time + 7*24*60*60*1000 - 5*30*1000;
        logger.error("-------------------------formid:" + formid);
        redisService.szSet(userFormidZsetOpenid + weixin.getOpenId(), formid, (double)time2);
        responseBody.setStatus(AjaxStatus.SUCCESS);
        responseBody.setData(null);
        return responseBody;
    }
}
