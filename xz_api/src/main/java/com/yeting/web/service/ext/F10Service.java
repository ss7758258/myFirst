package com.yeting.web.service.ext;

import com.github.pagehelper.PageInfo;
import com.yeting.framework.bean.ajax.YTResponseBody;
import com.yeting.web.bo.f10.F10Bo;

public interface F10Service {

    YTResponseBody<PageInfo<F10Bo>> selectMain(Integer startPage, Integer pageSize);
}
