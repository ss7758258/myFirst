package com.yeting.framework.controller;


import com.yeting.framework.bean.ajax.RequestHeader;
import com.yeting.framework.bean.ajax.ResponseHeader;
import com.yeting.framework.bean.ajax.YTResponse;
import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.weixin.Weixin;
import com.yeting.framework.utils.BeanUtil;
import com.yeting.framework.utils.DateUtil;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.framework.utils.StringUtil;
import com.yeting.web.service.redis.RedisService;
import com.yeting.web.utils.AuthToken;
import com.yeting.web.utils.WechatUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	private static final Logger logger = Logger.getLogger(WechatUtil.class);
	@Autowired
	private RedisService redisService;

	public Weixin getWeixin() {
		Weixin weixin = new Weixin();
		requestHeader = this.getRequestHeader();
		AuthToken authToken = null;
		try
		{
			Object authTokenStr = redisService.get("TOKEN:"+requestHeader.getToken());
			authToken = JsonUtil.deserialize(authTokenStr.toString(),AuthToken.class);
		}catch (Exception e)
		{
			logger.error("getAuthToken from redis error", e);
		}
		if(null==authToken || StringUtil.isEmpty(authToken.getOpenid()))
		{
			return null;
		}
		weixin.setOpenId(authToken.getOpenid());
		return weixin;
	}
	private RequestHeader requestHeader;
	public RequestHeader getRequestHeader() {
		try {
			String jsonHeader = StringUtil.getParamString("requestHeader",this.getRequest().getParameterMap());
			if(StringUtil.isEmpty(jsonHeader))
			{
				requestHeader = new RequestHeader();
				requestHeader.setDeviceSerialNum("");
				requestHeader.setLocale("");
				requestHeader.setToken("");
				return requestHeader;
			}
			requestHeader = JsonUtil.deserialize(jsonHeader,RequestHeader.class);
			return requestHeader;
		}catch (Exception e)
		{
			requestHeader = new RequestHeader();
			requestHeader.setDeviceSerialNum("");
			requestHeader.setLocale("");
			requestHeader.setToken("");
			return requestHeader;
		}
	}
	protected HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	protected Object getAttribute(String name) {
		return getRequest().getSession().getAttribute(name);
	}
	
	protected void setAttribute(String name, Object value) {
		getRequest().getSession().setAttribute(name, value);
	}
	
	protected void invalidate() {
		getRequest().getSession().invalidate();
	}
	
	protected String toJSON(YTResponseBody responseBody) {
		ResponseHeader responseHeader = new ResponseHeader();
		BeanUtil.copyProperties(this.getRequestHeader(),responseHeader);
		YTResponse response = new YTResponse();
		response.setResponseHeader(responseHeader);
		response.setResponseBody(responseBody);
		String result = JsonUtil.serialize(response);
		System.out.println(DateUtil.getDatetime()+"--->"+result);
		return result;
	}
	
}
