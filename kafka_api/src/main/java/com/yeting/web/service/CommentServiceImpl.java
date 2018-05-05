package com.yeting.web.service;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.common.base.BaseServiceImpl;
import com.yeting.framework.common.base.BeanCriteria;
import com.yeting.framework.utils.BeanUtil;
import com.yeting.framework.utils.PageHelper;
import com.yeting.framework.utils.StringUtil;
import com.yeting.web.bo.f10.F10Bo;
import com.yeting.web.bo.f20.F70Bo;
import com.yeting.web.bo.f30.F40Bo;
import com.yeting.web.mapper.entity.Comment;
import com.yeting.web.mapper.ext.CommentExtMapper;
import com.yeting.web.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Override
    public Integer updateCommentLike(String commentId) {
        return commentExtMapper.updateCommentLike(Long.valueOf(commentId));
    }

    @Override
    public PageInfo<F70Bo> selectCommentListByArtId(Long artId, String startPage, Integer pageSize) {
        PageHelper.startPage(Integer.valueOf(startPage), Integer.valueOf(pageSize));
        List<F70Bo> f70BoList = commentExtMapper.selectCommentListByArtId(artId);
        for (int i = 0; i < f70BoList.size(); i++) {
            String contentUrl = StrUtil.urlToHttps(f70BoList.get(i).getContentUrl());
            f70BoList.get(i).setContentUrl(contentUrl);
            f70BoList.get(i).setUserName(StringUtil.Base64ToGbk(f70BoList.get(i).getUserName()));
        }
        return new PageInfo<F70Bo>(f70BoList);
    }

    @Override
    public Integer updateCommentUnLike(String commentId) {
        return commentExtMapper.updateCommentUnLike(Long.valueOf(commentId));
    }

    @Override
    public PageInfo<F40Bo> selectCommentListByOpenId(PageInfo<F40Bo> pager, String openId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<F40Bo> f40BoList = commentExtMapper.selectCommentListByOpenId(openId);
        for (int i = 0; i < f40BoList.size(); i++) {
            f40BoList.get(i).setName(StringUtil.Base64ToGbk(f40BoList.get(i).getName()));
            String title = f40BoList.get(i).getTitle();
            if(!StringUtil.isEmpty(title) && title.length() > 10){
                title = title.substring(0, 10);
            }
            f40BoList.get(i).setTitle(title);
        }
        return new PageInfo<F40Bo>(f40BoList);
    }
}
