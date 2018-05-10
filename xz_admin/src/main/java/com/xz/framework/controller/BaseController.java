package com.xz.framework.controller;


import com.xz.framework.bean.ajax.RequestHeader;
import com.xz.framework.bean.ajax.ResponseHeader;
import com.xz.framework.bean.ajax.YTResponse;
import com.xz.framework.bean.ajax.YTResponseBody;
import com.xz.framework.utils.bean.BeanUtil;
import com.xz.framework.utils.date.DateUtil;
import com.xz.framework.utils.json.JsonUtil;
import com.xz.framework.utils.string.StringUtil;
import com.xz.web.utils.WechatUtil;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	private static final Logger logger = Logger.getLogger(WechatUtil.class);

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
		System.out.println(DateUtil.getCurrentTimestampSSS()+"--->"+result);
		return result;
	}
	
}
