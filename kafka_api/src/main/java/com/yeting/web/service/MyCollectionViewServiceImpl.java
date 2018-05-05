package com.yeting.web.service;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.bean.weixin.Weixin;
import com.yeting.framework.common.base.BaseServiceImpl;
import com.yeting.framework.utils.PageHelper;
import com.yeting.web.bo.f30.F20Bo;
import com.yeting.web.mapper.entity.MyCollectionView;
import com.yeting.web.mapper.ext.ArticleExtMapper;
import com.yeting.web.mapper.ext.MyCollectionViewExtMapper;
import com.yeting.web.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyCollectionViewServiceImpl extends BaseServiceImpl<MyCollectionView> implements MyCollectionViewService {

    @Autowired
    private MyCollectionViewExtMapper myCollectionViewExtMapper;
    @Autowired
    private ArticleExtMapper articleExtMapper;

    @Override
    public PageInfo<F20Bo> selectMyCollectionByUserId(Weixin weixin, PageInfo<F20Bo> pager, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<F20Bo> f20BoList = new ArrayList<F20Bo>();
        String openId = weixin.getOpenId();
        f20BoList = myCollectionViewExtMapper.selectMyCollectionByUserId(openId);
        for (int i = 0; i < f20BoList.size(); i++) {
            String backgroundUrl = StrUtil.urlToHttps(f20BoList.get(i).getBackgroundUrl());
            f20BoList.get(i).setBackgroundUrl(backgroundUrl);
        }

        //查询该用户是否点赞过该文章
        for(int i=0; i<f20BoList.size(); i++) {
            Integer flag = articleExtMapper.selectArtLikeByArtIdAndUserId(f20BoList.get(i).getArtId(), openId);
            if (flag > 0) {
                f20BoList.get(i).setLike(true);
            } else {
                f20BoList.get(i).setLike(false);
            }
        }

        return new PageInfo<F20Bo>(f20BoList);
    }
}
