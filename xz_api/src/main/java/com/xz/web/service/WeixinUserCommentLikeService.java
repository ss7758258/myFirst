package com.xz.web.service;

import com.xz.framework.common.base.BaseService;
import com.xz.web.mapper.entity.WeixinUserCommentLike;

public interface WeixinUserCommentLikeService extends BaseService<WeixinUserCommentLike> {

    void deleteByFeedIdAndCommentId(Long aLong, Long aLong1, String userId);
}
