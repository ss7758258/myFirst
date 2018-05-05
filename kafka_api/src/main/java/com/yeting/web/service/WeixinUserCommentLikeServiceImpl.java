package com.yeting.web.service;

import com.yeting.framework.common.base.BaseServiceImpl;
import com.yeting.web.mapper.entity.WeixinUserCommentLike;
import com.yeting.web.mapper.ext.CommentExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeixinUserCommentLikeServiceImpl extends BaseServiceImpl<WeixinUserCommentLike> implements WeixinUserCommentLikeService {

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Override
    public void deleteByFeedIdAndCommentId(Long feedId, Long commentId, String openId) {
        commentExtMapper.deleteByFeedIdAndCommentId(feedId, commentId, openId);
    }
}
