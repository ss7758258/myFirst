package com.xz.web.service;

import com.github.pagehelper.PageInfo;
import com.xz.framework.common.base.BaseService;
import com.xz.web.bo.f20.F10Bo;
import com.xz.web.bo.f20.F13Bo;
import com.xz.web.mapper.entity.Article;

public interface ArticleService extends BaseService<Article> {

    F10Bo selectDetailByArtId(Long artId, Integer pageSize, String userId, Integer hotCommentPageSize, Integer newCommentPageSize);

    Integer updateArtLike(String feedId);

    void updateArtCollectionByArtId(long l);

    Integer updateArtShare(String feedId);

    Integer updateArtUnLike(String feedId);

    void updateArtUnCollectionByArtId(long l);

    F13Bo selectNewCommnetById(Long aLong, Long commentId, String userId);

    PageInfo<com.xz.web.bo.f10.F10Bo> selectMainPageFromRedis(PageInfo<com.xz.web.bo.f10.F10Bo> page, Integer startPage, Integer pageSize);
}
