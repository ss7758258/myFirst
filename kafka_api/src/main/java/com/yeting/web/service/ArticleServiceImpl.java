package com.yeting.web.service;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.common.base.BaseServiceImpl;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.framework.utils.PageHelper;
import com.yeting.framework.utils.StringUtil;
import com.yeting.web.bo.f20.F10Bo;
import com.yeting.web.bo.f20.F12Bo;
import com.yeting.web.bo.f20.F13Bo;
import com.yeting.web.mapper.ArticleMapper;
import com.yeting.web.mapper.entity.Article;
import com.yeting.web.mapper.ext.ArticleExtMapper;
import com.yeting.web.service.redis.RedisService;
import com.yeting.web.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleExtMapper articleExtMapper;

    @Autowired
    private RedisService redisService;

    @Value("#{constants.detail_artDetail}")
    private String detailArtDetail;
    @Value("#{constants.detail_hotComment}")
    private String detailHotComment;
    @Value("#{constants.detail_newComment}")
    private String detailNewComment;
    @Value("#{constants.main_index}")
    private String mainIndex;
    @Value("#{constants.main_pageSize}")
    private Integer mainPageSize;

    @Override
    public PageInfo<com.yeting.web.bo.f10.F10Bo> selectMainPage(PageInfo<com.yeting.web.bo.f10.F10Bo> page, Integer startPage, Integer pageSize) {
        List<com.yeting.web.bo.f10.F10Bo> f10BoList = this.selectF10BoList(page, startPage, pageSize);
        return new PageInfo<com.yeting.web.bo.f10.F10Bo>(f10BoList);
    }

    public List<com.yeting.web.bo.f10.F10Bo> selectF10BoList(PageInfo<com.yeting.web.bo.f10.F10Bo> page, Integer startPage, Integer pageSize){
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<com.yeting.web.bo.f10.F10Bo> f10BoList = articleExtMapper.selectMainPage();
        if (null == f10BoList) {
            f10BoList = new ArrayList<>();
        }
        for (int i = 0; i < f10BoList.size(); i++) {
            String backgroundUrl = StrUtil.urlToHttps(f10BoList.get(i).getBackgroundUrl());
            String commentUrl = StrUtil.urlToHttps(f10BoList.get(i).getCommentUrl());
            String preview = StrUtil.urlToHttps(f10BoList.get(i).getPreview());
            f10BoList.get(i).setBackgroundUrl(backgroundUrl);
            f10BoList.get(i).setCommentUrl(commentUrl);
            f10BoList.get(i).setPreview(preview);
        }
        return f10BoList;
    }

    @Override
    public PageInfo<com.yeting.web.bo.f10.F10Bo> selectMainPageFromRedis(PageInfo<com.yeting.web.bo.f10.F10Bo> page, Integer startPage, Integer pageSize) {
        List<com.yeting.web.bo.f10.F10Bo> f10BoList = new ArrayList<>();
        if(redisService.hasKey(mainIndex)){
            List<String> list = new ArrayList<>();
            list = (List) redisService.lrange(mainIndex, 0, pageSize.longValue());
            for(int i=0; i<list.size(); i++){
                com.yeting.web.bo.f10.F10Bo f10Bo = new com.yeting.web.bo.f10.F10Bo();
                f10Bo = JsonUtil.deserialize(list.get(i), com.yeting.web.bo.f10.F10Bo.class);
                f10BoList.add(f10Bo);
            }
        }else {
            f10BoList = this.selectF10BoList(page, startPage, pageSize);
            //放入到redis
            if (null != f10BoList && f10BoList.size() > 0) {
                for (com.yeting.web.bo.f10.F10Bo f10Bo : f10BoList) {
                    String serializeJson = JsonUtil.serialize(f10Bo);
                    redisService.lrSet(mainIndex, serializeJson);
                }
            }
        }
        return new PageInfo<com.yeting.web.bo.f10.F10Bo>(f10BoList);
    }

    @Override
    public F10Bo selectDetailByArtId(Long artId, Integer pageSize, String openId) {
        //更新浏览量
        articleExtMapper.updateViewNumByArtId(artId);
        F10Bo f10Bo = new F10Bo();
        /**
         * 获取文章详细信息
         * 首先查找redis，如果没有查数据库
         */
        String detailRedisKey = detailArtDetail + "_" + artId;
        String commentRedisKey = detailNewComment + "_" + artId;

        if (redisService.hasKey(detailRedisKey)) {
            String deserializeJson = (String) redisService.get(detailRedisKey);
            f10Bo = JsonUtil.deserialize(deserializeJson, F10Bo.class);
            //修改浏览次数，更新redis
            f10Bo.setViewCount(f10Bo.getViewCount()+1);
            String serializeJson = JsonUtil.serialize(f10Bo);
            redisService.set(detailRedisKey, serializeJson);
        } else {
            f10Bo = articleExtMapper.selectArtDetailByArtId(artId);
            String serializeJson = JsonUtil.serialize(f10Bo);
            redisService.set(detailRedisKey, serializeJson);
        }

        /**
         * 更新主界面的redis的浏览数量
         */
        if (redisService.hasKey(mainIndex)) {
            List<String> list = new ArrayList<>();
            int index = -1;
            String serializeJson = "";
            list = (List) redisService.lrange(mainIndex, 0, mainPageSize.longValue()-1);
            for(int i=0; i<list.size(); i++){
                com.yeting.web.bo.f10.F10Bo f10Bo1 = new com.yeting.web.bo.f10.F10Bo();
                f10Bo1 = JsonUtil.deserialize(list.get(i), com.yeting.web.bo.f10.F10Bo.class);
                if(artId.equals(f10Bo1.getArtId())){
                    index = i;
                    f10Bo1.setViewNum(f10Bo1.getViewNum()+1);
                    serializeJson = JsonUtil.serialize(f10Bo1);
                }
            }
            redisService.lUpdateIndex(mainIndex, index, serializeJson);
        }

        //查询该用户是否点赞过该文章
        Integer flag = articleExtMapper.selectArtLikeByArtIdAndUserId(artId, openId);
        if (flag > 0) {
            f10Bo.setLike(true);
        } else {
            f10Bo.setLike(false);
        }
        flag = articleExtMapper.selectCollectionByArtIdAndUserId(artId, openId);
        if (flag > 0) {
            f10Bo.setCollection(true);
        } else {
            f10Bo.setCollection(false);
        }
        if (null != f10Bo) {
            //获取精选留言，不用做
/*            List<F11Bo> f11BoList = articleExtMapper.selectTopCommnetByArtId(artId, pageSize);
            f10Bo.setF11BoList(f11BoList);*/
            /**
             * 获取热门留言  这里由于只有三次，所以查询三次数据库，由于表比较大，所以这里不能连接查询
             * 由于热门留言是根据留言的浏览量来判断，所以这个不能从redis中获得
             */
            List<F12Bo> f12BoList = articleExtMapper.selectHotCommnetByArtId(artId, pageSize);
            if (null != f12BoList && f12BoList.size() > 0) {
                for (F12Bo f12Bo : f12BoList) {
                    f12Bo.setUserName(StringUtil.Base64ToGbk(f12Bo.getUserName()));
                    flag = articleExtMapper.selectCommentLikeByArtIdAndCommentIdAndUserId(artId, f12Bo.getCommentId(), openId);
                    if (flag > 0) {
                        f12Bo.setLike(true);
                    } else {
                        f12Bo.setLike(false);
                    }
                    String userHead = StrUtil.urlToHttps(f12Bo.getUserHead());
                    f12Bo.setUserHead(userHead);
                }
                f10Bo.setF12BoList(f12BoList);
            }

            /**
             * 获取最新留言  这里由于只有三次，所以查询三次数据库，由于表比较大，所以这里不能连接查询
             * 最新留言，每次增加留言后，向redis中插入数据，存储结构为list
             */
            List<F13Bo> f13BoList = new ArrayList<>();
            if (redisService.hasKey(commentRedisKey)) {
                List<String> list = new ArrayList<>();
                list = (List) redisService.lrange(commentRedisKey, 0, pageSize.longValue());
                //String deserializeJson = (String) redisService.get(detailNewComment);
                for(int i=0; i<list.size(); i++){
                    F13Bo f13Bo = new F13Bo();
                    f13Bo = JsonUtil.deserialize(list.get(i), F13Bo.class);
                    f13BoList.add(f13Bo);
                }
                //f13BoList = JsonUtil.deserializeList(list, F13Bo.class);
               // f13BoList = (List)redisService.get(detailNewComment);
            } else {
                f13BoList = articleExtMapper.selectNewCommnetByArtId(artId, pageSize);
                if (null != f13BoList && f13BoList.size() > 0) {
                    for (F13Bo f13Bo : f13BoList) {
                        f13Bo.setUserName(StringUtil.Base64ToGbk(f13Bo.getUserName()));
                        String serializeJson = JsonUtil.serialize(f13Bo);
                        redisService.lrSet(commentRedisKey, serializeJson);
                        flag = articleExtMapper.selectCommentLikeByArtIdAndCommentIdAndUserId(artId, f13Bo.getCommentId(), openId);
                        if (flag > 0) {
                            f13Bo.setLike(true);
                        } else {
                            f13Bo.setLike(false);
                        }
                        String userHead = StrUtil.urlToHttps(f13Bo.getUserHead());
                        f13Bo.setUserHead(userHead);
                    }
                }
            }
            f10Bo.setF13BoList(f13BoList);
        }
        return f10Bo;
    }

    @Override
    public Integer updateArtLike(String feedId) {
        return articleExtMapper.updateArtLike(Long.valueOf(feedId));
    }

    @Override
    public void updateArtCollectionByArtId(long feedId) {
        articleExtMapper.updateArtCollectionByArtId(Long.valueOf(feedId));
    }

    @Override
    public Integer updateArtShare(String feedId) {
        return articleExtMapper.updateArtShare(Long.valueOf(feedId));
    }


    private String intToDecimal(Integer viewCount) {
        String result = "0";
        if (0 <= viewCount && 100000 > viewCount) {
            result = viewCount.toString();
        } else {
            int i = viewCount.toString().length();
            result = Math.pow(10, i - 1) + "+";
        }
      /*  if(0 <= viewCount && 10000 < viewCount){
            result = viewCount.toString();
        }else if(10000 <= viewCount) {
            DecimalFormat df = new DecimalFormat("#.00");
            Double c = 1.0 * viewCount / 10000;
            String s = df.format(viewCount);
            result =  s.substring(0, s.length() - 1);
        }*/
        return result;
    }

    @Override
    public Integer updateArtUnLike(String feedId) {
        return articleExtMapper.updateArtUnLike(Long.valueOf(feedId));
    }

    @Override
    public void updateArtUnCollectionByArtId(long feedId) {
        articleExtMapper.updateArtUnCollectionByArtId(feedId);
    }

    @Override
    public F13Bo selectNewCommnetById(Long artId, Long commentId, String openId) {
        F13Bo f13Bo = articleExtMapper.selectNewCommnetById(commentId);
        int flag = 0;
        if (null != f13Bo) {
            flag = articleExtMapper.selectCommentLikeByArtIdAndCommentIdAndUserId(artId, commentId, openId);
            if (flag > 0) {
                f13Bo.setLike(true);
            } else {
                f13Bo.setLike(false);
            }
            String userHead = StrUtil.urlToHttps(f13Bo.getUserHead());
            f13Bo.setUserHead(userHead);
        }
        return f13Bo;
    }
}
