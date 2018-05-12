package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.web.bo.selectConstellation.X100Bo;
import com.xz.web.vo.selectConstellation.X100Vo;

public interface SelectConstellationService {

    XZResponseBody<X100Bo> saveConstellation(X100Vo constellationId, Weixin weixin) throws Exception;
}
