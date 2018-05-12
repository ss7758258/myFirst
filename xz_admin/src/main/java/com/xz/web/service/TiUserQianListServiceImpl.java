package com.xz.web.service;
import com.xz.web.entity.TiUserQianList;
import com.xz.web.entity.TiUserQianListExample;
import com.xz.web.mapper.TiUserQianListMapper;
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
public class TiUserQianListServiceImpl extends BaseServiceImpl<TiUserQianList> implements TiUserQianListService {

    @Autowired
    private TiUserQianListMapper mapper;

    public TiUserQianListMapper getMapper() {
        return mapper;
    }

    public void setMapper(TiUserQianListMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(TiUserQianList obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Long id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(TiUserQianList obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public TiUserQianList getById(Long id) {
        TiUserQianList article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<TiUserQianList> getAll() {
        TiUserQianListExample example = new TiUserQianListExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<TiUserQianList> findList(TiUserQianList searchCondition, PageInfo<TiUserQianList> pager) {
        TiUserQianListExample example = new TiUserQianListExample();
        if (searchCondition != null) {
TiUserQianListExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getQianDate() != null) 
criteria.andQianDateEqualTo(searchCondition.getQianDate());
if (searchCondition.getStatus() != null) 
criteria.andStatusEqualTo(searchCondition.getStatus());
if (searchCondition.getQianName() != null) 
criteria.andQianNameEqualTo(searchCondition.getQianName());
if (searchCondition.getQianContent() != null) 
criteria.andQianContentEqualTo(searchCondition.getQianContent());
if (searchCondition.getUserId() != null) 
criteria.andUserIdEqualTo(searchCondition.getUserId());
if (searchCondition.getFriendOpenId1() != null) 
criteria.andFriendOpenId1EqualTo(searchCondition.getFriendOpenId1());
if (searchCondition.getFriendOpenId2() != null) 
criteria.andFriendOpenId2EqualTo(searchCondition.getFriendOpenId2());
if (searchCondition.getFriendOpenId3() != null) 
criteria.andFriendOpenId3EqualTo(searchCondition.getFriendOpenId3());
if (searchCondition.getFriendOpenId4() != null) 
criteria.andFriendOpenId4EqualTo(searchCondition.getFriendOpenId4());
if (searchCondition.getFriendOpenId5() != null) 
criteria.andFriendOpenId5EqualTo(searchCondition.getFriendOpenId5());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<TiUserQianList> list = mapper.selectByExample(example);
        return new PageInfo<TiUserQianList>(list);
    }

}
