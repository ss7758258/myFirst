package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;

public interface StatisticsConstellationService {

    XZResponseBody<String> x600();

    XZResponseBody<String> x601();

    XZResponseBody<String> x602();

    XZResponseBody<String> x603();

    XZResponseBody<String> x604();

    XZResponseBody<String> x605();

    XZResponseBody<String> x606();

    XZResponseBody<String> x607();

    XZResponseBody<String> x610(Weixin weixin, String formid);
}
