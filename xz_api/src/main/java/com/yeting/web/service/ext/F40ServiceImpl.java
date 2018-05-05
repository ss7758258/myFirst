package com.yeting.web.service.ext;

import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.enums.AjaxStatus;
import com.yeting.framework.bean.weixin.Weixin;
import com.yeting.framework.common.Constants;
import com.yeting.framework.exception.MessageException;
import com.yeting.framework.utils.DateUtil;
import com.yeting.framework.utils.JsonUtil;
import com.yeting.kafka.producer.impl.KafkaProducerImpl;
import com.yeting.web.mapper.entity.Feedback;
import com.yeting.web.service.FeedbackService;
import com.yeting.web.service.SysSeqUidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class F40ServiceImpl implements F40Service {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    private SysSeqUidService sysSeqUidService;
    @Autowired
    private KafkaProducerImpl kafkaProducerImpl;

    @Override
    public YTResponseBody<Boolean> updateFeedBack(String content, Weixin weixin, String telphone) throws MessageException {
        YTResponseBody<Boolean> response = new YTResponseBody<Boolean>();
        Feedback fb = new Feedback();
        fb.setId(sysSeqUidService.getSQId(Constants.SQ_FEEDBACK_ID));
        fb.setContent(content);
        fb.setCreateTime(DateUtil.getDatetime());
        fb.setCreateOpenId(weixin.getOpenId());
        fb.setUpdatedTime(DateUtil.getDatetime());
        fb.setUpdatedOpenId(weixin.getOpenId());
        fb.setName(weixin.getCommentUserName());
        fb.setHeadImage(weixin.getCommentUserAvatar());
        fb.setWeixinName(weixin.getCommentUserName());
        fb.setPhone(telphone);

        String weixinJson = JsonUtil.serialize(fb);
        kafkaProducerImpl.sendAsync(Constants.FEEDBACK_TOPIC,Constants.INSERT,weixinJson);
        response.setStatus(AjaxStatus.SUCCESS);

        return response;
    }
}
