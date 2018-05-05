package com.xz.web.service;

import com.github.pagehelper.PageInfo;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.common.base.BaseService;
import com.xz.web.bo.f30.F20Bo;
import com.xz.web.mapper.entity.MyCollectionView;

public interface MyCollectionViewService extends BaseService<MyCollectionView> {

    PageInfo<F20Bo> selectMyCollectionByUserId(Weixin userId, PageInfo<F20Bo> pager, Integer pageNum, Integer pageSize);
}
