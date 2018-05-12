package com.xz.web.interceptor;

import com.xz.framework.common.base.AjaxBean;
import com.xz.framework.utils.json.JsonUtil;
import com.xz.web.constant.Constant;
import com.xz.web.entity.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView model) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        Map<String, String[]> parameters = request.getParameterMap();
        System.err.println("Json="+ JsonUtil.serialize(parameters));
        String path = request.getContextPath();
        request.getSession().setAttribute("path", path);
        Admin sessionUser = (Admin) request.getSession().getAttribute(Constant.ADMIN_SESSION);
        if(sessionUser ==null||sessionUser.getId()==null||sessionUser.getId()==0){
        	return true;
        }
        return true;
    }
    
    public String ajax(HttpServletResponse response ,String content, String type) {
        System.err.println(content);
        try {
            response.setContentType(type + ";charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String ajaxJson(HttpServletResponse response,AjaxBean<?> ajaxBean) {
        return ajax(response,JsonUtil.serialize(ajaxBean), "text/html");
    }
}
