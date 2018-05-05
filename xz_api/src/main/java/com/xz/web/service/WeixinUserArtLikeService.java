package com.xz.web.service;

import com.xz.framework.common.base.BaseService;
import com.xz.web.mapper.entity.WeixinUserArtLike;

public interface WeixinUserArtLikeService extends BaseService<WeixinUserArtLike> {

    void deleteByArtIdAndUserId(Long aLong, String userId);
}
