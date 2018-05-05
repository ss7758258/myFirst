package com.xz.web.service;

import com.xz.framework.common.base.BaseService;
import com.xz.web.mapper.entity.Collection;

public interface CollectionService extends BaseService<Collection> {

    void deleteByArtIdAndOpenId(long l, String openId);

    Integer selectIsCollectByArtIdAndOpenId(Long aLong, String openId);
}
