package com.yeting.web.service.ext;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.enums.AjaxStatus;
import com.yeting.framework.bean.weixin.Weixin;
import com.yeting.framework.common.Constants;
import com.yeting.framework.common.base.BeanCriteria;
import com.yeting.framework.exception.MessageException;
import com.yeting.framework.utils.DateUtil;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.framework.utils.StringUtil;
import com.yeting.kafka.producer.impl.KafkaProducerImpl;
import com.yeting.web.bo.f20.*;
import com.yeting.web.mapper.entity.*;
import com.yeting.web.service.*;
import com.yeting.web.service.redis.RedisService;
import com.yeting.web.utils.StrUtil;
import com.yeting.web.utils.WechatUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class F20ServiceImpl implements F20Service {

    private static final Logger logger = Logger.getLogger(F20ServiceImpl.class);

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private WeixinUserService weixinUserService;
    @Autowired
    private SysSeqUidService sysSeqUidService;
    @Autowired
    private WeixinUserArtLikeService weixinUserArtLikeService;
    @Autowired
    private WeixinUserCommentLikeService weixinUserCommentLikeService;
    @Autowired
    private RedisService redisService;

    @Autowired
    private KafkaProducerImpl kafkaProducerImpl;


    @Value("#{constants.detail_newComment}")
    private String detailNewComment;
    @Value("#{constants.detail_pageSize}")
    private Integer pageSize;
    @Value("#{constants.detail_artDetail}")
    private String detailArtDetail;
    @Value("#{constants.main_index}")
    private String mainIndex;
    @Value("#{constants.main_pageSize}")
    private Integer mainPageSize;
    @Value("#{constants.art_map_artId}")
    private String artMapArtId;
    @Value("#{constants.detail_hotComment}")
    private String detailHotComment;

    @Value("#{constants.likeArt_artid_userid}")
    private String likeArtArtidUserid;
    @Value("#{constants.collectArt_artid_userid}")
    private String collectArtArtidUserid;
    @Value("#{constants.likeComment_artid_commentId_userid}")
    private String likeCommentArtidCommentIdUserid;


    @Override
    public YTResponseBody<F10Bo> selectDetailByArtId(Long artId, Integer pageSize, Weixin weixin, Integer hotCommentPageSize, Integer newCommentPageSize) {
        YTResponseBody<F10Bo> responseBody = new YTResponseBody<F10Bo>();
        F10Bo f10Bo = articleService.selectDetailByArtId(artId, pageSize, weixin.getOpenId(), hotCommentPageSize, newCommentPageSize);
        if (null != f10Bo) {
            String musicUrl = StrUtil.urlToHttps(f10Bo.getMusicUrl());
            String backgroundUrl = StrUtil.urlToHttps(f10Bo.getBackgroundUrl());
            String commentUrl = StrUtil.urlToHttps(f10Bo.getCommentUrl());
            String preview = StrUtil.urlToHttps(f10Bo.getPreview());
            f10Bo.setMusicUrl(musicUrl);
            f10Bo.setBackgroundUrl(backgroundUrl);
            f10Bo.setCommentUrl(commentUrl);
            f10Bo.setPreview(preview);
        }
        responseBody.setStatus(AjaxStatus.SUCCESS);
        responseBody.setData(f10Bo);
        return responseBody;
    }

    @Override
    public YTResponseBody<F20Bo> saveComment(String feedId, String content, Boolean isAnonymity, Weixin weixin) throws MessageException {
        YTResponseBody<F20Bo> response = new YTResponseBody<F20Bo>();
        Comment comment = new Comment();
        F20Bo f20Bo = new F20Bo();
        //填充用户信息
        BeanCriteria beanCriteria = new BeanCriteria(WeixinUser.class);
        BeanCriteria.Criteria criteria = beanCriteria.createCriteria();
        criteria.andEqualTo("openId", weixin.getOpenId());
        List<WeixinUser> userList = weixinUserService.selectByExample(beanCriteria);
        String userhead = weixin.getCommentUserAvatar();
        String userName = weixin.getCommentUserName();
        if (null != userList && userList.size() > 0) {
            WeixinUser user = userList.get(0);
            if (null != user) {
                if (weixin.getCommentUserAvatar().equals(user.getHeadImage()) && weixin.getCommentUserName().equals(user.getNickName())) {
                    ;//不用更新
                } else {
                    user.setHeadImage(weixin.getCommentUserAvatar());
                    user.setNickName(weixin.getCommentUserName());
                    user.setUpdateTime(DateUtil.getDatetime());
                    weixinUserService.update(user);
                   // String weixinJson = JsonUtil.serialize(user);
                   // kafkaProducerImpl.sendAsync(Constants.WEINXIN_TOPIC, Constants.UPDATE, weixinJson);
                }
                comment.setCreateOpenId(weixin.getOpenId());
                comment.setUpdatedOpenId(weixin.getOpenId());
            }else {
                WeixinUser user2 = new WeixinUser();
                user2.setUserId(sysSeqUidService.getSQId(Constants.SQ_WEIXINUSER_ID));
                user2.setHeadImage(weixin.getCommentUserAvatar());
                user2.setNickName(weixin.getCommentUserName());
                user2.setCreateTime(DateUtil.getDatetime());
                user2.setUpdateTime(DateUtil.getDatetime());
                user2.setOpenId(weixin.getOpenId());
                weixinUserService.save(user);
                //String weixinJson = JsonUtil.serialize(user2);
                //kafkaProducerImpl.sendAsync(Constants.WEINXIN_TOPIC,Constants.INSERT,weixinJson);
            }
        }else
        {
            WeixinUser user = new WeixinUser();
            user.setUserId(sysSeqUidService.getSQId(Constants.SQ_WEIXINUSER_ID));
            user.setHeadImage(weixin.getCommentUserAvatar());
            user.setNickName(weixin.getCommentUserName());
            user.setCreateTime(DateUtil.getDatetime());
            user.setUpdateTime(DateUtil.getDatetime());
            user.setOpenId(weixin.getOpenId());
            weixinUserService.save(user);
            //String weixinJson = JsonUtil.serialize(user);
           // kafkaProducerImpl.sendAsync(Constants.WEINXIN_TOPIC,Constants.INSERT,weixinJson);
        }
        comment.setName(weixin.getCommentUserName());
        comment.setAvatar(weixin.getCommentUserAvatar());
        //填充评论信息
        Long commentId = sysSeqUidService.getSQId(Constants.SQ_COMMENT_ID);
        String createTime = DateUtil.getDatetime();
        comment.setCommId(commentId);
        comment.setContent(content);
        comment.setPraiseNum(0);
        comment.setAnonymous(isAnonymity == true ? 1 : 0);
        comment.setTop(0);
        comment.setArtId(Long.valueOf(feedId));
        comment.setCreateTime(createTime);
        comment.setUpdatedTime(createTime);
        comment.setCreateOpenId(weixin.getOpenId());
        comment.setUpdatedOpenId(weixin.getOpenId());

        f20Bo.setCommentId(commentId);
        f20Bo.setContent(StringUtil.Base64ToStr(content));
        f20Bo.setFeedId(Long.valueOf(feedId));
        f20Bo.setLikeCount(0);
        //f20Bo.setUserHead(userhead);
        f20Bo.setUserHead(isAnonymity == true ? Constants.ANNO : userhead);
        f20Bo.setUserName(isAnonymity == true ? "匿名" : StringUtil.Base64ToStr(userName));
        f20Bo.setCreateTime(createTime);

        String commentJson = JsonUtil.serialize(comment);
        kafkaProducerImpl.sendAsync(Constants.COMMENT_TOPIC,Constants.INSERT,commentJson);
        response.setStatus(AjaxStatus.SUCCESS);
        response.setData(f20Bo);

        //F13Bo f13Bo = articleService.selectNewCommnetById(Long.valueOf(feedId), commentId, weixin.getOpenId());
        F13Bo f13Bo = new F13Bo();
        f13Bo.setCommentId(commentId);
        f13Bo.setUserName(isAnonymity == true ? "匿名" : StringUtil.Base64ToStr(userName));
        f13Bo.setUserHead(isAnonymity == true ? Constants.ANNO : userhead);
        f13Bo.setContent(StringUtil.Base64ToStr(content));
        f13Bo.setLikeCount(0);
        f13Bo.setCreateTime(createTime);

        if (null != f13Bo) {
            //更新art_list_
            String saveCommentJson = JsonUtil.serialize(f13Bo);
            String commentRedisKey = detailNewComment + "_" + feedId;
            String artMapRedisKey = artMapArtId + "_" + feedId;
            String commentIdJson = f13Bo.getCommentId() + "";

            String json = commentRedisKey + Constants.KAFKA_FLAG + saveCommentJson + Constants.KAFKA_FLAG + pageSize + Constants.KAFKA_FLAG +
                    artMapRedisKey + Constants.KAFKA_FLAG + commentIdJson;

            kafkaProducerImpl.sendAsync(Constants.REDIS_SAVECOMMENT_TOPIC, Constants.UPDATE, json);
        }

        return response;
    }

    @Override
    public YTResponseBody<Boolean> updateArtLike(String feedId, String openId) {
        YTResponseBody<Boolean> response = new YTResponseBody<Boolean>();

        //String artLikeCount = "1";
        kafkaProducerImpl.sendAsync(Constants.ART_LIKE_COUNT_TOPIC,Constants.UPDATE,feedId);

        WeixinUserArtLike userArtLike = new WeixinUserArtLike();
        userArtLike.setArtId(Long.valueOf(feedId));
        userArtLike.setOpenId(openId);
        userArtLike.setUserArtLikeId(sysSeqUidService.getSQId(Constants.SQ_USERARTLIKE_ID));

        String userArtLikeJson = JsonUtil.serialize(userArtLike);
        kafkaProducerImpl.sendAsync(Constants.USER_ART_LIKE_TOPIC,Constants.INSERT,userArtLikeJson);

        String likeArtArtidUserid1 = likeArtArtidUserid + "_" + feedId + "_" + openId;
        String detailRedisKey = detailArtDetail + "_" + feedId;
        String json = detailRedisKey + Constants.KAFKA_FLAG + likeArtArtidUserid1;
        kafkaProducerImpl.sendAsync(Constants.REDIS_ARTLIKE_TOPIC,Constants.UPDATE,json);


        /**
         * 更新主界面的redis的点赞数量
         */
       // json = mainIndex + Constants.KAFKA_FLAG + mainPageSize + Constants.KAFKA_FLAG + feedId;
        //更新到kafka，修改redis
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


        //kafkaProducerImpl.sendAsync(Constants.REDIS_MAIN_LIKE_TOPIC,Constants.UPDATE,json);

        response.setStatus(AjaxStatus.SUCCESS);
        response.setData(true);

        return response;
    }

    @Override
    public YTResponseBody<Boolean> updateCommentLike(String feedId, String commentId, String openId) {
        YTResponseBody<Boolean> response = new YTResponseBody<Boolean>();

        //String commentLikeCount = "1";
        kafkaProducerImpl.sendAsync(Constants.COMMENT_LIKE_COUNT_TOPIC,Constants.UPDATE,commentId);

        WeixinUserCommentLike userCommentLike = new WeixinUserCommentLike();
        userCommentLike.setArtId(Long.valueOf(feedId));
        userCommentLike.setCommentId(Long.valueOf(commentId));
        userCommentLike.setOpenId(openId);
        userCommentLike.setUserCommentLikeId(sysSeqUidService.getSQId(Constants.SQ_USERCOMMENTLIKE_ID));

        String userCommentLikeJson = JsonUtil.serialize(userCommentLike);
        kafkaProducerImpl.sendAsync(Constants.USER_COMMENT_LIKE_TOPIC,Constants.INSERT,userCommentLikeJson);

        String commentRedisKey = detailNewComment + "_" + feedId;
        String detailHotComment1 = detailHotComment + "_" + feedId;
        String likeCommentArtidCommentIdUserid1 = likeCommentArtidCommentIdUserid + "_" + feedId + "_" + commentId + "_" + openId;

        String json = commentRedisKey + Constants.KAFKA_FLAG + commentId + Constants.KAFKA_FLAG + mainPageSize + Constants.KAFKA_FLAG
                + detailHotComment1 + Constants.KAFKA_FLAG + artMapArtId + Constants.KAFKA_FLAG + likeCommentArtidCommentIdUserid1;
        //更新到kafka，修改redis
        kafkaProducerImpl.sendAsync(Constants.REDIS_COMMENT_LIKE_TOPIC,Constants.UPDATE,json);

        response.setStatus(AjaxStatus.SUCCESS);
        response.setMessage("");
        response.setData(true);

        return response;
    }

    @Override
    public YTResponseBody<PageInfo<F70Bo>> selectCommentListByArtId(Long artId, String startPage, Integer pageSize, String openId) {
        YTResponseBody<PageInfo<F70Bo>> responseBody = new YTResponseBody<PageInfo<F70Bo>>();
        PageInfo<F70Bo> page = new PageInfo();
        page = commentService.selectCommentListByArtId(artId, startPage, pageSize, openId);
        responseBody.setStatus(AjaxStatus.SUCCESS);
        responseBody.setData(page);
        return responseBody;
    }

    @Override
    public YTResponseBody<PageInfo<F70Bo>> selectNewCommentListByArtId(Long artId, String startPage, Integer commentPageSize, String openId, Integer newCommentPageSize) {
        YTResponseBody<PageInfo<F70Bo>> responseBody = new YTResponseBody<PageInfo<F70Bo>>();
        PageInfo<F70Bo> page = new PageInfo();
        page = commentService.selectNewCommentListByArtId(artId, startPage, commentPageSize, openId, newCommentPageSize);
        responseBody.setStatus(AjaxStatus.SUCCESS);
        responseBody.setData(page);
        return responseBody;
    }

    @Override
    public YTResponseBody<F40Bo> saveCollect(String feedId, Weixin weixin) throws MessageException {
        YTResponseBody<F40Bo> response = new YTResponseBody<F40Bo>();

        //判断是否收藏
        String collectArtArtidUserid1 = collectArtArtidUserid + "_" + feedId + "_" + weixin.getOpenId();
        //查询该用户是否点赞过该文章
        if (redisService.hasKey(collectArtArtidUserid1)){
            response.setStatus(AjaxStatus.ERROR);
            response.setMessage("您已经收藏了!");
            return response;
        }

        Collection collection = new Collection();
        F40Bo f40Bo = new F40Bo();
        Long collectionId = sysSeqUidService.getSQId(Constants.SQ_COLLECT_ID);
        String createTime = DateUtil.getDatetime();
        collection.setId(collectionId);
        collection.setUpdatedTime(createTime);
        collection.setArtId(Long.parseLong(feedId));
        collection.setCreateTime(createTime);
        collection.setCreateOpenId(weixin.getOpenId());
        collection.setUpdatedOpenId(weixin.getOpenId());
        f40Bo.setCollectionId(collectionId);
        f40Bo.setCollection(true);
        f40Bo.setCreateTime(createTime);

        String collectJson = JsonUtil.serialize(collection);
        kafkaProducerImpl.sendAsync(Constants.COLLECT_TOPIC,Constants.INSERT,collectJson);

        kafkaProducerImpl.sendAsync(Constants.ART_COLLECT_COUNT_TOPIC,Constants.UPDATE,feedId);

        String detailRedisKey = detailArtDetail + "_" + feedId;
        String json = detailRedisKey + Constants.KAFKA_FLAG + collectArtArtidUserid1;
        kafkaProducerImpl.sendAsync(Constants.REDIS_ARTCOLLECT_TOPIC,Constants.UPDATE,json);

        response.setStatus(AjaxStatus.SUCCESS);
        response.setData(f40Bo);
        return response;
    }

    @Override
    public YTResponseBody<Boolean> updateArtShare(String feedId) {
        YTResponseBody<Boolean> response = new YTResponseBody<Boolean>();

        kafkaProducerImpl.sendAsync(Constants.ART_SHARE_COUNT_TOPIC,Constants.UPDATE,feedId);
        String detailRedisKey = detailArtDetail + "_" + feedId;
        kafkaProducerImpl.sendAsync(Constants.REDIS_ARTSHARE_TOPIC,Constants.UPDATE,detailRedisKey);
        response.setStatus(AjaxStatus.SUCCESS);
        return response;
    }

    @Override
    public YTResponseBody<Boolean> updateArtUnLike(String feedId, String openId) {
        YTResponseBody<Boolean> response = new YTResponseBody<Boolean>();

        kafkaProducerImpl.sendAsync(Constants.ART_UNLIKE_COUNT_TOPIC,Constants.UPDATE,feedId);

        String feedIdAndOpenId = feedId + Constants.KAFKA_FLAG + openId;
        kafkaProducerImpl.sendAsync(Constants.USER_ART_UNLIKE_TOPIC,Constants.DELETE,feedIdAndOpenId);
        String detailRedisKey = detailArtDetail + "_" + feedId;
        String likeArtArtidUserid1 = likeArtArtidUserid + "_" + feedId + "_" + openId;
        String json = detailRedisKey + Constants.KAFKA_FLAG + likeArtArtidUserid1;
        kafkaProducerImpl.sendAsync(Constants.REDIS_ARTUNLIKE_TOPIC,Constants.DELETE,json);

        /**
         * 更新主界面的redis的点赞数量
         */
        json = mainIndex + Constants.KAFKA_FLAG + mainPageSize + Constants.KAFKA_FLAG + feedId;
        //更新到kafka，修改redis
        kafkaProducerImpl.sendAsync(Constants.REDIS_MAIN_UNLIKE_TOPIC,Constants.UPDATE,json);

        response.setStatus(AjaxStatus.SUCCESS);
        response.setMessage("");
        response.setData(false);
        return response;
    }

    @Override
    public YTResponseBody<Boolean> updateCommentUnLike(String feedId, String commentId, String openId) {
        YTResponseBody<Boolean> response = new YTResponseBody<Boolean>();

        kafkaProducerImpl.sendAsync(Constants.COMMENT_UNLIKE_COUNT_TOPIC,Constants.UPDATE,commentId);

        String feedIdAndCommentIdAndOpenId = feedId + Constants.KAFKA_FLAG + commentId + Constants.KAFKA_FLAG + openId;
        kafkaProducerImpl.sendAsync(Constants.USER_COMMENT_UNLIKE_TOPIC,Constants.DELETE,feedIdAndCommentIdAndOpenId);

        String likeCommentArtidCommentIdUserid1 = likeCommentArtidCommentIdUserid + "_" + feedId + "_" + commentId + "_" + openId;
        String commentRedisKey = detailNewComment + "_" + feedId;
        String detailHotComment1 = detailHotComment + "_" + feedId;
        String json = commentRedisKey + Constants.KAFKA_FLAG + commentId + Constants.KAFKA_FLAG + mainPageSize + Constants.KAFKA_FLAG +
                likeCommentArtidCommentIdUserid1 + Constants.KAFKA_FLAG + detailHotComment1;
        //更新到kafka，修改redis
        kafkaProducerImpl.sendAsync(Constants.REDIS_COMMENT_UNLIKE_TOPIC,Constants.UPDATE,json);


      //  String[] str = valueStr.split(Constants.KAFKA_FLAG);
       // String commentRedisKey = str[0];
       // String commentId = str[1];
       // Integer mainPageSize = Integer.valueOf(str[2]);
       // String likeCommentArtidCommentIdUserid1 = str[3];
       // String detailHotComment1 = str[4];
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
                redisService.szInc(detailHotComment1, commentId, -1.0);
            }
        }
        redisService.del(likeCommentArtidCommentIdUserid1);

        response.setStatus(AjaxStatus.SUCCESS);
        response.setMessage("");
        response.setData(false);

        return response;
    }

    @Override
    public YTResponseBody<F40Bo> updateUnCollect(String feedId, Weixin weixin) {
        YTResponseBody<F40Bo> response = new YTResponseBody<F40Bo>();

        F40Bo f40Bo = new F40Bo();
        String openId = weixin.getOpenId();
        String createTime = DateUtil.getDatetime();
        f40Bo.setCollection(false);
        f40Bo.setCreateTime(createTime);

        String feedIdAndOpenId = feedId + Constants.KAFKA_FLAG + openId;
        kafkaProducerImpl.sendAsync(Constants.UNCOLLECT_TOPIC,Constants.DELETE,feedIdAndOpenId);
        //String artCollectCount = "-1";
        kafkaProducerImpl.sendAsync(Constants.ART_UNCOLLECT_COUNT_TOPIC,Constants.UPDATE,feedId);

        String collectArtArtidUserid1 = collectArtArtidUserid + "_" + feedId + "_" + weixin.getOpenId();
        String detailRedisKey = detailArtDetail + "_" + feedId;
        String json = detailRedisKey + Constants.KAFKA_FLAG + collectArtArtidUserid1;
        kafkaProducerImpl.sendAsync(Constants.REDIS_ARTUNCOLLECT_TOPIC,Constants.UPDATE,json);

        response.setStatus(AjaxStatus.SUCCESS);
        response.setData(f40Bo);

        return response;
    }
}
