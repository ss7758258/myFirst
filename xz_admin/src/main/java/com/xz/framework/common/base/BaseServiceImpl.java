package com.xz.framework.common.base;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 * service 基本实现
 *
 * @param <T>
 * @author jack
 */
@Transactional(rollbackFor = Exception.class)
public class BaseServiceImpl<T> implements BaseService<T> {

    protected Logger logger = Logger.getLogger(getClass());


}
