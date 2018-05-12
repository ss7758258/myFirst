package com.xz.web.service;
import com.xz.web.entity.TcQianYanUrl;
import com.xz.web.entity.TcQianYanUrlExample;
import com.xz.web.mapper.TcQianYanUrlMapper;
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
public class TcQianYanUrlServiceImpl extends BaseServiceImpl<TcQianYanUrl> implements TcQianYanUrlService {

    @Autowired
    private TcQianYanUrlMapper mapper;

    public TcQianYanUrlMapper getMapper() {
        return mapper;
    }

    public void setMapper(TcQianYanUrlMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(TcQianYanUrl obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Long id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(TcQianYanUrl obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public TcQianYanUrl getById(Long id) {
        TcQianYanUrl article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<TcQianYanUrl> getAll() {
        TcQianYanUrlExample example = new TcQianYanUrlExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<TcQianYanUrl> findList(TcQianYanUrl searchCondition, PageInfo<TcQianYanUrl> pager) {
        TcQianYanUrlExample example = new TcQianYanUrlExample();
        if (searchCondition != null) {
TcQianYanUrlExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getYanUrl() != null) 
criteria.andYanUrlEqualTo(searchCondition.getYanUrl());
if (searchCondition.getQianUrl() != null) 
criteria.andQianUrlEqualTo(searchCondition.getQianUrl());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<TcQianYanUrl> list = mapper.selectByExample(example);
        return new PageInfo<TcQianYanUrl>(list);
    }

}
