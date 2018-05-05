package com.yeting.web.utils;

import com.yeting.framework.common.Constants;
import com.yeting.framework.utils.StringUtil;

public class StrUtil {

    public static String urlToHttps(String backgroundUrl) {
        if(StringUtil.isEmpty(backgroundUrl)){
            return "";
        }
        if(!backgroundUrl.startsWith("http")){
            backgroundUrl = Constants.SCHEME + backgroundUrl;
        }
        return backgroundUrl;
    }

}
