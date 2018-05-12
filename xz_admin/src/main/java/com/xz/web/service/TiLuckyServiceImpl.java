package com.xz.web.service;
import com.xz.web.entity.TiLucky;
import com.xz.web.entity.TiLuckyExample;
import com.xz.web.mapper.TiLuckyMapper;
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
public class TiLuckyServiceImpl extends BaseServiceImpl<TiLucky> implements TiLuckyService {

    @Autowired
    private TiLuckyMapper mapper;

    public TiLuckyMapper getMapper() {
        return mapper;
    }

    public void setMapper(TiLuckyMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(TiLucky obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Long id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(TiLucky obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public TiLucky getById(Long id) {
        TiLucky article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<TiLucky> getAll() {
        TiLuckyExample example = new TiLuckyExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<TiLucky> findList(TiLucky searchCondition, PageInfo<TiLucky> pager) {
        TiLuckyExample example = new TiLuckyExample();
        if (searchCondition != null) {
TiLuckyExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getConstellationId() != null) 
criteria.andConstellationIdEqualTo(searchCondition.getConstellationId());
if (searchCondition.getLuckyType1() != null) 
criteria.andLuckyType1EqualTo(searchCondition.getLuckyType1());
if (searchCondition.getLuckyType2() != null) 
criteria.andLuckyType2EqualTo(searchCondition.getLuckyType2());
if (searchCondition.getLuckyType3() != null) 
criteria.andLuckyType3EqualTo(searchCondition.getLuckyType3());
if (searchCondition.getLuckyType4() != null) 
criteria.andLuckyType4EqualTo(searchCondition.getLuckyType4());
if (searchCondition.getLuckyScore1() != null) 
criteria.andLuckyScore1EqualTo(searchCondition.getLuckyScore1());
if (searchCondition.getLuckyScore2() != null) 
criteria.andLuckyScore2EqualTo(searchCondition.getLuckyScore2());
if (searchCondition.getLuckyScore3() != null) 
criteria.andLuckyScore3EqualTo(searchCondition.getLuckyScore3());
if (searchCondition.getLuckyScore4() != null) 
criteria.andLuckyScore4EqualTo(searchCondition.getLuckyScore4());
if (searchCondition.getRemindToday() != null) 
criteria.andRemindTodayEqualTo(searchCondition.getRemindToday());
if (searchCondition.getLuckyTypeMore1() != null) 
criteria.andLuckyTypeMore1EqualTo(searchCondition.getLuckyTypeMore1());
if (searchCondition.getLuckyTypeMore2() != null) 
criteria.andLuckyTypeMore2EqualTo(searchCondition.getLuckyTypeMore2());
if (searchCondition.getLuckyTypeMore3() != null) 
criteria.andLuckyTypeMore3EqualTo(searchCondition.getLuckyTypeMore3());
if (searchCondition.getLuckyTypeMore4() != null) 
criteria.andLuckyTypeMore4EqualTo(searchCondition.getLuckyTypeMore4());
if (searchCondition.getLuckyScoreMore1() != null) 
criteria.andLuckyScoreMore1EqualTo(searchCondition.getLuckyScoreMore1());
if (searchCondition.getLuckyScoreMore2() != null) 
criteria.andLuckyScoreMore2EqualTo(searchCondition.getLuckyScoreMore2());
if (searchCondition.getLuckyScoreMore3() != null) 
criteria.andLuckyScoreMore3EqualTo(searchCondition.getLuckyScoreMore3());
if (searchCondition.getLuckyScoreMore4() != null) 
criteria.andLuckyScoreMore4EqualTo(searchCondition.getLuckyScoreMore4());
if (searchCondition.getLuckyWords1() != null) 
criteria.andLuckyWords1EqualTo(searchCondition.getLuckyWords1());
if (searchCondition.getLuckyWords2() != null) 
criteria.andLuckyWords2EqualTo(searchCondition.getLuckyWords2());
if (searchCondition.getLuckyWords3() != null) 
criteria.andLuckyWords3EqualTo(searchCondition.getLuckyWords3());
if (searchCondition.getLuckyWords4() != null) 
criteria.andLuckyWords4EqualTo(searchCondition.getLuckyWords4());
if (searchCondition.getToDo() != null) 
criteria.andToDoEqualTo(searchCondition.getToDo());
if (searchCondition.getNotDo() != null) 
criteria.andNotDoEqualTo(searchCondition.getNotDo());
if (searchCondition.getPublishTime() != null) 
criteria.andPublishTimeEqualTo(searchCondition.getPublishTime());
if (searchCondition.getStatus() != null) 
criteria.andStatusEqualTo(searchCondition.getStatus());
if (searchCondition.getLuckyDate() != null) 
criteria.andLuckyDateEqualTo(searchCondition.getLuckyDate());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<TiLucky> list = mapper.selectByExample(example);
        return new PageInfo<TiLucky>(list);
    }

}
