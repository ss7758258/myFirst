package com.yeting.web.service;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.bean.weixin.Weixin;
import com.yeting.framework.common.base.BaseService;
import com.yeting.web.bo.f30.F20Bo;
import com.yeting.web.mapper.entity.MyCollectionView;

public interface MyCollectionViewService extends BaseService<MyCollectionView> {

    PageInfo<F20Bo> selectMyCollectionByUserId(Weixin userId, PageInfo<F20Bo> pager, Integer pageNum, Integer pageSize);
}
