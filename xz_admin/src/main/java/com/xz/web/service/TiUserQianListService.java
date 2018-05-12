package com.xz.web.service;
import com.xz.web.entity.TiUserQianList;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface TiUserQianListService extends BaseService<TiUserQianList> {
    int add(TiUserQianList obj);
    int removeById(Long id);
    int update(TiUserQianList obj);
    TiUserQianList getById(Long id);
    List<TiUserQianList> getAll();
    PageInfo<TiUserQianList> findList(TiUserQianList searchCondition, PageInfo<TiUserQianList> pager);
}
