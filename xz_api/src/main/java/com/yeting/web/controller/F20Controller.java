package com.yeting.web.controller;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.enums.AjaxStatus;
import com.yeting.framework.bean.weixin.Weixin;
import com.yeting.framework.controller.BaseController;
import com.yeting.framework.exception.MessageException;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.framework.utils.StringUtil;
import com.yeting.web.bo.f20.F10Bo;
import com.yeting.web.bo.f20.F20Bo;
import com.yeting.web.bo.f20.F40Bo;
import com.yeting.web.bo.f20.F70Bo;
import com.yeting.web.service.ext.F20Service;
import com.yeting.web.service.redis.RedisService;
import com.yeting.web.vo.f20.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@RequestMapping("/f20")
@Controller
public class F20Controller extends BaseController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private F20Service f20Service;
    @Value("#{constants.detail_pageSize}")
    private Integer pageSize;
    @Value("#{constants.detail_index}")
    private String detailIndex;
    @Value("#{constants.comment_pageSize}")
    private Integer commentPageSize;

    @Value("#{constants.hotComment_pageSize}")
    private Integer hotCommentPageSize;
    @Value("#{constants.newComment_pageSize}")
    private Integer newCommentPageSize;

    @RequestMapping("detail")
    @ResponseBody
    public String detail(String requestBody) {
        Weixin weixin = this.getWeixin();
        YTResponseBody<F10Bo> responseBody = new YTResponseBody<F10Bo>();
        F10Vo obj = JsonUtil.deserialize(requestBody, F10Vo.class);
        if (null == obj || StringUtil.isEmpty(obj.getFeedId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择具体的文章!");
            return this.toJSON(responseBody);
        }
        if (null == weixin) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("授权失败，请重新登录!");
            return this.toJSON(responseBody);
        }
        /**
         * 查看redis中是否有详情页，如果有，则取redis的，否则查数据库
         * 如果评论发生变化，则直接修改redis
         */
        String resultJson = "";
        responseBody = f20Service.selectDetailByArtId(Long.valueOf(obj.getFeedId()), pageSize, weixin, hotCommentPageSize, newCommentPageSize);
        resultJson = this.toJSON(responseBody);
        return resultJson;
    }


    @RequestMapping("sendComment")
    @ResponseBody
    public String sendComment(String requestBody) throws MessageException {
        Weixin weixin = this.getWeixin();
        YTResponseBody<F20Bo> responseBody = new YTResponseBody<F20Bo>();
        F20Vo obj = JsonUtil.deserialize(requestBody, F20Vo.class);
        Boolean isAnonymity = Boolean.FALSE;
        if (null == obj || StringUtil.isEmpty(obj.getFeedId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择文章!");
            return this.toJSON(responseBody);
        }
        if (null == obj || StringUtil.isEmpty(obj.getContent()) || 600 < obj.getContent().length()) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("评论内容为空或过长!");
            return this.toJSON(responseBody);
        }
        if (null == obj.getAnonymity()) {
            isAnonymity = Boolean.FALSE;
        } else {
            isAnonymity = obj.getAnonymity();
        }
        //String encodeNickname = new String(Base64.getEncoder().encode(obj.getCommentUserName().getBytes()));
        String encodeNickname = StringUtil.StrToBase64(obj.getCommentUserName());
        String encodeContent = StringUtil.StrToBase64(obj.getContent());
        obj.setContent(encodeContent);
        weixin.setCommentUserAvatar(obj.getCommentUserAvatar());
        weixin.setCommentUserName(encodeNickname);
        responseBody = f20Service.saveComment(obj.getFeedId(), obj.getContent(), isAnonymity, weixin);
        return this.toJSON(responseBody);
    }

    @RequestMapping("share")
    @ResponseBody
    public String share(String requestBody) {
        F10Vo obj = JsonUtil.deserialize(requestBody, F10Vo.class);
        YTResponseBody<Boolean> responseBody = new YTResponseBody<Boolean>();
        if (null == obj || StringUtil.isEmpty(obj.getFeedId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择具体的文章!");
            return this.toJSON(responseBody);
        }
        responseBody = f20Service.updateArtShare(obj.getFeedId());
        return this.toJSON(responseBody);
    }

    @RequestMapping("collect")
    @ResponseBody
    public String collect(String requestBody) {
        Weixin weixin = this.getWeixin();
        YTResponseBody<F40Bo> responseBody = new YTResponseBody<F40Bo>();
        F10Vo obj = JsonUtil.deserialize(requestBody, F10Vo.class);
        if (null == obj || null == obj.getFeedId() || StringUtil.isEmpty(obj.getFeedId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("feedId为空!");
            return this.toJSON(responseBody);
        }

        try {
            responseBody = f20Service.saveCollect(obj.getFeedId(), weixin);
        } catch (MessageException e) {
            e.printStackTrace();
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage(e.getMessage());
        }
        return this.toJSON(responseBody);
    }

    @RequestMapping("like")
    @ResponseBody
    public String like(String requestBody) {
        Weixin weixin = this.getWeixin();
        F50Vo obj = JsonUtil.deserialize(requestBody, F50Vo.class);
        YTResponseBody<Boolean> responseBody = new YTResponseBody<Boolean>();
        if (null == obj || StringUtil.isEmpty(obj.getFeedId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择具体的文章!");
            return this.toJSON(responseBody);
        }
        responseBody = f20Service.updateArtLike(obj.getFeedId(), weixin.getOpenId());
        return this.toJSON(responseBody);
    }

    @RequestMapping("likeComment")
    @ResponseBody
    public String likeComment(String requestBody) {
        Weixin weixin = this.getWeixin();
        F60Vo obj = JsonUtil.deserialize(requestBody, F60Vo.class);
        YTResponseBody<Boolean> responseBody = new YTResponseBody<Boolean>();
        if (null == obj || StringUtil.isEmpty(obj.getCommentId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择评论!");
            return this.toJSON(responseBody);
        }
        responseBody = f20Service.updateCommentLike(obj.getFeedId(), obj.getCommentId(), weixin.getOpenId());
        return this.toJSON(responseBody);
    }

    @RequestMapping("commentList")
    @ResponseBody
    public String commentList(String requestBody) {
        F70Vo obj = JsonUtil.deserialize(requestBody, F70Vo.class);
        Weixin weixin = this.getWeixin();
        YTResponseBody<PageInfo<F70Bo>> responseBody = new YTResponseBody<PageInfo<F70Bo>>();
        if (null == obj || StringUtil.isEmpty(obj.getFeedId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择具体的文章!");
            return this.toJSON(responseBody);
        }
        if (null == obj || StringUtil.isEmpty(obj.getStartPage())) {
            obj.setStartPage("1");
        }
        responseBody = f20Service.selectCommentListByArtId(Long.valueOf(obj.getFeedId()), obj.getStartPage(), commentPageSize, weixin.getOpenId());
        return this.toJSON(responseBody);
    }

    @RequestMapping("newCommentList")
    @ResponseBody
    public String newCommentList(String requestBody) {
        F70Vo obj = JsonUtil.deserialize(requestBody, F70Vo.class);
        Weixin weixin = this.getWeixin();
        YTResponseBody<PageInfo<F70Bo>> responseBody = new YTResponseBody<PageInfo<F70Bo>>();
        if (null == obj || StringUtil.isEmpty(obj.getFeedId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择具体的文章!");
            return this.toJSON(responseBody);
        }
        if (null == obj || StringUtil.isEmpty(obj.getStartPage())) {
            obj.setStartPage("1");
        }
        responseBody = f20Service.selectNewCommentListByArtId(Long.valueOf(obj.getFeedId()), obj.getStartPage(), commentPageSize, weixin.getOpenId(), newCommentPageSize);
        return this.toJSON(responseBody);
    }

    @RequestMapping("unLike")
    @ResponseBody
    public String unLike(String requestBody) {
        Weixin weixin = this.getWeixin();
        F50Vo obj = JsonUtil.deserialize(requestBody, F50Vo.class);
        YTResponseBody<Boolean> responseBody = new YTResponseBody<Boolean>();
        if (null == obj || StringUtil.isEmpty(obj.getFeedId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择具体的文章!");
            return this.toJSON(responseBody);
        }
        responseBody = f20Service.updateArtUnLike(obj.getFeedId(), weixin.getOpenId());
        return this.toJSON(responseBody);
    }


    @RequestMapping("unLikeComment")
    @ResponseBody
    public String unLikeComment(String requestBody) {
        Weixin weixin = this.getWeixin();
        F60Vo obj = JsonUtil.deserialize(requestBody, F60Vo.class);
        YTResponseBody<Boolean> responseBody = new YTResponseBody<Boolean>();
        if (null == obj || StringUtil.isEmpty(obj.getCommentId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择评论!");
            return this.toJSON(responseBody);
        }
        responseBody = f20Service.updateCommentUnLike(obj.getFeedId(), obj.getCommentId(), weixin.getOpenId());
        return this.toJSON(responseBody);
    }

    /**
     * 这个目前没用，在F30Controller中写的另一个
     *
     * @param requestBody
     * @return
     */
    @RequestMapping("unCollect")
    @ResponseBody
    public String unCollect(String requestBody) {
        Weixin weixin = this.getWeixin();
        YTResponseBody<F40Bo> responseBody = new YTResponseBody<F40Bo>();
        F10Vo obj = JsonUtil.deserialize(requestBody, F10Vo.class);
        if (null == obj || null == obj.getFeedId() || StringUtil.isEmpty(obj.getFeedId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择具体项目!");
            return this.toJSON(responseBody);
        }
       /* if (null == obj || null == obj.getId() || StringUtil.isEmpty(obj.getId())) {
            responseBody.setStatus(AjaxStatus.ERROR);
            responseBody.setMessage("请选择具体项目!");
            return this.toJSON(responseBody);
        }*/
        responseBody = f20Service.updateUnCollect(obj.getFeedId(), weixin);
        return this.toJSON(responseBody);
    }

}


