package com.yeting.framework.bean.ajax;

import java.io.Serializable;

/**
 * 返回结果header封装
 */
public class ResponseHeader implements Serializable {
	public String deviceSerialNum;
	public String locale;
	public String token;

	public String getDeviceSerialNum() {
		return deviceSerialNum;
	}

	public void setDeviceSerialNum(String deviceSerialNum) {
		this.deviceSerialNum = deviceSerialNum;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
