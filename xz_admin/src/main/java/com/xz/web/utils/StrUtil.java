package com.xz.web.utils;

import com.xz.framework.common.Constants;
import com.xz.framework.utils.StringUtil;

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
