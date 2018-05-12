package com.xz.framework.controller;


import com.xz.framework.bean.ajax.RequestHeader;

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
