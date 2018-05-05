package com.yeting.framework.controller;


import com.yeting.framework.bean.ajax.RequestHeader;
import com.yeting.framework.common.base.BeanCriteria;
import com.yeting.web.mapper.entity.SysUser;
import com.yeting.web.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 获取token或user
 */
public class CommonController extends BaseController {

    protected String getToken() {
        RequestHeader requestHeader = this.getRequestHeader();
        String token = requestHeader.getToken();
        return token;
    }

}
