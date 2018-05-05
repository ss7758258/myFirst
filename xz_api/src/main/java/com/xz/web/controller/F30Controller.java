package com.xz.web.controller;

import com.github.pagehelper.PageInfo;
import com.xz.framework.bean.ajax.YTResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.controller.BaseController;
import com.xz.framework.exception.MessageException;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.f30.F20Bo;
import com.xz.web.bo.f30.F40Bo;
import com.xz.web.mapper.entity.Comment;
import com.xz.web.mapper.entity.MyCollectionView;
import com.xz.web.service.ext.F30Service;
import com.xz.web.vo.PagerVo;
import com.xz.web.vo.f30.F301Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;

@RequestMapping("/f30")
@Controller
public class F30Controller extends BaseController {

    @Autowired
    F30Service f30Service;
    @Value("#{constants.detail_pageSize}")
    private Integer pageSize;
    @Value("#{constants.comment_pageSize}")
    private Integer commentPageSize;
    @Value("#{constants.collect_pageSize}")
    private Integer collectPageSize;

    @RequestMapping("myInfo")
    @ResponseBody
    public String myInfo(){
        Weixin weixin = this.getWeixin();
        YTResponseBody<Boolean> responseBody = new YTResponseBody<Boolean>();
        try {
        responseBody = f30Service.getMyInfo(weixin);
        } catch (MessageException e) {
            e.printStackTrace();
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage(e.getMessage());
        }
        return this.toJSON(responseBody);
    }
    @RequestMapping("myCollection")
    @ResponseBody
    public String myCollection(String requestBody) {
        PagerVo obj = JsonUtil.deserialize(requestBody,PagerVo.class);
        Weixin weixin = this.getWeixin();
        YTResponseBody<PageInfo<F20Bo>> responseBody = new YTResponseBody<PageInfo<F20Bo>>();
        if(obj==null||null==obj.getStartPage()||Integer.valueOf(obj.getStartPage())<1)
        {
            obj = new PagerVo();
            obj.setStartPage("1");
        }
        try {
            responseBody = f30Service.getMyCollection(weixin,obj,collectPageSize);
        } catch (MessageException e) {
            e.printStackTrace();
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage(e.getMessage());
        }
        return this.toJSON(responseBody);
    }
    @RequestMapping("deleteCollection")
    @ResponseBody
    public String deleteCollection(String requestBody) {
        Weixin weixin = this.getWeixin();
        YTResponseBody<Boolean> responseBody = new YTResponseBody<Boolean>();
        F301Vo obj = JsonUtil.deserialize(requestBody,F301Vo.class);
        if(null==obj|| null==obj.getId()||StringUtil.isEmpty(obj.getId()))
        {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择要删除的项目!");
            return this.toJSON(responseBody);
        }
        if(null==obj|| null==obj.getFeedId()||StringUtil.isEmpty(obj.getFeedId()))
        {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择要删除的项目!");
            return this.toJSON(responseBody);
        }
        try {
            Long id = Long.valueOf(obj.getId());
            Long feedId = Long.valueOf(obj.getFeedId());
            responseBody = f30Service.deleteCollection(id, feedId, weixin.getOpenId());
        } catch (MessageException e) {
            e.printStackTrace();
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage(e.getMessage());
        }
        return this.toJSON(responseBody);
    }
    @RequestMapping("myComments")
    @ResponseBody
    public String myComments(String requestBody) {
        PagerVo obj = JsonUtil.deserialize(requestBody,PagerVo.class);
        if(obj==null||null==obj.getStartPage()||Integer.valueOf(obj.getStartPage())<1)
        {
            obj = new PagerVo();
            obj.setStartPage("1");
        }
        Weixin weixin = this.getWeixin();
        YTResponseBody<PageInfo<F40Bo>> responseBody = new YTResponseBody<PageInfo<F40Bo>>();
        try {
            responseBody = f30Service.getMyComments(weixin,obj,commentPageSize);
        } catch (MessageException e) {
            e.printStackTrace();
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage(e.getMessage());
        }
        return this.toJSON(responseBody);
    }
    @RequestMapping("setComment")
    @ResponseBody
    public String setComment(String requestBody) {
        Weixin weixin = this.getWeixin();
        F301Vo obj = JsonUtil.deserialize(requestBody,F301Vo.class);
        YTResponseBody<Boolean> responseBody = new YTResponseBody<Boolean>();
        try {
            Long id = Long.valueOf(obj.getId());
            responseBody = f30Service.setComment(weixin,id);
        } catch (MessageException e) {
            e.printStackTrace();
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage(e.getMessage());
        }
        return this.toJSON(responseBody);
    }
    @RequestMapping("setUnComment")
    @ResponseBody
    public String setUnComment(String requestBody) {
        Weixin weixin = this.getWeixin();
        F301Vo obj = JsonUtil.deserialize(requestBody,F301Vo.class);
        YTResponseBody<Boolean> responseBody = new YTResponseBody<Boolean>();
        try {
            Long id = Long.valueOf(obj.getId());
            responseBody = f30Service.setUnComment(weixin,id);
        } catch (MessageException e) {
            e.printStackTrace();
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage(e.getMessage());
        }
        return this.toJSON(responseBody);
    }
    @RequestMapping("deleteComment")
    @ResponseBody
    public String deleteComment(String requestBody) {
        Weixin weixin = this.getWeixin();
        YTResponseBody<Boolean> responseBody = new YTResponseBody<Boolean>();
        F301Vo obj = JsonUtil.deserialize(requestBody,F301Vo.class);
        if(null==obj|| null==obj.getId()||StringUtil.isEmpty(obj.getId()))
        {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择要删除的项目!");
            return this.toJSON(responseBody);
        }
        try {
            Long id = Long.valueOf(obj.getId());
            Long feedId = Long.valueOf(obj.getFeedId());
            responseBody = f30Service.deleteComment(weixin,id, feedId);
        } catch (MessageException e) {
            e.printStackTrace();
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage(e.getMessage());
        }
        return this.toJSON(responseBody);
    }
}


