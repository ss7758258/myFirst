package com.xz.web.service;

import com.xz.framework.common.base.BaseServiceImpl;
import com.xz.web.mapper.entity.WeixinUserArtLike;
import com.xz.web.mapper.ext.ArticleExtMapper;
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
