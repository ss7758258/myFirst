package com.xz.web.service;
import com.xz.web.entity.Resource;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface ResourceService extends BaseService<Resource> {
    int add(Resource obj);
    int removeById(Integer id);
    int update(Resource obj);
    Resource getById(Integer id);
    List<Resource> getAll();
    PageInfo<Resource> findList(Resource searchCondition, PageInfo<Resource> pager);
}
