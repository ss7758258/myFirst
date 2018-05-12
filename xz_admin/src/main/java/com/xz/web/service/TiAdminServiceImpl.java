package com.xz.web.service;
import com.xz.web.entity.TiAdmin;
import com.xz.web.entity.TiAdminExample;
import com.xz.web.mapper.TiAdminMapper;
import com.xz.framework.utils.date.DateUtil;
import com.xz.framework.common.base.BaseServiceImpl;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.utils.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TiAdminServiceImpl extends BaseServiceImpl<TiAdmin> implements TiAdminService {

    @Autowired
    private TiAdminMapper mapper;

    public TiAdminMapper getMapper() {
        return mapper;
    }

    public void setMapper(TiAdminMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(TiAdmin obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Long id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(TiAdmin obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public TiAdmin getById(Long id) {
        TiAdmin article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<TiAdmin> getAll() {
        TiAdminExample example = new TiAdminExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<TiAdmin> findList(TiAdmin searchCondition, PageInfo<TiAdmin> pager) {
        TiAdminExample example = new TiAdminExample();
        if (searchCondition != null) {
TiAdminExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getUsername() != null) 
criteria.andUsernameEqualTo(searchCondition.getUsername());
if (searchCondition.getPassword() != null) 
criteria.andPasswordEqualTo(searchCondition.getPassword());
if (searchCondition.getStatus() != null) 
criteria.andStatusEqualTo(searchCondition.getStatus());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<TiAdmin> list = mapper.selectByExample(example);
        return new PageInfo<TiAdmin>(list);
    }

}
