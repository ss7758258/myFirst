package com.xz.web.service;
import com.xz.web.entity.TiQianList;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface TiQianListService extends BaseService<TiQianList> {
    int add(TiQianList obj);
    int removeById(Long id);
    int update(TiQianList obj);
    TiQianList getById(Long id);
    List<TiQianList> getAll();
    PageInfo<TiQianList> findList(TiQianList searchCondition, PageInfo<TiQianList> pager);
}
