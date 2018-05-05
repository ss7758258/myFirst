package com.yeting.web.utils;

import java.util.UUID;

/**
 * ID生成工具
 *
 */
public class IdUtil {

	public IdUtil() {
	}

	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
