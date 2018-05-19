package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
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

    private XZResponseBody<String> saveRedis(String string){
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        //统计
        if (redisService.hasKey(string)){
            redisService.incr(string, 1L);
        }else {
            redisService.set(string, 1L);
        }

        responseBody.setStatus(AjaxStatus.SUCCESS);
        responseBody.setData(null);
        return responseBody;
    }
}
