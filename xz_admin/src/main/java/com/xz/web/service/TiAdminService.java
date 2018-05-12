package com.xz.web.service;
import com.xz.web.entity.TiAdmin;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface TiAdminService extends BaseService<TiAdmin> {
    int add(TiAdmin obj);
    int removeById(Long id);
    int update(TiAdmin obj);
    TiAdmin getById(Long id);
    List<TiAdmin> getAll();
    PageInfo<TiAdmin> findList(TiAdmin searchCondition, PageInfo<TiAdmin> pager);
}
