package im.admin.generator.utils.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/** */

/**
 * 访问控制注解
 */
public class MySession {

    public String getSessionByKey(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(key);
    }

    public void setSessionByKey(HttpServletRequest request, String key, String value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

}