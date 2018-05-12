package com.xz.web.service;
import com.xz.web.entity.TiLucky;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface TiLuckyService extends BaseService<TiLucky> {
    int add(TiLucky obj);
    int removeById(Long id);
    int update(TiLucky obj);
    TiLucky getById(Long id);
    List<TiLucky> getAll();
    PageInfo<TiLucky> findList(TiLucky searchCondition, PageInfo<TiLucky> pager);
}
