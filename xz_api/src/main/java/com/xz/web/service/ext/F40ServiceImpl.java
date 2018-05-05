package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.YTResponseBody;
import com.xz.framework.bean.enums.AjaxStatus;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.common.Constants;
import com.xz.framework.exception.MessageException;
import com.xz.framework.utils.DateUtil;
import com.xz.framework.utils.JsonUtil;
import com.xz.kafka.producer.impl.KafkaProducerImpl;
import com.xz.web.mapper.entity.Feedback;
import com.xz.web.service.FeedbackService;
import com.xz.web.service.SysSeqUidService;
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
