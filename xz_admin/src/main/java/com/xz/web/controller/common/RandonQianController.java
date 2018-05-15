package com.xz.web.controller.common;

import com.xz.framework.common.base.PageInfo;
import com.xz.framework.utils.PageHelper;
import com.xz.framework.utils.date.DateUtil;
import com.xz.framework.utils.json.JsonUtil;
import com.xz.web.entity.TiQianList;
import com.xz.web.entity.TiQianListExample;
import com.xz.web.mapper.TiQianListMapper;
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
    private TiQianListMapper mapper;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("RandomQian Run Time=" + DateUtil.getCurrentTimestampSSS());
        TiQianListExample example = new TiQianListExample();
        TiQianListExample.Criteria criteria = example.createCriteria();
        PageInfo<TiQianList> pager1 = new PageInfo<TiQianList>();
        PageHelper.startPage(pager1.getPageNum(), pager1.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        TiQianList obj = new TiQianList();
        long count = mapper.countByExample(example);
        System.out.println("RandomQian count=" + count);
        //2 取一个1-size的随机数，然后经过pageNum=随机数，size=1去取一条记录
        int randomNum = (int) (Math.random() * count);
        TiQianListExample example2 = new TiQianListExample();
        TiQianListExample.Criteria criteria2 = example.createCriteria();
        example2.setOrderByClause("update_timestamp desc");
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