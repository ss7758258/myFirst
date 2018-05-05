package com.yeting.web.service.consumerkafka;

import com.yeting.framework.common.Constants;
import com.yeting.framework.exception.MessageException;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.framework.utils.StringUtil;
import com.yeting.web.bo.f10.F10Bo;
import com.yeting.web.bo.f20.F13Bo;
import com.yeting.web.mapper.entity.*;
import com.yeting.web.mapper.ext.ArticleExtMapper;
import com.yeting.web.service.*;
import com.yeting.web.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ConsumerKafka {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private WeixinUserArtLikeService weixinUserArtLikeService;
    @Autowired
    private WeixinUserCommentLikeService weixinUserCommentLikeService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private ArticleExtMapper articleExtMapper;

    @Autowired
    private RedisService redisService;
    @Autowired
    private WeixinUserService weixinUserService;

    /**
     * 评论存数据库
     *
     * @param jsonStr
     */
    public void commentTopic(String jsonStr) {
        Comment comment = new Comment();
        comment = JsonUtil.deserialize(jsonStr, Comment.class);
        if (null != comment) {
            try {
                commentService.save(comment);
            } catch (MessageException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 分享文章
     *
     * @param valueStr
     */
    public void artShareCountTopic(String valueStr) {
        articleService.updateArtShare(valueStr);
    }

    /**
     * 文章收藏
     *
     * @param valueStr
     */
    public void artCollectCountTopic(String valueStr) {
        articleService.updateArtCollectionByArtId(Long.parseLong(valueStr));
    }

    /**
     * 文章收藏(映射表)
     *
     * @param valueStr
     */
    public void collectTopic(String valueStr) {
        Collection collection = new Collection();
        collection = JsonUtil.deserialize(valueStr, Collection.class);
        if (null != collection) {
            try {
                collectionService.save(collection);
            } catch (MessageException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文档点赞（文章点赞数加1）
     *
     * @param valueStr
     */
    public void artLikeCountTopic(String valueStr) {
        articleService.updateArtLike(valueStr);
    }

    /**
     * 文档点赞（存用户-点赞表）
     *
     * @param valueStr
     */
    public void userArtLikeTopic(String valueStr) {
        WeixinUserArtLike weixinUserArtLike = new WeixinUserArtLike();
        weixinUserArtLike = JsonUtil.deserialize(valueStr, WeixinUserArtLike.class);
        if (null != weixinUserArtLike) {
            try {
                weixinUserArtLikeService.save(weixinUserArtLike);
            } catch (MessageException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 评论点赞（评论点赞数加1）
     *
     * @param valueStr
     */
    public void commentLikeCountTopic(String valueStr) {
        commentService.updateCommentLike(valueStr);
    }

    /**
     * 评论点赞（用户-评论点赞表加）
     *
     * @param valueStr
     */
    public void userCommentLikeTopic(String valueStr) {
        WeixinUserCommentLike weixinUserCommentLike = new WeixinUserCommentLike();
        weixinUserCommentLike = JsonUtil.deserialize(valueStr, WeixinUserCommentLike.class);
        if (null != weixinUserCommentLike) {
            try {
                weixinUserCommentLikeService.save(weixinUserCommentLike);
            } catch (MessageException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 取消文章点赞
     *
     * @param valueStr
     */
    public void artUnLikeCountTopic(String valueStr) {
        articleService.updateArtUnLike(valueStr);
    }

    /**
     * 取消文章点赞（删除文章-用户表）
     *
     * @param valueStr
     */
    public void userArtUnLikeTopic(String valueStr) {
        // feedId + Constants.KAFKA_FLAG + openId;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String feedId = str[0];
        String openId = str[1];
        weixinUserArtLikeService.deleteByArtIdAndUserId(Long.valueOf(feedId), openId);
    }

    /**
     * 取消评论点赞
     *
     * @param valueStr
     */
    public void commentUnLikeCountTopic(String valueStr) {
        commentService.updateCommentUnLike(valueStr);
    }

    /**
     * 取消评论点赞(用户-评论表)
     *
     * @param valueStr
     */
    public void userCommentUnLikeTopic(String valueStr) {
        //feedId + Constants.KAFKA_FLAG + commentId + Constants.KAFKA_FLAG + openId;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String feedId = str[0];
        String commentId = str[1];
        String openId = str[2];
        weixinUserCommentLikeService.deleteByFeedIdAndCommentId(Long.valueOf(feedId), Long.valueOf(commentId), openId);
    }

    /**
     * 取消文章收藏
     *
     * @param valueStr
     */
    public void artUnCollectCountTopic(String valueStr) {
        articleService.updateArtUnCollectionByArtId(Long.parseLong(valueStr));
    }

    /**
     * 取消文章收藏（删除映射表）
     *
     * @param valueStr
     */
    public void uncollectTopic(String valueStr) {
        // feedId + Constants.KAFKA_FLAG + openId;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String feedId = str[0];
        String openId = str[1];
        collectionService.deleteByArtIdAndOpenId(Long.parseLong(feedId), openId);
    }

    /**
     * 反馈
     *
     * @param valueStr
     */
    public void feedbackTopic(String valueStr) {
        Feedback feedback = new Feedback();
        feedback = JsonUtil.deserialize(valueStr, Feedback.class);
        if (null != feedback) {
            try {
                feedbackService.save(feedback);
            } catch (MessageException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除收藏
     *
     * @param valueStr
     */
    public void deleteCollectTopic(String valueStr) {
        try {
            collectionService.delete(Long.valueOf(valueStr));
        } catch (MessageException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新评论
     *
     * @param valueStr
     */
    public void updateSetCommentTopic(String valueStr) {
        Comment comment = new Comment();
        comment = JsonUtil.deserialize(valueStr, Comment.class);
        if (null != comment) {
            try {
                commentService.update(comment);
            } catch (MessageException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除评论
     *
     * @param valueStr
     */
    public void deleteCommentTopic(String valueStr) {
        try {
            commentService.delete(Long.valueOf(valueStr));
        } catch (MessageException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新主界面redis
     *
     * @param valueStr
     */
    public void updateRedisMainTopic(String valueStr) {
        // mainIndex + Constants.KAFKA_FLAG + detailArtDetail + Constants.KAFKA_FLAG + detailNewComment + Constants.KAFKA_FLAG + detailHotComment + Constants.KAFKA_FLAG + f10ListJson;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String mainIndex = str[0];
        String detailArtDetail = str[1];
        String detailNewComment = str[2];
        String detailHotComment = str[3];
        String f10ListJson = str[4];
        List<F10Bo> f10BoList = JsonUtil.deserializeList(f10ListJson, F10Bo.class);

        //开发时这个可以删除，方便调试redis用，上线删除即可
        redisService.delKeys(detailNewComment);
        redisService.delKeys(detailHotComment);
        //redisService.delKeys(mainIndex);
        redisService.delKeys(detailArtDetail);
        redisService.delKeys("collectArt");
        redisService.delKeys("likeArt");
        redisService.delKeys("likeComment");
        redisService.delKeys("art_map");

        if (null != f10BoList && f10BoList.size() > 0) {
            for (com.yeting.web.bo.f10.F10Bo f10Bo : f10BoList) {
                String serializeJson = JsonUtil.serialize(f10Bo);
                redisService.lrSet(mainIndex, serializeJson);
            }
        }

    }

    /**
     * 更新详情界面redis
     *
     * @param valueStr
     */
    public void updateRedisDetailTopic(String valueStr) {
        // detailRedisKey + Constants.KAFKA_FLAG + serializeJson;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String detailRedisKey = str[0];
        String serializeJson = str[1];
        redisService.set(detailRedisKey, serializeJson);
    }

    /**
     * 更新主界面文章浏览次数redis
     *
     * @param valueStr
     */
    public void updateRedisMainReadTopic(String valueStr) {
        // mainIndex + Constants.KAFKA_FLAG + mainPageSize + Constants.KAFKA_FLAG + artId;
        //String redisKey = mainIndex + Constants.KAFKA_FLAG + detailArtDetail + Constants.KAFKA_FLAG +
        // detailNewComment + Constants.KAFKA_FLAG + detailHotComment + Constants.KAFKA_FLAG + f10ListJson;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String mainIndex = str[0];
        Integer mainPageSize = Integer.valueOf(str[1]);
        Long artId = Long.valueOf(str[2]);
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
    }

    /**
     * 更新最新评论redis
     *
     * @param valueStr
     */
    public void updateRedisCommentTopic(String valueStr) {
        // commentJson = commentRedisKey + Constants.KAFKA_FLAG + serializeJson;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String commentRedisKey = str[0];
        String serializeJson = str[1];
        redisService.lrSet(commentRedisKey, serializeJson);
    }

    /**
     * 更新主界面浏览次数
     *
     * @param valueStr
     */
    public void updateMainReadCountTopic(String valueStr) {
        articleExtMapper.updateViewNumByArtId(Long.valueOf(valueStr));
    }

    /**
     * 保存评论到redis
     *
     * @param valueStr
     */
    public void updateRedisSaveCommentTopic(String valueStr) {
        //commentRedisKey + Constants.KAFKA_FLAG + saveCommentJson + Constants.KAFKA_FLAG + pageSize;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String commentRedisKey = str[0];
        String saveCommentJson = str[1];
        Long pageSize = Long.valueOf(str[2]);
        String artMapArtId = str[3];
        String commentId = str[4];

        if (redisService.hasKey(commentRedisKey)) {
            redisService.lSet(commentRedisKey, saveCommentJson);
            //更新hash的下表值，value都加1后，新来的插入0
            redisService.hincr(artMapArtId, commentId, 1D);
            redisService.hset(artMapArtId, commentId, 0);
        } else {
    /*        List<F13Bo> f13BoList = new ArrayList<>();
            F13Bo f13Bo = JsonUtil.deserialize(saveCommentJson, F13Bo.class);
            f13BoList.add(f13Bo);
            redisService.lSet(commentRedisKey, f13BoList);*/
            redisService.lSet(commentRedisKey, saveCommentJson);
            redisService.hset(artMapArtId, commentId, 0);
        }
    }

    /**
     * 更新文章分享到redis
     *
     * @param valueStr
     */
    public void updateRedisArtShareTopic(String valueStr) {
        com.yeting.web.bo.f20.F10Bo f10Bo = new com.yeting.web.bo.f20.F10Bo();
        if (redisService.hasKey(valueStr)) {
            String deserializeJson = (String) redisService.get(valueStr);
            f10Bo = JsonUtil.deserialize(deserializeJson, com.yeting.web.bo.f20.F10Bo.class);
            //修改浏览次数，更新redis
            f10Bo.setShareCount(f10Bo.getShareCount() + 1);
            String serializeJson = JsonUtil.serialize(f10Bo);
            redisService.set(valueStr, serializeJson);
        }
    }

    /**
     * 更新文章收藏到redis
     *
     * @param valueStr
     */
    public void updateRedisArtCollectTopic(String valueStr) {
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String detailRedisKey = str[0];
        String collectArtArtidUserid = str[1];
        com.yeting.web.bo.f20.F10Bo f10Bo = new com.yeting.web.bo.f20.F10Bo();
        if (redisService.hasKey(detailRedisKey)) {
            String deserializeJson = (String) redisService.get(detailRedisKey);
            f10Bo = JsonUtil.deserialize(deserializeJson, com.yeting.web.bo.f20.F10Bo.class);
            //修改浏览次数，更新redis
            f10Bo.setCollectCount(f10Bo.getCollectCount() + 1);
            f10Bo.setCollection(true);
            String serializeJson = JsonUtil.serialize(f10Bo);
            redisService.set(detailRedisKey, serializeJson);
        }
        //插入用户收藏
        redisService.set(collectArtArtidUserid, 1);
    }

    /**
     * 更新文章点赞到redis
     *
     * @param valueStr
     */
    public void updateRedisArtLikeTopic(String valueStr) {
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String detailRedisKey = str[0];
        String likeArtArtidUserid = str[1];
        com.yeting.web.bo.f20.F10Bo f10Bo = new com.yeting.web.bo.f20.F10Bo();
        if (redisService.hasKey(detailRedisKey)) {
            String deserializeJson = (String) redisService.get(detailRedisKey);
            f10Bo = JsonUtil.deserialize(deserializeJson, com.yeting.web.bo.f20.F10Bo.class);
            //修改浏览次数，更新redis
            f10Bo.setLikeCount(f10Bo.getLikeCount() + 1);
            String serializeJson = JsonUtil.serialize(f10Bo);
            redisService.set(detailRedisKey, serializeJson);
        }
        redisService.set(likeArtArtidUserid, 1);
    }

    /**
     * 更新主界面点赞到redis
     *
     * @param valueStr
     */
    public void updateRedisMainLikeTopic(String valueStr) {
        // mainIndex + Constants.KAFKA_FLAG + mainPageSize + Constants.KAFKA_FLAG + artId;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String mainIndex = str[0];
        Integer mainPageSize = Integer.valueOf(str[1]);
        Long feedId = Long.valueOf(str[2]);
        if (redisService.hasKey(mainIndex)) {
            List<String> list = new ArrayList<>();
            int index = -1;
            String serializeJson = "";
            Long size = redisService.lGetListSize(mainIndex);
            list = (List) redisService.lrange(mainIndex, 0, size);
            for (int i = 0; i < list.size(); i++) {
                com.yeting.web.bo.f10.F10Bo f10Bo1 = new com.yeting.web.bo.f10.F10Bo();
                f10Bo1 = JsonUtil.deserialize(list.get(i), com.yeting.web.bo.f10.F10Bo.class);
                if (null != f10Bo1 && Long.valueOf(feedId).equals(f10Bo1.getArtId())) {
                    index = i;
                    f10Bo1.setPraiseNum(f10Bo1.getPraiseNum() + 1);
                    serializeJson = JsonUtil.serialize(f10Bo1);
                    break;
                }
            }
            if (!StringUtil.isEmpty(serializeJson)) {
                redisService.lUpdateIndex(mainIndex, index, serializeJson);
            }
        }
    }

    /**
     * 更新评论点赞到redis
     *
     * @param valueStr
     */
    public void updateRedisCommentLikeTopic(String valueStr) {
        // commentRedisKey + Constants.KAFKA_FLAG + commentId + Constants.KAFKA_FLAG + mainPageSize + detailHotComment + artMapArtId;
        //  String json = commentRedisKey + Constants.KAFKA_FLAG + commentId + Constants.KAFKA_FLAG + mainPageSize + Constants.KAFKA_FLAG
        // + detailHotComment1 + Constants.KAFKA_FLAG + artMapArtId + Constants.KAFKA_FLAG + likeCommentArtidCommentIdUserid1;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String commentRedisKey = str[0];
        String commentId = str[1];
        Integer mainPageSize = Integer.valueOf(str[2]);
        String detailHotComment = str[3];
        String artMapArtId = str[4];
        String likeCommentArtidCommentIdUserid1 = str[5];
        if (redisService.hasKey(commentRedisKey)) {
            List<String> list = new ArrayList<>();
            int index = -1;
            String serializeJson = "";
            Long size = redisService.lGetListSize(commentRedisKey);
            list = (List) redisService.lrange(commentRedisKey, 0, size);
            for (int i = 0; i < list.size(); i++) {
                F13Bo f13Bo = new F13Bo();
                f13Bo = JsonUtil.deserialize(list.get(i), F13Bo.class);
                if (Long.valueOf(commentId).equals(f13Bo.getCommentId())) {
                    index = i;
                    f13Bo.setLike(true);
                    f13Bo.setLikeCount(f13Bo.getLikeCount() + 1);
                    serializeJson = JsonUtil.serialize(f13Bo);
                    break;
                }
            }
            if (!StringUtil.isEmpty(serializeJson)) {
                redisService.lUpdateIndex(commentRedisKey, index, serializeJson);
            }

            //更新zset
            if (redisService.hasKey(detailHotComment)){
                redisService.szInc(detailHotComment, commentId, 1D);
            }else {
                redisService.szSet(detailHotComment, commentId, 1D);
            }
        }
        redisService.set(likeCommentArtidCommentIdUserid1, 1);


    }

    /**
     * 更新文章取消点赞到redis
     *
     * @param valueStr
     */
    public void updateRedisArtUnLikeTopic(String valueStr) {
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String detailRedisKey = str[0];
        String likeArtArtidUserid = str[1];
        com.yeting.web.bo.f20.F10Bo f10Bo = new com.yeting.web.bo.f20.F10Bo();
        if (redisService.hasKey(detailRedisKey)) {
            String deserializeJson = (String) redisService.get(detailRedisKey);
            f10Bo = JsonUtil.deserialize(deserializeJson, com.yeting.web.bo.f20.F10Bo.class);
            //修改次数，更新redis
            f10Bo.setLikeCount(f10Bo.getLikeCount() > 0 ? f10Bo.getLikeCount() - 1 : 0);
            String serializeJson = JsonUtil.serialize(f10Bo);
            redisService.set(detailRedisKey, serializeJson);
        }
        redisService.del(likeArtArtidUserid);
    }

    /**
     * 更新主界面取消点赞到redis
     *
     * @param valueStr
     */
    public void updateRedisMainUnLikeTopic(String valueStr) {
        // mainIndex + Constants.KAFKA_FLAG + mainPageSize + Constants.KAFKA_FLAG + artId;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String mainIndex = str[0];
        Integer mainPageSize = Integer.valueOf(str[1]);
        Long feedId = Long.valueOf(str[2]);
        if (redisService.hasKey(mainIndex)) {
            List<String> list = new ArrayList<>();
            int index = -1;
            String serializeJson = "";
            Long size = redisService.lGetListSize(mainIndex);
            list = (List) redisService.lrange(mainIndex, 0, size);
            for (int i = 0; i < list.size(); i++) {
                com.yeting.web.bo.f10.F10Bo f10Bo1 = new com.yeting.web.bo.f10.F10Bo();
                f10Bo1 = JsonUtil.deserialize(list.get(i), com.yeting.web.bo.f10.F10Bo.class);
                if (null != f10Bo1 && Long.valueOf(feedId).equals(f10Bo1.getArtId())) {
                    index = i;
                    f10Bo1.setPraiseNum(f10Bo1.getPraiseNum() > 1 ? f10Bo1.getPraiseNum() - 1 : 0);
                    serializeJson = JsonUtil.serialize(f10Bo1);
                    break;
                }
            }
            if (!StringUtil.isEmpty(serializeJson)) {
                redisService.lUpdateIndex(mainIndex, index, serializeJson);
            }
        }
    }

    /**
     * 更新评论取消点赞到redis
     *
     * @param valueStr
     */
    public void updateRedisCommentUnLikeTopic(String valueStr) {
        // commentRedisKey + Constants.KAFKA_FLAG + commentId + Constants.KAFKA_FLAG + mainPageSize;
        //String json = commentRedisKey + Constants.KAFKA_FLAG + commentId + Constants.KAFKA_FLAG +
        // mainPageSize + Constants.KAFKA_FLAG + likeCommentArtidCommentIdUserid1;
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String commentRedisKey = str[0];
        String commentId = str[1];
        Integer mainPageSize = Integer.valueOf(str[2]);
        String likeCommentArtidCommentIdUserid1 = str[3];
        String detailHotComment1 = str[4];
        if (redisService.hasKey(commentRedisKey)) {
            List<String> list = new ArrayList<>();
            int index = -1;
            String serializeJson = "";
            Long size = redisService.lGetListSize(commentRedisKey);
            list = (List) redisService.lrange(commentRedisKey, 0, size);
            //list = (List) redisService.lrange(commentRedisKey, 0, mainPageSize.longValue());
            for (int i = 0; i < list.size(); i++) {
                F13Bo f13Bo = new F13Bo();
                f13Bo = JsonUtil.deserialize(list.get(i), F13Bo.class);
                if (Long.valueOf(commentId).equals(f13Bo.getCommentId())) {
                    index = i;
                    f13Bo.setLike(false);
                    f13Bo.setLikeCount(f13Bo.getLikeCount() > 0 ? f13Bo.getLikeCount() - 1 : 0);
                    serializeJson = JsonUtil.serialize(f13Bo);
                    break;
                }
            }
            if (!StringUtil.isEmpty(serializeJson)) {
                redisService.lUpdateIndex(commentRedisKey, index, serializeJson);
            }
        }
        //更新zset
        if (redisService.hasKey(detailHotComment1)){
            Double d = redisService.szGet(detailHotComment1, commentId);
            if (d < 2){
                redisService.szRemove(detailHotComment1, commentId);
            }else {
                redisService.szInc(detailHotComment1, commentId, -1D);
            }
        }
        redisService.del(likeCommentArtidCommentIdUserid1);
    }

    /**
     * 更新文章取消收藏到redis
     *
     * @param valueStr
     */
    public void updateRedisArtUnCollectTopic(String valueStr) {
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String detailRedisKey = str[0];
        String collectArtArtidUserid = str[1];
        com.yeting.web.bo.f20.F10Bo f10Bo = new com.yeting.web.bo.f20.F10Bo();
        if (redisService.hasKey(detailRedisKey)) {
            String deserializeJson = (String) redisService.get(detailRedisKey);
            f10Bo = JsonUtil.deserialize(deserializeJson, com.yeting.web.bo.f20.F10Bo.class);
            //修改浏览次数，更新redis
            f10Bo.setCollectCount(f10Bo.getCollectCount() > 0 ? f10Bo.getCollectCount() - 1 : 0);
            f10Bo.setCollection(false);
            String serializeJson = JsonUtil.serialize(f10Bo);
            redisService.set(detailRedisKey, serializeJson);
        }
        redisService.del(collectArtArtidUserid);
    }

    /**
     * 删除评论redis
     *
     * @param valueStr
     */
    public void updateRedisCommentDeleteTopic(String valueStr) {
        String[] str = valueStr.split(Constants.KAFKA_FLAG);
        String newCommentRedisKey = str[0];
        String hotCommentRedisKey = str[1];
        String commentId = str[2];

        if (redisService.hasKey(newCommentRedisKey)) {
            List<String> list = new ArrayList<>();
            Long size = redisService.lGetListSize(newCommentRedisKey);
            list = (List) redisService.lrange(newCommentRedisKey, 0, size);
            for (int i = 0; i < list.size(); i++) {
                F13Bo f13Bo = new F13Bo();
                f13Bo = JsonUtil.deserialize(list.get(i), F13Bo.class);
                if (Long.valueOf(commentId).equals(f13Bo.getCommentId())) {
                    redisService.lRemove(newCommentRedisKey, 1, list.get(i));
                    break;
                }
            }
        }

        if (redisService.hasKey(hotCommentRedisKey)) {
            List<String> list = new ArrayList<>();
            Long size = redisService.szRemove(hotCommentRedisKey, commentId);
        }

       // redisService.del(newCommentRedisKey);
       // redisService.del(hotCommentRedisKey);
    }

    /**
     * 增加用户
     *
     * @param valueStr
     */
    public void saveWeixinTopic(String valueStr) {
        WeixinUser weixinUser = JsonUtil.deserialize(valueStr, WeixinUser.class);
        try {
            weixinUserService.save(weixinUser);
        } catch (MessageException e) {
            e.printStackTrace();
        }
    }
}
