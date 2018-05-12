package com.xz.web.interceptor;

import com.xz.framework.bean.ajax.RequestHeader;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.dao.redis.RedisDao;
import com.xz.web.utils.AuthToken;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AccessInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(AccessInterceptor.class);

    @Autowired
    private RedisDao redisService;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        try {
            /*if(!validateHeader(request))
            {
                return false;
            }*/
            if (handler instanceof HandlerMethod) {
                if(!validateHeader(request))
                {
                    return true;
                }
                HandlerMethod h = (HandlerMethod) handler;
                String jsonBody = StringUtil.getParamString("requestBody", request.getParameterMap());
//                if (StringUtil.isEmpty(jsonBody)) {
//                    StringBuilder sb = new StringBuilder(1000);
//                    sb.append("DateTime: ").append(DateUtil.getDatetime()).append("\n");
//                    sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
//                    sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
//                    sb.append("Params    : ").append(StringUtil.getParamString(request.getParameterMap())).append("\n");
//                    sb.append("URI       : ").append(request.getRequestURI());
//                    System.out.println(sb.toString());
//                } else {
//                    request.setAttribute("requestBody", jsonBody);
//                    StringBuilder sb = new StringBuilder(1000);
//                    sb.append("DateTime: ").append(DateUtil.getDatetime()).append("\n");
//                    sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
//                    sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
//                    sb.append("Params    : ").append(StringUtil.getParamString(request.getParameterMap())).append("\n");
//                    sb.append("URI       : ").append(request.getRequestURI());
//                    System.out.println(sb.toString());
//                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validateHeader(HttpServletRequest request) {
        try {
            String jsonHeader = StringUtil.getParamString("requestHeader", request.getParameterMap());
            RequestHeader requestHeader = JsonUtil.deserialize(jsonHeader, RequestHeader.class);
            if(StringUtil.isEmpty(requestHeader.getToken()))
            {
                logger.error("requestHeader token is null-->"+ JsonUtil.serialize(requestHeader));
                return false;
            }
            AuthToken authToken = null;
            try
            {
                Object authTokenStr = redisService.get("TOKEN:"+requestHeader.getToken());
                authToken = JsonUtil.deserialize(authTokenStr.toString(),AuthToken.class);
            }catch (Exception e)
            {
                logger.error("getAuthToken from redis error", e);
            }
            if(null==authToken||StringUtil.isEmpty(authToken.getOpenid()))
            {
                logger.error("authToken token is null-->"+ JsonUtil.serialize(authToken));
                return false;
            }
            //可以继续验证token的有效性。
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void writeAjaxJson(HttpServletResponse response, String message) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(message);
        } catch (IOException e) {

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }


}
