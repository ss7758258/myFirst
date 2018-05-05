package com.yeting.web.service;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.common.base.BaseServiceImpl;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.framework.utils.PageHelper;
import com.yeting.framework.utils.StringUtil;
import com.yeting.web.bo.f20.F13Bo;
import com.yeting.web.bo.f20.F70Bo;
import com.yeting.web.bo.f30.F40Bo;
import com.yeting.web.mapper.entity.Comment;
import com.yeting.web.mapper.ext.ArticleExtMapper;
import com.yeting.web.mapper.ext.CommentExtMapper;
import com.yeting.web.service.redis.RedisService;
import com.yeting.web.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {

    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private ArticleExtMapper articleExtMapper;

    @Autowired
    private RedisService redisService;

    @Value("#{constants.detail_newComment}")
    private String detailNewComment;
    @Value("#{constants.detail_hotComment}")
    private String detailHotComment;
    @Value("#{constants.hotComment_pageSize}")
    private String hotCommentPageSize;
    @Value("#{constants.likeComment_artid_commentId_userid}")
    private String likeCommentArtidCommentIdUserid;


    @Override
    public Integer updateCommentLike(String commentId) {
        return commentExtMapper.updateCommentLike(Long.valueOf(commentId));
    }

    @Override
    public PageInfo<F70Bo> selectCommentListByArtId(Long artId, String startPage, Integer pageSize, String openId) {
        PageInfo<F70Bo> pageInfo = new PageInfo<F70Bo>();
        Boolean hasNext = false;
        String commentRedisKey = detailHotComment + "_" + artId;
        String newCommentRedisKey = detailNewComment + "_" + artId;
        List<F70Bo> f70BoList = new ArrayList<>();
        //这里调用的是第一页开始的，也就是30条
        if (redisService.hasKey(commentRedisKey)) {
            Long commentTotal = redisService.szGetSise(commentRedisKey);
            /*Integer startCount = Integer.valueOf(hotCommentPageSize) + (Integer.valueOf(startPage) - 2) * pageSize;
            Integer endCount = Integer.valueOf(hotCommentPageSize) + (Integer.valueOf(startPage) - 1) * pageSize;*/
            Integer startCount = (Integer.valueOf(startPage) - 1) * pageSize;
            Integer endCount = (Integer.valueOf(startPage)) * pageSize;
            Set<Object> commentSet = redisService.szReverse(commentRedisKey, startCount.longValue(), endCount.longValue());
            Long newCount = redisService.lGetListSize(newCommentRedisKey);
            for (Object str : commentSet) {
                String commentId = (String)str;
                //查询redis的文章评论详情
                List<String> list = (List) redisService.lrange(newCommentRedisKey, 0L, newCount);
                for(int i=0; i<list.size(); i++) {
                    F70Bo f70Bo = new F70Bo();
                    f70Bo = JsonUtil.deserialize(list.get(i), F70Bo.class);
                    if (f70Bo.getCommentId().toString().equals(commentId)){
                        //查询自己是否点赞过
                        String isLikeComment = likeCommentArtidCommentIdUserid + "_" + artId + "_" + commentId + "_" + openId;
                        if (redisService.hasKey(isLikeComment)){
                            f70Bo.setLike(true);
                        }else {
                            f70Bo.setLike(false);
                        }
                        f70BoList.add(f70Bo);
                        break;
                    }
                }
            }
            if (commentTotal > endCount){
                hasNext = true;
            }
            pageInfo.setHasNextPage(hasNext);
        }
        return new PageInfo<F70Bo>(f70BoList);
    }

    @Override
    public PageInfo<F70Bo> selectNewCommentListByArtId(Long artId, String startPage, Integer pageSize, String openId, Integer newCommentPageSize) {
        PageInfo<F70Bo> pageInfo = new PageInfo<F70Bo>();
        Boolean hasNext = false;
        String commentRedisKey = detailNewComment + "_" + artId;
        List<F70Bo> f70BoList = new ArrayList<>();
        //这里调用的是第二页开始的，也就是30条
        if (redisService.hasKey(commentRedisKey)) {
            List<String> list = new ArrayList<>();
            Integer startCount = newCommentPageSize + (Integer.valueOf(startPage) - 2) * pageSize;
            Integer endCount = newCommentPageSize + (Integer.valueOf(startPage) - 1) * pageSize;
            list = (List) redisService.lrange(commentRedisKey, startCount.longValue(), endCount.longValue());
            for(int i=0; i<list.size(); i++){
                F70Bo f17Bo = new F70Bo();
                f17Bo = JsonUtil.deserialize(list.get(i), F70Bo.class);
                f70BoList.add(f17Bo);
            }
            Long redisCount = redisService.lGetListSize(commentRedisKey);
            if (redisCount > endCount){
                hasNext = true;
            }
            pageInfo.setHasNextPage(hasNext);
        }
        return pageInfo;
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
            if (null != f40BoList.get(i)) {
                f40BoList.get(i).setName(StringUtil.Base64ToStr(f40BoList.get(i).getName()));
                f40BoList.get(i).setContent(StringUtil.Base64ToStr(f40BoList.get(i).getContent()));
                String title = f40BoList.get(i).getTitle();
                if (!StringUtil.isEmpty(title) && title.length() > 10) {
                    title = title.substring(0, 10);
                }
                f40BoList.get(i).setTitle(title);
            }
        }
        return new PageInfo<F40Bo>(f40BoList);
    }
}
