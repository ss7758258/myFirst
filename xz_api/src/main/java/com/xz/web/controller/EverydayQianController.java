package com.xz.web.controller;

import com.github.pagehelper.PageInfo;
import com.xz.framework.bean.ajax.RequestHeader;
import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.common.base.BeanCriteria;
import com.xz.framework.common.base.PageView;
import com.xz.framework.controller.BaseController;
import com.xz.framework.utils.JsonUtil;
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


