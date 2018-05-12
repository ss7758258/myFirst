package com.xz.web.controller;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.controller.BaseController;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.selectConstellation.X100Bo;
import com.xz.web.service.ext.IndexConstellationService;
import com.xz.web.utils.ResultUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页星座
 */
@RequestMapping("/indexConstellation")
@Controller
public class IndexConstellationController extends BaseController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IndexConstellationService indexConstellationService;

    /**
     * 返回首页（不用传参的，用户之前已经选择过星座）
     * @param requestBody
     * @return
     */
    @RequestMapping("x200")
    @ResponseBody
    public String x200(String requestBody) {
        XZResponseBody<X100Bo> responseBody = new XZResponseBody<X100Bo>();
        Weixin weixin = this.getWeixin();
        if (null == weixin || StringUtil.isEmpty(weixin.getOpenId())) {
            ResultUtil.returnResult(responseBody, "认证过期，请重新认证");
            return this.toJSON(responseBody);
        }
        //返回自己的星座首页
        try {
            responseBody = indexConstellationService.selectMyConstellation(weixin);
        } catch (Exception e) {
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }
}


