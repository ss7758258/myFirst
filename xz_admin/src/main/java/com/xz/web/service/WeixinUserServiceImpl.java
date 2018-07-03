package com.xz.web.service;
import com.xz.web.entity.WeixinUser;
import com.xz.web.entity.WeixinUserExample;
import com.xz.web.mapper.WeixinUserMapper;
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
public class WeixinUserServiceImpl extends BaseServiceImpl<WeixinUser> implements WeixinUserService {

    @Autowired
    private WeixinUserMapper mapper;

    public WeixinUserMapper getMapper() {
        return mapper;
    }

    public void setMapper(WeixinUserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(WeixinUser obj) {
        obj.setCreateTimestamp(DateUtil.getCurrentTimestamp());
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.insert(obj);
    }

    @Override
    public int removeById(Long id) {

        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(WeixinUser obj) {
        obj.setUpdateTimestamp(DateUtil.getCurrentTimestamp());
        return mapper.updateByPrimaryKey(obj);
    }

    @Override
    public WeixinUser getById(Long id) {
        WeixinUser article = mapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public List<WeixinUser> getAll() {
        WeixinUserExample example = new WeixinUserExample();
        example.setOrderByClause("update_timestamp desc");
        return mapper.selectByExample(example);
    }

    @Override
    public PageInfo<WeixinUser> findList(WeixinUser searchCondition, PageInfo<WeixinUser> pager) {
        WeixinUserExample example = new WeixinUserExample();
        if (searchCondition != null) {
WeixinUserExample.Criteria criteria = example.createCriteria();
if (searchCondition.getId() != null) 
criteria.andIdEqualTo(searchCondition.getId());
if (searchCondition.getConstellationId() != null) 
criteria.andConstellationIdEqualTo(searchCondition.getConstellationId());
if (searchCondition.getOpenId() != null) 
criteria.andOpenIdEqualTo(searchCondition.getOpenId());
if (searchCondition.getUserName() != null) 
criteria.andUserNameEqualTo(searchCondition.getUserName());
if (searchCondition.getNickName() != null) 
criteria.andNickNameEqualTo(searchCondition.getNickName());
if (searchCondition.getPhoneNo() != null) 
criteria.andPhoneNoEqualTo(searchCondition.getPhoneNo());
if (searchCondition.getIsDisabled() != null) 
criteria.andIsDisabledEqualTo(searchCondition.getIsDisabled());
if (searchCondition.getHeadImage() != null) 
criteria.andHeadImageEqualTo(searchCondition.getHeadImage());
if (searchCondition.getGender() != null) 
criteria.andGenderEqualTo(searchCondition.getGender());
if (searchCondition.getPasswd() != null) 
criteria.andPasswdEqualTo(searchCondition.getPasswd());
if (searchCondition.getAddress() != null) 
criteria.andAddressEqualTo(searchCondition.getAddress());
if (searchCondition.getCreateTimestamp() != null) 
criteria.andCreateTimestampEqualTo(searchCondition.getCreateTimestamp());
if (searchCondition.getUpdateTimestamp() != null) 
criteria.andUpdateTimestampEqualTo(searchCondition.getUpdateTimestamp());
}

        PageHelper.startPage(pager.getPageNum(), pager.getPageSize());
        example.setOrderByClause("update_timestamp desc");
        List<WeixinUser> list = mapper.selectByExample(example);
        return new PageInfo<WeixinUser>(list);
    }

}
