package com.yeting.web.service;

import com.yeting.framework.common.base.BaseServiceImpl;
import com.yeting.web.mapper.entity.WeixinUserArtLike;
import com.yeting.web.mapper.ext.ArticleExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeixinUserArtLikeServiceImpl extends BaseServiceImpl<WeixinUserArtLike> implements WeixinUserArtLikeService {

    @Autowired
    private ArticleExtMapper articleExtMapper;

    @Override
    public void deleteByArtIdAndUserId(Long artId, String openId) {
            articleExtMapper.deleteByArtIdAndUserId(artId, openId);
    }
}
