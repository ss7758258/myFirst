package com.yeting.web.controller;

import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.enums.AjaxStatus;
import com.yeting.framework.controller.BaseController;
import com.yeting.framework.exception.MessageException;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.framework.utils.StringUtil;
import com.yeting.web.service.ext.F00Service;
import com.yeting.web.vo.LoginVo;
import com.yeting.web.vo.f00.F00Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/f00")
@Controller
public class F00Controller extends BaseController {

    @Autowired
    F00Service f00Service;

    @RequestMapping("login")
    @ResponseBody
    public String login(String requestBody) {
        F00Vo obj = JsonUtil.deserialize(requestBody,F00Vo.class);
        YTResponseBody<LoginVo> responseBody = new YTResponseBody<LoginVo>();
        if(null==obj||StringUtil.isEmpty(obj.getCode()))
        {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("code为空!");
            return this.toJSON(responseBody);
        }
        try {
            responseBody = f00Service.login(obj.getCode());
        } catch (MessageException e) {
            e.printStackTrace();;
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage(e.getMessage());
        }
        return this.toJSON(responseBody);
    }
}


