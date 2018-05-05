package com.xz.web.service;

import com.github.pagehelper.PageInfo;
import com.xz.framework.common.base.BaseService;
import com.xz.web.bo.f20.F70Bo;
import com.xz.web.bo.f30.F40Bo;
import com.xz.web.mapper.entity.Comment;

public interface CommentService extends BaseService<Comment> {

    Integer updateCommentLike(String commentId);

    PageInfo<F70Bo> selectCommentListByArtId(Long artId, String startPage, Integer pageSize, String openId);

    Integer updateCommentUnLike(String commentId);

    PageInfo<F40Bo> selectCommentListByOpenId(PageInfo<F40Bo> pager, String openId, Integer pageNum, Integer pageSize);

    PageInfo<F70Bo> selectNewCommentListByArtId(Long artId, String startPage, Integer pageSize, String openId, Integer newCommentPageSize);
}
