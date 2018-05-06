package com.xz.web.utils.web;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * web context工具类
 *
 * @author jack
 */
public class WebUtility {

    public WebUtility() {
    }

    public static String getBasePath() {
        HttpServletRequest request = getRequest();
        String path = request.getContextPath();
        String basePath = (new StringBuilder()).append(request.getScheme()).append("://").append(request.getServerName()).append(":")
                .append(request.getServerPort()).append(path).append("/").toString();
        return basePath;
    }

    public static String getNameSpace() {
        HttpServletRequest request = getRequest();
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String namespace = requestUri.substring(contextPath.length(), requestUri.lastIndexOf("/"));
        return namespace;
    }

    public static String getSuffix() {
        HttpServletRequest request = getRequest();
        String requestUri = request.getRequestURI();
        String suffix = requestUri.substring(requestUri.lastIndexOf("/"));
        return suffix;
    }

    public static String getNameSpaceAndSuffix() {
        HttpServletRequest request = getRequest();
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String ns = requestUri.substring(contextPath.length());
        return ns;
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    public static int dateTruncatedCompareTo(Date bdate, Date smdate) {
        Integer days = Integer.valueOf(0);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / 0x5265c00L;
            days = Integer.valueOf(Integer.parseInt(String.valueOf(between_days)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days.intValue();
    }
}
