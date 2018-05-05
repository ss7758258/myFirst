package com.yeting.framework.exception;

/**
 * 自定异常信息接收类
 * 
 * @author jackpyf
 *
 */
public class MessageException extends Exception {

	private static final long serialVersionUID = 1L;

	public MessageException(Exception exception) {
		super(exception.getMessage(), exception.getCause());
	}

	public MessageException(String message) {
		super(message);
	}
}
