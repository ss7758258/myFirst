package com.yeting.web.service.ext;

import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.weixin.Weixin;
import com.yeting.framework.exception.MessageException;

public interface F40Service {

    YTResponseBody<Boolean> updateFeedBack(String content, Weixin weixin, String telphone) throws MessageException;
}
