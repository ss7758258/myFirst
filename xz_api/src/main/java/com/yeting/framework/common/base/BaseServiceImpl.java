package com.yeting.framework.common.base;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.exception.MessageException;
import com.yeting.framework.utils.PageHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * service 基本实现
 * 
 * @author jackpyf
 * 
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
public class BaseServiceImpl<T> implements BaseService<T> {

	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	private BaseMapper<T> mapper;

	public BaseMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public T selectByKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public int save(T entity) throws MessageException {
		return mapper.insert(entity);
	}

	@Override
	public int update(T entity) throws MessageException {
		return mapper.updateByPrimaryKey(entity);
	}

	@Override
	public int delete(Object key) throws MessageException {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int updateAll(T entity) throws MessageException {
		return mapper.updateByPrimaryKey(entity);
	}

	@Override
	public int updateNotNull(T entity) throws MessageException {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<T> selectByExample(BeanCriteria example) {
		return mapper.selectByExample(example);
	}

	@Override
	public PageInfo<T> selectByPage(PageInfo<T> page, BeanCriteria example) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<T> list = mapper.selectByExample(example);
		return new PageInfo<T>(list);
	}

	@Override
	public PageInfo<T> selectByPage(PageInfo<T> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<T> list = mapper.selectAll();
		return new PageInfo<T>(list);
	}

}
