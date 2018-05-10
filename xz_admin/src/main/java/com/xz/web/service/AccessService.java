package com.xz.web.service;
import com.xz.web.entity.Access;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface AccessService extends BaseService<Access> {
    int add(Access obj);
    int removeById(Integer id);
    int update(Access obj);
    Access getById(Integer id);
    List<Access> getAll();
    PageInfo<Access> findList(Access searchCondition, PageInfo<Access> pager);
}
