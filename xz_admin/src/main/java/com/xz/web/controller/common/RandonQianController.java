package com.xz.web.controller.common;

import com.xz.framework.utils.date.DateUtil;
import com.xz.framework.utils.json.JsonUtil;
import com.xz.web.entity.TiQianList;
import com.xz.web.mapper.ext.EverydayQianMapperExt;
import com.xz.web.redis.RedisDao;
import com.xz.web.service.TiLuckyService;
import com.xz.web.service.TiQianListService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RandonQianController extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(RandonQianController.class);

    @Autowired
    private TiLuckyService tiLuckyService;
    @Autowired
    private TiQianListService tiQianListService;
    @Autowired
    private RedisDao redisService;
    @Autowired
    private EverydayQianMapperExt mapper;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.debug("RandomQian Run Time=" + DateUtil.getCurrentTimestampSSS());
        //先拿出有效的签库
        long count = mapper.countActiveQianList();
        logger.debug("RandomQian count=" + count);
        int randomNum = (int) (Math.random() * count);
        TiQianList qian = mapper.randomActiveQianList(randomNum);
        if (null!=qian) {
            redisService.set("randomQian", JsonUtil.serialize(qian));
            logger.debug("randomQian=" + JsonUtil.serialize(qian));
        }
    }
}