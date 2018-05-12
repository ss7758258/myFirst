package com.xz.web.service;
import com.xz.web.entity.TiQianList;
import com.xz.web.entity.TiQianListExample;
import com.xz.web.mapper.TiQianListMapper;
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
public class TiQianListServiceImpl extends BaseServiceImpl<TiQianList> implements TiQianListService {

    @Autowired
    private TiQianListMapper mapper;

    public TiQianListMapper getMapper() {
        return mapper;
    }

    public void setMapper(TiQianListMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(TiQianList obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Long id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(TiQianList obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public TiQianList getById(Long id) {
        TiQianList article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<TiQianList> getAll() {
        TiQianListExample example = new TiQianListExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<TiQianList> findList(TiQianList searchCondition, PageInfo<TiQianList> pager) {
        TiQianListExample example = new TiQianListExample();
        if (searchCondition != null) {
TiQianListExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getName() != null) 
criteria.andNameEqualTo(searchCondition.getName());
if (searchCondition.getContent() != null) 
criteria.andContentEqualTo(searchCondition.getContent());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<TiQianList> list = mapper.selectByExample(example);
        return new PageInfo<TiQianList>(list);
    }

}
