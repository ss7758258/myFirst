package com.xz.web.controller;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.controller.BaseController;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.everydayWords.X400Bo;
import com.xz.web.service.ext.EverydayWordsService;
import com.xz.web.utils.ResultUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 一言
 */
@RequestMapping("/everydayWords")
@Controller
public class EverydayWordsController extends BaseController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EverydayWordsService everydayWordsService;

    /**
     * 每日一言
     * @param requestBody
     * @return
     */
    @RequestMapping("x400")
    @ResponseBody
    public String x400(String requestBody) {
        XZResponseBody<X400Bo> responseBody = new XZResponseBody<X400Bo>();
        Weixin weixin = this.getWeixin();
        if (null == weixin || StringUtil.isEmpty(weixin.getOpenId())) {
            ResultUtil.returnResult(responseBody, "认证过期，请重新认证");
            return this.toJSON(responseBody);
        }
        /**
         *  当天时间（有英文）；
         * 星座图片、描述（是否有多条）；
         * 星座总结话；
         */
        try {
            responseBody = everydayWordsService.selectEverydayWords(weixin);
        } catch (Exception e) {
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }

    /**
     * 保存一言图片
     * @param requestBody
     * @return
     */
    @RequestMapping("x401")
    @ResponseBody
    public String x401(String requestBody) {
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        try {
            responseBody = everydayWordsService.saveEverydayWords();
        } catch (Exception e) {
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }
}


