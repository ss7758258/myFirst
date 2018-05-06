package com.xz.web.service;
import com.xz.web.mapper.entity.Admin;
import com.xz.web.mapper.entity.AdminExample;
import com.xz.web.mapper.AdminMapper;
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
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    private AdminMapper mapper;

    public AdminMapper getMapper() {
        return mapper;
    }

    public void setMapper(AdminMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(Admin obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Integer id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Admin obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public Admin getById(Integer id) {
        Admin article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<Admin> getAll() {
        AdminExample example = new AdminExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<Admin> findList(Admin searchCondition, PageInfo<Admin> pager) {
        AdminExample example = new AdminExample();
        if (searchCondition != null) {
AdminExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getUsername() != null) 
criteria.andUsernameEqualTo(searchCondition.getUsername());
if (searchCondition.getPassword() != null) 
criteria.andPasswordEqualTo(searchCondition.getPassword());
if (searchCondition.getStatus() != null) 
criteria.andStatusEqualTo(searchCondition.getStatus());
if (searchCondition.getSex() != null) 
criteria.andSexEqualTo(searchCondition.getSex());
if (searchCondition.getContact() != null) 
criteria.andContactEqualTo(searchCondition.getContact());
if (searchCondition.getTitle() != null) 
criteria.andTitleEqualTo(searchCondition.getTitle());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<Admin> list = mapper.selectByExample(example);
        return new PageInfo<Admin>(list);
    }

}
