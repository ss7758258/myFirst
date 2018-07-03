package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.web.bo.moreConstellation.X300Bo;

public interface MoreConstellationService {

    XZResponseBody<X300Bo> selectMoreConstellation(Long constellationId, Weixin weixin) throws Exception;
}
