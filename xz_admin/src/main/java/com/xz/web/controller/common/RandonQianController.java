package com.xz.web.controller.common;

import com.xz.framework.common.base.BeanCriteria;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.utils.date.DateUtil;
import com.xz.framework.utils.json.JsonUtil;
import com.xz.web.entity.TiQianList;
import com.xz.web.service.TiLuckyService;
import com.xz.web.service.TiQianListService;
import com.xz.web.service.redis.RedisService;
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
    private RedisService redisService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("RandomQian Run Time=" + DateUtil.getCurrentTimestampSSS());
        BeanCriteria beanCriteria1 = new BeanCriteria(TiQianList.class);
        beanCriteria1.setOrderByClause("update_timestamp desc");
        PageInfo<TiQianList> pager1 = new PageInfo<TiQianList>();
        pager1.setPageSize(1);
        TiQianList obj = new TiQianList();
        pager1 = tiQianListService.findList(obj,pager1);
        long count = pager1.getTotal();
        //2 取一个1-size的随机数，然后经过pageNum=随机数，size=1去取一条记录
        int randomNum = (int) (Math.random() * count);
        BeanCriteria beanCriteria2 = new BeanCriteria(TiQianList.class);
        beanCriteria2.setOrderByClause("update_timestamp desc");
        PageInfo<TiQianList> pager2 = new PageInfo<TiQianList>();
        pager2.setPageSize(1);
        pager2.setPageNum(randomNum);
        pager2 = tiQianListService.findList(obj,pager2);
        if (pager2.getList().size() > 0) {
            TiQianList randomQian = pager2.getList().get(0);
            redisService.set("randomQian", JsonUtil.serialize(randomQian));
        }
    }
}