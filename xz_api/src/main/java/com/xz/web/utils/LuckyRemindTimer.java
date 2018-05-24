package com.xz.web.utils;


import com.xz.web.service.ext.SelectConstellationServiceImpl;

import javax.servlet.ServletContext;
import java.util.TimerTask;

public class LuckyRemindTimer extends TimerTask {

    private SelectConstellationServiceImpl selectConstellationServiceImpl;

    public SelectConstellationServiceImpl getSelectConstellationServiceImpl() {
        return selectConstellationServiceImpl;
    }

    public void setSelectConstellationServiceImpl(SelectConstellationServiceImpl selectConstellationServiceImpl) {
        this.selectConstellationServiceImpl = selectConstellationServiceImpl;
    }

    public LuckyRemindTimer() {
        super();
    }



    /**
     * 这个代表3点钟的时候执行任务
     */
    private static final int C_SCHEDULE_HOUR = 8;
    private static boolean isRunning = false;
    private ServletContext context = null;

    public LuckyRemindTimer(ServletContext context) {
        this.context = context;
    }

    public void run() {
        if (!isRunning) {
            isRunning = true;
            context.log("开始执行指定任务");
            selectConstellationServiceImpl.luckyRemindTimer();
            isRunning = false;
            context.log("指定任务执行结束");
        } else {
            context.log("上一次任务执行还未结束");
        }
    }
}
