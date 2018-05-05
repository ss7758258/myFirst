package com.yeting.framework.common.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的BaseMapper
 * 
 * @author jackpyf
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
