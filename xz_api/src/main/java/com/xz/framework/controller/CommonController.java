package com.xz.framework.controller;


import com.xz.framework.bean.ajax.RequestHeader;
import com.xz.framework.common.base.BeanCriteria;
import com.xz.web.mapper.entity.SysUser;
import com.xz.web.service.SysUserService;
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
