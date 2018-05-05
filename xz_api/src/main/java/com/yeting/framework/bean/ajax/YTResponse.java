package com.yeting.framework.bean.ajax;


import com.yeting.framework.bean.enums.AjaxStatus;

import java.io.Serializable;

/**
 * ajax请求返回Bean
 * @author jackpyf
 *
 * @param <T>
 */
public class YTResponse<T> implements Serializable{

	private ResponseHeader responseHeader;
	private YTResponseBody<T> responseBody;

	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public YTResponseBody<T> getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(YTResponseBody<T> responseBody) {
		this.responseBody = responseBody;
	}
}
