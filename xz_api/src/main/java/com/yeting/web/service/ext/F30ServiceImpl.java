package com.yeting.web.service.ext;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.enums.AjaxStatus;
import com.yeting.framework.bean.weixin.Weixin;
import com.yeting.framework.common.Constants;
import com.yeting.framework.exception.MessageException;
import com.yeting.framework.utils.DateUtil;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.kafka.producer.impl.KafkaProducerImpl;
import com.yeting.web.bo.f30.F20Bo;
import com.yeting.web.bo.f30.F40Bo;
import com.yeting.web.mapper.entity.Comment;
import com.yeting.web.mapper.ext.ArticleExtMapper;
import com.yeting.web.service.CollectionService;
import com.yeting.web.service.CommentService;
import com.yeting.web.service.MyCollectionViewService;
import com.yeting.web.service.redis.RedisService;
import com.yeting.web.vo.PagerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class F30ServiceImpl implements F30Service {

    @Autowired
    private MyCollectionViewService myCollectionViewService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleExtMapper articleExtMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private KafkaProducerImpl kafkaProducerImpl;


    @Value("#{constants.detail_artDetail}")
    private String detailArtDetail;
    @Value("#{constants.detail_newComment}")
    private String detailNewComment;
    @Value("#{constants.detail_hotComment}")
    private String detailHotComment;
    @Value("#{constants.collectArt_artid_userid}")
    private String collectArtArtidUserid;

    @Override
    public YTResponseBody<Boolean> getMyInfo(Weixin weixin) throws MessageException {
        return null;
    }

    @Override
    public YTResponseBody<PageInfo<F20Bo>> getMyCollection(Weixin weixin, PagerVo pagerVo, Integer pageSize) throws MessageException {
        YTResponseBody<PageInfo<F20Bo>> response = new YTResponseBody<PageInfo<F20Bo>>();
        PageInfo<F20Bo> pager = new PageInfo<F20Bo>();
        pager = myCollectionViewService.selectMyCollectionByUserId(weixin, pager, Integer.valueOf(pagerVo.getStartPage()), pageSize);
        response.setData(pager);
        response.setStatus(AjaxStatus.SUCCESS);
        return response;
    }

    @Override
    public YTResponseBody<Boolean> deleteCollection(Long id, Long feedId, String openId) throws MessageException {
        YTResponseBody<Boolean> response = new YTResponseBody<Boolean>();

        kafkaProducerImpl.sendAsync(Constants.DELETE_COLLECT_TOPIC,Constants.DELETE,id.toString());
        kafkaProducerImpl.sendAsync(Constants.ART_UNCOLLECT_COUNT_TOPIC,Constants.UPDATE,feedId.toString());

        String detailRedisKey = detailArtDetail + "_" + feedId;
        String collectArtArtidUserid1 = collectArtArtidUserid + "_" + feedId + "_" + openId;
        String json = detailRedisKey + Constants.KAFKA_FLAG + collectArtArtidUserid1;
        kafkaProducerImpl.sendAsync(Constants.REDIS_ARTUNCOLLECT_TOPIC,Constants.UPDATE,json);

        response.setStatus(AjaxStatus.SUCCESS);
        return response;
    }

    @Override
    public YTResponseBody<PageInfo<F40Bo>> getMyComments(Weixin weixin, PagerVo obj, Integer pageSize) throws MessageException {
        YTResponseBody<PageInfo<F40Bo>> response = new YTResponseBody<PageInfo<F40Bo>>();
        PageInfo<F40Bo> pager = new PageInfo<F40Bo>();
        pager.setPageNum(Integer.valueOf(obj.getStartPage()));
        pager = commentService.selectCommentListByOpenId(pager, weixin.getOpenId(), Integer.valueOf(obj.getStartPage()), pageSize);
        response.setData(pager);
        response.setStatus(AjaxStatus.SUCCESS);
        return response;
    }

    @Override
    public YTResponseBody<Boolean> setComment(Weixin weixin, Long id) throws MessageException {
        YTResponseBody<Boolean> response = new YTResponseBody<Boolean>();
        Comment comment = commentService.selectByKey(id);
        if(null==comment)
        {
            response.setStatus(AjaxStatus.ERROR);
            response.setMessage("失败!");
            return response;
        }else
        {
            comment.setAnonymous(1);
            comment.setUpdatedTime(DateUtil.getDatetime());

            String userCommentLikeJson = JsonUtil.serialize(comment);
            kafkaProducerImpl.sendAsync(Constants.SETCOMMENT_TOPIC,Constants.UPDATE,userCommentLikeJson);
            response.setStatus(AjaxStatus.SUCCESS);
            return response;
        }
    }

    @Override
    public YTResponseBody<Boolean> setUnComment(Weixin weixin, Long id) throws MessageException {
        YTResponseBody<Boolean> response = new YTResponseBody<Boolean>();
        Comment comment = commentService.selectByKey(id);
        if(null==comment)
        {
            response.setStatus(AjaxStatus.ERROR);
            response.setMessage("取消收藏失败!");
            return response;
        }else
        {
            comment.setAnonymous(0);
            comment.setUpdatedTime(DateUtil.getDatetime());

            String userCommentLikeJson = JsonUtil.serialize(comment);
            kafkaProducerImpl.sendAsync(Constants.SETCOMMENT_TOPIC,Constants.UPDATE,userCommentLikeJson);
            response.setStatus(AjaxStatus.SUCCESS);

            return response;
        }
    }

    @Override
    public YTResponseBody<Boolean> deleteComment(Weixin weixin, Long id, Long feedId) throws MessageException {
        YTResponseBody<Boolean> response = new YTResponseBody<Boolean>();
        kafkaProducerImpl.sendAsync(Constants.DELETE_COMMENT_TOPIC,Constants.DELETE,id.toString());
        //删除redis的数据
        String commentRedisKey = detailNewComment + "_" + feedId;
        String hotCommentRedisKey = detailHotComment + "_" + feedId;
        String json = commentRedisKey + Constants.KAFKA_FLAG + hotCommentRedisKey + Constants.KAFKA_FLAG + id;
        if (redisService.hasKey(commentRedisKey)) {
            kafkaProducerImpl.sendAsync(Constants.REDIS_COMMENTDELETE_TOPIC,Constants.DELETE,json);
        }
        response.setStatus(AjaxStatus.SUCCESS);

        return response;
    }
}
