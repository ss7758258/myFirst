package com.xz.web.interceptor;

import com.xz.framework.common.base.AjaxBean;
import com.xz.framework.common.base.AjaxStatus;
import com.xz.framework.utils.json.JsonUtil;
import com.xz.web.common.SessionUser;
import com.xz.web.constant.Constant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
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
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/resource/login.html";
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(Constant.ADMIN_SESSION);

        if(sessionUser ==null||sessionUser.getUserUid()==null||sessionUser.getUserUid().trim().equals("")){
        	request.getRequestDispatcher(basePath);
        	return false;
        }
        
        List<String> list =sessionUser.getUserUrlList();
        System.err.println(request.getRequestURI());
        boolean flag = false;
        for(int i=0;i<list.size();i++){
        	if(list.get(i)!=null&&request.getRequestURI().indexOf(list.get(i))>=0){
        		flag =true;
        		break;
        	}
        }
        if(flag){
        	return true;
        }else{
        	System.err.println("Addr:"+request.getRequestURI());
        	AjaxBean<String> ajaxBean = new AjaxBean<String>();
            ajaxBean.setStatus(AjaxStatus.ERROR);
            ajaxBean.setMessage("没有按钮操作权限");
            ajaxBean.setData("没有该操作权限");
            ajaxJson(response,ajaxBean);
            return false;
        }
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
