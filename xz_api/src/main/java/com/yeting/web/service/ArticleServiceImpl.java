package com.yeting.web.service;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.common.Constants;
import com.yeting.framework.common.base.BaseServiceImpl;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.framework.utils.PageHelper;
import com.yeting.framework.utils.StringUtil;
import com.yeting.kafka.producer.impl.KafkaProducerImpl;
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
import scala.Int;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleExtMapper articleExtMapper;

    @Autowired
    private KafkaProducerImpl kafkaProducerImpl;
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
    @Value("#{constants.art_map_artId}")
    private String artMapArtId;

    @Value("#{constants.likeArt_artid_userid}")
    private String likeArtArtidUserid;
    @Value("#{constants.collectArt_artid_userid}")
    private String collectArtArtidUserid;
    @Value("#{constants.likeComment_artid_commentId_userid}")
    private String likeCommentArtidCommentIdUserid;

    @Override
    public PageInfo<com.yeting.web.bo.f10.F10Bo> selectMainPageFromRedis(PageInfo<com.yeting.web.bo.f10.F10Bo> page, Integer startPage, Integer pageSize) {
        PageInfo<com.yeting.web.bo.f10.F10Bo> pageInfo = new PageInfo<>();
        List<com.yeting.web.bo.f10.F10Bo> f10BoList = new ArrayList<>();
        Boolean hasNext = false;
        if(redisService.hasKey(mainIndex)){
            Long redisTotal = redisService.lGetListSize(mainIndex);
            List<String> list = new ArrayList<>();
            Integer startCount = (startPage - 1) * pageSize;
            Integer endCount = startPage * pageSize;
            list = (List) redisService.lrange(mainIndex, startCount.longValue(), endCount.longValue());
            for(int i=0; i<list.size(); i++){
                com.yeting.web.bo.f10.F10Bo f10Bo = new com.yeting.web.bo.f10.F10Bo();
                f10Bo = JsonUtil.deserialize(list.get(i), com.yeting.web.bo.f10.F10Bo.class);
                f10BoList.add(f10Bo);
            }
            if (endCount < redisTotal){
                hasNext = true;
            }
        }else {
            Integer total = articleExtMapper.selectArticleNum();
            //查询数据库
            f10BoList = this.selectF10BoList(page, startPage, pageSize);
            com.yeting.web.bo.f10.F10Bo f10Bo = new com.yeting.web.bo.f10.F10Bo();
            String f10ListJson = JsonUtil.serialize(f10BoList);
            String redisKey = mainIndex + Constants.KAFKA_FLAG + detailArtDetail + Constants.KAFKA_FLAG + detailNewComment + Constants.KAFKA_FLAG + detailHotComment + Constants.KAFKA_FLAG + f10ListJson;

            //更新到kafka，修改redis
            kafkaProducerImpl.sendAsync(Constants.REDIS_MAIN_TOPIC,Constants.UPDATE,redisKey);
            if(total > f10BoList.size()){
                hasNext = true;
            }
        }
        pageInfo.setHasNextPage(hasNext);
        pageInfo.setList(f10BoList);
        return pageInfo;
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
    public F10Bo selectDetailByArtId(Long artId, Integer pageSize, String openId, Integer hotCommentPageSize, Integer newCommentPageSize) {
        //更新数据库浏览量
        kafkaProducerImpl.sendAsync(Constants.UPDATE_MAINREADCOUNT_TOPIC,Constants.UPDATE,artId.toString());

        /**
         * 更新主界面的redis的浏览数量
         */
        String json = mainIndex + Constants.KAFKA_FLAG + mainPageSize + Constants.KAFKA_FLAG + artId;
        //更新到kafka，修改redis
        // kafkaProducerImpl.sendAsync(Constants.REDIS_MAIN_READ_TOPIC,Constants.UPDATE,json);
        if (redisService.hasKey(mainIndex)) {
            List<String> list = new ArrayList<>();
            int index = -1;
            String serializeJson = "";
            Long size = redisService.lGetListSize(mainIndex);
            list = (List) redisService.lrange(mainIndex, 0, size);
            for (int i = 0; i < list.size(); i++) {
                com.yeting.web.bo.f10.F10Bo f10Bo1 = new com.yeting.web.bo.f10.F10Bo();
                f10Bo1 = JsonUtil.deserialize(list.get(i), com.yeting.web.bo.f10.F10Bo.class);
                if (artId.equals(f10Bo1.getArtId())) {
                    index = i;
                    f10Bo1.setViewNum(f10Bo1.getViewNum() + 1);
                    serializeJson = JsonUtil.serialize(f10Bo1);
                    break;
                }
            }
            if (!StringUtil.isEmpty(serializeJson)) {
                redisService.lUpdateIndex(mainIndex, index, serializeJson);
            }
        }

        F10Bo f10Bo = new F10Bo();
        /**
         * 获取文章详细信息
         * 首先查找redis，如果没有查数据库
         */
        String detailRedisKey = detailArtDetail + "_" + artId;
        String commentRedisKey = detailNewComment + "_" + artId;
        String hotCommentRedisKey = detailHotComment + "_" + artId;

        if (redisService.hasKey(detailRedisKey)) {
            String deserializeJson = (String) redisService.get(detailRedisKey);
            f10Bo = JsonUtil.deserialize(deserializeJson, F10Bo.class);
            //修改浏览次数，更新redis
            f10Bo.setViewCount(f10Bo.getViewCount()+1);
            String likeArtArtidUserid1 = likeArtArtidUserid + "_" + artId + "_" + openId;
            //查询该用户是否点赞过该文章
            Integer flag = 0;
            if (redisService.hasKey(likeArtArtidUserid1)){
                flag = 1;
            }
            if (flag > 0) {
                f10Bo.setLike(true);
            } else {
                f10Bo.setLike(false);
            }

            String collectArtArtidUserid1 = collectArtArtidUserid + "_" + artId + "_" + openId;
            //查询该用户是否点赞过该文章
            if (redisService.hasKey(collectArtArtidUserid1)){
                flag = 1;
            }else {
                flag = 0;
            }
            if (flag > 0) {
                f10Bo.setCollection(true);
            } else {
                f10Bo.setCollection(false);
            }
            String serializeJson = JsonUtil.serialize(f10Bo);
            redisService.set(detailRedisKey, serializeJson);
        } else {
            f10Bo = articleExtMapper.selectArtDetailByArtId(artId);
            if (null != f10Bo) {
                //背景图片、评论图片、预览图关联查询
                String backgroundUrl = f10Bo.getBackgroundUrl();
                String commentUrl = f10Bo.getCommentUrl();
                String previewUrl = f10Bo.getPreview();
                Long bl = StringUtil.StrToLong(backgroundUrl);
                Long cl = StringUtil.StrToLong(commentUrl);
                Long pl = StringUtil.StrToLong(previewUrl);
                if (0L != bl) {
                    backgroundUrl = articleExtMapper.selectUrlByUrl(StringUtil.StrToLong(backgroundUrl));
                }
                if (0L != cl) {
                    commentUrl = articleExtMapper.selectUrlByUrl(StringUtil.StrToLong(commentUrl));
                }
                if (0L != pl) {
                    previewUrl = articleExtMapper.selectUrlByUrl(StringUtil.StrToLong(previewUrl));
                }
                if (StringUtil.isEmpty(backgroundUrl)) backgroundUrl = "";
                if (StringUtil.isEmpty(commentUrl)) commentUrl = "";
                if (StringUtil.isEmpty(previewUrl)) previewUrl = "";
                f10Bo.setBackgroundUrl(backgroundUrl);
                f10Bo.setCommentUrl(commentUrl);
                f10Bo.setPreview(previewUrl);
                f10Bo.setViewCount(f10Bo.getViewCount()+1);
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

                String serializeJson = JsonUtil.serialize(f10Bo);
                String json1 = detailRedisKey + Constants.KAFKA_FLAG + serializeJson;
                //更新到kafka，修改redis
                kafkaProducerImpl.sendAsync(Constants.REDIS_DETAIL_TOPIC, Constants.UPDATE, json1);
            }
        }


        if (null != f10Bo) {
            /**
             * 获取热门留言
             */
            List<F12Bo> f12BoList = new ArrayList<>();
            if (redisService.hasKey(hotCommentRedisKey)) {
                Set<String> commentSet = (Set) redisService.szReverse(hotCommentRedisKey, 0L, hotCommentPageSize.longValue());
                String newCommentRedisKey = detailNewComment + "_" + artId;
                Long newCount = redisService.lGetListSize(newCommentRedisKey);
                for (String str : commentSet) {
                    String commentId = str;
                    //查询redis的文章评论详情
                    List<String> list = (List) redisService.lrange(newCommentRedisKey, 0L, newCount);
                    for(int i=0; i<list.size(); i++) {
                        F12Bo f12Bo = new F12Bo();
                        f12Bo = JsonUtil.deserialize(list.get(i), F12Bo.class);
                        if (f12Bo.getCommentId().toString().equals(commentId)){
                            String likeCommentArtidCommentIdUserid1 = likeCommentArtidCommentIdUserid + "_" + artId + "_" + f12Bo.getCommentId() + "_" + openId;
                            if (redisService.hasKey(likeCommentArtidCommentIdUserid1)){
                                f12Bo.setLike(true);
                            }else {
                                f12Bo.setLike(false);
                            }
                            f12BoList.add(f12Bo);
                            break;
                        }
                    }
                }
            }
            f10Bo.setF12BoList(f12BoList);

            /**
             * 获取最新留言，由于表比较大，所以这里不能连接查询
             * 最新留言，每次增加留言后，向redis中插入数据，存储结构为list
             */
            List<F13Bo> f13BoList = new ArrayList<>();
            if (redisService.hasKey(commentRedisKey)) {
                List<String> list = new ArrayList<>();
                list = (List) redisService.lrange(commentRedisKey, 0, newCommentPageSize.longValue());
                for(int i=0; i<list.size(); i++){
                    F13Bo f13Bo = new F13Bo();
                    f13Bo = JsonUtil.deserialize(list.get(i), F13Bo.class);
                    String likeCommentArtidCommentIdUserid1 = likeCommentArtidCommentIdUserid + "_" + artId + "_" + f13Bo.getCommentId() + "_" + openId;
                    if (redisService.hasKey(likeCommentArtidCommentIdUserid1)){
                        f13Bo.setLike(true);
                    }else {
                        f13Bo.setLike(false);
                    }
                    f13BoList.add(f13Bo);
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
