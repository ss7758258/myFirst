package com.xz.web.controller;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.controller.BaseController;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.selectConstellation.X100Bo;
import com.xz.web.service.ext.SelectConstellationService;
import com.xz.web.utils.ResultUtil;
import com.xz.web.vo.selectConstellation.X100Vo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 选择星座
 */
@RequestMapping("/selectConstellation")
@Controller
public class QRCodeConstellationController extends BaseController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SelectConstellationService selectConstellationService;

    /**
     * 选择星座后返回首页
     * @param requestBody
     * @return
     */
    @RequestMapping("x100")
    @ResponseBody
    public String x100(String requestBody) {
        X100Vo obj = JsonUtil.deserialize(requestBody, X100Vo.class);
        XZResponseBody<X100Bo> responseBody = new XZResponseBody<X100Bo>();
        if (null == obj || null == obj.getConstellationId()) {
            ResultUtil.returnResult(responseBody, "请选择星座");
            return this.toJSON(responseBody);
        }
        if (null == obj || StringUtil.isEmpty(obj.getNickName())) {
            ResultUtil.returnResult(responseBody, "网络异常，获取用户昵称失败");
            return this.toJSON(responseBody);
        }
        if (null == obj || StringUtil.isEmpty(obj.getHeadImage())) {
            ResultUtil.returnResult(responseBody, "网络异常，获取用户昵称失败");
            return this.toJSON(responseBody);
        }
        Weixin weixin = this.getWeixin();
        if (null == weixin || StringUtil.isEmpty(weixin.getOpenId())) {
            ResultUtil.returnResult(responseBody, "认证过期，请重新认证");
            return this.toJSON(responseBody);
        }
        String encodeNickname = StringUtil.StrToBase64(obj.getNickName());
        obj.setNickName(encodeNickname);
        //插入自己的星座，同时返回星座首页
        try {
            responseBody = selectConstellationService.saveConstellation(obj, weixin);
        } catch (Exception e) {
            ResultUtil.returnResultLog(responseBody, "网络异常，更新异常", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }
}


