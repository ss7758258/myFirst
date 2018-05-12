package com.xz.web.service;
import com.xz.web.entity.WeixinUser;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface WeixinUserService extends BaseService<WeixinUser> {
    int add(WeixinUser obj);
    int removeById(Long id);
    int update(WeixinUser obj);
    WeixinUser getById(Long id);
    List<WeixinUser> getAll();
    PageInfo<WeixinUser> findList(WeixinUser searchCondition, PageInfo<WeixinUser> pager);
}
