package com.xz.web.interceptor;


import com.xz.web.service.ext.SelectConstellationServiceImpl;
import com.xz.web.utils.LuckyRemindTimer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LuckyRemindListener implements ServletContextListener {

    public LuckyRemindListener() {
    }

    private java.util.Timer timer = null;

    public void contextInitialized(ServletContextEvent event) {
        /**
         * 设置一个定时器
         */
        timer = new java.util.Timer(true);
        event.getServletContext().log("定时器已启动");

        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        SelectConstellationServiceImpl selectConstellationServiceImpl = (SelectConstellationServiceImpl) wac.getBean("selectConstellationServiceImpl");

        /**
         * 定时器到指定的时间时,执行某个操作(如某个类,或方法)
         */
        //后边最后一个参数代表监视器的监视周期,现在为四个小时
        //timer.schedule(new RecordDraftTimer(event.getServletContext()), 60 * 60 * 1000, 4 * 60 * 60 * 1000);
        String time = "21:59:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + time);
        Date startTime = new Date();
        try {
            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LuckyRemindTimer recordDraftTimer = new LuckyRemindTimer(event.getServletContext());
        recordDraftTimer.setSelectConstellationServiceImpl(selectConstellationServiceImpl);
        timer.schedule(recordDraftTimer, startTime, 60 * 1000);
        event.getServletContext().log("已经添加任务调度表");
    }

    public void contextDestroyed(ServletContextEvent event) {
        timer.cancel();
        System.out.println("定时器销毁");
        event.getServletContext().log("定时器销毁");
    }
}
