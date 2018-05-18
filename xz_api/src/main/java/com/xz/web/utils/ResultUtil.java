package com.xz.web.utils;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.utils.DateUtil;
import org.slf4j.Logger;

public class ResultUtil {

    /**
     *
     * @param result
     * @param errorMassage
     *
     */
    public static void returnResult(XZResponseBody result, Object errorMassage) {
        result.setStatus(AjaxStatus.ERROR);
        result.setMessage((String) errorMassage);
    }

    public static void returnResultLog(XZResponseBody result, Object errorMassage, Object logMassage , Logger logger) {
        /**
         * 当前执行线程
         */
        Thread thread =  Thread.currentThread();

        /**
         * 当前线程的所有执行方法，收集进入StackTraceElement数组
         */
        StackTraceElement[] stackTraceElement = thread.getStackTrace();

        /**
         * 【0】代表getStackTrace（）方法，
         * 【1】代表目前的returnResult（）方法，
         * 【2】代表次线程上一个执行的方法；
         */

        String methodName = stackTraceElement[2].getMethodName();
        System.out.println(methodName);
        result.setStatus(AjaxStatus.ERROR);
        result.setMessage((String) errorMassage);
        logger.error(DateUtil.getDatetime() + "----" + methodName + " error  " + (String)errorMassage, logMassage);
    }

}
