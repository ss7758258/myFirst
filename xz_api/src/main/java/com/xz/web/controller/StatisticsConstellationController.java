package com.xz.web.controller;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.controller.BaseController;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.selectConstellation.X100Bo;
import com.xz.web.service.ext.SelectConstellationService;
import com.xz.web.service.ext.StatisticsConstellationService;
import com.xz.web.utils.ResultUtil;
import com.xz.web.vo.selectConstellation.X100Vo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统计
 */
@RequestMapping("statisticsConstellation")
@Controller
public class StatisticsConstellationController extends BaseController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StatisticsConstellationService statisticsConstellationService;


    /**
     * 运势保存次数redis
     * @return
     */
    @RequestMapping("x600")
    @ResponseBody
    public String x600() {
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        responseBody = statisticsConstellationService.x600();
        return this.toJSON(responseBody);
    }

    /**
     * 运势二维码进来次数redis
     * @return
     */
    @RequestMapping("x601")
    @ResponseBody
    public String x601() {
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        responseBody = statisticsConstellationService.x601();
        return this.toJSON(responseBody);
    }

    /**
     * 一签分享好友次数redis, 人均分享次数
     * @return
     */
    @RequestMapping("x602")
    @ResponseBody
    public String x602() {
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        responseBody = statisticsConstellationService.x602();
        return this.toJSON(responseBody);
    }

    /**
     * 一签分享引流次数redis
     * @return
     */
    @RequestMapping("x603")
    @ResponseBody
    public String x603() {
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        responseBody = statisticsConstellationService.x603();
        return this.toJSON(responseBody);
    }

    /**
     * 一签图片保存次数redis
     * @return
     */
    @RequestMapping("x604")
    @ResponseBody
    public String x604() {
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        responseBody = statisticsConstellationService.x604();
        return this.toJSON(responseBody);
    }

    /**
     * 一签二维码引流次数redis
     * @return
     */
    @RequestMapping("x605")
    @ResponseBody
    public String x605() {
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        responseBody = statisticsConstellationService.x605();
        return this.toJSON(responseBody);
    }

    /**
     * 一言图片保存次数redis
     * @return
     */
    @RequestMapping("x606")
    @ResponseBody
    public String x606() {
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        responseBody = statisticsConstellationService.x606();
        return this.toJSON(responseBody);
    }

    /**
     * 一言图片二维码引流次数redis
     * @return
     */
    @RequestMapping("x607")
    @ResponseBody
    public String x607() {
        XZResponseBody<String> responseBody = new XZResponseBody<String>();
        responseBody = statisticsConstellationService.x607();
        return this.toJSON(responseBody);
    }



}


