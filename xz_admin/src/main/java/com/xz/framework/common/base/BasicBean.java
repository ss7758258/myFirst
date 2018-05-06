package com.xz.framework.common.base;


import com.xz.framework.utils.JsonUtil;

import java.io.Serializable;


/**
 * 实体超类
 * 
 */
public class BasicBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public String toString() {
		return JsonUtil.serialize(this);
	}

}
