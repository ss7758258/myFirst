package com.xz.web.service;
import com.xz.web.mapper.entity.Resource;
import com.xz.web.mapper.entity.ResourceExample;
import com.xz.web.mapper.ResourceMapper;
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
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService {

    @Autowired
    private ResourceMapper mapper;

    public ResourceMapper getMapper() {
        return mapper;
    }

    public void setMapper(ResourceMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(Resource obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Integer id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Resource obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public Resource getById(Integer id) {
        Resource article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<Resource> getAll() {
        ResourceExample example = new ResourceExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<Resource> findList(Resource searchCondition, PageInfo<Resource> pager) {
        ResourceExample example = new ResourceExample();
        if (searchCondition != null) {
ResourceExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getResourceName() != null) 
criteria.andResourceNameEqualTo(searchCondition.getResourceName());
if (searchCondition.getResourceCode() != null) 
criteria.andResourceCodeEqualTo(searchCondition.getResourceCode());
if (searchCondition.getStatus() != null) 
criteria.andStatusEqualTo(searchCondition.getStatus());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<Resource> list = mapper.selectByExample(example);
        return new PageInfo<Resource>(list);
    }

}
