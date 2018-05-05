package com.xz.web.controller;

import com.xz.framework.bean.ajax.RequestHeader;
import com.xz.framework.bean.ajax.YTResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.controller.BaseController;
import com.xz.framework.exception.MessageException;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.service.FeedbackService;
import com.xz.web.service.ext.F40Service;
import com.xz.web.vo.TestVo;
import com.xz.web.vo.f00.F00Vo;
import com.xz.web.vo.f40.F40Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/f40")
@Controller
public class F40Controller extends BaseController {

    @Autowired
    F40Service f40Service;

    @RequestMapping("feedBack")
    @ResponseBody
    public String feedBack(String requestBody) {
        F40Vo obj = JsonUtil.deserialize(requestBody,F40Vo.class);
        Weixin weixin = this.getWeixin();
        String telphone = "";
        YTResponseBody<Boolean> responseBody = new YTResponseBody<Boolean>();
        if(null==obj||StringUtil.isEmpty(obj.getContent()))
        {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("反馈内容为空!");
            return this.toJSON(responseBody);
        }
        if(null!=obj && !StringUtil.isEmpty(obj.getTelphone()))
        {
            telphone = obj.getTelphone();
        }
        String encodeNickname = StringUtil.StrToBase64(obj.getCommentUserName());
        String encodeContent = StringUtil.StrToBase64(obj.getContent());
        obj.setContent(encodeContent);
        weixin.setCommentUserAvatar(obj.getCommentUserAvatar());
        weixin.setCommentUserName(encodeNickname);
        try {
            responseBody = f40Service.updateFeedBack(obj.getContent(),weixin, telphone);
        } catch (MessageException e) {
            e.printStackTrace();
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage(e.getMessage());
        }
        return this.toJSON(responseBody);
    }
}


