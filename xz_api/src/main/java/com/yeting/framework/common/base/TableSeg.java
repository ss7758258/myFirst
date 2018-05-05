package com.yeting.framework.common.base;

import java.lang.annotation.*;

/**
 * table标记
 * 
 * @author jackpyf
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TableSeg {
	/**
	 * 表名
	 * 
	 * @return
	 */
	public String tableName();

}