package com.yeting.web.service.ext;

import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.framework.exception.MessageException;
import com.yeting.web.vo.LoginVo;

public interface F00Service {

    YTResponseBody<LoginVo> login(String code) throws MessageException;
}
