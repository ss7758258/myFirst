package com.xz.web.service;
import com.xz.web.entity.TiQianLib;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface TiQianLibService extends BaseService<TiQianLib> {
    int add(TiQianLib obj);
    int removeById(Long id);
    int update(TiQianLib obj);
    TiQianLib getById(Long id);
    List<TiQianLib> getAll();
    PageInfo<TiQianLib> findList(TiQianLib searchCondition, PageInfo<TiQianLib> pager);
}
