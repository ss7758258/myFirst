package com.xz.web.controller;

import com.github.pagehelper.PageInfo;
import com.xz.framework.bean.ajax.RequestHeader;
import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.common.base.BeanCriteria;
import com.xz.framework.common.base.PageView;
import com.xz.framework.controller.BaseController;
import com.xz.framework.utils.DateUtil;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.everydayQian.X500Bo;
import com.xz.web.bo.everydayWords.X400Bo;
import com.xz.web.mapper.entity.TiUserQianList;
import com.xz.web.service.TiUserQianListService;
import com.xz.web.service.ext.EverydayQianService;
import com.xz.web.utils.ResultUtil;
import com.xz.web.vo.everydayQian.X510Vo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        XZResponseBody<TiUserQianList> responseBody = new XZResponseBody<TiUserQianList>();
        String currentDate = DateUtil.getDate();
        try {
            int userid =0;
            BeanCriteria beanCriteria = new BeanCriteria(TiUserQianList.class);
            BeanCriteria.Criteria criteria = beanCriteria.createCriteria();
            criteria.andEqualTo("userId", userid);
            criteria.andEqualTo("qianDate", currentDate);
            beanCriteria.setOrderByClause("update_timestamp desc");
            PageInfo<TiUserQianList> pager = new PageInfo<TiUserQianList>();
            pager = tiUserQianListService.selectByPage(pager, beanCriteria);
            List<TiUserQianList> list = pager.getList();
            if(list.size()>0)
            {
                TiUserQianList obj = list.get(0);
                if(obj.getStatus()==0)
                {
                    responseBody.setStatus(AjaxStatus.SUCCESS);
                    responseBody.setMessage("");
                    responseBody.setData(obj);
                    return this.toJSON(responseBody);
                }else
                {
                    responseBody.setStatus(AjaxStatus.ERROR);
                    responseBody.setMessage("无签");
                    responseBody.setData(obj);
                    return this.toJSON(responseBody);
                }
            }else
            {
                TiUserQianList obj = new TiUserQianList();
                obj.setQianDate(DateUtil.getDate());
                obj.setQianName("AAA");//TODO:获取签
                obj.setQianContent("BBBBB");//TODO:获取签
                obj.setStatus(0);
                obj.setUserId(0L); //TODO:后期获取id
                obj.setCreateTimestamp(DateUtil.getDatetime());
                obj.setUpdateTimestamp(DateUtil.getDatetime());
                tiUserQianListService.save(obj);
                responseBody.setStatus(AjaxStatus.SUCCESS);
                responseBody.setMessage("");
                responseBody.setData(obj);
                return this.toJSON(responseBody);
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
        try {
            RequestHeader requestHeader = this.getRequestHeader();
            X510Vo obj = JsonUtil.deserialize(requestBody, X510Vo.class);
            if (obj == null) {
                ResultUtil.returnResultLog(responseBody, "ID为空!", null, logger);
            }
            TiUserQianList data = tiUserQianListService.selectByKey(obj.getId());
            if(StringUtil.isEmpty(data.getFriendOpenId1()))
                data.setFriendOpenId1("1");
            else if(StringUtil.isEmpty(data.getFriendOpenId2()))
                data.setFriendOpenId1("2");
            else if(StringUtil.isEmpty(data.getFriendOpenId3()))
                data.setFriendOpenId1("3");
            else if(StringUtil.isEmpty(data.getFriendOpenId4()))
                data.setFriendOpenId1("4");
            else if(StringUtil.isEmpty(data.getFriendOpenId5())) {
                data.setFriendOpenId1("5");
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
        XZResponseBody<List<TiUserQianList>> responseBody = new XZResponseBody<List<TiUserQianList>>();
        try {
            RequestHeader requestHeader = this.getRequestHeader();
            int userid = 0;
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
            criteria.andEqualTo("userId", userid);
            beanCriteria.setOrderByClause("update_timestamp desc");
            PageInfo<TiUserQianList> pager = new PageInfo<TiUserQianList>();
            pager.setPageSize(obj.getPageSize());
            pager.setPageNum(obj.getPageNum());
            pager = tiUserQianListService.selectByPage(pager, beanCriteria);
            responseBody.setData(pager.getList());
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
        XZResponseBody<TiUserQianList> responseBody = new XZResponseBody<TiUserQianList>();
        try {
            RequestHeader requestHeader = this.getRequestHeader();
            X510Vo obj = JsonUtil.deserialize(requestBody, X510Vo.class);
            if (obj == null) {
                ResultUtil.returnResultLog(responseBody, "ID为空!", null, logger);
            }
            TiUserQianList data = tiUserQianListService.selectByKey(obj.getId());
            responseBody.setData(data);
        } catch (Exception e) {
            e.getMessage();
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }
}


