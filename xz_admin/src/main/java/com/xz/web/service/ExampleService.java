package com.xz.web.service;
import com.xz.web.entity.Example;
import com.xz.framework.common.base.PageInfo;
import com.xz.framework.common.base.BaseService;
import java.util.List;

public interface ExampleService extends BaseService<Example> {
    int add(Example obj);
    int removeById(Long id);
    int update(Example obj);
    Example getById(Long id);
    List<Example> getAll();
    PageInfo<Example> findList(Example searchCondition, PageInfo<Example> pager);
}