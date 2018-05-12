package com.xz.web.service.ext;

import com.xz.framework.bean.ajax.XZResponseBody;
import com.xz.framework.bean.weixin.Weixin;
import com.xz.web.bo.selectConstellation.X100Bo;
import com.xz.web.mapper.ext.SelectConstellationMapperExt;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexConstellationServiceImpl implements IndexConstellationService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SelectConstellationMapperExt selectConstellationMapperExt;

    /**
     * 返回首页（不用传参的，用户之前已经选择过星座）
     * @param weixin
     * @return
     */
    @Override
    public XZResponseBody<X100Bo> selectMyConstellation(Weixin weixin) throws Exception {
        //参考selectConstellation
        return null;
    }
}
