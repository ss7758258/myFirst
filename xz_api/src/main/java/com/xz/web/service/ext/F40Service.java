package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.YTResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.framework.exception.MessageException;

public interface F40Service {

    YTResponseBody<Boolean> updateFeedBack(String content, Weixin weixin, String telphone) throws MessageException;
}
