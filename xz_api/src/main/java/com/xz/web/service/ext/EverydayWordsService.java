package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.web.bo.everydayWords.X400Bo;

public interface EverydayWordsService {

    XZResponseBody<X400Bo> selectEverydayWords(Weixin weixin);

    XZResponseBody<String> saveEverydayWords();
}
