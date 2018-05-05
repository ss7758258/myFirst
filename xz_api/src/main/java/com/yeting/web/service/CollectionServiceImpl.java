package com.yeting.web.service;

import com.yeting.framework.common.base.BaseServiceImpl;
import com.yeting.web.mapper.entity.Collection;
import com.yeting.web.mapper.ext.ArticleExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl extends BaseServiceImpl<Collection> implements CollectionService {

    @Autowired
    private ArticleExtMapper articleExtMapper;

    @Override
    public void deleteByArtIdAndOpenId(long artId, String openId) {
        articleExtMapper.deleteByArtIdAndOpenId(artId, openId);
    }

    @Override
    public Integer selectIsCollectByArtIdAndOpenId(Long artId, String openId) {
        return articleExtMapper.selectIsCollectByArtIdAndOpenId(artId, openId);
    }
}
