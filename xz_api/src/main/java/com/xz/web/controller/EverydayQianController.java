package com.xz.web.controller;

import com.github.pagehelper.PageInfo;
import com.xz.framework.bean.ajax.RequestHeader;
import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.common.base.BeanCriteria;
import com.xz.framework.controller.BaseController;
import com.xz.framework.utils.BeanUtil;
import com.xz.framework.utils.DateUtil;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.everydayQian.X500Bo;
import com.xz.web.dao.redis.RedisDao;
import com.xz.web.mapper.entity.TiQianList;
import com.xz.web.mapper.entity.TiUserQianList;
import com.xz.web.service.TiQianListService;
import com.xz.web.service.TiUserQianListService;
import com.xz.web.service.ext.EverydayQianService;
import com.xz.web.utils.ResultUtil;
import com.xz.web.vo.everydayQian.X510Vo;
import com.xz.web.vo.everydayQian.X511;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 一言
 */
@RequestMapping("/everydayQian")
@Controller
public class EverydayQianController extends BaseController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EverydayQianService everydayQianService;
    @Autowired
    TiUserQianListService tiUserQianListService;
    @Autowired
    TiQianListService tiQianListService;
    @Autowired
    private RedisDao redisService;
    /**
     * 每日一签
     * @param requestBody
     * @return
     */
    @RequestMapping("x500")
    @ResponseBody
    public String x500(String requestBody) {
        XZResponseBody<X500Bo> responseBody = new XZResponseBody<X500Bo>();
        /**
         *  当天时间（有英文）；
         * 星座图片、描述（是否有多条）；
         * 星座总结话；
         */
        try {
            responseBody = everydayQianService.selectEverydayQian();
        } catch (Exception e) {
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }

    /**
     * 保存一签图片
     * @param requestBody
     * @return
     */
    @RequestMapping("x501")
    @ResponseBody
    public String x501(String requestBody) {
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        try {
            responseBody = everydayQianService.saveEverydayQian();
        } catch (Exception e) {
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }

    /**
     * 拆签
     * @param requestBody
     * @return
     */
    @RequestMapping("x504")
    @ResponseBody
    public String x504(String requestBody) {
        XZResponseBody<X511> responseBody = new XZResponseBody<X511>();
        responseBody.setStatus(AjaxStatus.SUCCESS);
        Weixin weixin = this.getWeixin();
        if (null == weixin || StringUtil.isEmpty(weixin.getOpenId())) {
            ResultUtil.returnResult(responseBody, "认证过期，请重新认证");
            return this.toJSON(responseBody);
        }
        String useridStr = redisService.get("openId-:"+weixin.getOpenId());
        Long userId = Long.valueOf(useridStr);
        String currentDate = DateUtil.getDate();
        try {
            BeanCriteria beanCriteria = new BeanCriteria(TiUserQianList.class);
            BeanCriteria.Criteria criteria = beanCriteria.createCriteria();
            criteria.andEqualTo("userId", userId);
            criteria.andEqualTo("qianDate", currentDate);
            beanCriteria.setOrderByClause("update_timestamp desc");
            PageInfo<TiUserQianList> pager = new PageInfo<TiUserQianList>();
            pager = tiUserQianListService.selectByPage(pager, beanCriteria);
            List<TiUserQianList> list = pager.getList();
            if(list.size()>0)
            {
                TiUserQianList obj = list.get(0);
                X511 x511 = new X511();
                BeanUtil.copyProperties(obj,x511);
                String ownerOpenId = weixin.getOpenId();
                if(StringUtil.isNotEmpty(ownerOpenId))
                {
                    String ownerImage = redisService.get("headImage-:"+ownerOpenId);
                    x511.setOwnerHeadImage(ownerImage);
                    String ownerNickName = redisService.get("nickName-:"+ownerOpenId);
                    x511.setOwnerNickName(ownerNickName);
                }
                String openId1 = obj.getFriendOpenId1();
                String openId2 = obj.getFriendOpenId2();
                String openId3 = obj.getFriendOpenId3();
                String openId4 = obj.getFriendOpenId4();
                String openId5 = obj.getFriendOpenId5();
                if(StringUtil.isNotEmpty(openId1))
                {
                    String headImage1 = redisService.get("headImage-:"+openId1);
                    x511.setFriendHeadImage1(headImage1);
                }
                if(StringUtil.isNotEmpty(openId2)) {
                    String headImage2 = redisService.get("headImage-:" + openId2);
                    x511.setFriendHeadImage2(headImage2);
                }
                if(StringUtil.isNotEmpty(openId3)) {
                    String headImage3 = redisService.get("headImage-:" + openId3);
                    x511.setFriendHeadImage3(headImage3);
                }
                if(StringUtil.isNotEmpty(openId4)) {
                    String headImage4 = redisService.get("headImage-:" + openId4);
                    x511.setFriendHeadImage4(headImage4);
                }
                if(StringUtil.isNotEmpty(openId5)) {
                    String headImage5 = redisService.get("headImage-:" + openId5);
                    x511.setFriendHeadImage5(headImage5);
                }
                if(obj.getStatus()==0)
                {
                    responseBody.setStatus(AjaxStatus.SUCCESS);
                    responseBody.setMessage("");
                    responseBody.setData(x511);
                    return this.toJSON(responseBody);
                }else
                {
                    responseBody.setStatus(AjaxStatus.ERROR);
                    responseBody.setMessage("无签");
                    responseBody.setData(x511);
                    return this.toJSON(responseBody);
                }
            }else
            {
                //1 先算出QianList里面有多少个记录，比如是size
                BeanCriteria beanCriteria1 = new BeanCriteria(TiQianList.class);
                beanCriteria1.setOrderByClause("update_timestamp desc");
                PageInfo<TiQianList> pager1 = new PageInfo<TiQianList>();
                pager1.setPageSize(1);
                pager1 = tiQianListService.selectByPage(pager1,beanCriteria1);
                long count = pager1.getTotal();
                //2 取一个1-size的随机数，然后经过pageNum=随机数，size=1去取一条记录
                int randomNum = (int) (Math.random() * count);

                BeanCriteria beanCriteria2 = new BeanCriteria(TiQianList.class);
                beanCriteria2.setOrderByClause("update_timestamp desc");
                PageInfo<TiQianList> pager2 = new PageInfo<TiQianList>();
                pager2.setPageSize(1);
                pager2.setPageNum(randomNum);
                pager2 = tiQianListService.selectByPage(pager2,beanCriteria1);
                if(pager2.getList().size()>0)
                {
                    TiQianList tiQianList = pager2.getList().get(0);
                    TiUserQianList obj = new TiUserQianList();
                    obj.setQianDate(DateUtil.getDate());
                    obj.setQianName(tiQianList.getName());
                    obj.setQianContent(tiQianList.getContent());
                    obj.setStatus(0);
                    obj.setUserId(userId);
                    obj.setCreateTimestamp(DateUtil.getDatetime());
                    obj.setUpdateTimestamp(DateUtil.getDatetime());
                    tiUserQianListService.save(obj);

                    X511 x511 = new X511();
                    BeanUtil.copyProperties(obj,x511);
                    String ownerOpenId = weixin.getOpenId();
                    if(StringUtil.isNotEmpty(ownerOpenId))
                    {
                        String ownerImage = redisService.get("headImage-:"+ownerOpenId);
                        x511.setOwnerHeadImage(ownerImage);
                        String ownerNickName = redisService.get("nickName-:"+ownerOpenId);
                        x511.setOwnerNickName(ownerNickName);
                    }
                    String openId1 = obj.getFriendOpenId1();
                    String openId2 = obj.getFriendOpenId2();
                    String openId3 = obj.getFriendOpenId3();
                    String openId4 = obj.getFriendOpenId4();
                    String openId5 = obj.getFriendOpenId5();
                    if(StringUtil.isNotEmpty(openId1))
                    {
                        String headImage1 = redisService.get("headImage-:"+openId1);
                        x511.setFriendHeadImage1(headImage1);
                    }
                    if(StringUtil.isNotEmpty(openId2)) {
                        String headImage2 = redisService.get("headImage-:" + openId2);
                        x511.setFriendHeadImage2(headImage2);
                    }
                    if(StringUtil.isNotEmpty(openId3)) {
                        String headImage3 = redisService.get("headImage-:" + openId3);
                        x511.setFriendHeadImage3(headImage3);
                    }
                    if(StringUtil.isNotEmpty(openId4)) {
                        String headImage4 = redisService.get("headImage-:" + openId4);
                        x511.setFriendHeadImage4(headImage4);
                    }
                    if(StringUtil.isNotEmpty(openId5)) {
                        String headImage5 = redisService.get("headImage-:" + openId5);
                        x511.setFriendHeadImage5(headImage5);
                    }

                    responseBody.setStatus(AjaxStatus.SUCCESS);
                    responseBody.setMessage("");
                    responseBody.setData(x511);
                    return this.toJSON(responseBody);
                }else
                {
                    responseBody.setStatus(AjaxStatus.ERROR);
                    responseBody.setMessage("签已经用完!");
                    return this.toJSON(responseBody);
                }
            }
        } catch (Exception e) {
            e.getMessage();
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }

    /**
     * 解签列表
     * @param requestBody
     * @return
     */
    @RequestMapping("x506")
    @ResponseBody
    public String x506(String requestBody) {
        XZResponseBody<TiUserQianList> responseBody = new XZResponseBody<TiUserQianList>();
        responseBody.setStatus(AjaxStatus.SUCCESS);
        Weixin weixin = this.getWeixin();
        if (null == weixin || StringUtil.isEmpty(weixin.getOpenId())) {
            ResultUtil.returnResult(responseBody, "认证过期，请重新认证");
            return this.toJSON(responseBody);
        }
        String useridStr = redisService.get("openId-:"+weixin.getOpenId());
        Long userId = Long.valueOf(useridStr);
        try {
            RequestHeader requestHeader = this.getRequestHeader();
            X510Vo obj = JsonUtil.deserialize(requestBody, X510Vo.class);
            if (obj == null) {
                ResultUtil.returnResultLog(responseBody, "ID为空!", null, logger);
            }
            TiUserQianList data = tiUserQianListService.selectByKey(obj.getId());
            if(StringUtil.isEmpty(data.getFriendOpenId1()))
                data.setFriendOpenId1(weixin.getOpenId());
            else if(StringUtil.isEmpty(data.getFriendOpenId2()))
                data.setFriendOpenId2(weixin.getOpenId());
            else if(StringUtil.isEmpty(data.getFriendOpenId3()))
                data.setFriendOpenId3(weixin.getOpenId());
            else if(StringUtil.isEmpty(data.getFriendOpenId4()))
                data.setFriendOpenId4(weixin.getOpenId());
            else if(StringUtil.isEmpty(data.getFriendOpenId5())) {
                data.setFriendOpenId5(weixin.getOpenId());
                data.setStatus(1);
            }
            data.setUpdateTimestamp(DateUtil.getDatetime());
            responseBody.setData(data);
        } catch (Exception e) {
            e.getMessage();
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }

    /**
     * 解签列表
     * @param requestBody
     * @return
     */
    @RequestMapping("x510")
    @ResponseBody
    public String x510(String requestBody) {
        XZResponseBody<List<X511>> responseBody = new XZResponseBody<List<X511>>();
        Weixin weixin = this.getWeixin();
        if (null == weixin || StringUtil.isEmpty(weixin.getOpenId())) {
            ResultUtil.returnResult(responseBody, "认证过期，请重新认证");
            return this.toJSON(responseBody);
        }
        String useridStr = redisService.get("openId-:"+weixin.getOpenId());
        Long userId = Long.valueOf(useridStr);
        try {
            RequestHeader requestHeader = this.getRequestHeader();
            X510Vo obj = JsonUtil.deserialize(requestBody, X510Vo.class);
            if (obj == null) {
                obj = new X510Vo();
            }
            if (obj.getPageSize() == null) {
                obj.setPageSize(5);
            }
            if (obj.getPageNum() == null) {
                obj.setPageNum(1);
            }
            BeanCriteria beanCriteria = new BeanCriteria(TiUserQianList.class);
            BeanCriteria.Criteria criteria = beanCriteria.createCriteria();
            criteria.andEqualTo("userId", userId);
            beanCriteria.setOrderByClause("update_timestamp desc");
            PageInfo<TiUserQianList> pager = new PageInfo<TiUserQianList>();
            pager.setPageSize(obj.getPageSize());
            pager.setPageNum(obj.getPageNum());
            pager = tiUserQianListService.selectByPage(pager, beanCriteria);
            List<TiUserQianList> list = pager.getList();
            List<X511> x511List = new ArrayList<X511>();
            for(TiUserQianList data:list)
            {
                X511 x511 = new X511();
                BeanUtil.copyProperties(data,x511);
                String ownerOpenId = weixin.getOpenId();
                if(StringUtil.isNotEmpty(ownerOpenId))
                {
                    String ownerImage = redisService.get("headImage-:"+ownerOpenId);
                    x511.setOwnerHeadImage(ownerImage);
                    String ownerNickName = redisService.get("nickName-:"+ownerOpenId);
                    x511.setOwnerNickName(ownerNickName);
                }
                String openId1 = data.getFriendOpenId1();
                String openId2 = data.getFriendOpenId2();
                String openId3 = data.getFriendOpenId3();
                String openId4 = data.getFriendOpenId4();
                String openId5 = data.getFriendOpenId5();
                if(StringUtil.isNotEmpty(openId1))
                {
                    String headImage1 = redisService.get("headImage-:"+openId1);
                    x511.setFriendHeadImage1(headImage1);
                }
                if(StringUtil.isNotEmpty(openId2)) {
                    String headImage2 = redisService.get("headImage-:" + openId2);
                    x511.setFriendHeadImage2(headImage2);
                }
                if(StringUtil.isNotEmpty(openId3)) {
                    String headImage3 = redisService.get("headImage-:" + openId3);
                    x511.setFriendHeadImage3(headImage3);
                }
                if(StringUtil.isNotEmpty(openId4)) {
                    String headImage4 = redisService.get("headImage-:" + openId4);
                    x511.setFriendHeadImage4(headImage4);
                }
                if(StringUtil.isNotEmpty(openId5)) {
                    String headImage5 = redisService.get("headImage-:" + openId5);
                    x511.setFriendHeadImage5(headImage5);
                }
                x511List.add(x511);
            }
            responseBody.setData(x511List);
        } catch (Exception e) {
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }

    /**
     * 解签列表
     * @param requestBody
     * @return
     */
    @RequestMapping("x511")
    @ResponseBody
    public String x511(String requestBody) {
        XZResponseBody<X511> responseBody = new XZResponseBody<X511>();
        responseBody.setStatus(AjaxStatus.SUCCESS);
        Weixin weixin = this.getWeixin();
        if (null == weixin || StringUtil.isEmpty(weixin.getOpenId())) {
            ResultUtil.returnResult(responseBody, "认证过期，请重新认证");
            return this.toJSON(responseBody);
        }
        try {
            RequestHeader requestHeader = this.getRequestHeader();
            X510Vo obj = JsonUtil.deserialize(requestBody, X510Vo.class);
            if (obj == null) {
                ResultUtil.returnResultLog(responseBody, "ID为空!", null, logger);
            }
            TiUserQianList data = tiUserQianListService.selectByKey(obj.getId());
            X511 x511 = new X511();
            BeanUtil.copyProperties(data,x511);
            String ownerOpenId = weixin.getOpenId();
            if(StringUtil.isNotEmpty(ownerOpenId))
            {
                String ownerImage = redisService.get("headImage-:"+ownerOpenId);
                x511.setOwnerHeadImage(ownerImage);
                String ownerNickName = redisService.get("nickName-:"+ownerOpenId);
                x511.setOwnerNickName(ownerNickName);
            }
            String openId1 = data.getFriendOpenId1();
            String openId2 = data.getFriendOpenId2();
            String openId3 = data.getFriendOpenId3();
            String openId4 = data.getFriendOpenId4();
            String openId5 = data.getFriendOpenId5();
            if(StringUtil.isNotEmpty(openId1))
            {
                String headImage1 = redisService.get("headImage-:"+openId1);
                x511.setFriendHeadImage1(headImage1);
            }
            if(StringUtil.isNotEmpty(openId2)) {
                String headImage2 = redisService.get("headImage-:" + openId2);
                x511.setFriendHeadImage2(headImage2);
            }
            if(StringUtil.isNotEmpty(openId3)) {
                String headImage3 = redisService.get("headImage-:" + openId3);
                x511.setFriendHeadImage3(headImage3);
            }
            if(StringUtil.isNotEmpty(openId4)) {
                String headImage4 = redisService.get("headImage-:" + openId4);
                x511.setFriendHeadImage4(headImage4);
            }
            if(StringUtil.isNotEmpty(openId5)) {
                String headImage5 = redisService.get("headImage-:" + openId5);
                x511.setFriendHeadImage5(headImage5);
            }
            responseBody.setData(x511);
        } catch (Exception e) {
            e.getMessage();
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }
}


