package com.xz.web.service;
import com.xz.web.entity.TiYanList;
import com.xz.web.entity.TiYanListExample;
import com.xz.web.mapper.TiYanListMapper;
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
public class TiYanListServiceImpl extends BaseServiceImpl<TiYanList> implements TiYanListService {

    @Autowired
    private TiYanListMapper mapper;

    public TiYanListMapper getMapper() {
        return mapper;
    }

    public void setMapper(TiYanListMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(TiYanList obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Long id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(TiYanList obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public TiYanList getById(Long id) {
        TiYanList article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<TiYanList> getAll() {
        TiYanListExample example = new TiYanListExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<TiYanList> findList(TiYanList searchCondition, PageInfo<TiYanList> pager) {
        TiYanListExample example = new TiYanListExample();
        if (searchCondition != null) {
TiYanListExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getConstellationId() != null) 
criteria.andConstellationIdEqualTo(searchCondition.getConstellationId());
if (searchCondition.getPrevPic() != null) 
criteria.andPrevPicEqualTo(searchCondition.getPrevPic());
if (searchCondition.getSpeech() != null) 
criteria.andSpeechEqualTo(searchCondition.getSpeech());
if (searchCondition.getPublishPerson() != null) 
criteria.andPublishPersonEqualTo(searchCondition.getPublishPerson());
if (searchCondition.getPublishStatus() != null) 
criteria.andPublishStatusEqualTo(searchCondition.getPublishStatus());
if (searchCondition.getPublishTime() != null) 
criteria.andPublishTimeEqualTo(searchCondition.getPublishTime());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<TiYanList> list = mapper.selectByExample(example);
        return new PageInfo<TiYanList>(list);
    }

}
