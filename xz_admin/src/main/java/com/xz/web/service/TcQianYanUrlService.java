package com.xz.web.service;
import com.xz.web.entity.TcQianYanUrl;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface TcQianYanUrlService extends BaseService<TcQianYanUrl> {
    int add(TcQianYanUrl obj);
    int removeById(Long id);
    int update(TcQianYanUrl obj);
    TcQianYanUrl getById(Long id);
    List<TcQianYanUrl> getAll();
    PageInfo<TcQianYanUrl> findList(TcQianYanUrl searchCondition, PageInfo<TcQianYanUrl> pager);
}
