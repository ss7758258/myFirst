package com.xz.web.controller;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.controller.BaseController;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.bo.moreConstellation.X300Bo;
import com.xz.web.service.ext.MoreConstellationService;
import com.xz.web.utils.ResultUtil;
import com.xz.web.vo.selectConstellation.X100Vo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 查看更多
 */
@RequestMapping("/indexConstellation")
@Controller
public class MoreConstellationController extends BaseController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MoreConstellationService moreConstellationService;

    /**
     * 返回星座运势（更多）
     * @param requestBody
     * @return
     */
    @RequestMapping("x300")
    @ResponseBody
    public String x300(String requestBody) {
        X100Vo obj = JsonUtil.deserialize(requestBody, X100Vo.class);
        XZResponseBody<X300Bo> responseBody = new XZResponseBody<X300Bo>();
        if (null == obj || StringUtil.isEmpty(obj.getConstellationId())) {
            ResultUtil.returnResult(responseBody, "请选择星座");
            return this.toJSON(responseBody);
        }
        //返回星座运势
        try {
            responseBody = moreConstellationService.selectMoreConstellation();
        } catch (Exception e) {
            ResultUtil.returnResultLog(responseBody, "服务器异常，请稍后再试", e.getMessage(), logger);
        }finally {
            return this.toJSON(responseBody);
        }
    }
}


