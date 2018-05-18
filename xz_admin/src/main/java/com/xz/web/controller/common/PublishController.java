package com.xz.web.controller.common;

import com.xz.framework.common.base.PageInfo;
import com.xz.framework.utils.date.DateUtil;
import com.xz.web.entity.TiLucky;
import com.xz.web.entity.TiYanList;
import com.xz.web.service.TiLuckyService;
import com.xz.web.service.TiYanListService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class PublishController extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(PublishController.class);

    @Autowired
    private TiLuckyService tiLuckyService;

    @Autowired
    private TiYanListService tiYanListService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try
        {
            logger.debug("Publish Run Time=" + DateUtil.getCurrentTimestampSSS());
            //XXXXXX1
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
            //XXXXXX2
            PageInfo<TiYanList> pager2 = new PageInfo<TiYanList>();
            TiYanList searchCondition2 = new TiYanList();
            searchCondition2.setPublishStatus("0");
            pager2 = tiYanListService.findList(searchCondition2, pager2);
            List<TiYanList> list2 = pager2.getList();
            if(list.size()>0)
            {
                for(TiYanList obj:list2)
                {
                    String publishTime = obj.getPublishTime();
                    if(publishTime!=null&&publishTime.trim().length()<=10)
                        publishTime = obj.getPublishTime()+" 00:00:00";
                    int diffTime = DateUtil.compareTime(DateUtil.getCurrentTimestamp(),publishTime);
                    if(diffTime>=0)
                    {
                        obj.setPublishStatus("1");
                        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
                        tiYanListService.update(obj);
                        logger.info(DateUtil.getCurrentTimestampSSS()+"-->发布一言ID："+obj.getId());
                    }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}