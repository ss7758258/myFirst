package com.xz.web.controller.common;

import com.xz.framework.common.base.PageInfo;
import com.xz.framework.utils.date.DateUtil;
import com.xz.web.entity.TiLucky;
import com.xz.web.service.TiLuckyService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class MyJobController extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(MyJobController.class);

    @Autowired
    private TiLuckyService tiLuckyService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Run Time=" + DateUtil.getCurrentTimestampSSS());
        PageInfo<TiLucky> pager = new PageInfo<TiLucky>();
        TiLucky searchCondition = new TiLucky();
        searchCondition.setStatus(0);
        pager = tiLuckyService.findList(searchCondition, pager);
        List<TiLucky> list = pager.getList();
        if(list.size()>0)
        {
            for(TiLucky tiLucky:list)
            {
                String publishTime = tiLucky.getPublishTime();
                if(publishTime!=null&&publishTime.trim().length()<=10)
                    publishTime = tiLucky.getPublishTime()+" 00:00:00";
                int diffTime = DateUtil.compareTime(publishTime,DateUtil.getCurrentTimestamp());
                if(diffTime<0)
                {
                    tiLucky.setStatus(1);
                    tiLucky.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                    tiLuckyService.update(tiLucky);
                    logger.info(DateUtil.getCurrentTimestampSSS()+"-->发布运势ID："+tiLucky.getId());
                }
            }
        }

    }
}