package com.yeting.web.service;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.common.base.BaseService;
import com.yeting.web.bo.f20.F10Bo;
import com.yeting.web.bo.f20.F13Bo;
import com.yeting.web.mapper.entity.Article;

public interface ArticleService extends BaseService<Article> {

    PageInfo<com.yeting.web.bo.f10.F10Bo> selectMainPage(PageInfo<com.yeting.web.bo.f10.F10Bo> page, Integer startPage, Integer pageSize);

    F10Bo selectDetailByArtId(Long artId, Integer pageSize, String userId);

    Integer updateArtLike(String feedId);

    void updateArtCollectionByArtId(long l);

    Integer updateArtShare(String feedId);

    Integer updateArtUnLike(String feedId);

    void updateArtUnCollectionByArtId(long l);

    F13Bo selectNewCommnetById(Long aLong, Long commentId, String userId);

    PageInfo<com.yeting.web.bo.f10.F10Bo> selectMainPageFromRedis(PageInfo<com.yeting.web.bo.f10.F10Bo> page, Integer startPage, Integer pageSize);
}
