package com.xz.web.service.ext;

import com.github.pagehelper.PageInfo;
import com.xz.framework.bean.ajax.YTResponseBody;
import com.xz.web.bo.f10.F10Bo;

public interface F10Service {

    YTResponseBody<PageInfo<F10Bo>> selectMain(Integer startPage, Integer pageSize);
}
