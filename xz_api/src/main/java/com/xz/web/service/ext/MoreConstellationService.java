package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.web.bo.moreConstellation.X300Bo;

public interface MoreConstellationService {

    XZResponseBody<X300Bo> selectMoreConstellation() throws Exception;
}
