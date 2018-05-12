package com.xz.web.service;
import com.xz.web.entity.Admin;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface AdminService extends BaseService<Admin> {
    int add(Admin obj);
    int removeById(Long id);
    int update(Admin obj);
    Admin getById(Long id);
    List<Admin> getAll();
    PageInfo<Admin> findList(Admin searchCondition, PageInfo<Admin> pager);
}
