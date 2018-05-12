package com.xz.web.controller;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.controller.BaseController;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.loginConstellation.X000Bo;
import com.xz.web.service.ext.LoginConstellationService;
import com.xz.web.utils.ResultUtil;
import com.xz.web.vo.loginConstellation.X000Vo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class LoginConstellationController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LoginConstellationService loginConstellationService;

    @RequestMapping("login")
    @ResponseBody
    public String login(String requestBody) {
        X000Vo obj = JsonUtil.deserialize(requestBody, X000Vo.class);
        XZResponseBody<X000Bo> responseBody = new XZResponseBody<X000Bo>();
        if (null == obj || StringUtil.isEmpty(obj.getCode())) {
            ResultUtil.returnResult(responseBody, "code为空");
            return this.toJSON(responseBody);
        }
        try {
            responseBody = loginConstellationService.saveWeixinUser(obj.getCode());
        } catch (Exception e) {
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }
}
