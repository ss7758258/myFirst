package com.yeting.web.service;

import com.yeting.framework.common.base.BaseService;
import com.yeting.web.mapper.entity.WeixinUserArtLike;

public interface WeixinUserArtLikeService extends BaseService<WeixinUserArtLike> {

    void deleteByArtIdAndUserId(Long aLong, String userId);
}
