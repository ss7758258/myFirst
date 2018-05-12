package com.xz.web.service;
import com.xz.web.entity.TcConstellation;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface TcConstellationService extends BaseService<TcConstellation> {
    int add(TcConstellation obj);
    int removeById(Long id);
    int update(TcConstellation obj);
    TcConstellation getById(Long id);
    List<TcConstellation> getAll();
    PageInfo<TcConstellation> findList(TcConstellation searchCondition, PageInfo<TcConstellation> pager);
}
