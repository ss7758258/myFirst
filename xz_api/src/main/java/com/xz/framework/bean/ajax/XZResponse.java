package com.xz.framework.bean.ajax;


import java.io.Serializable;

/**
 * ajax请求返回Bean
 * @author jackpyf
 *
 * @param <T>
 */
public class XZResponse<T> implements Serializable{

	private ResponseHeader responseHeader;
	private XZResponseBody<T> responseBody;

	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public XZResponseBody<T> getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(XZResponseBody<T> responseBody) {
		this.responseBody = responseBody;
	}
}
