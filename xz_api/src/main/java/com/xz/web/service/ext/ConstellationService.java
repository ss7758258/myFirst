package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.YTResponseBody;
import com.xz.framework.bean.ajax.YTResponseBody;
import com.xz.framework.exception.MessageException;
import com.xz.web.vo.LoginVo;

public interface ConstellationService {

    YTResponseBody<LoginVo> login(String code) throws MessageException;
}
