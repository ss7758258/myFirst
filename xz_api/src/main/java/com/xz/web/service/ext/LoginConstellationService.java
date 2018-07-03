package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.web.bo.loginConstellation.X000Bo;

public interface LoginConstellationService {

    XZResponseBody<X000Bo> saveWeixinUser(String code) throws Exception;
}
