package com.xz.framework.common.base;

import com.xz.framework.utils.json.JsonUtil;
import com.xz.framework.utils.web.WebUtility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * base controller
 *
 * @author jack
 */
public class BaseController {

    protected static final String VIEWS = "admin/";

    protected Logger logger = Logger.getLogger(getClass());


    public boolean Base(String selector) {
        return true;
    }

    /**
     * 获取request操作对象
     *
     * @return
     */
    protected HttpServletRequest getRequest() {
        return WebUtility.getRequest();
    }

    /**
     * 获取request值
     *
     * @param key
     * @return
     */
    protected Object getRequestAttribute(String key) {
        return getRequest().getAttribute(key);
    }

    /**
     * 设置request值
     *
     * @param key
     * @param value
     */
    protected void setRequestAttribute(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    /**
     * 获取session操作对象
     *
     * @return
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取session值
     *
     * @return
     */
    protected Object getSessionAttribute(String key) {
        return getRequest().getSession().getAttribute(key);
    }

    /**
     * remove session值
     *
     * @return
     */
    protected void removeSessionAttribute(String key) {
        getRequest().getSession().removeAttribute(key);
    }

    /**
     * 设置session值
     *
     * @param key
     * @param value
     */
    protected void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 获取response操作对象
     *
     * @return
     */
    protected HttpServletResponse getResponse() {
        return WebUtility.getResponse();
    }

    public String ajax(String content, String type) {
        System.err.println(content);
        try {
            HttpServletResponse response = this.getResponse();
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


    public String ajaxJson(AjaxBean<?> ajaxBean) {
        return ajax(JsonUtil.serialize(ajaxBean), "text/html");
    }
}
