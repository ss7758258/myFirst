package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.web.bo.everydayQian.X500Bo;

public interface EverydayQianService {

    XZResponseBody<X500Bo> selectEverydayQian();

    XZResponseBody<String> saveEverydayQian();
}
