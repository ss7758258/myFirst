package com.xz.web.service;
import com.xz.web.entity.TiYanList;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface TiYanListService extends BaseService<TiYanList> {
    int add(TiYanList obj);
    int removeById(Long id);
    int update(TiYanList obj);
    TiYanList getById(Long id);
    List<TiYanList> getAll();
    PageInfo<TiYanList> findList(TiYanList searchCondition, PageInfo<TiYanList> pager);
}
