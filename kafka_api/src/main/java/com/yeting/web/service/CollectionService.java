package com.yeting.web.service;

import com.yeting.framework.common.base.BaseService;
import com.yeting.web.mapper.entity.Collection;

public interface CollectionService extends BaseService<Collection> {

    void deleteByArtIdAndOpenId(long l, String openId);
}
