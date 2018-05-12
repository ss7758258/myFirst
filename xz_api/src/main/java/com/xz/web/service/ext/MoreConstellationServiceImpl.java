package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.web.bo.moreConstellation.X300Bo;
import com.xz.web.mapper.ext.MoreConstellationMapperExt;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoreConstellationServiceImpl implements MoreConstellationService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MoreConstellationMapperExt moreConstellationMapperExt;

    /**
     * 返回星座运势（更多）
     * @return
     */
    @Override
    public XZResponseBody<X300Bo> selectMoreConstellation() throws Exception {
        /**
         * 接口返回运势指数；
         * 去做内容（标题及内容）；
         * 别做内容（标题及内容）；
         */
        return null;
    }
}
