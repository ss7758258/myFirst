package com.yeting.web.service;

import com.yeting.framework.common.base.BaseService;
import com.yeting.web.mapper.entity.WeixinUserCommentLike;

public interface WeixinUserCommentLikeService extends BaseService<WeixinUserCommentLike> {

    void deleteByFeedIdAndCommentId(Long aLong, Long aLong1, String userId);
}
