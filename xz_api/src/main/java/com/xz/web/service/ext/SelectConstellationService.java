package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.web.bo.selectConstellation.X100Bo;

public interface SelectConstellationService {

    XZResponseBody<X100Bo> saveConstellation(String constellationId, Weixin weixin) throws Exception;
}
