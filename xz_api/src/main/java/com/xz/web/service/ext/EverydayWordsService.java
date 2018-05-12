package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.web.bo.everydayWords.X400Bo;

public interface EverydayWordsService {

    XZResponseBody<X400Bo> selectEverydayWords();

    XZResponseBody<String> saveEverydayWords();
}
