package com.xz.web.service;
import com.xz.web.entity.TiQianLib;
import com.xz.web.entity.TiQianLibExample;
import com.xz.web.mapper.TiQianLibMapper;
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
public class TiQianLibServiceImpl extends BaseServiceImpl<TiQianLib> implements TiQianLibService {

    @Autowired
    private TiQianLibMapper mapper;

    public TiQianLibMapper getMapper() {
        return mapper;
    }

    public void setMapper(TiQianLibMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(TiQianLib obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Long id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(TiQianLib obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public TiQianLib getById(Long id) {
        TiQianLib article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<TiQianLib> getAll() {
        TiQianLibExample example = new TiQianLibExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<TiQianLib> findList(TiQianLib searchCondition, PageInfo<TiQianLib> pager) {
        TiQianLibExample example = new TiQianLibExample();
        if (searchCondition != null) {
TiQianLibExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getPic() != null) 
criteria.andPicEqualTo(searchCondition.getPic());
if (searchCondition.getName() != null) 
criteria.andNameEqualTo(searchCondition.getName());
if (searchCondition.getPublishTime() != null) 
criteria.andPublishTimeEqualTo(searchCondition.getPublishTime());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<TiQianLib> list = mapper.selectByExample(example);
        return new PageInfo<TiQianLib>(list);
    }

}
