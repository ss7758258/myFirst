package com.yeting.web.controller;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.controller.BaseController;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.framework.utils.StringUtil;
import com.yeting.web.bo.f10.F10Bo;
import com.yeting.web.service.ext.F10Service;
import com.yeting.web.service.redis.RedisService;
import com.yeting.web.vo.f10.F10Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/f10")
@Controller
public class F10Controller extends BaseController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private F10Service f10Service;
    @Value("#{constants.main_pageSize}")
    private Integer pageSize;
    @Value("#{constants.main_index}")
    private String mainIndex;

    /**
     * 首页，可以不传起始页，下拉后还是调用该接口
     *
     * @param requestBody
     * @return
     */
    @RequestMapping("main")
    @ResponseBody
    public String selectMain(String requestBody) {
        F10Vo obj = new F10Vo();
        if(StringUtil.isNotEmpty(requestBody))
            obj = JsonUtil.deserialize(requestBody, F10Vo.class);
        else
        {
            obj.setStartPage("1");
        }
        YTResponseBody<PageInfo<F10Bo>> responseBody = new YTResponseBody<PageInfo<F10Bo>>();
        String startPage = "1";
        String resultJson = "";
        if (obj == null) {
            obj = new F10Vo();
        }
        if (!StringUtil.isEmpty(obj.getStartPage())) {
            startPage = obj.getStartPage();
        }
        /**
         * 查询redis，redis没有查数据库
         */
        responseBody = f10Service.selectMain(Integer.valueOf(startPage), pageSize);
        resultJson = this.toJSON(responseBody);
        return resultJson;
    }
}



