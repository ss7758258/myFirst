package com.xz.framework.controller;


import com.xz.framework.bean.ajax.RequestHeader;
import com.xz.framework.bean.ajax.ResponseHeader;
import com.xz.framework.bean.ajax.XZResponse;
import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.utils.BeanUtil;
import com.xz.framework.utils.DateUtil;
import com.xz.framework.utils.JsonUtil;
import com.xz.framework.utils.StringUtil;
import com.xz.web.dao.redis.RedisDao;
import com.xz.web.utils.AuthToken;
import com.xz.web.utils.WechatUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	private static final Logger logger = Logger.getLogger(WechatUtil.class);
	@Autowired
	private RedisDao redisService;

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
	
	protected String toJSON(XZResponseBody responseBody) {
		ResponseHeader responseHeader = new ResponseHeader();
		BeanUtil.copyProperties(this.getRequestHeader(),responseHeader);
		XZResponse response = new XZResponse();
		response.setResponseHeader(responseHeader);
		response.setResponseBody(responseBody);
		String result = JsonUtil.serialize(response);
		System.out.println(DateUtil.getDatetime()+"--->"+result);
		return result;
	}
	
}
