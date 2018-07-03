package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.web.bo.selectConstellation.X100Bo;

public interface IndexConstellationService {

    XZResponseBody<X100Bo> selectMyConstellation(Weixin weixin) throws Exception;
}
