package com.xz.web.service;
import com.xz.web.mapper.entity.Example;
import com.xz.web.mapper.entity.ExampleExample;
import com.xz.web.mapper.ExampleMapper;
import com.xz.web.utils.date.DateUtil;
import com.xz.framework.common.base.BaseServiceImpl;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.utils.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExampleServiceImpl extends BaseServiceImpl<Example> implements ExampleService {

    @Autowired
    private ExampleMapper mapper;

    public ExampleMapper getMapper() {
        return mapper;
    }

    public void setMapper(ExampleMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(Example obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Integer id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Example obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public Example getById(Integer id) {
        Example article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<Example> getAll() {
        ExampleExample example = new ExampleExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<Example> findList(Example searchCondition, PageInfo<Example> pager) {
        ExampleExample example = new ExampleExample();
        if (searchCondition != null) {
ExampleExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getUsername() != null) 
criteria.andUsernameEqualTo(searchCondition.getUsername());
if (searchCondition.getPassword() != null) 
criteria.andPasswordEqualTo(searchCondition.getPassword());
if (searchCondition.getAge() != null) 
criteria.andAgeEqualTo(searchCondition.getAge());
if (searchCondition.getStatus() != null) 
criteria.andStatusEqualTo(searchCondition.getStatus());
if (searchCondition.getSex() != null) 
criteria.andSexEqualTo(searchCondition.getSex());
if (searchCondition.getBrif() != null) 
criteria.andBrifEqualTo(searchCondition.getBrif());
if (searchCondition.getUrl() != null) 
criteria.andUrlEqualTo(searchCondition.getUrl());
if (searchCondition.getTitle() != null) 
criteria.andTitleEqualTo(searchCondition.getTitle());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
if (searchCondition.getFile() != null) 
criteria.andFileEqualTo(searchCondition.getFile());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<Example> list = mapper.selectByExample(example);
        return new PageInfo<Example>(list);
    }

}
