package com.xz.web.service;
import com.xz.web.mapper.entity.Access;
import com.xz.web.mapper.entity.AccessExample;
import com.xz.web.mapper.AccessMapper;
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
public class AccessServiceImpl extends BaseServiceImpl<Access> implements AccessService {

    @Autowired
    private AccessMapper mapper;

    public AccessMapper getMapper() {
        return mapper;
    }

    public void setMapper(AccessMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(Access obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Integer id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Access obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public Access getById(Integer id) {
        Access article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<Access> getAll() {
        AccessExample example = new AccessExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<Access> findList(Access searchCondition, PageInfo<Access> pager) {
        AccessExample example = new AccessExample();
        if (searchCondition != null) {
AccessExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getResourceId() != null) 
criteria.andResourceIdEqualTo(searchCondition.getResourceId());
if (searchCondition.getResourceName() != null) 
criteria.andResourceNameEqualTo(searchCondition.getResourceName());
if (searchCondition.getResourceCode() != null) 
criteria.andResourceCodeEqualTo(searchCondition.getResourceCode());
if (searchCondition.getStatus() != null) 
criteria.andStatusEqualTo(searchCondition.getStatus());
if (searchCondition.getUserid() != null) 
criteria.andUseridEqualTo(searchCondition.getUserid());
if (searchCondition.getUsername() != null) 
criteria.andUsernameEqualTo(searchCondition.getUsername());
if (searchCondition.getAddFlag() != null) 
criteria.andAddFlagEqualTo(searchCondition.getAddFlag());
if (searchCondition.getDelFlag() != null) 
criteria.andDelFlagEqualTo(searchCondition.getDelFlag());
if (searchCondition.getUpdFlag() != null) 
criteria.andUpdFlagEqualTo(searchCondition.getUpdFlag());
if (searchCondition.getViewFlag() != null) 
criteria.andViewFlagEqualTo(searchCondition.getViewFlag());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<Access> list = mapper.selectByExample(example);
        return new PageInfo<Access>(list);
    }

}
