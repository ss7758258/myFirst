package com.xz.web.interceptor;


import com.xz.web.service.ext.SelectConstellationServiceImpl;
import com.xz.web.utils.LuckyRemindTimer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
       /* String time = "08:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + time);
        Date startTime = new Date();
        try {
            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */
        //时间间隔(一天)
        long PERIOD_DAY = 2 * 60 * 60 * 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8); //凌晨1点
        calendar.set(Calendar.MINUTE, 0 );
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime(); //第一次执行定时任务的时间
        if (date.before(new Date())) {
            date = this.addDay(date, 1);
        }
        LuckyRemindTimer recordDraftTimer = new LuckyRemindTimer(event.getServletContext());
        recordDraftTimer.setSelectConstellationServiceImpl(selectConstellationServiceImpl);
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        timer.schedule(recordDraftTimer,date,PERIOD_DAY);
        //timer.schedule(recordDraftTimer, date, 1 * 60 * 60 * 1000);
        event.getServletContext().log("已经添加任务调度表");
    }

    public void contextDestroyed(ServletContextEvent event) {
        timer.cancel();
        System.out.println("定时器销毁");
        event.getServletContext().log("定时器销毁");
    }

    // 增加或减少天数
    public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }
}
