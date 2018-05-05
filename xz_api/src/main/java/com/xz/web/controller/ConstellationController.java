package com.xz.web.controller;

import com.xz.framework.bean.ajax.YTResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.controller.BaseController;
import com.xz.framework.exception.MessageException;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.service.ext.ConstellationService;
import com.xz.web.vo.LoginVo;
import com.xz.web.vo.f00.F00Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/constellation")
@Controller
public class ConstellationController extends BaseController {

    @Autowired
    ConstellationService constellationService;

    @RequestMapping("x10")
    @ResponseBody
    public String x10(String requestBody) {
        F00Vo obj = JsonUtil.deserialize(requestBody,F00Vo.class);
        YTResponseBody<LoginVo> responseBody = new YTResponseBody<LoginVo>();
        if(null==obj||StringUtil.isEmpty(obj.getCode()))
        {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("code为空!");
            return this.toJSON(responseBody);
        }
        try {
            responseBody = constellationService.login(obj.getCode());
        } catch (MessageException e) {
            e.printStackTrace();;
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage(e.getMessage());
        }
        return this.toJSON(responseBody);
    }
}


