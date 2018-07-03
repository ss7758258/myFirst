package com.xz.web.service;
import com.xz.web.entity.TcConstellation;
import com.xz.web.entity.TcConstellationExample;
import com.xz.web.mapper.TcConstellationMapper;
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
public class TcConstellationServiceImpl extends BaseServiceImpl<TcConstellation> implements TcConstellationService {

    @Autowired
    private TcConstellationMapper mapper;

    public TcConstellationMapper getMapper() {
        return mapper;
    }

    public void setMapper(TcConstellationMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(TcConstellation obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Long id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(TcConstellation obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public TcConstellation getById(Long id) {
        TcConstellation article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<TcConstellation> getAll() {
        TcConstellationExample example = new TcConstellationExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<TcConstellation> findList(TcConstellation searchCondition, PageInfo<TcConstellation> pager) {
        TcConstellationExample example = new TcConstellationExample();
        if (searchCondition != null) {
TcConstellationExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getConstellationName() != null) 
criteria.andConstellationNameEqualTo(searchCondition.getConstellationName());
if (searchCondition.getStartDate() != null) 
criteria.andStartDateEqualTo(searchCondition.getStartDate());
if (searchCondition.getEndDate() != null) 
criteria.andEndDateEqualTo(searchCondition.getEndDate());
if (searchCondition.getPictureUrl() != null) 
criteria.andPictureUrlEqualTo(searchCondition.getPictureUrl());
if (searchCondition.getRemark() != null) 
criteria.andRemarkEqualTo(searchCondition.getRemark());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<TcConstellation> list = mapper.selectByExample(example);
        return new PageInfo<TcConstellation>(list);
    }

}
